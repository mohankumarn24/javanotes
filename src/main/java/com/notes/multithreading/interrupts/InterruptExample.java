package com.notes.multithreading.interrupts;

// https://medium.com/@satyendra.jaiswal/thread-interruption-and-termination-in-java-9a90d20661b3
public class InterruptExample {
	
	static class WorkerThread extends Thread {
		
		@Override
		public void run() {
			try {
				while (!Thread.interrupted()) {
					// Perform a time-consuming task
					System.out.println("Working..." + Thread.currentThread().getName());
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// Handle interruption gracefully
				System.out.println(Thread.currentThread().getName() + " Thread interrupted!");
			}
			System.out.println("Thread terminated gracefully: " + Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) {

		// worker thread
		Thread t1 = new WorkerThread();
		t1.start();

		// Allow the thread to work for some time
		// Main thread sleeps for 5 seconds, so the worker thread prints "Working..." about 5 times
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Interrupt the worker thread
		t1.interrupt();
		/*
		* After 5 seconds, the main thread calls thread.interrupt():
		*  - This sets the worker thread’s interrupt flag.
		*  - Since the worker thread is in Thread.sleep(), it throws InterruptedException.
		*  - The worker thread catches it, prints the interruption message, and terminates gracefully.
		*/
	}
}

/**
 * Checking interruption status
 * 	- Thread.interrupted() → returns and clears the flag.
 *  - isInterrupted() → returns the flag but does NOT clear it.
 */