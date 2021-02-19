package net.rom.utility.io;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Values {
	
	/**
	 * The Unix directory separator character.
	 */
	public static final char UNIX_DIR_SEPARATOR = '/';
	
	/**
	 * The Windows directory separator character.
	 */
	public static final char WIN_DIR_SEPARATOR = '\\';

	/**
	 * The Unix line separator string.
	 */
	public static final String UNIX_LINE_SEPARATOR = "\n";
	
	/**
	 * The Windows line separator string.
	 */
	public static final String WIN_LINE_SEPARATOR = "\r\n";
	
	/**
	 * The system-dependent line separator string as returned by {@link System#lineSeparator()}.
	 */
	public static final String LINE_SEPARATOR = System.lineSeparator();
	
}
