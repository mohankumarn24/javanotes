package com.notes.multithreading.threadstates;

public class RunnableState {

	public static void main(String[] args) {
		
		Thread t = new Thread(() -> {
		    System.out.println("Thread is running");
		});
		t.start(); // Thread t moves to RUNNABLE state
		System.out.println(t.getState()); // Likely outputs: RUNNABLE
	}
	
}
