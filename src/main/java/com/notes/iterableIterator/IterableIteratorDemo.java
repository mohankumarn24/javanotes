package com.notes.iterableIterator;

import java.util.*;

public class IterableIteratorDemo {
	public static void main(String[] args) {
		List<String> names = new ArrayList<>();
		names.add("Mohan");
		names.add("John");
		names.add("Sam");

		// Iterable: The collection itself
		Iterable<String> iterable = names;

		// Iterator: Actual cursor
		Iterator<String> iterator = iterable.iterator();

		while (iterator.hasNext()) {
			String name = iterator.next();
			System.out.println(name);
		}
	}
}

/*
Notes:
When to use Iterator?
 - When you want to remove elements while iterating. (for-each loop cannot remove items)
 - When working with fail-fast vs fail-safe behavior
 - When writing custom collections
*/