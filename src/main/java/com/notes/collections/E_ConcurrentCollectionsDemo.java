package com.notes.collections;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Demonstrates ALL important Java Concurrent Collections
 * Java 8+ (valid till Java 21)
 */
public class E_ConcurrentCollectionsDemo {

    public static void main(String[] args) throws Exception {
        
    	// List
        copyOnWriteArrayListExample();
        
        // Queue/Deque
        blockingQueueExample();				// Producer - Consumer
        blockingDequeExample();				// Double-ended Blocking Queue
        concurrentLinkedQueueExample();		// Non-blocking Queue
        
        // Set
        copyOnWriteArraySetExample();
        concurrentSkipListSetExample();		// Sorted Concurrent Set
        
        // Map
        concurrentHashMapExample();			// -- IMPORTANT --
        concurrentSkipListMapExample();		// Sorted Concurrent Map
    }

    /* ---------------------------------------------------
     * 1. CopyOnWriteArrayList
     * --------------------------------------------------- */
    private static void copyOnWriteArrayListExample() {
        System.out.println("=== CopyOnWriteArrayList ===");

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");

        for (String str : list) {
            list.add("C"); 						// Safe: no ConcurrentModificationException
        }

        System.out.println("List: " + list);	// List: [A, B, C, C]
    }
    
    /* ---------------------------------------------------
     * 2. BlockingQueue (Producer - Consumer)
     * --------------------------------------------------- */
    private static void blockingQueueExample() throws InterruptedException {
        System.out.println("\n=== BlockingQueue ===");

        // A pipe between producer and consumer that can hold max 3 items
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);	// Creates a thread-safe queue. Size = 3

        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                	// If queue has space → inserts immediately
                	// If queue is full → thread waits until consumer removes an element
                    queue.put(i);										// waits if queue is full
                    System.out.println("Produced: " + i);
                }
            } catch (InterruptedException ignored) {}
        };

        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                	// If queue has data → removes and returns item
                	// If queue is empty → thread waits until producer adds an element
                    System.out.println("Consumed: " + queue.take());	// waits if queue is empty
                }
            } catch (InterruptedException ignored) {}
        };

        new Thread(producer).start();
        new Thread(consumer).start();

        // Keeps main thread active
        // Allows producer & consumer to finish
        Thread.sleep(1000);
        
        /*
			Note: Print order ≠ execution order
			
			Consumed: 1
			Produced: 1
			Produced: 2
			Produced: 3
			Produced: 4
			Consumed: 2
			Consumed: 3
			Consumed: 4
			Consumed: 5
			Produced: 5
        */
    }
    
    /* ---------------------------------------------------
     * 3. BlockingDeque (Double-ended Blocking Queue)
     * --------------------------------------------------- */
    private static void blockingDequeExample() throws InterruptedException {
        System.out.println("\n=== BlockingDeque ===");

        BlockingDeque<String> deque = new LinkedBlockingDeque<>();

        deque.putFirst("First");
        deque.putLast("Last");

        System.out.println("Take First: " + deque.takeFirst());
        System.out.println("Take Last: " + deque.takeLast());
        
        /*
			Take First: First
			Take Last: Last
         */
    }
    
    /* ---------------------------------------------------
     * 4. ConcurrentLinkedQueue (Non-blocking Queue)
     * --------------------------------------------------- */
    private static void concurrentLinkedQueueExample() {
        System.out.println("\n=== ConcurrentLinkedQueue ===");

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.offer("A");
        queue.offer("B");

        System.out.println("Poll: " + queue.poll());
        System.out.println("Remaining: " + queue);
        
        /*
			Poll: A
			Remaining: [B]
         */
    }
    
    /* ---------------------------------------------------
     * 5. CopyOnWriteArraySet
     * --------------------------------------------------- */
    private static void copyOnWriteArraySetExample() {
        System.out.println("\n=== CopyOnWriteArraySet ===");

        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        set.add(1);
        set.add(2);
        set.add(2); 							// duplicate ignored

        System.out.println("Set: " + set);		// Set: [1, 2]
    }
    
    /* ---------------------------------------------------
     * 6. ConcurrentSkipListSet (Sorted Concurrent Set)
     * --------------------------------------------------- */
    private static void concurrentSkipListSetExample() {
        System.out.println("\n=== ConcurrentSkipListSet ===");

        ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
        set.add(30);
        set.add(10);
        set.add(20);

        System.out.println("Sorted Set: " + set);		// Sorted Set: [10, 20, 30]
    }
    
    /* ---------------------------------------------------
     * 7. ConcurrentHashMap
     * --------------------------------------------------- */
    private static void concurrentHashMapExample() throws InterruptedException {
        System.out.println("\n=== ConcurrentHashMap WITH THREADS ===");

        // Shared maps used by multiple threads
        ConcurrentHashMap<String, String> orders = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, String> userCache = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Integer> itemCount = new ConcurrentHashMap<>();

        Runnable task = () -> {

            /* ----------------------------------------------------
             * 1) putIfAbsent – prevent duplicate order
             * ---------------------------------------------------- */
            String orderResult = orders.putIfAbsent("ORDER-1", "CREATED");	// Won't overwrite existing key

            if (orderResult == null) {
                System.out.println(Thread.currentThread().getName() + " placed ORDER-1");
            } else {
                System.out.println(Thread.currentThread().getName() + " duplicate ORDER-1 rejected");
            }

            /* ----------------------------------------------------
             * 2) computeIfPresent – update order status safely
             * ---------------------------------------------------- */
            orders.computeIfPresent("ORDER-1", (k, v) -> "CONFIRMED");		// CREATED -> CONFIRMED
            																// Updates only if key exists
            
            /* ----------------------------------------------------
             * 3) computeIfAbsent – load user profile only once
             * ---------------------------------------------------- */
            String userProfile = userCache.computeIfAbsent(					// Adds only if key is absent
                    "USER-1",
                    k -> {
                        System.out.println("Loading USER-1 from DB by " + Thread.currentThread().getName());
                        return "User Profile Data";
                    }
            );

            /* ----------------------------------------------------
             * 4) merge – count ordered items
             * ---------------------------------------------------- */
            itemCount.merge("Burger", 1, Integer::sum);						// Merges value (Burger -> 0 + 1 = 1)
            itemCount.merge("Burger", 1, Integer::sum);						// Merges value (Burger -> 1 + 1 = 2)
            itemCount.merge("Pizza",  1, Integer::sum);						// Merges value (Burger -> 2; Pizza -> 0 + 1 = 1)
        };

        // Two threads executing same logic
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Final results
        System.out.println("\nFinal Order Status: " + orders.get("ORDER-1"));
        System.out.println("User Cache: " + userCache);
        System.out.println("Item Count: " + itemCount);
        
        /*
			=== ConcurrentHashMap WITH THREADS ===
			Thread-2 duplicate ORDER-1 rejected
			Thread-1 placed ORDER-1
			Loading USER-1 from DB by Thread-1
			
			Final Order Status: CONFIRMED
			User Cache: {USER-1=User Profile Data}
			Item Count: {Burger=4, Pizza=2}
		  */
    }
    
    /* ---------------------------------------------------
     * 8. ConcurrentSkipListMap (Sorted Concurrent Map)
     * --------------------------------------------------- */
    private static void concurrentSkipListMapExample() {
        System.out.println("\n=== ConcurrentSkipListMap ===");

        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(3, "C");
        map.put(1, "A");
        map.put(2, "B");

        System.out.println("Sorted Map: " + map);		// Sorted Map: {1=A, 2=B, 3=C}
    }
}
