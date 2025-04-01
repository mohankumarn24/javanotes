package com.notes.multithreading.interruptthreads;

public class InterruptingThreadDemo2 extends Thread {

	/**
	 * The isInterrupted() method returns the interrupted flag either true or false. 
	 * The static interrupted() method returns the interrupted flag afterthat it sets the flag to false if it is true.
	 */
	@Override
	public void run() {
		for (int i = 1; i <= 2; i++) {
			if (Thread.interrupted()) {
				System.out.println("code for interrupted thread");
			} else {
				System.out.println("code for normal thread");
			}
		}
	}

	public static void main(String args[]) {

		InterruptingThreadDemo2 t1 = new InterruptingThreadDemo2();
		InterruptingThreadDemo2 t2 = new InterruptingThreadDemo2();

		t1.start();
		t1.interrupt();

		t2.start();
	}
}
