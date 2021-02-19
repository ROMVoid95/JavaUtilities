package net.rom.utility.system;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MacVersion {
	
	/**
	 * Mac OS X 10.8 Mountain Lion.
	 */
	public static final OS.Version MOUNTAIN_LION = get("Mountain Lion", 8);

	/**
	 * Mac OS X 10.9 Mavericks.
	 */
	public static final OS.Version MAVERICKS = get("Mavericks", 9);

	/**
	 * Mac OS X 10.10 Yosemite.
	 */
	public static final OS.Version YOSEMITE = get("Yosemite", 10);

	/**
	 * Mac OS X 10.11 El Capitan.
	 */
	public static final OS.Version EL_CAPITAN = get("El Capitan", 11);

	/**
	 * macOS 10.12 Sierra.
	 */
	public static final OS.Version SIERRA = get("Sierra", 12);

	/**
	 * macOS 10.13 High Sierra.
	 */
	public static final OS.Version HIGH_SIERRA = get("High Sierra", 13);

	/**
	 * macOS 10.14 Mojave.
	 */
	public static final OS.Version MOJAVE = get("Mojave", 14);

	/**
	 * macOS 10.15 Catalina.
	 */
	public static final OS.Version CATALINA = get("Catalina", 15);

	private static final Set<OS.Version> versions = ImmutableSet.of(
			MOUNTAIN_LION,
			MAVERICKS,
			YOSEMITE,
			EL_CAPITAN,
			SIERRA,
			HIGH_SIERRA,
			MOJAVE,
			CATALINA
	);

	/**
	 * Returns all {@link OSOS.Version} instances that represent known macOS/Mac OS X versions.
	 *
	 * @return a mutable {@link Set} containing all {@link OSOS.Version} instances that represent
	 * known macOS/Mac OS X versions.
	 */
	public static Set<OS.Version> getVersions() {
		return new HashSet<>(versions);
	}

	private static OS.Version get(String name, int versionNumber) {
		return new OS.Version(
				(versionNumber > 11 ? "macOS " : "Mac OS X ") + name,
				"10." + versionNumber
		);
	}
}
