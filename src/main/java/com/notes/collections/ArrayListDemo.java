package com.notes.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrayListDemo {

	public static void main(String[] args) {

		ArrayList<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		
		// sort
		Collections.sort(list);
		
        // contains
		System.out.println("contains");
        System.out.println(String.format("Contains A? %s", list.contains("A")));
        
        // indexOf
        System.out.println("\nindexOf");
        System.out.println(String.format("indexOf A: %s", list.indexOf("A")));
        
        // size
        System.out.println("\nsize");
        System.out.println(String.format("size: %d", list.size()));
        list.forEach(str ->  System.out.println(str));

		System.out.println("\nPrint using for loop");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		System.out.println("\nPrint using for-each loop");
		for (String str : list) {
			System.out.println(str);
		}
		
		System.out.println("\nPrint using iterator");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		System.out.println("\nPrint using list iterator");
		ListIterator<String> listIterator = list.listIterator(list.size());
		while (listIterator.hasPrevious()) {
			System.out.println(listIterator.previous());
		}
		
		System.out.println("\nPrint using forEach");
		list.forEach(str ->  System.out.println(str));
		
		System.out.println("\nPrint using forEachRemaining");
		Iterator<String> itr = list.iterator();
		itr.forEachRemaining(str -> System.out.println(str));
		
		System.out.println("\nAdd Hello at index 1");
		list.add(1, "hello");
		list.forEach(str ->  System.out.println(str));
		
		System.out.println("\nRemove element at index 1");
		list.remove(1);	// list.remove("hello");
		list.forEach(str ->  System.out.println(str));
				
		ArrayList<String> list2 = new ArrayList<>();
		list2.add("X");
		list2.add("Y");
		list2.add("Z");
		
		System.out.println("\nAppend another list at end");
		list.addAll(list2);
		list.forEach(str ->  System.out.println(str));
		
		System.out.println("\nRemove appended list");
		list.removeAll(list2);
		list.forEach(str ->  System.out.println(str));
		
		System.out.println("\nAppend another list at index 1");
		list.addAll(1, list2);
		list.forEach(str ->  System.out.println(str));
		
		System.out.println("\nRemove appended list at index 1");
		list.removeAll(list2);
		list.forEach(str ->  System.out.println(str));
		
		System.out.println("\nRemove if");
		list.add("Baba Yaga");
		list.removeIf(str -> str.contains("Baba Yaga"));
		list.forEach(str ->  System.out.println(str));
		
		System.out.println("\nRetainAll"); // retain elements present in both list
		ArrayList<String> list3 = new ArrayList<>();
		list3.add("A");
		list3.add("X");
		list.retainAll(list3);
		list.forEach(str ->  System.out.println(str));
        
		System.out.println("\nClear");	// remove all elements
		list.clear();
		System.out.println("Is list emepty? " + list.isEmpty());
	}
}
