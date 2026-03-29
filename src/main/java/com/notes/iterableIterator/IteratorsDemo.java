package com.notes.iterableIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IteratorsDemo{
	public static void main(String[] args) {
		
		// Basic Explicit Iterator Usage
		System.out.println("Basic Explicit Iterator Usage:");
        List<String> names = new ArrayList<>();
        names.add("Mohan");
        names.add("John");
        names.add("Sam");

        Iterator<String> iterator = names.iterator();  			// get iterator
        while (iterator.hasNext()) {   							// check next element
            System.out.println(iterator.next());				// get next element
        }
        
        // Explicit Iterator with remove()
        System.out.println("\nExplicit Iterator with remove():");
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer value = it.next();
            if (value == 20) {
                it.remove();   									// safe removal
            }
        }
        System.out.println(list);
        
        // Explicit Iterator on Custom Iterable
        System.out.println("\nExplicit Iterator on Custom Iterable:");
        String[] arr = {"Mohan", "John", "Sam"};
        NamesCollection collection = new NamesCollection(arr);

        Iterator<String> it2 = collection.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next());
        }
        
        // Explicit Iterator on Set (no indexing)
        System.out.println("\nExplicit Iterator on Set (no indexing):");
        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("C");

        Iterator<String> it3 = set.iterator();
        while (it3.hasNext()) {
            System.out.println(it3.next());
        }
        
        // Explicit Iterator on Map (using entrySet)
        System.out.println("\nExplicit Iterator on Map (using entrySet):");
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");

        Iterator<Map.Entry<Integer, String>> it4 = map.entrySet().iterator();
        while (it4.hasNext()) {
            Map.Entry<Integer, String> entry = it4.next();
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
	}
}
