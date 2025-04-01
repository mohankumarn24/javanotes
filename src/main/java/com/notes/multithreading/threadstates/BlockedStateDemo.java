package com.notes.multithreading.threadstates;

public class BlockedStateDemo {
	
    public static void main(String[] args) throws InterruptedException {
    	
        Object lock = new Object();
        
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                // Hold the lock for 5 seconds
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread 2 acquired the lock");
            }
        });
        
        t1.start(); 					// t1 acquires the lock
        Thread.sleep(100); 				// Give t1 time to acquire lock
        
        t2.start(); 					// t2 tries to acquire lock but gets blocked
        Thread.sleep(100); 				// Give t2 time to try acquiring lock
        
        System.out.println("t2 state: " + t2.getState()); // Outputs: BLOCKED
    }
}
