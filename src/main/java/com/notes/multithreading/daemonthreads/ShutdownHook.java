package com.notes.multithreading.daemonthreads;

public class ShutdownHook extends Thread {
	
	@Override
	public void run() {
		
		System.out.println("Shutdownhook executed successfully");
	}

}
