package com.notes.multithreading.basics;

/**
 * Thread priority is not guaranteed
 */
public class Basics {
	
	public static void main(String[] args) {
		
		Thread t1 = new Thread(() -> {
			System.out.println("hello");
		});
		
		Thread t2 = new Thread(() -> {
			System.out.println("world");
		});
		
		System.out.println(String.format("Current thread name: %s", Thread.currentThread().getName()));
		System.out.println(String.format("Current thread priority: %d", Thread.currentThread().getPriority()));
		
		System.out.println(String.format("\nThread1 name is %s and priority is %d", t1.getName(), t1.getPriority()));
		System.out.println(String.format("Thread2 name is %s and priority is %d", t2.getName(), t2.getPriority()));
		
		t1.setName("t1");
		t1.setPriority(Thread.MIN_PRIORITY);	
		
		t2.setName("t2");
		t2.setPriority(Thread.MAX_PRIORITY);

		System.out.println(String.format("\nUpdated thread1 name is %s and priority is %d", t1.getName(), t1.getPriority()));
		System.out.println(String.format("Updated thread2 name is %s and priority is %d", t2.getName(), t2.getPriority()));
		
		
		System.out.println("\nThreads: ");
		t1.start();
		t2.start();
	}
}

/*
Current thread name: main
Current thread priority: 5

Thread1 name is Thread-0 and priority is 5
Thread2 name is Thread-1 and priority is 5

Updated thread1 name is t1 and priority is 1
Updated thread2 name is t2 and priority is 10

Threads: 
hello
world
*/