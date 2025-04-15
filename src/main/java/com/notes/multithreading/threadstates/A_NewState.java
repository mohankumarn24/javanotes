package com.notes.multithreading.threadstates;

public class A_NewState {
	
	public static void main(String[] args) {
		
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
		Thread t = new Thread(() -> {
		    System.out.println("Thread is running");
		});
		// Thread t is in NEW state here
		System.out.println(t.getState()); // Outputs: NEW
	}

}
