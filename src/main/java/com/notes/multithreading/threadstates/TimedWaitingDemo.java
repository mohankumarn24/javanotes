package com.notes.multithreading.threadstates;

public class TimedWaitingDemo {
	
    public static void main(String[] args) throws InterruptedException {
    	
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5000); // Thread enters TIMED_WAITING state
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        t.start();
        Thread.sleep(100); // Give thread time to enter TIMED_WAITING state
        
        System.out.println("Thread state: " + t.getState()); // Outputs: TIMED_WAITING
    }
}
