package com.notes.multithreading.basics;

public class DaemonThread extends Thread {

	@Override
	public void run() {
		if (Thread.currentThread().isDaemon()) {
			System.out.println("daemon thread");
		} else {
			System.out.println("user thread");
		}
	}

	public static void main(String[] args) {

		DaemonThread daemonThread1 = new DaemonThread();
		DaemonThread daemonThread2 = new DaemonThread();

		daemonThread1.setDaemon(true);

		daemonThread1.start();
		daemonThread2.start();
	}
}
