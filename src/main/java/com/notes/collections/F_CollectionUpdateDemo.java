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
		 * 👉 Key Idea:
		 * Collections with index/key → support direct update
		 * Collections without index/order → require remove + add
		 *
		 * ✔️ Direct update supported
		 *   - List (ArrayList, LinkedList) → set(index, value)
		 *   - Map → put(key, value), replace()
		 *
		 * ❌ No direct update (must remove + add)
		 *   - PriorityQueue → heap structure (no index, maintains ordering)
		 *   - ArrayDeque → no random/index access
		 *   - Set → no index + uniqueness constraint (equals/hashCode based)
		 */
    	
        // 1. ArrayList → update using index
        List<String> arrayList = new ArrayList<>(List.of("A", "B", "C"));
        arrayList.set(1, "B_UPDATED");  										// replace index 1
        System.out.println("ArrayList	: " + arrayList);						// ArrayList: [A, B_UPDATED, C]


        // 2. LinkedList → same as List (index-based)
        List<String> linkedList = new LinkedList<>(List.of("X", "Y", "Z"));
        linkedList.set(2, "Z_UPDATED");  										// replace index 2
        System.out.println("LinkedList	: " + linkedList);						// LinkedList: [X, Y, Z_UPDATED]


        // 3. PriorityQueue → no direct update ❌
        // Must remove + add (heap structure, no index access)
        // Updating directly would break heap ordering
        PriorityQueue<Integer> pq = new PriorityQueue<>(List.of(10, 20, 30, 20));
        pq.remove(20);          												// removes only the first occurrence, not all occurrences
        pq.add(25);             												// add new value
        System.out.println("PriorityQueue	: " + pq);							// PriorityQueue: [10, 20, 30, 25]
        																		// The printed order is heap order, not guaranteed sorted order


        // 4. ArrayDeque → no random access ❌
        // Use remove + add
        Deque<String> deque = new ArrayDeque<>(List.of("D1", "D2", "D3", "D2"));
        deque.remove("D2");     												// removes only the first occurrence, not all occurrences
        deque.add("D2_UPDATED"); 												// adds at tail
        System.out.println("ArrayDeque	: " + deque);							// ArrayDeque: [D1, D3, D2, D2_UPDATED]


        // 5. Set → no index, no duplicates ❌
        // Update = remove + add (must match equals/hashCode of existing element)
        Set<String> set = new HashSet<>(Set.of("S1", "S2", "S3"));
        set.remove("S2");
        set.add("S2_UPDATED");
        System.out.println("Set		: " + set);									// Set: [S3, S2_UPDATED, S1]


        // 6. Map → direct update using key ✅
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);

        // map.replace("B", 20);												// replace() → only updates if key exists (safer in some cases)
        map.put("B", 20);  														// put() → insert or overwrite

        System.out.println("Map		: " + map);									// Map: {A=1, B=20}
	}
    
    public static void mapGetOrDefault() {
    	
    	System.out.print("\n===== map.getOrDefault('A', 0) =====\n");

        Map<String, Integer> marks = new HashMap<>();

        marks.put("Math", 90);
        marks.put("Science", 80);

        // 1. Key exists → returns actual value
        int mathMarks = marks.getOrDefault("Math", 0);
        System.out.println("Math	: " + mathMarks);							// Math: 90

        // 2. Key does NOT exist → returns default
        int englishMarks = marks.getOrDefault("English", 0);
        System.out.println("English	: " + englishMarks);						// English: 0

        // 3. Classic use case → counting frequency
        String[] fruits = {"apple", "banana", "apple", "orange", "banana", "apple"};
        
        // 3.a Using getOrDefault (classic approach)
        Map<String, Integer> freqMap = new HashMap<>();
        for (String fruit : fruits) {
            freqMap.put(fruit, freqMap.getOrDefault(fruit, 0) + 1);
        }
        System.out.println("Frequency Map (getOrDefault)	: " + freqMap);		// Frequency Map: {banana=2, orange=1, apple=3}
        
		// 3.b Using merge (cleaner, modern approach)
		Map<String, Integer> freqMap2 = new HashMap<>();
		
		for (String fruit : fruits) {
		    freqMap2.merge(fruit, 1, Integer::sum);
		}
		System.out.println("Frequency Map (merge)		: " + freqMap2);
		
		// 3.c Using computeIfAbsent (best for grouping / initialization)
		Map<String, List<String>> grouped = new HashMap<>();
		for (String fruit : fruits) {
		    grouped.computeIfAbsent(fruit, k -> new ArrayList<>()).add(fruit);
		}
		System.out.println("Grouped Map (computeIfAbsent)	: " + grouped);
    }
}

/*
ArrayList	: [A, B_UPDATED, C]
LinkedList	: [X, Y, Z_UPDATED]
PriorityQueue	: [10, 20, 30, 25]
ArrayDeque	: [D1, D3, D2, D2_UPDATED]
Set		: [S3, S2_UPDATED, S1]
Map		: {A=1, B=20}

===== map.getOrDefault('A', 0) =====
Math	: 90
English	: 0
Frequency Map (getOrDefault)	: {banana=2, orange=1, apple=3}
Frequency Map (merge)		: {orange=1, banana=2, apple=3}
Grouped Map (computeIfAbsent)	: {orange=[orange], banana=[banana, banana], apple=[apple, apple, apple]}

*/
