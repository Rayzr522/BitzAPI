
package com.rayzr522.bitzapi.utils;

public class NumberUtils {

	public static String formatNumber(int number) {

		String output = "";
		String input = "" + number;

		String[] splitIn = input.split("");

		for (int i = 0; i < splitIn.length; i++) {

			int pos = splitIn.length - 1 - i;

			if (!splitIn[pos].equals("")) {

				if (i > 0 && i % 3 == 0) {

					output = "," + output;

				}

				output = splitIn[pos] + output;

			}

		}

		return output;

	}

	public static boolean isNegative(int i) {

		return i != Math.abs(i);

	}

}
