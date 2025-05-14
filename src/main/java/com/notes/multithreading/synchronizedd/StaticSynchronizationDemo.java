package com.notes.multithreading.synchronizedd;

// https://www.tpointtech.com/static-synchronization-in-java
public class StaticSynchronizationDemo {
	
	public static void main(String[] args) {
		
		Printer p1 = new Printer();  
		Printer p2 = new Printer(); 
		
		Thread t1 = new MyThread(p1, "A");  
		Thread t2 = new MyThread(p2, "B");
		
		t1.start();
        t2.start();
	}
}
