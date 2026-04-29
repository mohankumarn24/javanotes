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

/*
 * Step-by-step execution:
 * ----------------------
 * 
 * 1. JVM starts → main thread begins execution.
 * 
 * 2. Main thread:
 *    - Registers a shutdown hook.
 *    - Creates t1 (daemon thread).
 *    - Creates t2 (user/non-daemon thread).
 * 
 * 3. Main thread sets:
 *    - t1 as daemon → t1.setDaemon(true)
 * 
 * 4. Main thread starts both threads:
 *    - t1.start()
 *    - t2.start()
 * 
 * 5. Main thread finishes execution of main() method.
 *    - Main thread TERMINATES here.
 * 
 * 6. JVM checks:
 *    - Are any user (non-daemon) threads alive?
 *    - YES → t2 is still running
 *    → JVM CONTINUES running
 * 
 * 7. t1 (daemon thread):
 *    - Keeps running in background (infinite/long-running)
 * 
 * 8. t2 (user thread):
 *    - Runs for ~5 seconds
 *    - Then completes execution
 * 
 * 9. JVM checks again:
 *    - Any user threads alive?
 *    - NO → only daemon thread (t1) remains
 * 
 * 10. JVM shutdown begins:
 *     - All daemon threads are TERMINATED immediately
 *     - Shutdown hook is invoked
 * 
 * 11. JVM exits completely.
 * 
 * Key rule:
 * JVM lives as long as at least one user (non-daemon) thread is alive.
*/


/*
 * Summary:
 * -------
 * We have three threads: main thread, one daemon thread (t1), and one user thread (t2).
 *  - Main thread starts both threads and then finishes.
 *  - JVM continues running because t2 (user thread) is still alive.
 *  - After t2 completes, only daemon threads remain.
 *  - JVM shuts down immediately, terminating daemon threads.
 *  - Before shutdown, registered shutdown hooks are executed.
*/