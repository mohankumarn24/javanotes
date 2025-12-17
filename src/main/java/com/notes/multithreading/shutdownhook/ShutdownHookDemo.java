package com.notes.multithreading.shutdownhook;

public class ShutdownHookDemo {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Main thread running...");
		
		// create thread
		Thread t1 = new WorkerThread();
		
		// create shutdownhook
		Runtime runtime = Runtime.getRuntime();    
		runtime.addShutdownHook(t1);						// shutdownhook. Invoked automatically on JVM shutdown
		
		// Runtime.getRuntime().addShutdownHook(t1);
		// Runtime.getRuntime().removeShutdownHook(t1);
		
		// sleep main thread for 2 seconds
		Thread.sleep(2000);
	}
}
