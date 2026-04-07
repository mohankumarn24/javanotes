package com.notes.multithreading.join;

public class JoinDemo {

	public static void main(String[] args) throws InterruptedException {
		
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println("Hi");
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		});
		
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println("Hello");
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		});
		
		t1.start();
		Thread.sleep(10);
		t2.start();
		
		// join() is not needed to keep JVM alive.
		// join() is only required if you want the main thread to wait before moving forward
		t1.join();
		t2.join();
		
		System.out.println("Bye");
	}
}

/*
 * main thread finishing does NOT terminate the JVM immediately.
 *
 * JVM exits only when all non-daemon (user) threads are finished.
 *
 * Here:
 *   - main thread starts t1 and t2
 *   - main thread prints "Bye"
 *   - main thread ends
 *   - But t1 and t2 are still running
 *   - So JVM waits until both threads complete
 *
 * join() is not needed to keep JVM alive.
 * join() is only needed when main thread should wait for worker threads.
 *
 * Without join():
 *   main thread finishes early
 *   worker threads continue running
 *   JVM exits after worker threads finish
 *
 * With join():
 *   main thread waits for t1 and t2 to finish
 *   then prints "Bye"
 *
 * If you want JVM to terminate immediately after main thread ends,
 * make worker threads daemon threads:
 *
 *   t1.setDaemon(true);
 *   t2.setDaemon(true);
 *
 * JVM does not wait for daemon threads.
 */