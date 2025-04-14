package com.notes.multithreading.interrupts;

// https://medium.com/@satyendra.jaiswal/thread-interruption-and-termination-in-java-9a90d20661b3
public class SharedFlagTermination {
	
	private static volatile boolean shutdownRequested = false;
	
	static class WorkerThread extends Thread {
		
		public void run() {
			while (!shutdownRequested) {
				// Perform tasks
				System.out.println("Working...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Handle interruption gracefully
					System.out.println(Thread.currentThread().getName() + " Thread interrupted!");
				}
			}
			System.out.println("Thread terminated gracefully: " + Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) {
		// Start multiple threads
		Thread thread1 = new WorkerThread();
		Thread thread2 = new WorkerThread();

		thread1.start();
		thread2.start();

		// Allow threads to work for some time
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Request shutdown
		shutdownRequested = true;

		// Interrupt threads
		thread1.interrupt();
		thread2.interrupt();
	}
}
