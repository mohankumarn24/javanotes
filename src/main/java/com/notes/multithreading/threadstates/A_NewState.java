package com.notes.multithreading.threadstates;

public class A_NewState {
	
	public static void main(String[] args) {
		
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
		Thread t = new Thread(() -> {
		    System.out.println("Thread is running");
		});
		
		System.out.println(t.getState()); 		// Outputs: NEW
	}

}
