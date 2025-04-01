package com.notes.multithreading.threadstates;

public class WaitingStateDemo {
	
    public static void main(String[] args) throws InterruptedException {
    	
        Object lock = new Object();
        
        Thread t = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // Thread enters WAITING state
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
            lock.notify();
        }
        
        // Thread.sleep(100);
        // System.out.println("Thread state: " + t.getState()); // Outputs: TERMINATED
    }
}
