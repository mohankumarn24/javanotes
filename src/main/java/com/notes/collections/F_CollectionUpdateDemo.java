package com.notes.collections;

import java.util.*;

public class F_CollectionUpdateDemo {

    public static void main(String[] args) {

        // Collection update
    	collectionUpdate();

        // Map getOrDefault()
        mapGetOrDefault();
    }

	private static void collectionUpdate() {
		
		/*
    	 * ✔️ Direct update supported
    	 *  	- ArrayList, LinkedList → set(index, value)
    	 *  	- Map → put(key, value) or replace()
    	 *  
    	 *  ❌ No direct update (must remove + add)
    	 *  	- PriorityQueue → heap structure
    	 *  	- ArrayDeque → no index access
    	 *  	- Set → no index + uniqueness constraint
    	*/
    	
        // 1. ArrayList → update using index
        List<String> arrayList = new ArrayList<>(List.of("A", "B", "C"));
        arrayList.set(1, "B_UPDATED");  										// replace index 1
        System.out.println("ArrayList: " + arrayList);							// ArrayList: [A, B_UPDATED, C]


        // 2. LinkedList → same as List (index-based)
        List<String> linkedList = new LinkedList<>(List.of("X", "Y", "Z"));
        linkedList.set(2, "Z_UPDATED");  										// replace index 2
        System.out.println("LinkedList: " + linkedList);						// LinkedList: [X, Y, Z_UPDATED]


        // 3. PriorityQueue → no direct update ❌
        // Must remove + add (heap structure)
        PriorityQueue<Integer> pq = new PriorityQueue<>(List.of(10, 20, 30));
        pq.remove(20);          												// remove old value
        pq.add(25);             												// add new value
        System.out.println("PriorityQueue: " + pq);								// PriorityQueue: [10, 30, 25]


        // 4. ArrayDeque → no random access ❌
        // Use remove + add
        Deque<String> deque = new ArrayDeque<>(List.of("D1", "D2", "D3"));
        deque.remove("D2");     												// remove old
        deque.add("D2_UPDATED"); 												// add new (end)
        System.out.println("ArrayDeque: " + deque);								// ArrayDeque: [D1, D3, D2_UPDATED]


        // 5. Set → no index, no duplicates ❌
        // Remove + add
        Set<String> set = new HashSet<>(Set.of("S1", "S2", "S3"));
        set.remove("S2");
        set.add("S2_UPDATED");
        System.out.println("Set: " + set);										// Set: [S3, S2_UPDATED, S1]


        // 6. Map → direct update using key ✅
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);

        // map.replace("B", 20);
        map.put("B", 20);  														// overwrite existing key

        System.out.println("Map: " + map);										// Map: {A=1, B=20}
	}
    
    public static void mapGetOrDefault() {
    	
    	System.out.print("\n===== map.getOrDefault('A', 0) =====\n");

        Map<String, Integer> marks = new HashMap<>();

        marks.put("Math", 90);
        marks.put("Science", 80);

        // 1. Key exists → returns actual value
        int mathMarks = marks.getOrDefault("Math", 0);
        System.out.println("Math: " + mathMarks);								// Math: 90

        // 2. Key does NOT exist → returns default
        int englishMarks = marks.getOrDefault("English", 0);
        System.out.println("English: " + englishMarks);							// English: 0

        // 3. Classic use case → counting frequency
        String[] words = {"apple", "banana", "apple", "orange", "banana", "apple"};

        Map<String, Integer> freqMap = new HashMap<>();

        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        System.out.println("Frequency Map: " + freqMap);						// Frequency Map: {banana=2, orange=1, apple=3}
    }
}

/*
ArrayList: [A, B_UPDATED, C]
LinkedList: [X, Y, Z_UPDATED]
PriorityQueue: [10, 30, 25]
ArrayDeque: [D1, D3, D2_UPDATED]
Set: [S3, S2_UPDATED, S1]
Map: {A=1, B=20}

===== map.getOrDefault('A', 0) =====
Math: 90
English: 0
Frequency Map: {banana=2, orange=1, apple=3}
*/
