package com.notes.multithreading.threadstates;

import java.time.Instant;

public class D2_WaitingStateDemoNotifyAll {
	
    public static void main(String[] args) throws InterruptedException {

    	Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
        Object lock = new Object();
        
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                	lock.wait(); 									// Thread enters WAITING state and releases lock
                    // lock.wait(5000);								// Thread enters TIMED_WAITING state and releases lock
                } catch (InterruptedException e) {
                	Thread.currentThread().interrupt();				// SEE NOTES
                }
                // Thread wakes once it re-acquires the monitor
                System.out.println(Thread.currentThread().getName() + " got notified at time: " + Instant.now().toString()); 	// System.currentTimeMillis()
            }
        }, "t1");
        
        
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                	lock.wait(); 									// Thread enters WAITING state and releases lock
                    // lock.wait(5000);								// Thread enters TIMED_WAITING state and releases lock
                } catch (InterruptedException e) {
                	Thread.currentThread().interrupt();				// Best practise
                }
                // Thread wakes once it re-acquires the monitor
                System.out.println(Thread.currentThread().getName() + " got notified at time: " + Instant.now().toString()); 	// System.currentTimeMillis()
            }
        }, "t2");
        
        
        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                // lock.notify();
                lock.notifyAll();									// Wake up 't1' and 't2' threads waiting on object monitor 'lock'
                
                // after this synchronized block ends, t1 and t2 will compete to re-acquire the lock
            }
        }, "t3");
        
        
        t1.start();
        Thread.sleep(100);
        System.out.println("t1 state: " + t1.getState()); 			// Outputs: WAITING

        t2.start();
        Thread.sleep(100);
        System.out.println("t2 state: " + t2.getState()); 			// Outputs: WAITING
        
        // main thread sleeps for 5 seconds
        Thread.sleep(5000);
        
        // notifyAll()
        t3.start();												    // thread t2 sends notify on lock object. Use thread t2 or main thread
        Thread.sleep(100);
        
        /*
        synchronized (lock) {										// main thread sends notify on lock object
            lock.notify(); 											// The notify() method wakes up a single thread that is waiting on this object's monitor
            // lock.notifyAll(); 									// Wakes up all threads that are waiting on this object's monitor
        }
        */
        
        // System.out.println();
        // System.out.println("t1 state: " + t1.getState()); 		// Outputs: TERMINATED
        // System.out.println("t2 state: " + t2.getState()); 		// Outputs: TERMINATED
    }
}

/* OUTPUT
t1 state: WAITING
t2 state: WAITING
t1 got notified at time: 2025-11-18T14:38:08.962757300Z
t2 got notified at time: 2025-11-18T14:38:08.977208600Z

 -- Program terminated --
*/

/* NOTES:
 * Why use Thread.currentThread().interrupt() instead of e.printStackTrace()?
 * - When a thread is interrupted during wait(), sleep(), or join(), Java throws InterruptedException AND automatically clears the thread's interrupt flag
 * - If we only call e.printStackTrace(), the interrupt signal is LOST. The thread continues normally as if it was never interrupted 
 *    -- You lose the interrupt signal forever
 *    -- The thread thinks nothing happened
 *    -- Higher-level code cannot detect the interruption
 * - By calling Thread.currentThread().interrupt(), we RESTORE the interrupt status so higher-level code can detect it and stop the thread gracefully
 * 	  -- You restore the interrupted status
 * 	  -- You respect the fact that someone wanted this thread to stop/wake up
 *    -- Other parts of your code can check Thread.interrupted() later
 * - This is the recommended best practice in Java concurrency (Goetz, Bloch). It is not mandatory for small demos, but it's the correct way in real apps
 * 
 * Summary:
 * - InterruptedException clears the interrupt flag
 * - Restore it using 'Thread.currentThread().interrupt()' so the thread knows it was interrupted
*/

/*
wait() method:
 - The wait() method causes current thread to release the lock and wait until either another thread invokes the notify() method or the notifyAll() method for this object, or a specified amount of time has elapsed.
 - The current thread must own this object's monitor, so it must be called from the synchronized method only otherwise it will throw exception.
 
notify() method:
 - The notify() method wakes up a single thread that is waiting on this object's monitor. If any threads are waiting on this object, one of them is chosen to be awakened. The choice is arbitrary and occurs at the discretion of the implementation.
*/