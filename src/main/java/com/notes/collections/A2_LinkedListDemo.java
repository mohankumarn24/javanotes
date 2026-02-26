package com.notes.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class A2_LinkedListDemo {
	
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

/*
========================================================
JAVA COLLECTIONS – TIME & SPACE COMPLEXITY (Java 8+)
========================================================

--------------------------------------------------------
LIST
--------------------------------------------------------
	Collection   | get(index) | add(end)        | add(middle) | remove | search | space
	-----------------------------------------------------------------------------------
	ArrayList    | O(1)       | O(1) amortized | O(n)        | O(n)   | O(n)   | O(n)
	LinkedList   | O(n)       | O(1)           | O(1)*       | O(1)*  | O(n)   | O(n)
	Vector       | O(1)       | O(1) amortized | O(n)        | O(n)   | O(n)   | O(n)
	Stack        | O(1)       | O(1)           | -           | O(1)   | O(n)   | O(n)

--------------------------------------------------------
SET
--------------------------------------------------------
	Collection        | add       | remove    | contains  | space
	-------------------------------------------------------------
	HashSet           | O(1)      | O(1)      | O(1)      | O(n)
	LinkedHashSet     | O(1)      | O(1)      | O(1)      | O(n)
	TreeSet           | O(log n)  | O(log n)  | O(log n)  | O(n)
	EnumSet           | O(1)      | O(1)      | O(1)      | O(1)

--------------------------------------------------------
QUEUE / DEQUE
--------------------------------------------------------
	Collection        | offer     | poll      | peek      | space
	-------------------------------------------------------------
	ArrayDeque        | O(1)      | O(1)      | O(1)      | O(n)
	LinkedList        | O(1)      | O(1)      | O(1)      | O(n)
	PriorityQueue     | O(log n)  | O(log n)  | O(1)      | O(n)

--------------------------------------------------------
MAP
--------------------------------------------------------
	Collection            | get       | put       | remove    | space
	-----------------------------------------------------------------
	HashMap               | O(1)      | O(1)      | O(1)      | O(n)
	LinkedHashMap         | O(1)      | O(1)      | O(1)      | O(n)
	TreeMap               | O(log n)  | O(log n)  | O(log n)  | O(n)
	Hashtable             | O(1)      | O(1)      | O(1)      | O(n)
	ConcurrentHashMap     | O(1)      | O(1)      | O(1)      | O(n)
	EnumMap               | O(1)      | O(1)      | O(1)      | O(1)

--------------------------------------------------------
CONCURRENT COLLECTIONS
--------------------------------------------------------
	Collection                  | get  | add/put | remove | space
	-------------------------------------------------------------
	CopyOnWriteArrayList         | O(1) | O(n)    | O(n)   | O(n)
	CopyOnWriteArraySet          | O(n) | O(n)    | O(n)   | O(n)
	ConcurrentLinkedQueue        | O(1) | O(1)    | O(1)   | O(n)
	ArrayBlockingQueue           | O(1) | O(1)    | O(1)   | O(n)
	LinkedBlockingQueue          | O(1) | O(1)    | O(1)   | O(n)

--------------------------------------------------------
SEARCH / SORT
--------------------------------------------------------
	 Algorithm        | Time Complexity | Space Complexity                         
	------------------|-----------------|-----------------
	 Bubble Sort      | O(n²)           | O(1)                                     
	 Selection Sort   | O(n²)           | O(1)                                     
	 Insertion Sort   | O(n²)           | O(1)                                     
	 Merge Sort       | O(n log n)      | O(n)                                     
	 Radix Sort       | O(n × k)        | O(n + k)                                 
	 Binary Search    | O(log n)        | O(1) (iterative) / O(log n) (recursive)  

--------------------------------------------------------
6) SUMMARY
--------------------------------------------------------
	Use Case                         | Best Choice              | Reason
	------------------------------------------------------------------------------
	Fast lookup                      | HashMap                  | O(1) access
	Sorted data                      | TreeMap / TreeSet        | Red-Black Tree
	Insertion order required         | LinkedHashMap            | Predictable iteration
	Thread-safe high concurrency     | ConcurrentHashMap        | No global lock
	Stack implementation             | ArrayDeque               | Faster than Stack
	Queue implementation             | ArrayDeque               | No locking overhead
	Read-heavy list                  | CopyOnWriteArrayList     | Lock-free reads

--------------------------------------------------------
IMPORTANT INTERVIEW NOTES
--------------------------------------------------------
	- HashMap worst case O(n) → Java 8 converts bucket to Red-Black Tree → O(log n)
	- ArrayList resize is O(n) but amortized insertion is O(1)
	- LinkedList has high memory overhead due to node pointers
	- ConcurrentHashMap ≠ synchronized HashMap (uses CAS / bucket locking)
	- Prefer ArrayDeque over Stack in modern Java
	- O(1) only if node reference is known
	- HashMap worst case O(n) → Java 8 tree bin O(log n)
*/
