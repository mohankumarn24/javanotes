package com.notes.multithreading.threadLocal;

public class ThreadLocalDemo {
	
	public static class MyRunnable implements Runnable {
		
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
		
		MyRunnable runnableInstance = new MyRunnable();
		Thread t1 = new Thread(runnableInstance);
		Thread t2 = new Thread(runnableInstance);
		
		t1.start();
		t2.start();
	}
}

/*
ThreadLocal variables are special kinds of variables created and provided by the Java ThreadLocal class. 
These variables are only allowed to be read and written by the same thread. 
Two threads cannot be able to see each other’s ThreadLocal variable, so even if they will execute the same code, then there won't be any race condition and the code will be thread-safe.  
*/