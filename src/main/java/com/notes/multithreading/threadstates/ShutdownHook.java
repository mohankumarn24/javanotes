package com.notes.multithreading.threadstates;

public class ShutdownHook extends Thread {
	
	@Override
	public void run() {
		System.out.println("\n -- Program terminated --");
	}
}
