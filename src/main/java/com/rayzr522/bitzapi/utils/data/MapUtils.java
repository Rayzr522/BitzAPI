
package com.rayzr522.bitzapi.utils.data;

import java.util.HashMap;

import com.rayzr522.bitzapi.utils.Pair;

public class MapUtils {

	/**
	 * Creates an empty map
	 * 
	 * @return the new (empty) map
	 * 
	 */
	public static <K, V> HashMap<K, V> empty() {

		return new HashMap<K, V>();

	}

	/**
	 * Adds a pair to a map
	 * 
	 * @param pair
	 *            the pair
	 * @param map
	 *            the map
	 */
	public static <A, B> void add(Pair<A, B> pair, HashMap<A, B> map) {

		map.put(pair.a(), pair.b());

	}

}
