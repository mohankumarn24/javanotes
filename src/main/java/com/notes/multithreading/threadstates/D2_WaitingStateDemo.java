package com.notes.multithreading.threadstates;

public class D2_WaitingStateDemo {
	
    public static void main(String[] args) throws InterruptedException {

    	Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
        Object lock = new Object();
        
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                	lock.wait(); 									// Thread enters WAITING state
                    // lock.wait(5000);								// Thread enters TIMED_WAITING state
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
        
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                lock.notify(); 										// The notify() method wakes up a single thread that is waiting on this object's monitor
                // lock.notifyAll(); 								// Wakes up all threads that are waiting on this object's monitor
            }
        });
        
        t1.start();
        Thread.sleep(100);

        System.out.println("t1 state: " + t1.getState()); 			// Outputs: WAITING
        
        t2.start();												    // thread t2 sends notify on lock object. Use thread t2 or main thread
        Thread.sleep(100);
        
        /*
        synchronized (lock) {										// main thread sends notify on lock object
            lock.notify(); 											// The notify() method wakes up a single thread that is waiting on this object's monitor
            // lock.notifyAll(); 									// Wakes up all threads that are waiting on this object's monitor
        }
        */
        
        System.out.println("t2 state: " + t2.getState()); 			// Outputs: TERMINATED
    }
}
