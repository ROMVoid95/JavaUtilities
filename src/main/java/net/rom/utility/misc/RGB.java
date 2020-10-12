package net.rom.utility.misc;

import java.lang.reflect.Field;
import java.util.*;

public class RGB {
	
	public static final RGB ALICEBLUE = new RGB(240, 248, 255);
	public static final RGB ANTIQUEWHITE = new RGB(250, 235, 215);
	public static final RGB AQUA = new RGB(0, 255, 255);
	public static final RGB AQUAMARINE = new RGB(127, 255, 212);
	public static final RGB AZURE = new RGB(240, 255, 255);
	public static final RGB BEIGE = new RGB(245, 245, 220);
	public static final RGB BISQUE = new RGB(255, 228, 196);
	public static final RGB BLACK = new RGB(0, 0, 0);
	public static final RGB BLANCHEDALMOND = new RGB(255, 235, 205);
	public static final RGB BLUE = new RGB(0, 0, 255);
	public static final RGB BLUEVIOLET = new RGB(138, 43, 226);
	public static final RGB BROWN = new RGB(165, 42, 42);
	public static final RGB BURLYWOOD = new RGB(222, 184, 135);
	public static final RGB CADETBLUE = new RGB(95, 158, 160);
	public static final RGB CHARTREUSE = new RGB(127, 255, 0);
	public static final RGB CHOCOLATE = new RGB(210, 105, 30);
	public static final RGB CORAL = new RGB(255, 127, 80);
	public static final RGB CORNFLOWERBLUE = new RGB(100, 149, 237);
	public static final RGB CORNSILK = new RGB(255, 248, 220);
	public static final RGB CRIMSON = new RGB(220, 20, 60);
	public static final RGB CYAN = new RGB(0, 255, 255);
	public static final RGB DARKBLUE = new RGB(0, 0, 139);
	public static final RGB DARKCYAN = new RGB(0, 139, 139);
	public static final RGB DARKGOLDENROD = new RGB(184, 134, 11);
	public static final RGB DARKGRAY = new RGB(169, 169, 169);
	public static final RGB DARKGREEN = new RGB(0, 100, 0);
	public static final RGB DARKGREY = DARKGRAY;
	public static final RGB DARKKHAKI = new RGB(189, 183, 107);
	public static final RGB DARKMAGENTA = new RGB(139, 0, 139);
	public static final RGB DARKOLIVEGREEN = new RGB(85, 107, 47);
	public static final RGB DARKORANGE = new RGB(255, 140, 0);
	public static final RGB DARKORCHID = new RGB(153, 50, 204);
	public static final RGB DARKRED = new RGB(139, 0, 0);
	public static final RGB DARKSALMON = new RGB(233, 150, 122);
	public static final RGB DARKSEAGREEN = new RGB(143, 188, 143);
	public static final RGB DARKSLATEBLUE = new RGB(72, 61, 139);
	public static final RGB DARKSLATEGRAY = new RGB(47, 79, 79);
	public static final RGB DARKSLATEGREY = DARKSLATEGRAY;
	public static final RGB DARKTURQUOISE = new RGB(0, 206, 209);
	public static final RGB DARKVIOLET = new RGB(148, 0, 211);
	public static final RGB DEEPPINK = new RGB(255, 20, 147);
	public static final RGB DEEPSKYBLUE = new RGB(0, 191, 255);
	public static final RGB DIMGRAY = new RGB(105, 105, 105);
	public static final RGB DIMGREY = DIMGRAY;
	public static final RGB DODGERBLUE = new RGB(30, 144, 255);
	public static final RGB FIREBRICK = new RGB(178, 34, 34);
	public static final RGB FLORALWHITE = new RGB(255, 250, 240);
	public static final RGB FORESTGREEN = new RGB(34, 139, 34);
	public static final RGB FUCHSIA = new RGB(255, 0, 255);
	public static final RGB GAINSBORO = new RGB(220, 220, 220);
	public static final RGB GHOSTWHITE = new RGB(248, 248, 255);
	public static final RGB GOLD = new RGB(255, 215, 0);
	public static final RGB GOLDENROD = new RGB(218, 165, 32);
	public static final RGB GRAY = new RGB(128, 128, 128);
	public static final RGB GREEN = new RGB(0, 128, 0);
	public static final RGB GREENYELLOW = new RGB(173, 255, 47);
	public static final RGB GREY = GRAY;
	public static final RGB HONEYDEW = new RGB(240, 255, 240);
	public static final RGB HOTPINK = new RGB(255, 105, 180);
	public static final RGB INDIANRED = new RGB(205, 92, 92);
	public static final RGB INDIGO = new RGB(75, 0, 130);
	public static final RGB IVORY = new RGB(255, 255, 240);
	public static final RGB KHAKI = new RGB(240, 230, 140);
	public static final RGB LAVENDER = new RGB(230, 230, 250);
	public static final RGB LAVENDERBLUSH = new RGB(255, 240, 245);
	public static final RGB LAWNGREEN = new RGB(124, 252, 0);
	public static final RGB LEMONCHIFFON = new RGB(255, 250, 205);
	public static final RGB LIGHTBLUE = new RGB(173, 216, 230);
	public static final RGB LIGHTCORAL = new RGB(240, 128, 128);
	public static final RGB LIGHTCYAN = new RGB(224, 255, 255);
	public static final RGB LIGHTGOLDENRODYELLOW = new RGB(250, 250,
		210);
	public static final RGB LIGHTGRAY = new RGB(211, 211, 211);
	public static final RGB LIGHTGREEN = new RGB(144, 238, 144);
	public static final RGB LIGHTGREY = LIGHTGRAY;
	public static final RGB LIGHTPINK = new RGB(255, 182, 193);
	public static final RGB LIGHTSALMON = new RGB(255, 160, 122);
	public static final RGB LIGHTSEAGREEN = new RGB(32, 178, 170);
	public static final RGB LIGHTSKYBLUE = new RGB(135, 206, 250);
	public static final RGB LIGHTSLATEGRAY = new RGB(119, 136, 153);
	public static final RGB LIGHTSLATEGREY = LIGHTSLATEGRAY;
	public static final RGB LIGHTSTEELBLUE = new RGB(176, 196, 222);
	public static final RGB LIGHTYELLOW = new RGB(255, 255, 224);
	public static final RGB LIME = new RGB(0, 255, 0);
	public static final RGB LIMEGREEN = new RGB(50, 205, 50);
	public static final RGB LINEN = new RGB(250, 240, 230);
	public static final RGB MAGENTA = new RGB(255, 0, 255);
	public static final RGB MAROON = new RGB(128, 0, 0);
	public static final RGB MEDIUMAQUAMARINE = new RGB(102, 205, 170);
	public static final RGB MEDIUMBLUE = new RGB(0, 0, 205);
	public static final RGB MEDIUMORCHID = new RGB(186, 85, 211);
	public static final RGB MEDIUMPURPLE = new RGB(147, 112, 219);
	public static final RGB MEDIUMSEAGREEN = new RGB(60, 179, 113);
	public static final RGB MEDIUMSLATEBLUE = new RGB(123, 104, 238);
	public static final RGB MEDIUMSPRINGGREEN = new RGB(0, 250, 154);
	public static final RGB MEDIUMTURQUOISE = new RGB(72, 209, 204);
	public static final RGB MEDIUMVIOLETRED = new RGB(199, 21, 133);
	public static final RGB MIDNIGHTBLUE = new RGB(25, 25, 112);
	public static final RGB MINTCREAM = new RGB(245, 255, 250);
	public static final RGB MISTYROSE = new RGB(255, 228, 225);
	public static final RGB MOCCASIN = new RGB(255, 228, 181);
	public static final RGB NAVAJOWHITE = new RGB(255, 222, 173);
	public static final RGB NAVY = new RGB(0, 0, 128);
	public static final RGB OLDLACE = new RGB(253, 245, 230);
	public static final RGB OLIVE = new RGB(128, 128, 0);
	public static final RGB OLIVEDRAB = new RGB(107, 142, 35);
	public static final RGB ORANGE = new RGB(255, 165, 0);
	public static final RGB ORANGERED = new RGB(255, 69, 0);
	public static final RGB ORCHID = new RGB(218, 112, 214);
	public static final RGB PALEGOLDENROD = new RGB(238, 232, 170);
	public static final RGB PALEGREEN = new RGB(152, 251, 152);
	public static final RGB PALETURQUOISE = new RGB(175, 238, 238);
	public static final RGB PALEVIOLETRED = new RGB(219, 112, 147);
	public static final RGB PAPAYAWHIP = new RGB(255, 239, 213);
	public static final RGB PEACHPUFF = new RGB(255, 218, 185);
	public static final RGB PERU = new RGB(205, 133, 63);
	public static final RGB PINK = new RGB(255, 192, 203);
	public static final RGB PLUM = new RGB(221, 160, 221);
	public static final RGB POWDERBLUE = new RGB(176, 224, 230);
	public static final RGB PURPLE = new RGB(128, 0, 128);
	public static final RGB RED = new RGB(255, 0, 0);
	public static final RGB ROSYBROWN = new RGB(188, 143, 143);
	public static final RGB ROYALBLUE = new RGB(65, 105, 225);
	public static final RGB SADDLEBROWN = new RGB(139, 69, 19);
	public static final RGB SALMON = new RGB(250, 128, 114);
	public static final RGB SANDYBROWN = new RGB(244, 164, 96);
	public static final RGB SEAGREEN = new RGB(46, 139, 87);
	public static final RGB SEASHELL = new RGB(255, 245, 238);
	public static final RGB SIENNA = new RGB(160, 82, 45);
	public static final RGB SILVER = new RGB(192, 192, 192);
	public static final RGB SKYBLUE = new RGB(135, 206, 235);
	public static final RGB SLATEBLUE = new RGB(106, 90, 205);
	public static final RGB SLATEGRAY = new RGB(112, 128, 144);
	public static final RGB SLATEGREY = SLATEGRAY;
	public static final RGB SNOW = new RGB(255, 250, 250);
	public static final RGB SPRINGGREEN = new RGB(0, 255, 127);
	public static final RGB STEELBLUE = new RGB(70, 130, 180);
	public static final RGB TAN = new RGB(210, 180, 140);
	public static final RGB TEAL = new RGB(0, 128, 128);
	public static final RGB THISTLE = new RGB(216, 191, 216);
	public static final RGB TOMATO = new RGB(255, 99, 71);
	public static final RGB TURQUOISE = new RGB(64, 224, 208);
	public static final RGB VIOLET = new RGB(238, 130, 238);
	public static final RGB WHEAT = new RGB(245, 222, 179);
	public static final RGB WHITE = new RGB(255, 255, 255);
	public static final RGB WHITESMOKE = new RGB(245, 245, 245);
	public static final RGB YELLOW = new RGB(255, 255, 0);
	public static final RGB YELLOWGREEN = new RGB(154, 205, 50);

	private static final Map<String, RGB> COLORS =
		new HashMap<>();

	static {
		for (final Field f : RGB.class.getDeclaredFields()) {
			final Object value;
			try {
				value = f.get(null);
			}
			catch (final IllegalArgumentException e) {
				continue;
			}
			catch (final IllegalAccessException e) {
				continue;
			}
			if (!(value instanceof RGB)) continue; // not a color
			final RGB color = (RGB) value;
			COLORS.put(f.getName().toLowerCase(), color);
		}
	}

	private final float red;
	private final float green;
	private final float blue;
	private final float alpha;

	public RGB(int color) {
		this((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
	}

	public RGB(int red, int green, int blue) {
		this(red / 255f, green / 255f, blue / 255f, 1f);
	}

	public RGB(int red, int green, int blue, int alpha) {
		this(red / 255f, green / 255f, blue / 255f, alpha / 255f);
	}

	public RGB(float red, float green, float blue) {
		this(red, green, blue, 1f);
	}

	public RGB(float red, float green, float blue, float alpha) {
		this.red   = red;
		this.green = green;
		this.blue  = blue;
		this.alpha = alpha;
	}
	
	public int getColor () {
		int r = (int) (red * 255f) << 16;
		int g = (int) (green * 255f) << 8;
		int b = (int) (blue * 255f);
		return r + g + b;
	}

	public int getColorWithA () {
		int a = (int) (alpha * 255f) << 24;
		int r = (int) (red * 255f) << 16;
		int g = (int) (green * 255f) << 8;
		int b = (int) (blue * 255f);
		return a + r + g + b;
	}

	public float getRed () {
		return red;
	}

	public float getGreen () {
		return green;
	}

	public float getBlue () {
		return blue;
	}

	public float getAlpha () {
		return alpha;
	}

	public int getRedInt () {
		return (int) (red * 255f);
	}

	public int getGreenInt () {
		return (int) (green * 255f);
	}

	public int getBlueInt () {
		return (int) (blue * 255f);
	}

	public int getAlphaInt () {

		return (int) (alpha * 255f);
	}

	/**
	 * Gets the preset color with the given name. For example,
	 * {@code Colors.get("red")} will return {@link Colors#RED}.
	 */
	public static RGB getColor(final String name) {
		return COLORS.get(name);
	}

	/** Gets the name of the preset matching the given color. */
	public static String getName(final RGB color) {
		if (color == null) return null;
		for (final String name : COLORS.keySet()) {
			final RGB value = COLORS.get(name);
			if (color.equals(value)) return name;
		}
		return null;
	}

	/** Gets the table of all preset colors. */
	public static Map<String, RGB> map() {
		return Collections.unmodifiableMap(COLORS);
	}

	/** Gets the list of all preset colors. */
	public static Collection<RGB> values() {
		return COLORS.values();
	}
}
