package com.notes.multithreading.shutdownhook;

public class WorkerThread extends Thread {

	@Override
	public void run() {
		System.out.println("shutdownhook task completed (shutdownhook is invoked automatically on JVM shutdown)");
	}
}
