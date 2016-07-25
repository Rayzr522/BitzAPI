
package com.rayzr522.bitzapi.utils.data;

import java.util.Arrays;

public class ArrayUtils {

	public static String concatArray(Object[] arr, String filler) {

		if (arr.length == 0) {

		return "";

		}

		String output = "";
		for (int i = 0; i < arr.length - 1; i++) {

			output += arr[i].toString();
			output += filler;

		}

		output += arr[arr.length - 1].toString();

		return output;

	}

	public static Object[] removeFirst(Object[] arr) {

		if (arr.length < 2) {

		return arr;

		}

		return Arrays.copyOfRange(arr, 1, arr.length);

	}

}
