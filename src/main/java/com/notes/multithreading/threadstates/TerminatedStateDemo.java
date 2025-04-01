package com.notes.multithreading.threadstates;

public class TerminatedStateDemo {
	
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("Thread running");
            // Method complete, thread will terminate
        });
        
        t.start();
        Thread.sleep(500); // Give thread time to complete
        
        System.out.println("Thread state: " + t.getState()); // Outputs: TERMINATED
    }
}
