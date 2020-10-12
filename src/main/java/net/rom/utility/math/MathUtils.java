package net.rom.utility.math;

public class MathUtils {
	
	/**
	 * Clamp a number between its lower and upper bound. If x {@literal>} upper
	 * bound return upper bound. If x {@literal<} lower bound return lower bound
	 * 
	 * @param value      the input value
	 * @param lowerBound the lower bound
	 * @param upperBound the upper bound
	 * @param            <T> The type of the input and return value
	 * @return the original value if it's between bounds or the bound
	 * @since 1.0.0
	 */
	public static <T extends Number & Comparable<T>> T clampNumber(T value, T lowerBound, T upperBound) {
		if (value.compareTo(lowerBound) <= 0) {
			return lowerBound;
		} else if (value.compareTo(upperBound) >= 0) {
			return upperBound;
		}
		return value;
	}
	
	/**
	 * Find the closest value to a number which can be divided by the divisor.
	 * 
	 * <pre>
	 * if  dividend%divisor == 0 return dividend
	 * if  dividend%divisor != 0 return the value closest
	 *     to dividend which fulfills the condition
	 * </pre>
	 * 
	 * If two numbers are exactly the same distance away one of them will returned.
	 * 
	 * @param dividend the dividend
	 * @param divisor  the divisor
	 * @return the nearest number to dividend which can be divided by divisor
	 * @throws java.lang.ArithmeticException if divisor is zero
	 * 
	 * @since 1.0.0
	 */
	public static long findClosestDivisibleInteger(int dividend, int divisor) {
		int quot = dividend / divisor;

		int n1 = divisor * quot;

		int n2 = (dividend * divisor) > 0 ? (divisor * (quot + 1)) : (divisor * (quot - 1));

		if (Math.abs(dividend - n1) < Math.abs(dividend - n2))
			return n1;

		return n2;
	}
	
	/**
	 * Check if the supplied variable represents a numeric value
	 * 
	 * @param var the variable to check
	 * @return true if the variable is a number, false otherwise
	 * @since 1.0.0
	 */
	public static boolean isNumeric(Object var) {
		return var instanceof Number;
	}

}
