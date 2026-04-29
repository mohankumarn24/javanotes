package com.notes.multithreading.threadpool.completableFuture;

import java.util.concurrent.CompletableFuture;

public class A1_CompletableFutureExample {
	
	public static void main(String[] args) throws Exception {
		
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return "Hello from CompletableFuture!";
		});

        // Attach callback chain (non-blocking)
        CompletableFuture<Void> callbackFuture = future
        		.thenApply(msg -> msg + " Processed")
        		.thenAccept(result -> System.out.println(result));
        
		System.out.println("-- Main thread (start) --"); // runs even if above line is not completed
		
		/**
		 * Option 1: callbackFuture.join()
		 * - Waits for CompletableFuture (including callback chain) to finish
		 * - Keeps main thread alive until async work completes
		 * - Prevents JVM from exiting early (since async runs on daemon threads)
		 * - Cleaner and more reliable than Thread.sleep()
		 */
		callbackFuture.join();
		
		
		/**
		 * Option 2: Thread.sleep(...)
		 * - Artificially keeps main thread alive for demo purposes
		 * - Helps visualize non-blocking behavior
		 * - Not reliable (depends on timing guess)
		 * - Not recommended in production code
		 * - Not needed in long-running applications (e.g., servers)
		 */
		// Thread.sleep(3000);
		
		System.out.println("-- Main thread (end) --");  // runs AFTER async task completes (because of join())
	}
}

/*
-- Main thread (start) --
Hello from CompletableFuture! Processed
-- Main thread (end) --
*/



/*
 * CompletableFuture.supplyAsync() uses ForkJoinPool.commonPool()
 * → Threads in this pool are daemon threads
 *
 * - JVM does NOT wait for daemon threads
 * - If main ends early, JVM can exit before async work completes
 *
 * 1. main thread starts
 * 2. async task submitted to ForkJoinPool (daemon thread)
 * 3. callback chain attached
 * 4. main thread prints: "-- Main thread (start) --"
 * 5. main thread finishes execution of main() method
 *
 * 6. JVM checks:
 *    - Any user (non-daemon) threads alive? ❌ No (only daemon threads)
 *
 * 7. JVM initiates shutdown
 * 8. All daemon threads are terminated abruptly
 * 9. Async task may NOT complete
 *
 * --------------------------------------------------
 * Summary:
 *
 * - CompletableFuture → submits task → ForkJoinPool (daemon thread)
 * - main thread ends
 *   → JVM checks for user threads
 *   → none found
 *   → JVM shuts down
 *   → daemon threads are killed
 *   → async task may stop midway
 *
 * NOTE:
 * CompletableFuture itself does NOT "terminate"
 * → JVM shutdown causes daemon threads to stop
 *
 * --------------------------------------------------
 * Using non-daemon threads (IMPORTANT):
 *
 * ExecutorService executor = Executors.newFixedThreadPool(2);
 *
 * CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
 *     // task
 *     return "Hello";
 * }, executor);
 *
 * // Ensure task completion (optional but recommended)
 * future.join();
 *
 * // Properly shutdown executor, Only when YOU create the executor
 * executor.shutdown();
 * 
 * If YOU create Executor → YOU must shutdown()
 * If JVM provides Executor (commonPool) → DO NOT shutdown()
 *
 * Now:
 * - Threads are user (non-daemon) threads
 * - JVM will wait for them to finish
 * - Even without join(), task will complete
 * - shutdown() is required to release resources and allow JVM to exit cleanly
 */





/*
 * 1. Using Future (ExecutorService):
 * 
 *    ExecutorService executor = Executors.newSingleThreadExecutor();
 *    
 *    Future<Integer> future = executor.submit(() -> {
 *        Thread.sleep(1000);
 *        return 10;
 *    });
 *    
 *    System.out.println("Waiting for result...");
 *    System.out.println("Result: " + future.get()); // BLOCKS main thread until result is available
 *    
 *    executor.shutdown();
 * 
 * Key points:
 * - future.get() is blocking
 * - No easy way to chain further processing
 * - Manual thread management required
*/


/*
 * 2. Using CompletableFuture:
 * 
 *    CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
 *        try { Thread.sleep(1000); } catch (InterruptedException e) {}
 *        return 10;
 *    });
 *    
 *    // Non-blocking style (callback)
 *    cf.thenAccept(result -> System.out.println("Result: " + result));
 *    
 *    // Blocking style (similar to Future.get())
 *    // optional blocking
 *    System.out.println("Blocking result: " + cf.join());
 * 
 * Key points:
 * - thenAccept() is non-blocking (runs asynchronously)
 * - join()/get() blocks if you explicitly call it
 * - Supports chaining and composition
*/


/*
 - Future.get() → only blocking
 - CompletableFuture.get() → same blocking behavior, but you also get a whole async API (join(), chaining, callbacks, manual completion).
*/


