package net.rom.utility.system;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class WindowsVersion {
	
	/**
	 * Windows Vista.
	 */
	public static final OS.Version WINDOWS_VISTA = get("Vista");

	/**
	 * Windows 7.
	 */
	public static final OS.Version WINDOWS_7 = get("7");

	/**
	 * Windows Server 2008, the server version of Windows Vista,
	 * or Windows Server 2008 R2, the server version of Windows 7.
	 */
	public static final OS.Version WINDOWS_SERVER_2008 = get("Server 2008");

	/**
	 * Windows 8.
	 */
	public static final OS.Version WINDOWS_8 = get("8");

	/**
	 * Windows 8.1.
	 */
	public static final OS.Version WINDOWS_8_1 = get("8.1");

	/**
	 * Windows Server 2012, the server version of Windows 8.
	 */
	public static final OS.Version WINDOWS_SERVER_2012 = get("Server 2012");

	/**
	 * Windows 10.
	 */
	public static final OS.Version WINDOWS_10 = get("10");

	/**
	 * Windows Server 2016, the server version of Windows 10.
	 */
	public static final OS.Version WINDOWS_SERVER_2016 = get("Server 2016");

	private static final Set<OS.Version> versions = ImmutableSet.of(
			WINDOWS_VISTA,
			WINDOWS_7,
			WINDOWS_SERVER_2008,
			WINDOWS_8,
			WINDOWS_8_1,
			WINDOWS_SERVER_2012,
			WINDOWS_10,
			WINDOWS_SERVER_2016
	);

	/**
	 * Returns all {@link OS.Version} instances that represent known Windows versions.
	 *
	 * @return a mutable {@link Set} containing all {@link OS.Version} instances that represent
	 * known Windows versions.
	 */
	public static Set<OS.Version> getVersions() {
		return new HashSet<>(versions);
	}

	private static OS.Version get(String versionNumber) {
		return new OS.Version("Windows " + versionNumber, versionNumber);
	}
}
