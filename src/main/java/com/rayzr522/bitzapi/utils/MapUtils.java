
package com.rayzr522.bitzapi.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

	public static <K, V> HashMap<K, V> empty() {

		return new HashMap<K, V>();

	}

	@SuppressWarnings({
		"rawtypes", "unchecked"
	})

	public static void add(Pair pair, HashMap map) {

		map.put(pair.a(), pair.b());

	}

	public static Map<String, Object> serializerMap() {
		return new HashMap<String, Object>();
	}

}
