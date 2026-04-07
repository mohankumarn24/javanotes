package com.notes.multithreading.daemonthreads;

public class DaemonThreadDemo {
	
	public static void main(String[] args) {

		Runtime runtime = Runtime.getRuntime();				// shutdownhook. Invoked automatically on JVM shutdown
		runtime.addShutdownHook(new ShutdownHook());
		
		Thread t1 = new MyThread();							// daemon thread. runs forever
		Thread t2 = new MyThread();							// user thread. runs for 5 sec and terminates. Then, it kills all daemon threads and invokes shutdownhook
		
		t1.setDaemon(true);
		
		t1.start();
		t2.start();
	}
}

/*
 * - JVM waits for all user threads (non-daemon threads) to finish.
 *   But, JVM does NOT wait for daemon threads.
 *
 * - As soon as all user threads are completed,
 *   JVM terminates even if daemon threads are still running.
 */