
package com.rayzr522.bitzapi.utils;

public class Pair<K, V> {

	private K	a;
	private V	b;

	public Pair(K a, V b) {

		this.a = a;
		this.b = b;

	}

	public K a() {
		return a;
	}

	public V b() {
		return b;
	}

	public void a(K a) {
		this.a = a;
	}

	public void b(V b) {
		this.b = b;
	}

}
