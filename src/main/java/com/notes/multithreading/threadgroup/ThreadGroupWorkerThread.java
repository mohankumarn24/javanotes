package com.notes.multithreading.threadgroup;

class ThreadGroupWorkerThread implements Runnable {

	@Override
	public void run() {
		try {
			System.out.println("hello, world");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
