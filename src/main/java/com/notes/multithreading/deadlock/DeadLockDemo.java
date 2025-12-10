package com.notes.multithreading.deadlock;

public class DeadLockDemo {
	
	// private static final String resource1 = "foo";
	// private static final String resource2 = "bar";
	
	public static void main(String[] args) {
		
		final String resource1 = "foo";     // or final Object resource1 = new Object();
		final String resource2 = "bar";		// or final Object resource2 = new Object();

		// t1 tries to lock resource1 then resource2
		Thread t1 = new Thread(() -> {
			synchronized (resource1) {
				System.out.println("Thread 1: locked resource 1");
				try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
				synchronized (resource2) {
					System.out.println("Thread 1: locked resource 2");
				}
			}
		});
		
		// t2 tries to lock resource2 then resource1
		Thread t2 = new Thread(() -> {
				synchronized (resource2) {
					System.out.println("Thread 2: locked resource 2");
					try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
					synchronized (resource1) {
						System.out.println("Thread 2: locked resource 1");
					}
				}
		});
		
		t1.start();
		t2.start();
	}
}

/*
Thread 2: locked resource 2
Thread 1: locked resource 1
*/