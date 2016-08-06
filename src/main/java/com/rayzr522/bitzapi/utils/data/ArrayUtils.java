
package com.rayzr522.bitzapi.utils.data;

import java.util.Arrays;

public class ArrayUtils {

	/**
	 * Concatenates the array with a certain string
	 * 
	 * @param arr
	 * @param filler
	 * @return
	 */
	public static String concatArray(Object[] arr, String filler) {

		if (arr == null || arr.length == 0 || filler == null) {

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

	/**
	 * Removes the first element from an array if the array length is > 1
	 * 
	 * @param arr
	 * @return
	 */
	public static <T> T[] removeFirst(T[] arr) {

		if (arr.length <= 1) {

		return arr;

		}

		return Arrays.copyOfRange(arr, 1, arr.length);

	}

	/**
	 * Removes the last element from an array if the array length is > 1
	 * 
	 * @param arr
	 * @return
	 */

	public static Object[] removeLast(Object[] arr) {

		if (arr.length <= 1) {

		return arr;

		}

		Object[] out = new Object[arr.length - 1];

		for (int i = 0; i < arr.length - 1; i++) {

			out[i] = arr[i];

		}

		return out;

	}

	/**
	 * Returns the last element in the array or null if the arr is null or has a
	 * length of zero
	 * 
	 * @param arr
	 * @return
	 */
	public static <T> T last(T[] arr) {

		if (arr == null || arr.length == 0) { return null; }
		return arr[arr.length - 1];

	}

}
