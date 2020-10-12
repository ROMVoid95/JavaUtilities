package net.rom.utility.system;

import java.util.Locale;

public enum OS implements Comparable<OS> {
	
	WINDOWS("windows", "win"),
	LINUX("linux"),
	SOLARIS("sunos", "solaris"),
	MAC_OS("darwin", "mac", "macos", "mac os x"),
	UNKNOWN("generic/unknown");
	
	private static OS currentOS = null;
	private String[] osIdentifier;
	
	private OS(String... osId) {
		osIdentifier = osId;
	}

	/**
	 * Return the OS type this program is executed on
	 * 
	 * @return the OS used to launch this JVM
	 */
	public static OS getOS() {
		return currentOS == null ? retrieveInfo() : currentOS;
	}
	
	@Override
	public String toString() {
		String osPretty = currentOS.name().toLowerCase().replace("_", " ");
		osPretty = osPretty.substring(0, 1).toUpperCase() + osPretty.substring(1);

		int index = 0;
		while ((index = osPretty.indexOf(" ", index + 1)) != -1) {
			osPretty = osPretty.substring(0, 1).toUpperCase() + osPretty.substring(1);
		}
		return osPretty + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version");
	}

	
	private static OS retrieveInfo() {
		String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH); // turkey
		for (OS os : OS.values()) {
			for (String osIdentifier : os.osIdentifier) {
				if (osName.contains(osIdentifier)) {
					currentOS = os;
					return currentOS;
				}
				
			}
		}
		// Never happens
		return UNKNOWN;
	}
}
