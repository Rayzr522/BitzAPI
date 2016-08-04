
package com.rayzr522.bitzapi.utils;

public class MathUtils {

	public static int clamp(int input, int min, int max) {

		if (min == max) { return min; }

		if (min > max) {

			int tempmin = min;
			int tempmax = max;
			min = tempmax;
			max = tempmin;

		}

		int output = input;

		if (output > max) {
			output = max;
		} else if (output < min) {
			output = min;
		}

		return output;

	}

}
