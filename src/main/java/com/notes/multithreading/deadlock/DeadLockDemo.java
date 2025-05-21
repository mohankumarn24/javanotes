package com.notes.multithreading.deadlock;

// https://www.tpointtech.com/deadlock-in-java
public class DeadLockDemo {
	
	public static void main(String[] args) {
		
		final String resource1 = "foo";     // or final Object resource1 = new Object();
		final String resource2 = "bar";		// or final Object resource2 = new Object();

		// t1 tries to lock resource1 then resource2
		Thread t1 = new Thread(() -> {
			synchronized (resource1) {
				System.out.println("Thread 1: locked resource 1"); // first print statement
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				synchronized (resource2) {
					System.out.println("Thread 1: locked resource 2"); // second print statement
				}
			}
		});
		
		// t2 tries to lock resource2 then resource1
		Thread t2 = new Thread(() -> {
				synchronized (resource2) {
					System.out.println("Thread 2: locked resource 2"); // third print statement
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					synchronized (resource1) {
						System.out.println("Thread 2: locked resource 1"); // fourth print statement
					}
				}
		});
		
		t1.start();
		t2.start();
	}
}
