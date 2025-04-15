package com.notes.multithreading.threadstates;

public class C_TerminatedStateDemo {
	
    public static void main(String[] args) throws InterruptedException {
    	
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
        Thread t = new Thread(() -> {
            System.out.println("Thread running..."); 			// On method complete, thread will terminate
        });
        
        t.start();
        Thread.sleep(1000); 									// Give thread t time to complete. Main thread goes to sleep for 1 second
        
        System.out.println("Thread state: " + t.getState()); 	// Outputs: TERMINATED
    }
}
