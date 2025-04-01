package com.notes.multithreading.interruptthreads;

public class InterruptingThreadDemo1 {

	public static void main(String[] args) {

		InterruptingThread t1 = new InterruptingThread();
		t1.start();

		try {
			t1.interrupt();
		} catch (RuntimeException e) {
			System.out.println("RuntimeException handled " + e);
		}
	}
}
