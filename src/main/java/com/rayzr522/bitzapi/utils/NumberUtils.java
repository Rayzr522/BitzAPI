
package com.rayzr522.bitzapi.utils;

public class NumberUtils {

	/**
	 * 
	 * Formats a large number by inserting commas, as is usually when writing
	 * numbers by hand.
	 * 
	 * @param num
	 *            = the number to format.
	 * @return A formated string representation of the number.
	 */
	public static String formatNumber(long num) {
		String in = num + "";
		if (num < 1000) {
			return in;
		}
		String out = in.substring(0, in.length() % 3);
		for (int i = in.length() % 3; i < in.length(); i += 3) {
			out += (i > 0 ? "," : "") + in.substring(i, i + 3 > in.length() ? in.length() : i + 3);
		}
		return out;
	}

	/**
	 * 
	 * Returns whether the parameter is negative
	 * 
	 * @param i
	 * @return
	 */
	public static boolean isNegative(int i) {

		return i != Math.abs(i);

	}

}
