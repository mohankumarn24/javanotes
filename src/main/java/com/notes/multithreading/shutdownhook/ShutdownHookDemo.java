package com.notes.multithreading.shutdownhook;

public class ShutdownHookDemo {

	public static void main(String[] args) throws InterruptedException {
		
		Thread t1 = new WorkerThread();
		Runtime runtime = Runtime.getRuntime();    
		runtime.addShutdownHook(t1);
		// Runtime.getRuntime().addShutdownHook(t1);
		// Runtime.getRuntime().removeShutdownHook(t1);
		
		Thread.sleep(3000);
	}
}
