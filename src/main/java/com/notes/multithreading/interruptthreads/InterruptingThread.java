package com.notes.multithreading.interruptthreads;

public class InterruptingThread extends Thread {
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted: " + e.getMessage());
			throw new RuntimeException("Thread interrupted: " + e.getMessage());
		} 
	}

}
