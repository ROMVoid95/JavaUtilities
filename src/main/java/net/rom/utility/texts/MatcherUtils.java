package net.rom.utility.texts;

import java.util.function.Function;
import java.util.regex.Matcher;

import net.rom.utility.annotations.ExtensionClass;
import net.rom.utility.reflections.EasyReflections;

@ExtensionClass
public class MatcherUtils {
	private static final Function<Matcher, CharSequence> MATCHER_TEXT = EasyReflections.getObjectField(Matcher.class, "text");

	public static CharSequence getOriginalText(Matcher matcher) {
		return MATCHER_TEXT.apply(matcher);
	}

	public static Function<Matcher, String> replaceAll(Function<String, String> replacement) {
		return matcher -> replaceAll(matcher, replacement);
	}

	public static String replaceAll(Matcher matcher, Function<String, String> replacement) {
		matcher.reset();
		boolean result = matcher.find();
		if (result) {
			StringBuffer sb = new StringBuffer();
			do {
				matcher.appendReplacement(sb, replacement.apply(matcher.group()));
				result = matcher.find();
			} while (result);
			matcher.appendTail(sb);
			return sb.toString();
		}
		return getOriginalText(matcher).toString();
	}
}

