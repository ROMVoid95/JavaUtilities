package net.rom.utility.system;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Locale;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.rom.utility.system.property.IBMSystemProperties;
import net.rom.utility.system.property.SunSystemProperties;
import net.rom.utility.system.property.SystemProperties;
import net.rom.utility.system.property.type.IntProperty;

@UtilityClass
@Slf4j
public final class SystemPlatform {

	/**
	 * The current {@link OS}.
	 */
	public static final OS CURRENT_OS = getOS();

	/**
	 * The current {@link OS.Version}.
	 */
	public static final OS.Version CURRENT_OS_VERSION = CURRENT_OS.isVersionNumberInOSName() ?
			CURRENT_OS.getVersionByName(SystemProperties.osName.get()) :
			CURRENT_OS.getVersionByVersionNumber(SystemProperties.osVersion.get());

	/**
	 * The current JVM {@link Architecture}.
	 */
	public static final Arch JVM_ARCHITECTURE;

	/**
	 * The JRE directory.
	 */
	public static final Path JRE_DIRECTORY = Preconditions.checkNotNull(
			SystemProperties.javaInstallationDirectory.get(),
			"java.home should not be null"
	);

	/**
	 * The JRE {@code bin} directory.
	 */
	public static final Path JRE_BIN_DIRECTORY = JRE_DIRECTORY.resolve("bin");

	/**
	 * The {@code java} executable.
	 */
	public static final Path JAVA_EXECUTABLE = getJREExecutable("java");

	/**
	 * The {@code javaw} executable.
	 */
	public static final Path JAVAW_EXECUTABLE = getJREExecutable("javaw");

	/**
	 * The current working directory, i.e. the directory in which {@code java} or {@code javaw}
	 * was started.
	 */
	public static final Path CURRENT_WORKING_DIRECTORY =
			SystemProperties.userCurrentWorkingDirectory.get(Paths.get(".").toAbsolutePath());

	static {
		final IntProperty dataModel = SunSystemProperties.architectureDataModel.hasValue() ?
				SunSystemProperties.architectureDataModel : IBMSystemProperties.vmBitMode;

		if (dataModel.valueEquals(32)) {
			JVM_ARCHITECTURE = Arch._32BIT;
		} else if (dataModel.valueEquals(64)) {
			JVM_ARCHITECTURE = Arch._64BIT;
		} else {
			//Fall back to os.arch.
			JVM_ARCHITECTURE = Arch.fromName(SystemProperties.jvmArchitecture.get());
		}
	}

	/**
	 * Returns the JRE executable with the specified file name.
	 *
	 * @param name a JRE executable file name.
	 * @return a {@link Path} that represents the JRE executable with the specified file name.
	 */
	public static Path getJREExecutable(String name) {
		Preconditions.checkNotNull(name, "name should not be null");
		name = name.toLowerCase(Locale.ENGLISH);
		Preconditions.checkArgument(name.matches("[a-z]+"), "name should be alphabetic");
		return JRE_BIN_DIRECTORY.resolve(CURRENT_OS == OS.WINDOWS ? name + ".exe" : name);
	}

	/**
	 * Returns the MAC address in lowercase of the local machine with each group separated by
	 * a colon.
	 *
	 * @return the MAC address in lowercase of the local machine with each group separated by
	 * a colon, or {@code null} if it cannot be retrieved.
	 */
	@Nullable
	public static String getMACAddress() {
		return getMACAddress(':');
	}

	/**
	 * Returns the MAC address in lowercase of the local machine with each group separated by the
	 * specified character.
	 *
	 * @param separator a separator character.
	 * @return the MAC address in lowercase of the local machine with each group separated by the
	 * specified character, or {@code null} if it cannot be retrieved.
	 */
	@Nullable
	public static String getMACAddress(char separator) {
		return getMACAddress(String.valueOf(separator));
	}

	/**
	 * Returns the MAC address in lowercase of the local machine with each group separated by the
	 * specified string.
	 *
	 * @param separator a separator string.
	 * @return the MAC address in lowercase of the local machine with each group separated by the
	 * specified string, or {@code null} if it cannot be retrieved.
	 */
	@Nullable
	public static String getMACAddress(String separator) {
		try {
			final StringBuilder macAddress = new StringBuilder();

			final InetAddress localHost = InetAddress.getLocalHost();
			final NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);

			if (networkInterface == null) {
				log.warn("Failed to retrieve MAC address of local machine");
				return null;
			}

			for (byte b : networkInterface.getHardwareAddress()) {
				//Format each byte as a hexadecimal number.
				macAddress.append(String.format("%02x", b)).append(separator);
			}

			return macAddress.substring(0, macAddress.length() - separator.length());
		} catch (UnknownHostException | SocketException ex) {
			log.warn("Failed to retrieve MAC address of local machine", ex);
		}

		return null;
	}

	/**
	 * Returns the path to the base location of the specified class.
	 * <br>
	 * If the class is directly on the filesystem, then the path to the base directory is returned.
	 * For example, if the path to the class is {@code /path/to/my/package/MyClass.class},
	 * {@code /path/to} is returned.
	 * <br>
	 * If the class is inside a JAR file, the path to the JAR is returned.
	 *
	 * @param clazz a class.
	 * @return the path to the location of the specified class. If a valid path cannot be found,
	 * {@code null} is returned.
	 */
	@Nullable
	public static Path getClassLocation(Class<?> clazz) {
		Preconditions.checkNotNull(clazz, "clazz should not be null");

		//Taken from: https://stackoverflow.com/a/12733172

		final URL url = getClassURL(clazz);

		if (url == null) {
			return null;
		}

		final String path = url.toExternalForm();
		String correctedPath = path;

		try {
			return Paths.get(new URL(correctedPath).toURI());
		} catch (MalformedURLException | URISyntaxException ignored) {}

		//Try again, but without the "file:" prefix.
		if (path.startsWith("file:")) {
			return Paths.get(path.substring(5));
		}

		log.warn("Failed to convert class location URL to path: {}", url);
		return null;
	}

	@Nullable
	private static URL getClassURL(Class<?> clazz) {
		try {
			final CodeSource codeSource = AccessController.doPrivileged(
					(PrivilegedAction<ProtectionDomain>) clazz::getProtectionDomain
			).getCodeSource();

			if (codeSource != null) {
				final URL location = codeSource.getLocation();

				if (location != null) {
					return location;
				}
			}
		} catch (SecurityException ignored) {
			//This occurs when we aren't allowed to retrieve the ProtectionDomain.
		}

		//We ask for the class itself as a resource, then strip the class's path from the URL.
		final URL resource = clazz.getResource(clazz.getSimpleName() + ".class");

		if (resource == null) {
			//The class resource cannot be found, so we give up.
			return null;
		}

		final String url = resource.toExternalForm();
		final String canonicalName = clazz.getCanonicalName();

		if (canonicalName == null) {
			return null;
		}

		final String suffix = canonicalName.replace('.', '/') + ".class";

		if (!url.endsWith(suffix)) {
			//The URL is in an unknown format, so we give up.
			log.warn("Class location URL is in an unknown format: {}", url);
			return null;
		}

		String base = url.substring(0, url.length() - suffix.length());

		//We remove the "jar:" prefix and "!/" suffix if they are present.
		if (base.startsWith("jar:")) {
			base = base.substring(4, base.length() - 2);
		}

		try {
			return new URL(base);
		} catch (MalformedURLException ex) {
			log.warn("Class location URL is in an invalid format: {}", base, ex);
		}

		return null;
	}

	private static OS getOS() {
		final OS os = OS.fromName(SystemProperties.osName.get());

		if (os == OS.LINUX && SystemProperties.jvmName.stringEquals("Dalvik")) {
			return OS.ANDROID;
		}

		return os;
	}
	
}
