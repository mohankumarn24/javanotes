package com.notes.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.PriorityQueue;

public class QueueDemo {
	
	public static void main(String[] args) {
		
		/************ PriorityQueue *************/
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
		*/  
		
		
		/************ ArrayDeque *************/
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
	}
}
