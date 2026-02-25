package com.notes.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {
	
	public static void main(String[] args) {
	
		LinkedList<String> list = new LinkedList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		
		// all arraylist methods + below methods
		
		// print
		list.forEach(str ->  System.out.println(str));
		
		// addFirst, addLast
		System.out.println("\naddFirst, addLast");
		list.addFirst("FIRST");
		list.addLast("LAST");
		list.forEach(str ->  System.out.println(str));
		
		// removeFirst, removeLast, removeFirstOccurrence, removeLastOccurrence
		System.out.println("\nremoveFirst, removeLast, removeFirstOccurrence, removeLastOccurrence");
		list.removeFirst();
		list.removeLast();
		list.removeFirstOccurrence("FIRST");
		list.removeLastOccurrence("LAST");
		list.forEach(str ->  System.out.println(str));
		
		// print in descending order
		System.out.println("\nprint in descending order");
		Iterator<String> iterator = list.descendingIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		// EXTRA
		// convert list to array
		System.out.println("\nconvert list to array");
		List<String> list2 = Arrays.asList("A", "B", "C");
		// String[] array = list2.toArray(new String[list2.size()]);
		String[] array = list2.toArray(String[]::new);
		for (String str : array) {
			System.out.println(str);
		}
	}
}
