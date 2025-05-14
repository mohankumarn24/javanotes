package com.notes.multithreading.synchronizedd;

// https://www.tpointtech.com/static-synchronization-in-java
public class Printer {
	
	// static synchronized method declaration  
	public static synchronized void print(String s) {
		
		for (int i = 0; i < 4; i++) {
			System.out.print("Good Night: ");
			System.out.println(s);
			try {
				Thread.sleep(1000); // sleep thread for 1 sec
			} catch (InterruptedException e) {
				System.out.println("Interrupted exception occurred for thread: " + Thread.currentThread().getName());
			}
		}
	}
}