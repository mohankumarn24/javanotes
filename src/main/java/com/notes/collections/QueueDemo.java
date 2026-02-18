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
		priorityQueue.add("A");
		priorityQueue.add("B");
		priorityQueue.add("C");
		
		// print priorityQueue
        System.out.println("\npriorityQueue elements");    
        Iterator<String> iterator = priorityQueue.iterator();    
        while (iterator.hasNext()) {    
            System.out.println(iterator.next());    
        } 
        
		// print head
		System.out.println("\nprint head");
		System.out.println(priorityQueue.peek());			// returns null if the queue is empty  	
		// System.out.println(priorityQueue.element());		// throws an exception if the queue is empty  
		
		// remove head
		/*
		System.out.println("\nremove head");
		priorityQueue.poll();  								// returns null if the queue is empty
		priorityQueue.remove(); 							// throws an exception if the queue is empty
															// If {22, 11, 33} are added to priorotyQueue, then priorityQueue.remove() will remove '11' and not '22'
															// A PriorityQueue is not FIFO; it dequeues elements based on priority, not insertion order

															// By default, the smallest element (natural ordering) has the highest priority
															// Internally implemented as a heap
		*/  
		
		/*
		 PriorityQueue Methods Overview

		 +-----------+----------------------+------------------+---------------------------------+
		 | Method    | Returns head element | Removes element  | If queue is empty               |
		 +-----------+----------------------+------------------+---------------------------------+
		 | peek()    | ✅ Yes               | ❌ No            | returns null                     |
		 | poll()    | ✅ Yes               | ✅ Yes           | returns null                     |
		 +-----------+----------------------+------------------+---------------------------------+
		 | element() | ✅ Yes               | ❌ No            | throws NoSuchElementException    |
		 | remove()  | ✅ Yes               | ✅ Yes           | throws NoSuchElementException    |
		 +-----------+----------------------+------------------+---------------------------------+
		*/


		
		
		/************ ArrayDeque (also used as Stack).  *************/
		// ArrayDeque is a FIFO (and also LIFO) data structure — but NOT a priority-based one
		// FIFO -> Queue (add, poll)
		// LIFO -> Stack (push, pop)
		// Note: FIFO Queue (LinkedList, ArrayDeque)
		// Maintains insertion order
		// No null elements allowed
		// Faster than LinkedList for queue/stack use cases
		
		System.out.println("\nArrayDeque");
		
		// create PriorityQueue
		ArrayDeque<String> arrayDeque = new ArrayDeque<>();
		arrayDeque.add("A");
		arrayDeque.add("B");
		arrayDeque.add("C");
		
		// print priorityQueue
        System.out.println("\narrayDeque elements");    
        Iterator<String> iterator2 = arrayDeque.iterator();    
        while (iterator2.hasNext()) {    
            System.out.println(iterator2.next());    
        } 
        
		// print priorityQueue
        System.out.println("\narrayDeque elements");
        for (String str : arrayDeque) {
        	System.out.println(str);  
        }
        
        // remove first and last element
        System.out.println("\nremove first and last element");
        // deque.poll();  									// retrieve and removes the head of this deque, or returns null if this deque is empty.
        arrayDeque.pollFirst();//it is same as poll()  
        arrayDeque.pollLast();  							// Retrieves and removes the last element of this deque,or returns null if this deque is empty.
        for(String str : arrayDeque){  
            System.out.println(str);  
        }
        
        /*
        ArrayDeque (Queue / Deque) Methods Overview:

        +-------------+-----------------------+------------------+-------------------------------+
        | Method      | Returns element       | Removes element  | If deque is empty             |
        +-------------+-----------------------+------------------+-------------------------------+
        | peek()      | ✅ Yes (head)         | ❌ No             | returns null                  |
        | poll()      | ✅ Yes (head)         | ✅ Yes            | returns null                  |
        | element()   | ✅ Yes (head)         | ❌ No             | throws NoSuchElementException |
        | remove()    | ✅ Yes (head)         | ✅ Yes            | throws NoSuchElementException |
        +-------------+--------------------=--+------------------+-------------------------------+
        | peekFirst() | ✅ Yes                | ❌ No             | returns null                  | A
        | pollFirst() | ✅ Yes                | ✅ Yes            | returns null                  | A
        | peekLast()  | ✅ Yes                | ❌ No             | returns null                  | C
        | pollLast()  | ✅ Yes                | ✅ Yes            | returns null                  | C
        +-------------+--------------------=--+------------------+-------------------------------+
        */
        
        
        /*********** ArrayQueue as Queue replacemnt *****************/
        System.out.println("\nStack");
        
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println(stack.peek()); 		// 30
        System.out.println(stack.pop());  		// 30 removed

        System.out.println();
        stack.forEach(System.out::println);		// 20 10 
        
        System.out.println(stack.contains(10)); // true
        stack.clear();

	}
}
