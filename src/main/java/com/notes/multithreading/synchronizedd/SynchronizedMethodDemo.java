package com.notes.multithreading.synchronizedd;

public class SynchronizedMethodDemo {

	private class Counter {
		private int count = 0;

		// prevents race conditions
		public synchronized void increment() {
			count++;
		}

		// prevents race conditions
		public synchronized int getCount() {
			return count;
		}
	}

	public static void main(String[] args) throws InterruptedException {

		Counter counter = (new SynchronizedMethodDemo()).new Counter();

		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				counter.increment();
			}
		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				counter.increment();
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println("Count: " + counter.getCount());
	}
}
