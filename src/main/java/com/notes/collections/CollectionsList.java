package com.notes.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class CollectionsList {

	public static void main(String[] args) {
		
		/**
		 * List: ArrayList, LinkedList, Vector, Stack
		 * Queue: PriorityQueue, ArrayDeque
		 * Set: HashSet, LinkedHashSet, TreeSet
		 * Map: HashMap, LinkedHashMap, TreeMap
		 * HashTable, EnumSet, EnumMap
		 * 
		 * 
		 * -- ArrayList --
		 * - add, addAll 
		 * - remove, removeAll, removeIf, retainAll, replaceAll 
		 * - set, get
		 * - clear, isEmpty, size, clone, contains, containsAll, equals
		 * - indexOf, lastIndexOf, toArray(), sort
		 * - for, foreach, foreach using lambda exp., iterator interface, listIterator interface
		 * 
		 * 
		 * -- LinkedList --
		 * - add, addAll, addFirst, addLast
		 * - remove, removeAll, removeIf, retainAll, replaceAll, removeFirst, removeLast, removeFirstOccurrence, removeLastOccurrence
		 * - set, get, getFirst, getLast
		 * - clear, isEmpty, size, clone, contains, containsAll, equals
		 * - indexOf, lastIndexOf, toArray(), sort
		 * - for, foreach, foreach using lambda exp., iterator interface, listIterator interface
		 * - listIterator, descendingIterator
		 * - poll, pollFirst, pollLast (Returns null if a list is empty)
		 * - peek, peekFirst, peekLast (Throws NoSuchElementException is list is empty)
		 * - Queue: offer, offerFirst, offerLast (https://stackoverflow.com/questions/15591431/difference-between-offer-and-add-in-priority-queue-in-java)
		 * - Stack: push, pop
		 * 
		 * 
		 * -- Vector -- 
		 * - add, addAll, addElement
		 * - capacity, clear, clone, contains, containsAll, copyInto
		 * - elementAt, elements, ensureCapacity
		 * - firstElement, get, indexOf, insertElementAt, isEmpty
		 * - iterator, lastElement, lastIndexOf, listIterator
		 * - remove, removeAll, removeAllElements, removeElement, removeElementAt, removeIf, replaceAll, retainAll
		 * - set, setElementAt, setSize, size, sort, spliterator, toArray, trimToSize
		 * 
		 * 
		 * -- Stack -- 
		 * - add, addAll, addElement
		 * - capacity, clear, clone, contains, containsAll, copyInto
		 * - elementAt, elements, empty, ensureCapacity
		 * - firstElement, get, indexOf, insertElementAt, isEmpty
		 * - iterator, lastElement, lastIndexOf, listIterator
		 * - push, pop, peek
		 * - remove, removeAll, removeAllElements, removeElement, removeElementAt, removeIf, replaceAll, retainAll
		 * - set, search, setElementAt, setSize, size, sort, spliterator, toArray, trimToSize
		 * 
		 *
		 * -- PriorityQueue -- 
		 * - add, addAll
		 * - remove, removeAll, removeIf, retainAll
		 * - clear, isEmpty, size, contains, containsAll
		 * - elements, equals
		 * - iterator, spliterator, toArray
		 * - peek, poll, offer
		 * 
		 * 
		 * -- ArrayDeque -- 
		 * - add, addAll, addFirst, addLast
		 * - remove, removeAll, removeFirst, removeLast, removeFirstOccurence, removeLastOccurence, removeIf, retainAll
		 * - clear, isEmpty, size, clone, contains, containsAll
		 * - element, equals, getFirst, getLast, toArray
		 * - iterator, spliterator, descendingIterator
		 * - poll, pollFirst, pollLast
		 * - peek, peekFirst, peekLast
		 * - offer, offerFirst, offerLast
		 * - push, pop
		 * 
		 * 
		 * -- HashSet --
		 * - add, addAll
		 * - remove, removeAll, removeIf, retainAll
		 * - clear, isEmpty, size, clone, contains, containsAll
		 * - iterator, splitertor, toArray
		 * 
		 * 
		 * -- LinkedHashSet --
		 * - add, addAll 
		 * - remove, removeAll, removeIf, retainAll 
		 * - clear, isEmpty, size, clone, contains, containsAll, equals
		 * - spliterator, toArray()
		 * - for, foreach, foreach using lambda exp., iterator interface
		 * 
		 * 
		 * -- TreeSet --
		 * - add, addAll 
		 * - remove, removeAll, removeIf, retainAll 
		 * - set, get
		 * - clear, isEmpty, size, clone, contains, containsAll, equals
		 * - indexOf, lastIndexOf, sort, toArray()
		 * - for, foreach, foreach using lambda exp., iterator interface, descendingIterator
		 * - ceiling, floor, first, last, higher, lower
		 * - poll, pollFirst, pollLast
		 * - subSet, headSet, tailSet, descendingSet
		 * 
		 * 
		 * -- HashMap --
		 * - put, putAll, putIfAbsent
		 * - remove, replace, replaceAll
		 * - clear, isEmpty, size, clone, compute, computeIfPresent, computeIfAbsent
		 * - get, getOrDefault
		 * - containsKey, containsValue
		 * - entrySet, keySet, values
		 * - forEach, merge 
		 * 
		 * 
		 * -- LinkedHashMap --
		 * - put, putAll, putIfAbsent
		 * - remove, replace, replaceAll
		 * - clear, isEmpty, size, clone, computeIfPresent, computeIfAbsent
		 * - get, getOrDefault
		 * - containsKey, containsValue
		 * - entrySet, keySet, values
		 * - forEach, merge 
		 * 
		 * 
		 * -- TreeMap --
		 * - put, putAll, putIfAbsent
		 * - remove, replace, replaceAll
		 * - clear, isEmpty, size, clone, compute, computeIfPresent, computeIfAbsent
		 * - get, getOrDefault
		 * - entrySet, containsKey, containsValue, keySet, values
		 * - forEach, merge, comparator
		 * - ceilingEntry, floorEntry, higherEntry, lowerEntry, firstEntry, lastEntry
		 * - ceilingKey, floorKey, higherKey, lowerKey, firstKey, lastKey, descendingKeySet, navigableKeySet
		 * - pollFirstEntry, pollLastEntry
		 * - headMap, tailMap, subMap, descendingMap
		 * 
		 */
		

		String method = "collectionsArrayList";
		switch (method) {
			case "collectionsArrayList":
				collectionsArrayList();
				break;
			case "collectionsLinkedList":
				collectionsLinkedList();
				break;
			case "collectionsVector":
				collectionsVector();
				break;
			case "collectionsStack":
				collectionsStack();
				break;
			case "collectionsPriorityQueue":
				collectionsPriorityQueue();
				break;
			case "collectionsDeQueue":
				collectionsDeQueue();
				break;
			case "collectionsHashSet":
				collectionsHashSet();
				break;
			case "collectionsLinkedHashSet":
				collectionsLinkedHashSet();
				break;
			case "collectionsTreeSet":
				collectionsTreeSet();
				break;
			case "collectionsHashMap":
				collectionsHashMap();
				break;
			case "collectionsLinkedHashMap":
				collectionsLinkedHashMap();
				break;
			case "collectionsTreeMap":
				collectionsTreeMap();
				break;
			case "collectionsHashTable":
				collectionsHashTable(); // legacy
				break;
			case "collectionsEnumSet":
				collectionsEnumSet(); // todo
				break;
			case "collectionsEnumMap":
				collectionsEnumMap(); // todo
				break;
			default:
				collectionsArrayList();
				break;
		}
	}

	private static void collectionsArrayList() {

		System.out.println("--- ArrayList ---");
		List<Integer> linkedlist = new ArrayList<>();
		System.out.println(String.format("is linkedlist instanceof ArrayList? %b", linkedlist instanceof ArrayList));
		ArrayList<Integer> arrayList = (ArrayList<Integer>) linkedlist; // we can can access all classes of ArrayList by typecasting
		arrayList.add(12);
		arrayList.add(51);
		arrayList.add(23);
		arrayList.add(null); //supports adding null value

		System.out.println(String.format("Print ArrayList: %s", arrayList));
		
		// Size
		System.out.println(String.format("Size: %d", arrayList.size()));
		
		// isEmpty
		System.out.println(String.format("isEmpty: %b", arrayList.isEmpty()));
		
		// change value
		arrayList.set(3, 45);
		System.out.println(String.format("Print ArrayList with changed values: %s", arrayList));
		
		// sort
		Collections.sort(arrayList);
		System.out.println(String.format("Print ArrayList with sorted values: %s", arrayList));
				
		// Iterate using for loop
		for (int i = 0; i < arrayList.size(); i++) {
			System.out.println(String.format("Iterate using for loop: %d", arrayList.get(i)));
		}
		
		// Iterate using foreach loop
		for (Integer value: arrayList) {
			System.out.println(String.format("Iterate using foreach loop: %d", value));
		}
		
		// Iterate using lambda expression
		arrayList.stream().forEach(t -> System.out.println(String.format("Iterate using lambda expression: %d", t)));		
		
		// Iterate using Iterator
		Iterator<Integer> iterator = arrayList.iterator();
		while (iterator.hasNext()) {
			System.out.println(String.format("Iterate using Iterator: %d", iterator.next()));
		}
		
		// Iterate using ListIterator
		ListIterator<Integer> listIterator = arrayList.listIterator();
		while (listIterator.hasNext()) {
			System.out.println(String.format("Iterate using ListIterator: %d", listIterator.next()));
		}
		
		// Iterate using forEachRemaining
		listIterator = arrayList.listIterator(); // Need to set again!
		listIterator.forEachRemaining(t -> System.out.println(String.format("Iterate using forEachRemaining: %d", t)));
		
		// Iterate in reverse order using ListIterator
		ListIterator<Integer> reverseIterator = arrayList.listIterator(arrayList.size());
		while (reverseIterator.hasPrevious()) {
			System.out.println(String.format("Iterate in reverse order using ListIterator: %d", reverseIterator.previous()));
		}
		
		// sort by last digit
		Collections.sort(arrayList, (i, j) -> (i % 10 > j %10) ? 1 : -1);
		System.out.println(String.format("Print ArrayList with sorted values on second digit: %s", arrayList));	
		
		// Adding elements
		ArrayList<String> namesList1 = new ArrayList<String>();
		namesList1.add("A1"); // Add element at the end
		namesList1.add("B1");
		namesList1.add("C1");
		namesList1.add(1, "AA1"); // Add element at position 1
		System.out.println(String.format("Add elements: %s", namesList1));
		
		// Adding one list to another list at the end
		ArrayList<String> namesList2 = new ArrayList<String>();
		namesList2.add("A2");
		namesList2.add("B2");
		namesList2.add("C2");
		namesList1.addAll(namesList2);
		// namesList1.addAll(1, namesList2); // add elements at position 1
		System.out.println(String.format("Adding one list to another list at the end: %s", namesList1));
		
		// Remove elements by value or position
		namesList1.remove("AA1"); // removes all elements with value AA1
		// namesList1.remove(1); // remove element at position
		System.out.println(String.format("Remove elements by value or position: %s", namesList1));
		
		// Remove array of elements from anonther array
		namesList1.removeAll(namesList2);
		System.out.println(String.format("Remove array of elements from anonther array: %s", namesList1));
		
        // Removing elements on the basis of specified condition
		namesList1.removeIf(str -> str.contains("C1"));   //Here, we are using Lambda expression 
		System.out.println(String.format("Removing elements on the basis of specified condition: %s", namesList1)); //Here, we are using Lambda expression
		
        // Removing all the elements available in the list  
		namesList1.clear();  
		System.out.println(String.format("Removing all the elements available in the list: %s", namesList1));
		
		// Retain only the elements that are present in both al and al2  
		ArrayList<String> al = new ArrayList<>();
        al.add("A");  
        al.add("B");  
        al.add("C");  
        ArrayList<String> al2 = new ArrayList<String>();  
        al2.add("A");  
        al2.add("E"); 
        al.retainAll(al2);
        System.out.println(String.format("Retain only the elements that are present in both al and al2: %s", al));
        
        // Convert list to array
        ArrayList<String> al3 = new ArrayList<>();
        al3.add("A");  
        al3.add("B");  
        al3.add("C");
        Object[] arrays = al3.toArray();
        for (Object array: arrays) {
        	System.out.println(String.format("Convert list to array: %s", array));
        }
        
        // contains
        System.out.println(String.format("Contains: %s", al3.contains("A")));
        
        // indexOf
        System.out.println(String.format("indexOf: %s", al3.indexOf("B")));
        
        // lastIndexOf
        System.out.println(String.format("indexOf: %s", al3.lastIndexOf("B")));
        System.out.println(String.format("indexOf: %s", al3.lastIndexOf("x")));
	}

	private static void collectionsLinkedList() {
		
		// add first, add last
		LinkedList<String> linkedList = new LinkedList<>();
		linkedList.add("A");
		linkedList.add("B");
		linkedList.add("C");
		linkedList.addFirst("first");
		linkedList.addLast("last");
		System.out.println(String.format("add first, add last: %s", linkedList));
		
		// Get first, get last
		System.out.println(String.format("First: %s", linkedList.getFirst()));
		System.out.println(String.format("Last: %s", linkedList.getLast()));
		
		// peek, peek first, peek last
		System.out.println(String.format("Peek: %s", linkedList.peek())); 				// retrieves the first element of a list
		System.out.println(String.format("Peek first: %s", linkedList.peekFirst())); 	// retrieves the first element of a list
		System.out.println(String.format("Peek last: %s", linkedList.peekLast())); 		// retrieves the last element of a list
		
		// poll, poll first, poll last
		System.out.println(String.format("Before poll: %s", linkedList));
		System.out.println(String.format("Poll: %s", linkedList.poll())); 				// retrieves the first element of a list and removes it. Returns null if a list is empty
		System.out.println(String.format("Poll first: %s", linkedList.pollFirst())); 	// retrieves the first element of a list and removes it. Returns null if a list is empty
		System.out.println(String.format("Poll last: %s", linkedList.pollLast())); 		// retrieves the last element of a list	and removes it. Returns null if a list is empty
		System.out.println(String.format("After poll: %s", linkedList));
		
		// poll, pollFirst, pollLast
		linkedList.clear();
		linkedList.addFirst("first");
		linkedList.add("A");
		linkedList.add("A");		
		linkedList.add("B");
		linkedList.add("B");
		linkedList.add("C");
		linkedList.add("C");
		linkedList.addLast("last");
		
		linkedList.removeFirst();
		linkedList.removeLast();		
		boolean status = linkedList.removeFirstOccurrence("A");
		status = linkedList.removeLastOccurrence("C");
		linkedList.remove();

		status = linkedList.removeLastOccurrence("C");
		System.out.println(String.format("Before poll: %s", linkedList));
		System.out.println(String.format("Poll: %s", linkedList.poll())); 				// retrieves the first element of a list and removes it. Returns null if a list is empty
		System.out.println(String.format("Poll first: %s", linkedList.pollFirst())); 	// retrieves the first element of a list and removes it. Returns null if a list is empty
		System.out.println(String.format("Poll last: %s", linkedList.pollLast())); 		// retrieves the last element of a list	and removes it. Returns null if a list is empty
		System.out.println(String.format("After poll: %s", linkedList));
		
		// remove, removeFirst, removeLast
		linkedList.clear();
		linkedList.addFirst("first");
		linkedList.add("X");
		linkedList.add("A");
		linkedList.add("A");		
		linkedList.add("B");
		linkedList.add("B");
		linkedList.add("C");
		linkedList.add("C");
		linkedList.addLast("last");
		
		System.out.println(String.format("Before remove: %s", linkedList));
		linkedList.removeFirst();
		linkedList.removeLast();		
		status = linkedList.removeFirstOccurrence("A");
		status = linkedList.removeLastOccurrence("C");
		linkedList.remove();

		System.out.println(String.format("After remove: %s", linkedList));
		
		// Object poll = (new LinkedList<>()).poll(); 		// retrieves the first element of a list and removes it. Returns null if a list is empty
		// Object remove = (new LinkedList<>()).remove();   // retrieves the first element of a list and removes it. Throws NoSuchElementException is list is empty
		
		// offer, offerfirst, offerLast
		linkedList.clear();
		linkedList.offerFirst("first");
		linkedList.offer("A");		
		linkedList.offer("B");
		linkedList.offer("C");
		linkedList.offerLast("last");

		System.out.println(String.format("Using offer to add elements: %s", linkedList));
	}
	
	private static void collectionsVector() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsStack() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsPriorityQueue() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsDeQueue() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsHashSet() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsLinkedHashSet() {
		
	}

	private static void collectionsTreeSet() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsHashMap() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsLinkedHashMap() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsTreeMap() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsHashTable() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsEnumSet() {
		// TODO Auto-generated method stub
		
	}

	private static void collectionsEnumMap() {
		// TODO Auto-generated method stub
		
	}	
}
