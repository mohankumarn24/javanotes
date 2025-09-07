package com.notes.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class SetDemo {

	public static void main(String[] args) {

		/*************************** Hashset ************************************/
		// Hashset
		System.out.println("--HashSet--");
		
		HashSet<String> set = new HashSet<>();
		set.add("A");
		set.add("B");
		set.add("C");
		set.add("A"); // duplicate not added

		// print HashSet
		System.out.println("print HashSet");
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		// remove element
		// add and remove elment
		System.out.println("\nadd and remove elment");
		set.add("Z"); 		// supports addAll, removeIf, removeAll, clear
		set.remove("Z");
		set.forEach(str -> System.out.println(str));
		
		/********************* LinkedHashSet ************************************/
		// LinkedHashSet
		System.out.println("\n--LinkedHashSet--");
		
		LinkedHashSet<String> linkedHashSet=new LinkedHashSet<>();
		linkedHashSet.add("One");    
		linkedHashSet.add("Two");    
		linkedHashSet.add("Three");   
		linkedHashSet.add("Four");  
		linkedHashSet.add("Five");
		
		// print LinkedHashSet
		System.out.println("print LinkedHashSet");
		linkedHashSet.forEach(str -> System.out.println(str));
		
		/********************* TreeSet ************************************/
		// TreeSet
		System.out.println("\n--TreeSet--");
		
		TreeSet<String> treeSet = new TreeSet<>();
		treeSet.add("A");
		treeSet.add("B");
		treeSet.add("C");
		
		// print TreeSet (default ascending order)
		System.out.println("print TreeSet (default ascending order)");
		treeSet.forEach(str -> System.out.println(str));
		
		// print TreeSet using descendingIterator
		System.out.println("\nprint TreeSet using descendingIterator");
		Iterator<String> iterator2 = treeSet.descendingIterator();
		while (iterator2.hasNext()) {
			System.out.println(iterator2.next());
		}
		
		// print lowest Value
		System.out.println("\nLowest Value: " + treeSet.pollFirst());    // Retrieves and removes the first (lowest) element,or returns null if this set is empty.
		
		// print highest  Value
		System.out.println("\nHighest Value: " + treeSet.pollLast());	//Retrieves and removes the first (lowest) element,or returns null if this set is empty.
		
	}
}
