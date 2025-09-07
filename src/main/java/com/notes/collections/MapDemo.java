package com.notes.collections;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapDemo {

	public static void main(String[] args) {

		// create map
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 100);
		map.put(2, 200);
		map.put(3,  300);
		
		// iterate map
		System.out.println("iterate map");
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			System.out.println(String.format("Key=%d and Value=%d", entry.getKey(), entry.getValue()));
		}

		Set<Integer> keySet = map.keySet();
		boolean containsKey = map.containsKey(1);
		boolean containsValue = map.containsValue(100);

		System.out.println("\n");
		System.out.println("Keyset: " + keySet);
		System.out.println("Is contains key 1: " + containsKey);
		System.out.println("Is contains value 100: " + containsValue);

		// forEach using Java 11
		System.out.println("\nforEach using Java 11");
		map.forEach((key, value) -> System.out.println("key: " + key + " value: " + value));
		
		// Traversing Map using iterator
		System.out.println("\nTraversing Map using iterator");
		Set set = map.entrySet();// Converting to Set so that we can traverse
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			// Converting to Map.Entry so that we can get key and value separately
			Map.Entry entry = (Map.Entry) itr.next();
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	    
		// Traversing Map using streams (ascending order)
		System.out.println("\nTraversing Map using streams (ascending order)");
        map.entrySet() 									// Returns a Set view of the mappings contained in this map  
        		.stream() 								// Returns a sequential Stream with this collection as its source  
        		.sorted(Map.Entry.comparingByKey()) 	// Sorted according to the provided Comparator  
        		.forEach(System.out::println); 			// Performs an action for each element of this stream 
        
		// Traversing Map using streams (descending order)
		System.out.println("\nTraversing Map using streams (descending order)");
        map.entrySet() 															// Returns a Set view of the mappings contained in this map  
        		.stream() 														// Returns a sequential Stream with this collection as its source  
        		.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())) 	// Sorted according to the provided Comparator  
        		.forEach(System.out::println); 									// Performs an action for each element of this stream  
        
        // Misc methods
        /**
         * map.putIfAbsent(3, 300);
		 * map.put(1, 100);				// duplicate key not added
		 * map.putAll(another map);
		 * map.remove(1);
		 * map.remove(1, 100);
		 * map.replace(2, 2000);
		 * map.replace(2, 200, 2000); 
         */
        
        /*********** LinkedHashMap  *************/
        System.out.println("\nLinkedHashMap");
        Map<Integer, String> lienkedHashMap = new LinkedHashMap<>();
        
        // Adding some student records to the LinkedHashMap  
        lienkedHashMap.put(1001, "John Smith");  
        lienkedHashMap.put(1002, "Emily Brown");  
        lienkedHashMap.put(1003, "Michael Johnson");  
        
        // Iterating LinkedHashMap
        System.out.println("Iterating LinkedHashMap");  
        for (Map.Entry<Integer, String> entry : lienkedHashMap.entrySet()) {  
            System.out.println(entry.getKey() + " " + entry.getValue());  
        }  
        
        
        /*********** LinkedHashMap  *************/
        System.out.println("\nTreeMap");
        Map<Integer, String> treeMap = new TreeMap<>();
        
        // Adding some student records to the LinkedHashMap  
        treeMap.put(1003, "Michael Johnson");  
        treeMap.put(1001, "John Smith");  
        treeMap.put(1002, "Emily Brown");  
        
        // Iterating LinkedHashMap
        System.out.println("Iterating TreeMap");  
        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {  
            System.out.println(entry.getKey() + " " + entry.getValue());  
        }         
	}
}

/*
iterate map
Key=1 and Value=100
Key=2 and Value=200
Key=3 and Value=300


Keyset: [1, 2, 3]
Is contains key 1: true
Is contains value 100: true

forEach using Java 11
key: 1 value: 100
key: 2 value: 200
key: 3 value: 300

Traversing Map using iterator
1 100
2 200
3 300

Traversing Map using streams (ascending order)
1=100
2=200
3=300

Traversing Map using streams (descending order)
3=300
2=200
1=100

LinkedHashMap
Iterating LinkedHashMap
1001 John Smith
1002 Emily Brown
1003 Michael Johnson

TreeMap
Iterating TreeMap
1001 John Smith
1002 Emily Brown
1003 Michael Johnson

*/