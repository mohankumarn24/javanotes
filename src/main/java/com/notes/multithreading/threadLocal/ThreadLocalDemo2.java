package com.notes.multithreading.threadLocal;

// ignore this example
// URL: https://aeontanvir.medium.com/java-multithreading-a-step-by-step-guide-for-concurrent-programming-3bf5dccbbfa1
public class ThreadLocalDemo2 extends Thread {

	@Override
	public void run() {
		try {
			ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
			threadLocal.set(42); // Set thread-local value
			int value = threadLocal.get(); // Get thread-local value
			System.out.println(String.format("Thread %s has value %d", Thread.currentThread().getName(), value));
		} catch (Exception e) {
			System.out.println("Exception occurred for thread " + Thread.currentThread().getName());
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

		Thread t1 = new ThreadLocalDemo2();
		t1.setName("t1");
		t1.start();
	}
}
