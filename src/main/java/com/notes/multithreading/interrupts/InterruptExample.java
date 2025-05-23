package com.notes.multithreading.interrupts;

// https://medium.com/@satyendra.jaiswal/thread-interruption-and-termination-in-java-9a90d20661b3
public class InterruptExample extends Thread {
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Perform a time-consuming task
				System.out.println("Working...");
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// Handle interruption gracefully
			System.out.println(Thread.currentThread().getName() + " Thread interrupted!");
		}
		System.out.println("Thread terminated gracefully: " + Thread.currentThread().getName());
	}

	public static void main(String[] args) {

		Thread thread = new InterruptExample();
		thread.start();

		// Allow the thread to work for some time
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Interrupt the thread
		thread.interrupt();
	}
}

/**
 * The isInterrupted() method returns the interrupted flag either true or false. 
 * The static interrupted() method returns the interrupted flag after that it sets the flag to false if it is true.
 */