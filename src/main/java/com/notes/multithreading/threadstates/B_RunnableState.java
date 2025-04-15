package com.notes.multithreading.threadstates;

public class B_RunnableState {

	public static void main(String[] args) {
		
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
		Thread t = new Thread(() -> {
		    System.out.println("Thread is running...");
		});
		
		t.start(); 								// Thread t moves to RUNNABLE state
		System.out.println(t.getState()); 		// Likely outputs: RUNNABLE
	}
	
}
