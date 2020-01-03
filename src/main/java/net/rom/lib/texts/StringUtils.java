package net.rom.lib.texts;

import java.util.*;
import java.util.regex.Pattern;

public class StringUtils {
	public static final Pattern SPLIT_PATTERN = Pattern.compile("\\s+");

	public static String[] efficientSplitArgs(String args, int expectedArgs) {
		List<String> result = new ArrayList<>();
		boolean inAString = false, escaping = false;
		StringBuilder currentBlock = new StringBuilder();
		char[] array = args.toCharArray();

		for (int i = 0; i < array.length; i++) {
			char c = array[i];
			if (escaping) {
				escaping = false;
				currentBlock.append(escape(c));
				continue;
			}

			if (c == '\\') {
				escaping = true;
				continue;
			}

			if (c == '"') {
				inAString = !inAString;
				continue;
			}

			if (inAString) {
				currentBlock.append(c);
				continue;
			}

			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				String block = currentBlock.toString();
				currentBlock = new StringBuilder();

				if (!block.isEmpty()) result.add(block);
				continue;
			}

			currentBlock.append(c);
		}

		String block = currentBlock.toString();
		if (!block.isEmpty()) result.add(block);

		String[] raw = result.toArray(new String[result.size()]);
		return expectedArgs < 1 ? raw : normalizeArray(raw, expectedArgs);
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	public static String limit(String value, int length) {
		StringBuilder buf = new StringBuilder(value);
		if (buf.length() > length) {
			buf.setLength(length - 3);
			buf.append("...");
		}

		return buf.toString();
	}

	/**
	 * Normalize an {@link String} Array.
	 *
	 * @param raw          the String array to be normalized
	 * @param expectedSize the final size of the Array.
	 * @return {@link String}[] with the size of expectedArgs
	 */
	public static String[] normalizeArray(String[] raw, int expectedSize) {
		String[] normalized = new String[expectedSize];

		Arrays.fill(normalized, "");
		for (int i = 0; i < normalized.length; i++)
			if (i < raw.length && raw[i] != null && !raw[i].isEmpty()) normalized[i] = raw[i];
		return normalized;
	}

	public static Map<String, Optional<String>> parse(String[] args) {
		Map<String, Optional<String>> options = new LinkedHashMap<>();

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.charAt(0) == '-' || arg.charAt(0) == '/') { //This start with - or /
				arg = arg.substring(1);
				if (i + 1 >= args.length || args[i + 1].charAt(0) == '-' || args[i + 1].charAt(0) == '/') { //Next start with - (or last arg)
					options.put(arg, Optional.empty());
				} else {
					options.put(arg, Optional.of(args[i + 1]));
					i++;
				}
			} else {
				String a = arg;
				options.compute(null, (k, v) -> {
					if (v == null) v = Optional.empty();
					return Optional.of(v.map(s -> s.concat(" ").concat(a)).orElse(a));
				});
			}
		}

		return options;
	}

	/**
	 * Enchanced {@link String#split(String, int)} with SPLIT_PATTERN as the Pattern used.
	 *
	 * @param args         the {@link String} to be split.
	 * @param expectedArgs the size of the returned array of Non-null {@link String}s
	 * @return a {@link String}[] with the size of expectedArgs
	 */
	public static String[] splitArgs(String args, int expectedArgs) {
		String[] raw = SPLIT_PATTERN.split(args, expectedArgs);
		if (expectedArgs < 1) return raw;
		return normalizeArray(raw, expectedArgs);
	}

	private static char escape(char c) {
		switch (c) {
			case 'n':
				return '\n';
			case 'r':
				return '\r';
			case 't':
				return '\t';
			default:
				return c;
		}
	}
}

