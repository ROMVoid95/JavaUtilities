package net.rom.utility.system;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.Immutable;

public enum OS implements Comparable<OS> {
	
	ANDROID("android", "linux"),
	GNU_LINUX("GNU/Linux", "gnu"),
	LINUX("linux"),
	SOLARIS("Solaris", "solaris", "sunos"),
	MACOS("macOS", MacVersion.getVersions(), false, "Mac OS X"),
	WINDOWS("Windows", WindowsVersion.getVersions(), true),
	UNKNOWN("generic/unknown");
	
	/**
	 * An immutable set of all {@link OS}'s.
	 */
	public static final ImmutableSet<OS> VALUES = ImmutableSet.copyOf(values());
	
	/**
	 * An immutable set of all {@link OS}'s other than {@link #UNKNOWN}.
	 */
	public static final ImmutableSet<OS> KNOWN_VALUES = Arrays.stream(values()).
			filter(os -> os != UNKNOWN).
			collect(ImmutableSet.toImmutableSet());
	
	private final String friendlyName;
	private final ImmutableList<String> knownNames;
	private final ImmutableSet<OS.Version> versions;
	private final boolean versionNumberInOSName;
	
	OS(String friendlyName, String... knownNames) {
		this(friendlyName, ImmutableSet.of(), false, knownNames);
	}
	
	OS(
			String friendlyName, Set<OS.Version> versions, boolean versionNumberInOSName,
			String... knownNames
	) {
		this.friendlyName = friendlyName;

		if (knownNames.length == 0) {
			this.knownNames = ImmutableList.of(friendlyName.toLowerCase(Locale.ENGLISH));
		} else {
			this.knownNames = ImmutableList.copyOf(knownNames);
		}

		this.versions = ImmutableSet.copyOf(versions);
		this.versionNumberInOSName = versionNumberInOSName;
	}

	/**
	 * Returns the friendly name of this {@link OS}.
	 *
	 * @return the friendly name of this {@link OS}.
	 */
	public String getFriendlyName() {
		return friendlyName;
	}

	/**
	 * Returns an immutable set containing all known versions of this {@link OS}.
	 *
	 * @return an immutable set containing all known versions of this {@link OS}.
	 */
	public Set<OS.Version> getVersions() {
		return versions;
	}
	
	/**
	 * Returns whether the version number of any {@link OS.Version} of this {@link OS} is included in
	 * The {@code {@code os.name}} system property. For example, this is {@code true} for Windows,
	 * as The {@code {@code os.name}} system property.contains the version number in its value, e.g.
	 * {@code "Windows 8.1"} or {@code "Windows 10"}.
	 *
	 * @return {@code true} if the version number of any {@link OS.Version} of this {@link OS} is
	 * included in The {@code {@code os.name}} system property. or otherwise {@code false}.
	 */
	public boolean isVersionNumberInOSName() {
		return versionNumberInOSName;
	}

	/**
	 * Returns the {@link OS.Version} with a name equivalent to the specified name.
	 *
	 * @param name a name, e.g. {@code "Windows 10"}.
	 * @return the {@link OS.Version} with a name equivalent to the specified name,
	 * or {@link OS.Version#unknown()} if it cannot be found.
	 */
	public OS.Version getVersionByName(String name) {
		Preconditions.checkNotNull(name, "name should not be null");

		if (versions.isEmpty()) {
			return OS.Version.unknown();
		}

		name = name.trim();

		if (name.isEmpty()) {
			return OS.Version.unknown();
		}

		return getVersion(name, OS.Version::getFullName);
	}

	/**
	 * Returns the {@link OS.Version} with a version number equivalent to the specified version
	 * number.
	 *
	 * @param versionNumber a version number.
	 * @return the {@link OS.Version} with a version number equivalent to the specified version
	 * number, or {@link OS.Version#unknown()} if it cannot be found.
	 */
	public OS.Version getVersionByVersionNumber(String versionNumber) {
		if (versions.isEmpty()) {
			return OS.Version.unknown();
		}

		versionNumber = versionNumber.trim();

		if (versionNumber.isEmpty()) {
			return OS.Version.unknown();
		}

		//First, we try directly matching the version numbers.
		final OS.Version quickMatch = getVersion(versionNumber, OS.Version::getVersionNumber);

		if (!quickMatch.isUnknown()) {
			return quickMatch;
		}

		//Then, we try matching by ignoring minor versions, e.g. 10.13.6 is matched to 10.13.
		final String[] versionParts = versionNumber.split("\\.");

		for (OS.Version version : versions) {
			if (versionsMatch(versionParts, version.getVersionNumber().split("\\."))) {
				return version;
			}
		}

		return OS.Version.unknown();
	}

	private OS.Version getVersion(String toFind, Function<OS.Version, String> versionToString) {
		for (OS.Version version : versions) {
			if (toFind.equalsIgnoreCase(versionToString.apply(version))) {
				return version;
			}
		}

		return OS.Version.unknown();
	}

	private boolean versionsMatch(String[] version1Parts, String[] version2Parts) {
		final int partsToCheck = Math.min(version1Parts.length, version2Parts.length);

		for (int i = 0; i < partsToCheck; i++) {
			if (!version1Parts[i].equalsIgnoreCase(version2Parts[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the {@link OS} with the specified OS name.
	 *
	 * @param osName an OS name.
	 * @return the {@link OS} with the specified OS name, or {@link #UNKNOWN} if it does not
	 * exist.
	 */
	public static OS fromName(String osName) {
		final String nameToFind = osName.trim().toLowerCase(Locale.ENGLISH);

		if (nameToFind.isEmpty()) {
			return UNKNOWN;
		}

		for (OS os : KNOWN_VALUES) {
			if (os.knownNames.stream().anyMatch(nameToFind::startsWith)) {
				return os;
			}
		}

		return UNKNOWN;
	}
	
	@Immutable
	public static final class Version implements Comparable<Version> {
		
		private static final Version UNKNOWN = new Version("Unknown", "Unknown");

		private final String fullName;
		private final String versionNumber;

		Version(String fullName, String versionNumber) {
			this.fullName = fullName;
			this.versionNumber = versionNumber;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			return Objects.hash(fullName, versionNumber);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object object) {
			//It isn't actually necessary to implement equals here; I've just done it to fix the
			//warnings.
			return this == object;
		}

		/**
		 * Returns the full name of this {@link Version}, e.g. {@code "Windows 7"} or
		 * {@code "macOS Catalina"}.
		 *
		 * @return the full name of this {@link Version}.
		 */
		@Override
		public String toString() {
			return fullName;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compareTo(Version version) {
			return fullName.compareTo(version.fullName);
		}

		/**
		 * Returns the full name of this {@link Version}, e.g. {@code "Windows 7"} or
		 * {@code "macOS Catalina"}.
		 *
		 * @return the full name of this {@link Version}.
		 */
		public String getFullName() {
			return fullName;
		}

		/**
		 * Returns this {@link Version}'s version number, e.g. {@code "10.10"}.
		 * This number may be but is not necessarily included in the full name returned by
		 * {@link #getFullName()}).
		 *
		 * @return this {@link Version}'s version number.
		 */
		public String getVersionNumber() {
			return versionNumber;
		}

		/**
		 * Returns whether this {@link Version} is {@link Version#unknown()}.
		 *
		 * @return whether this {@link Version} is {@link Version#unknown()}.
		 */
		public boolean isUnknown() {
			return equals(UNKNOWN);
		}

		/**
		 * Returns an {@link Version} that represents an unknown OS version.
		 *
		 * @return an {@link Version} that represents an unknown OS version.
		 */
		public static Version unknown() {
			return UNKNOWN;
		}
	}
}
