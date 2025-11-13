package com.notes.multithreading.threadLocal;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Thread-local variables are variables that are local to each thread. 
 * They allow us to store data that is specific to a particular thread, ensuring that each thread has its own independent copy of the variable.
 * URL: https://aeontanvir.medium.com/java-multithreading-a-step-by-step-guide-for-concurrent-programming-3bf5dccbbfa1
 */
public class ThreadLocalDemo {
	
	public static class MyRunnable implements Runnable {
		
		private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

		@Override
		public void run() {
			// threadLocal.set((int) (Math.random() * 50D));
			// threadLocal.set(new Random().nextInt(500)); 							// 0-499
			threadLocal.set(ThreadLocalRandom.current().nextInt(50));				// 0-49
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Exception occurred for thread: " + Thread.currentThread().getName());
			}
			System.out.println(String.format("threadLocal=%d for Thread %s", threadLocal.get(), Thread.currentThread().getName()));
		}
	}

	public static void main(String[] args) {
		
		MyRunnable runnableInstance = new MyRunnable();
		Thread t1 = new Thread(runnableInstance);
		Thread t2 = new Thread(runnableInstance);
		
		t1.start();
		t2.start();
	}
}

/*
 * - Math.random() → returns something like 0.73842
 * - (int) 0.73842 → becomes 0
 * 
 * so,
 * - Math.random() * 50D → returns something like 36.921
 * - (int) 36.921 → becomes 36
 */

/*
ThreadLocal variables are special kinds of variables created and provided by the Java ThreadLocal class. 
These variables are only allowed to be read and written by the same thread. 
Two threads cannot be able to see each other’s ThreadLocal variable, so even if they will execute the same code, then there won't be any race condition and the code will be thread-safe.  
*/

/* Extend Thread class instead of implementing Runnable interface:

public class ThreadLocalDemo {

	public static class MyRunnable extends Thread {
		
		private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
	
		@Override
		public void run() {
			threadLocal.set((int) (Math.random() * 50D));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Exception occurred for thread: " + Thread.currentThread().getName());
			}
			System.out.println(String.format("threadLocal=%d for Thread %s", threadLocal.get(), Thread.currentThread().getName()));
		}
	}
	
	public static void main(String[] args) {
		
		Thread t1 = new MyRunnable();
		Thread t2 = new MyRunnable();
		
		t1.start();
		t2.start();
	}
}
*/