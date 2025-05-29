package com.notes.collections;

import java.util.HashMap;
import java.util.Map;

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

		// forEach using Java 11
		map.forEach((key, value) -> System.out.println("key: " + key + " value: " + value));
	}

}
