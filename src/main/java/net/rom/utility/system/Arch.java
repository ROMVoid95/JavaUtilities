package net.rom.utility.system;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public enum Arch {

	/**
	 * 32-bit architecture.
	 */
	_32BIT(
			"32-bit",
			"x86_32",
			"x86-32",
			"x86",
			"i386",
			"i486",
			"i586",
			"i686",
			"ia32",
			"x32",
			"sparc",
			"sparc32",
			"arm",
			"arm32",
			"ppc",
			"ppc32",
			"powerpc",
			"s390"),
	/**
	 * 64-bit architecture.
	 */
	_64BIT(
			"64-bit",
			"x86_64",
			"x86-64",
			"amd64",
			"ia32e",
			"em64t",
			"x64",
			"ia64",
			"itanium64",
			"sparcv9",
			"sparc64",
			"aarch64",
			"powerpc64",
			"ppc64",
			"ppc64le",
			"s390x"),
	/**
	 * Unknown architecture.
	 */
	UNKNOWN("Unknown");

	private final String friendlyName;
	private final ImmutableSet<String> architectureNames;
	private final ImmutableSet<String> searchNames;
	
	Arch(String friendlyName, String... architectureNames) {
		this.friendlyName = friendlyName;
		this.architectureNames = ImmutableSet.copyOf(architectureNames);

		//We create a separate set with hyphens and underscores stripped so that
		//Arch#fromName has an easier time finding the correct Arch.
		final Set<String> searchNames = new HashSet<>(architectureNames.length + 1);
		searchNames.add(friendlyName.replaceAll("[-_]", ""));

		for (String name : architectureNames) {
			searchNames.add(name.replaceAll("[-_]", ""));
		}

		this.searchNames = ImmutableSet.copyOf(searchNames);
	}

	/**
	 * Returns an immutable set containing the names of all known supported architectures with
	 * this {@link Arch}'s bitness.
	 *
	 * @return the names of all known supported architectures with this {@link Arch}'s
	 * bitness.
	 */
	public Set<String> getArchNames() {
		return architectureNames;
	}

	/**
	 * Returns the friendly name of this {@link Arch}.
	 *
	 * @return the friendly name of this {@link Arch}.
	 */
	public String getFriendlyName() {
		return friendlyName;
	}

	/**
	 * Returns the {@link Arch} that the specified architecture name represents.
	 *
	 * @param architectureName an architecture name, for example,
	 * {@code "32-bit"} or {@code "x86-64"}.
	 * Casing, whitespace, hyphens and underscores are ignored.
	 * @return the {@link Arch} that the specified architecture name represents, or
	 * {@link #UNKNOWN} if it cannot be found.
	 */
	public static Arch fromName(String architectureName) {
		Preconditions.checkNotNull(architectureName, "architectureName should not be null");
		architectureName = architectureName.toLowerCase(Locale.ENGLISH).replaceAll("[\\s-_]", "");

		for (Arch architecture : values()) {
			if (architecture.searchNames.contains(architectureName)) {
				return architecture;
			}
		}

		return UNKNOWN;
	}
}
