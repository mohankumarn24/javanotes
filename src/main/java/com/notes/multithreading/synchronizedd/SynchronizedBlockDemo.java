package com.notes.multithreading.synchronizedd;


public class SynchronizedBlockDemo {
	
	private class Counter {
	    private int count = 0;

	    public synchronized void increment() {
	        count++;
	    }

	    public synchronized int getCount() {
	        return count;
	    }
	}

	public static void main(String[] args) throws InterruptedException {
		
		Counter counter = (new SynchronizedBlockDemo()).new Counter();
		
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
		System.out.println("Bye");
	}
	
}
