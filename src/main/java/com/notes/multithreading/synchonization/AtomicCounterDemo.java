package com.notes.multithreading.synchonization;

// https://aeontanvir.medium.com/java-multithreading-a-step-by-step-guide-for-concurrent-programming-3bf5dccbbfa1
// https://medium.com/@lakshyachampion/thread-safety-in-java-essential-techniques-for-modern-developers-f4b0cafe583d
public class AtomicCounterDemo {

	public static void main(String[] args) throws InterruptedException {
		
		AtomicCounter counter = new AtomicCounter();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final Count: " + counter.getCount());
	}
}
