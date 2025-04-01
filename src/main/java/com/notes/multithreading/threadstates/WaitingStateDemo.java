package com.notes.multithreading.threadstates;

public class WaitingStateDemo {
	
    public static void main(String[] args) throws InterruptedException {
    	
        Object lock = new Object();
        
        Thread t = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // Thread enters WAITING state
                    /**
                     * The wait() method causes current thread to release the lock and wait until 
                     * either another thread invokes the notify() method or the notifyAll() method
                     * for this object, or a specified amount of time has elapsed
                     */
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        t.start();
        Thread.sleep(100); // Give thread time to enter WAITING state
        
        System.out.println("Thread state: " + t.getState()); // Outputs: WAITING
        
        // To proceed, we need to notify the waiting thread
        synchronized (lock) {
            lock.notify(); 			// The notify() method wakes up a single thread that is waiting on this object's monitor
            // lock.notifyAll(); 	// Wakes up all threads that are waiting on this object's monitor
        }
        
        // Thread.sleep(100);
        // System.out.println("Thread state: " + t.getState()); // Outputs: TERMINATED
    }
}
