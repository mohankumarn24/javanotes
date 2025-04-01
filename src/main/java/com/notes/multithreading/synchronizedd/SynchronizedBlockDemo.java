package com.notes.multithreading.synchronizedd;

public class SynchronizedBlockDemo {
	
	private class Counter {
	    private int count;
	    private final Object lock;
	    
	    public Counter(int count, Object lock) {
	    	this.count = count;
	    	this.lock = lock;
	    }

	    /**
	     * can also use synchronized(this)
	     * can also use Synchronized block on class iw static Synchronization i.e., synchronized (SynchronizedMethodDemo.class) {
	     */
	    public void increment() {
	    	synchronized (lock) {
	    	// synchronized (Counter.class) {
	    		count++;
	    	}
	    }

	    public int getCount() {
	    	synchronized (lock) {
	    	// synchronized (Counter.class) {
	    		return count;
			}
	    }
	}

	public static void main(String[] args) throws InterruptedException {
		
		Object lock = new Object();
		Counter counter = (new SynchronizedBlockDemo()).new Counter(0, lock);
		
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
