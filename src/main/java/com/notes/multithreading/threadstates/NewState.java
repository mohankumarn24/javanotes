package com.notes.multithreading.threadstates;

public class NewState {
	
	public static void main(String[] args) {
		
		Thread t = new Thread(() -> {
		    System.out.println("Thread is running");
		});
		// Thread t is in NEW state here
		System.out.println(t.getState()); // Outputs: NEW
	}

}
