package com.notes.multithreading.threadstates;

public class C_BlockedStateDemo {
	
    public static void main(String[] args) throws InterruptedException {
    	
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
        Object lock = new Object();
        
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                // Hold the lock for 5 seconds
                try {
                    Thread.sleep(2000); // Thread enters TIMED_WAITING state for 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread t2 = new Thread(() -> {
            synchronized (lock) {	// Thread enters BLOCKED state till lock is acquired
                System.out.println("Thread 2 acquired the lock");
            }
        });
        
        t1.start(); 					// t1 acquires the lock
        Thread.sleep(100); 				// Give t1 time to acquire lock
        
        t2.start(); 					// t2 tries to acquire lock but gets blocked
        Thread.sleep(100); 				// Give t2 time to try acquiring lock
        
        System.out.println("t1 state: " + t1.getState()); // Outputs: TIMED_WAITING
        System.out.println("t2 state: " + t2.getState()); // Outputs: BLOCKED
    }
}
