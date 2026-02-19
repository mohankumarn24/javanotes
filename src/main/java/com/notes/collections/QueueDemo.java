package com.notes.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.PriorityQueue;

public class QueueDemo {
	
	public static void main(String[] args) {
		
		/************ PriorityQueue (Priority order) *************/
		System.out.println("PriorityQueue");
		
		// create PriorityQueue
		PriorityQueue<String> priorityQueue = new PriorityQueue<>();
		priorityQueue.add("A");	 							// priorityQueue.add(12);
		priorityQueue.add("B");  							// priorityQueue.add(11);
		priorityQueue.add("C");  							// priorityQueue.add(13);
		
		// print priorityQueue
        System.out.println("\npriorityQueue elements");    
        Iterator<String> iterator = priorityQueue.iterator();    
        while (iterator.hasNext()) {    
            System.out.println(iterator.next());    		// 11 12 13
        } 
        
        /*
        for (String str : priorityQueue) {
			System.out.println(str);						// 11 12 13
		}
        */
        
		// print head
		System.out.println("\nprint head");
		System.out.println(priorityQueue.peek());			// returns null if the queue is empty. Returns 11 and not 12  	
		// System.out.println(priorityQueue.element());		// throws an exception if the queue is empty. Returns 11 and not 12  
		
		// remove head
		/*
		System.out.println("\nremove head");
		priorityQueue.poll();  								// returns null if the queue is empty. Removes 11 and not 12 
		priorityQueue.remove(); 							// throws an exception if the queue is empty. Removes 11 and not 12
															// A PriorityQueue is not FIFO; it dequeues elements based on priority, not insertion order
															// By default, the smallest element (natural ordering) has the highest priority
															// Internally implemented as a heap
		*/  
		
		/*
		 PriorityQueue Methods Overview:
		 
		 PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
		 priorityQueue.add(12);
		 priorityQueue.add(11);
		 priorityQueue.add(13);
		 
		 +-----------+----------------------+------------------+---------------------------------+
		 | Method    | Returns head element | Removes element  | If queue is empty               |
		 +-----------+----------------------+------------------+---------------------------------+
		 | peek()    | ✅ Yes               | ❌ No            | returns null                     | returns 11
		 | poll()    | ✅ Yes               | ✅ Yes           | returns null                     | returns and removes 11
		 +-----------+----------------------+------------------+---------------------------------+
		 | element() | ✅ Yes               | ❌ No            | throws NoSuchElementException    | returns 11
		 | remove()  | ✅ Yes               | ✅ Yes           | throws NoSuchElementException    | returns and removes 11
		 +-----------+----------------------+------------------+---------------------------------+
		*/


		
		
		/************ ArrayDeque (also used as Stack).  *************/
		// ArrayDeque is a FIFO (and also LIFO) data structure — but NOT a priority-based one
		// Queue (add, poll) -> FIFO
		// Stack (push, pop) -> LIFO
		// Note: FIFO Queue (LinkedList, ArrayDeque)
		// Maintains insertion order
		// No null elements allowed
		// Faster than LinkedList for queue/stack use cases
		
		System.out.println("\nArrayDeque");
		
		// create PriorityQueue
		ArrayDeque<String> arrayDeque = new ArrayDeque<>();
		arrayDeque.add("A");								// arrayDeque.add(12);
		arrayDeque.add("B");								// arrayDeque.add(11);
		arrayDeque.add("C");								// arrayDeque.add(13);
		
		// print priorityQueue
        System.out.println("\narrayDeque elements");    
        Iterator<String> iterator2 = arrayDeque.iterator();    
        while (iterator2.hasNext()) {    
            System.out.println(iterator2.next());   		// 12 11 13   
        } 
        
        /* if you iterate again without initialization you will not get any data
        iterator2 = arrayDeque.iterator(); 					// <-- IMPORTANT -->
        while (iterator2.hasNext()) {    
            System.out.println(iterator2.next());   		// 12 11 13   
        }
        */
        
		// print priorityQueue
        System.out.println("\narrayDeque elements");
        for (String str : arrayDeque) {
        	System.out.println(str);						// 12 11 13
        }
        
        // remove first and last element
        System.out.println("\nremove first and last element");
        // deque.poll();  									// retrieve and removes the head of this deque, or returns null if this deque is empty.
        arrayDeque.pollFirst();								//it is same as poll()  
        arrayDeque.pollLast();  							// Retrieves and removes the last element of this deque,or returns null if this deque is empty.
        for(String str : arrayDeque){  
            System.out.println(str);  
        }
        
        /*
        ArrayDeque (Queue / Deque) Methods Overview:
        
		ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
		arrayDeque.add(12);
		arrayDeque.add(11);
		arrayDeque.add(13);
		
        +-------------+-----------------------+------------------+-------------------------------+
        | Method      | Returns element       | Removes element  | If deque is empty             |
        +-------------+-----------------------+------------------+-------------------------------+
        | peek()      | ✅ Yes (head)         | ❌ No             | returns null                  |	returns 12
        | poll()      | ✅ Yes (head)         | ✅ Yes            | returns null                  |	returns and removes 12
        | element()   | ✅ Yes (head)         | ❌ No             | throws NoSuchElementException |	returns 12
        | remove()    | ✅ Yes (head)         | ✅ Yes            | throws NoSuchElementException | returns and removes 12
        +-------------+--------------------=--+------------------+-------------------------------+
        | peekFirst() | ✅ Yes                | ❌ No             | returns null                  | returns 12. 					A	
        | pollFirst() | ✅ Yes                | ✅ Yes            | returns null                  | returns and removes 12. 		A
        | peekLast()  | ✅ Yes                | ❌ No             | returns null                  | returns 13.					C
        | pollLast()  | ✅ Yes                | ✅ Yes            | returns null                  | returns and removes 13.		C
        +-------------+--------------------=--+------------------+-------------------------------+
        */
        
        
        /*********** ArrayQueue as Queue replacemnt *****************/
        System.out.println("\nStack");
        
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println(stack.peek()); 			// 30
        System.out.println(stack.pop());  			// 30 removed

        System.out.println();
        stack.forEach(System.out::println);			// 20 10 
        
        System.out.println(stack.contains(10)); 	// true
        stack.clear();

	}
}

/*
================================================================================
PriorityQueue – Adding Priority in Java (Complete Notes)
================================================================================

	BASICS
	------
	- PriorityQueue does NOT follow insertion order
	- Priority is decided using:
	  	1) Natural ordering (Comparable)
	  	2) Custom Comparator
	- Internally implemented using a Min-Heap
	- Highest priority element is returned by poll() / peek()

--------------------------------------------------------------------------------
1) Default Priority (Natural Ordering)
--------------------------------------------------------------------------------
	- String  : Lexicographical order (A → Z)
	- Integer: Ascending order (smallest value = highest priority)
	
		Example:
			PriorityQueue<String> pq = new PriorityQueue<>();
			pq.add("C");
			pq.add("A");
			pq.add("B");
	
		poll() order:
			A → B → C

--------------------------------------------------------------------------------
2) Reverse Priority using Comparator
--------------------------------------------------------------------------------
	- Highest lexicographical value gets highest priority (Z → A)
	
		Example:
			PriorityQueue<String> pq = new PriorityQueue<>(Comparator.reverseOrder());
			pq.add("A");
			pq.add("B");
			pq.add("C");
		
		poll() order:
			C → B → A

--------------------------------------------------------------------------------
3) Custom Priority Logic
--------------------------------------------------------------------------------
	- Explicit business rule based priority
	- Example: "B" has highest priority, then "A", then "C"
	
		Example:
			PriorityQueue<String> pq = new PriorityQueue<>(
			    (s1, s2) -> {
			        if (s1.equals("B")) return -1;
			        if (s2.equals("B")) return 1;
			        return s1.compareTo(s2);
			    }
			);
		
			pq.add("A");
			pq.add("B");
			pq.add("C");
		
		poll() order:
			B → A → C

--------------------------------------------------------------------------------
4) Best Practice – Wrapper Class with Priority Field
--------------------------------------------------------------------------------
	- Used in real-world systems:
	  • Job schedulers
	  • Thread pools
	  • Event processing
	  • Kafka consumers
	- Lower priority number = Higher priority
	
		Example:
			class Task {
			    String name;
			    int priority;
			}
			
			PriorityQueue<Task> pq = new PriorityQueue<>(
			    (t1, t2) -> Integer.compare(t1.priority, t2.priority)
			);
			
			pq.add(new Task("Low", 3));
			pq.add(new Task("High", 1));
			pq.add(new Task("Medium", 2));
			
		poll() order:
			High → Medium → Low

--------------------------------------------------------------------------------
IMPORTANT INTERVIEW POINTS
--------------------------------------------------------------------------------
	- PriorityQueue is NOT fully sorted
	- Iteration order is NOT priority order
	- poll() removes and returns highest priority element
	- peek() returns highest priority element without removing it
	
	Time Complexity:
	- add()  → O(log n)
	- poll() → O(log n)
	- peek() → O(1)

================================================================================
*/

