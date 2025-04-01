package com.notes.multithreading.threadstates;

/**
 * Example that demonstrates all thread states
 * NEW
 * RUNNABLE
 * BLOCKED
 * WAITING/TIMED_WAITING
 * TERMINATED
 */
public class ThreadStatesDemo {

	public static void main(String[] args) throws InterruptedException {

		final Object lock = new Object();

		Thread thread = new Thread(() -> {
			System.out.println("3. Thread starting...");

			// Demonstrate RUNNABLE state
			for (int i = 0; i < 1000000; i++) {
				// Just busy work
				Math.sqrt(i);
			}

			// Demonstrate BLOCKED state
			synchronized (lock) {
				System.out.println("4. Acquired lock");
			}

			// Demonstrate WAITING state
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					System.out.println("Thread interrupted from WAITING");
				}
			}

			// Demonstrate TIMED_WAITING state
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted from TIMED_WAITING");
			}

			System.out.println("8. Thread ending...");
			// After this, thread will be in TERMINATED state
		});

		// NEW state
		System.out.println("1. Thread state: " + thread.getState()); // NEW

		// Start the thread - moves to RUNNABLE
		thread.start();
		System.out.println("2. Thread state after start: " + thread.getState()); // RUNNABLE

		// Give thread time to execute and reach the synchronized block
		Thread.sleep(10);

		// Create another thread to demonstrate BLOCKED state
		Thread blocker = new Thread(() -> {
			synchronized (lock) {
				try {
					Thread.sleep(2000); // Hold lock for 2 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		synchronized (lock) {
			// Start blocker thread, but it can't get the lock yet
			blocker.start();
			Thread.sleep(10);

			// When our thread tries to acquire lock, it will be BLOCKED
			System.out.println("5. Blocker thread state: " + blocker.getState());
		}

		// Wait for the blocker thread to finish
		blocker.join();

		// Now our main thread should be in WAITING state
		Thread.sleep(100);
		System.out.println("6. Thread state during wait(): " + thread.getState());

		// Notify the waiting thread
		synchronized (lock) {
			lock.notify();
		}

		// Thread should now be in TIMED_WAITING due to sleep()
		Thread.sleep(100);
		System.out.println("7. Thread state during sleep(): " + thread.getState());

		// Wait for thread to finish
		thread.join();

		// Thread should now be TERMINATED
		System.out.println("9. Thread state at end: " + thread.getState());
	}
}