package com.notes.multithreading.daemonthreads;

public class MyThread extends Thread {
	
	@Override
	public void run() {
		
		if (Thread.currentThread().isDaemon()) {
			while (true) {
				System.out.println("Daemon thread running...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("@@ User thread sleeps for 5 seconds...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("@@ User thread terminated automatically after 5 seconds");
		}
	}
}
