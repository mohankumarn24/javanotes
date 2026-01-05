package com.notes.multithreading.daemonthreads;

public class ShutdownHook extends Thread {
	
	@Override
	public void run() {
		System.out.println("-- shutdown hook invoked automatically on JVM shutdown --");
	}
}
