package com.notes.multithreading.interrupts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// https://medium.com/@satyendra.jaiswal/thread-interruption-and-termination-in-java-9a90d20661b3
public class ExecutorServiceTermination {
	
	private static class WorkerTask implements Runnable {
		
		public void run() {
			while (!Thread.interrupted()) {
				// Perform tasks
				System.out.println(Thread.currentThread().getName() + " Working...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Handle interruption if needed
					Thread.currentThread().interrupt(); // Restore interrupted status
				}
			}
			System.out.println(Thread.currentThread().getName() + " Task terminated gracefully.");
		}
	}
	
	public static void main(String[] args) {
		// Create an ExecutorService with a fixed thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		// Submit tasks to the pool
		for (int i = 0; i < 5; i++) {
			executorService.submit(new WorkerTask());
		}

		// Allow tasks to work for some time
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Shut down the ExecutorService
		executorService.shutdown();

		// Attempt to interrupt any remaining tasks
		try {
			if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
				// If tasks are not terminated after the specified time, interrupt them
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
