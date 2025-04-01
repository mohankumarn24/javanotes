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
		
		System.out.println("Current thread name: " + Thread.currentThread().getName());
		
		System.out.println(String.format("Thread1 name is %s and priority is %d", t1.getName(), t1.getPriority()));
		System.out.println(String.format("Thread2 name is %s and priority is %d", t2.getName(), t2.getPriority()));
		
		t1.setName("t1");
		t1.setPriority(Thread.MIN_PRIORITY);
		
		t2.setName("t2");
		t2.setPriority(Thread.MAX_PRIORITY);
		
		System.out.println(String.format("Updated thread1 name is %s and priority is %d", t1.getName(), t1.getPriority()));
		System.out.println(String.format("Updated thread2 name is %s and priority is %d", t2.getName(), t2.getPriority()));
		
		t1.start();
		t2.start();
	}
}
