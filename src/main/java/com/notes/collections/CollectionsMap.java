package com.notes.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CollectionsMap {

	public static void main(String[] args) {

		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 100);
		map.put(2, 200);
		map.put(3, 300);

		// iterate map
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			System.out.println(String.format("Key=%d and Value=%d", entry.getKey(), entry.getValue()));
		}

		Set<Integer> keySet = map.keySet();
		boolean containsKey = map.containsKey(1);
		boolean containsValue = map.containsValue(100);

		System.out.println();
		System.out.println("Keyset: " + keySet);
		System.out.println("Is contains key 1: " + containsKey);
		System.out.println("Is contains value 100: " + containsValue);

		// forEach using Java 11
		System.out.println();
		map.forEach((key, value) -> System.out.println("key: " + key + " value: " + value));
	}
}
