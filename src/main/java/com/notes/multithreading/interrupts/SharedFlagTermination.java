package com.notes.multithreading.interrupts;

public class SharedFlagTermination {
	
	// When we use volatile keyword with a variable, all the threads read it's value directly from the memory and don't cache it. 
	// This makes sure that the value read is the same as in the memory.
	private static volatile boolean shutdownRequested = false;
	
	static class WorkerThread extends Thread {
		
		@Override
		public void run() {
			try {
				while (!shutdownRequested) {
					// Perform tasks
					System.out.println("Working..." + Thread.currentThread().getName());
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// Handle interruption if needed
				// Thread.currentThread().interrupt(); // Restore interrupted status
				System.out.println(Thread.currentThread().getName() + " Thread interrupted!");
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
