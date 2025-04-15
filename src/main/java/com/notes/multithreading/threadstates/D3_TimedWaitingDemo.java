package com.notes.multithreading.threadstates;

public class D3_TimedWaitingDemo {
	
    public static void main(String[] args) throws InterruptedException {
    	
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000); 		// Thread enters TIMED_WAITING state
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        t.start();
        Thread.sleep(100); // Give thread time to enter TIMED_WAITING state
        
        System.out.println("Thread state: " + t.getState()); // Outputs: TIMED_WAITING
    }
}
