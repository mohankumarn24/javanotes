package com.notes.multithreading.shutdownhook;

public class WorkerThread extends Thread {

	@Override
	public void run() {
		System.out.println("shut down hook task completed");
	}
}
