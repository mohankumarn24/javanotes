package com.notes.programs;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a Doubly Linked List
 * 
 * Each node has:
 * 		prev ← [node] → next
 * 
 * And we keep 2 dummy nodes:
 * 		head <-> ... <-> tail
 * 
 * head.next → most recently used (MRU)
 * tail.prev → least recently used (LRU)		
 */
class Node {
	
	int key;
	int value;
	
	Node prev;
	Node next;

	Node(int k, int v) {
		key = k;
		value = v;
	}
}

/**
 * Where is LRU used?
 * 	- Redis cache eviction
 *  - OS page replacement
 *  - Browser caching
 */
class LRUCache {
	
	private Node head; 
	private Node tail;
	
	private int capacity;
	private Map<Integer, Node> map;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		this.map = new HashMap<>();

		head = new Node(0, 0);
		tail = new Node(0, 0);

		head.next = tail;
		tail.prev = head;
	}
	
	/**
	 * Always insert right after head (MRU position)
	 * 
	 * Suppose:
	 * 		head <-> A <-> B <-> tail
	 * 
	 * Insert new node X:
	 * 	Step 1:
	 * 		node.next = head.next;
	 *  Step 2:
	 *  	node.prev = head;
	 *  Step 3:
	 *  	head.next.prev = node;
	 *  Step 4:
	 *  	head.next = node;
	 *  Final:
	 *  	head <-> X <-> A <-> B <-> tail
	 *  
	 */
	private void insert(Node node) {
	    node.prev = head;								// head <-  X
	    node.next = head.next;							// head <-  X ->  A

	    head.next.prev = node;							// head <-  X <-> A
	    head.next = node;								// head <-> X <-> A
	}

	/**
	 * head <-> A <-> B <-> C <-> tail
	 * 
	 * Suppose removing B:
	 * 	Before:
	 * 		A <-> B <-> C
	 * 	We want:
	 * 		A <-> C
	 * 	
	 * Step 1:
	 * 		node.prev.next = node.next;
	 * Step 2:
	 * 		node.next.prev = node.prev;
	 * 
	 * B is disconnected. Skip the node from both sides
	 * 
	 */
	private void remove(Node node) {
	    node.prev.next = node.next;
	    node.next.prev = node.prev;
	}

	/**
	 * 1. Key already exists
	 * 		- map.get → O(1)
	 * 		- remove → O(1)
	 * 		- insert → O(1)
	 * 
	 *    Final: O(1)
	 * 
	 * 2. New key
	 * 		- map.put → O(1)
	 * 		- insert → O(1)
	 * 		- eviction (if needed):
	 * 			-- access tail.prev → O(1)
	 * 			-- remove → O(1)
	 * 			-- map.remove → O(1)
	 * 
	 *    Final: O(1)
	 *
	 */
	public void put(int key, int value) {
	    // If key already exists, remove the old node from the linked list
	    // (we will reinsert it as most recently used)
	    if (map.containsKey(key)) {
	    	Node node = map.get(key);
	        remove(node);
	    }

	    // Create a new node with updated value
	    Node node = new Node(key, value);

	    // Insert node at the head (mark as most recently used)
	    insert(node);
	    
	    // Put the new node in hashmap for O(1) access
	    map.put(key, node);

	    // If capacity exceeded, remove least recently used (LRU) node
	    if (map.size() > capacity) {
	        // LRU node is just before tail
	        Node lruNode = tail.prev;

	        // Remove it from linked list
	        remove(lruNode);

	        // Remove it from hashmap as well
	        map.remove(lruNode.key);
	    }
	}
	
	/**
	 * HashMap lookup → O(1)
	 * 	- remove(node) → O(1)
	 * 	- insert(node) → O(1)
	 * 
	 * 	Final: O(1)
	 * 
	 */
	public int get(int key) {
	    // If key is not present in cache, return -1 (cache miss)
	    if (!map.containsKey(key)) {
	    	return -1;
	    }

	    // Fetch the node from hashmap (O(1))
	    Node node = map.get(key);

	    // Since this key is accessed, it becomes most recently used
	    // Remove it from current position in linked list
	    remove(node);

	    // Re-insert it at head (mark as most recently used)
	    insert(node);

	    // Return the value
	    return node.value;
	}

	public void printCache() {
		
	    Node curr = head.next;

	    System.out.print("Cache: [");
	    while (curr != tail) {
	        System.out.print(curr.key);

	        if (curr.next != tail) {
	            System.out.print(", "); // comma between elements
	        }

	        curr = curr.next;
	    }

	    System.out.println("]");
	    System.out.println();
	}
}

public class LRUCacheDemo {
	
	public static void main(String[] args) {
		
	    // Create LRU Cache with capacity = 2
	    LRUCache cache = new LRUCache(2);

	    // Step 1: Add key 1
	    cache.put(1, 1);
	    cache.printCache();   												// Cache: [1]

	    // Step 2: Add key 2 (most recent comes first)
	    cache.put(2, 2);
	    cache.printCache();  					 							// Cache: [2, 1]

	    // Step 3: Access key 1 → becomes most recent
	    System.out.println("get(1): " + cache.get(1)); // returns 1
	    cache.printCache();   												// Cache: [1, 2]

	    // Step 4: Add key 3 → capacity exceeded → remove LRU (2)
	    cache.put(3, 3);
	    cache.printCache();   												// Cache: [3, 1]

	    // Step 5: Try to access key 2 → already removed
	    System.out.println("get(2): " + cache.get(2)); // returns -1
	    cache.printCache();   												// Cache: [3, 1]

	    // Step 6: Add key 4 → capacity exceeded → remove LRU (1)
	    cache.put(4, 4);
	    cache.printCache();   												// Cache: [4, 3]

	    // Step 7: Try to access key 1 → already removed
	    System.out.println("get(1): " + cache.get(1)); // returns -1
	    cache.printCache();   												// Cache: [4, 3]

	    // Step 8: Access key 3 → becomes most recent
	    System.out.println("get(3): " + cache.get(3)); // returns 3
	    cache.printCache();   												// Cache: [3, 4]

	    // Step 9: Access key 4 → becomes most recent
	    System.out.println("get(4): " + cache.get(4)); // returns 4
	    cache.printCache();   												// Cache: [4, 3]
	}
}

/*
class LRUCache {

    private int capacity;
    private LinkedList<Integer> list = new LinkedList<>();
    private Map<Integer, Integer> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        // move key to most recent
        list.remove((Integer) key);
        list.addLast(key);

        return map.get(key);
    }

    public void put(int key, int value) {

        if (map.containsKey(key)) {
            // remove old position
            list.remove((Integer) key);
        } else {
            // if full, remove LRU
            if (list.size() == capacity) {
                int lru = list.removeFirst();
                map.remove(lru);
            }
        }

        // add as most recent
        list.addLast(key);
        map.put(key, value);
    }
}
*/

/*
class LRUCache extends LinkedHashMap<Integer, Integer> {

	private static final long serialVersionUID = 1L;
	
	private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // access order
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
*/