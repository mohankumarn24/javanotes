package com.notes.multithreading.threadpool.completableFuture;

import java.util.concurrent.CompletableFuture;

// https://chatgpt.com/share/68b1a945-fce4-8004-970f-175e01ef7440
// https://chatgpt.com/share/68b1b1b6-75e0-8004-b8cf-6dda11fb09c5
public class CompletableFutureExample {

	public static void main(String[] args) throws Exception {

		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return "Hello from CompletableFuture!";
		});

		// Attach callback (non-blocking)
		future.thenApply(msg -> msg + " Processed").thenAccept(result -> System.out.println(result));
		System.out.println("-- Main thread (start) --"); // runs even if above line is not completed
		
		// Blocking style (same as Future.get)
		// System.out.println("Blocking result: " + future.get());
		// System.out.println("Main thread working..."); // blocked till above line is completed
		
		// Ensure main thread doesn’t exit early
		Thread.sleep(3000);
		
		System.out.println(" -- Main thread (end) --"); // runs even if above line is not completed
	}
}

/*
1. Using Future:
	ExecutorService executor = Executors.newSingleThreadExecutor();
	Future<Integer> future = executor.submit(() -> {
	    Thread.sleep(1000);
	    return 10;
	});
	
	System.out.println("Waiting for result...");
	System.out.println("Result: " + future.get()); // Blocks
	executor.shutdown();


2. Using CompletableFuture:
	CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
	    try { Thread.sleep(1000); } catch (InterruptedException e) {}
	    return 10;
	});
	
	// Non-blocking style
	cf.thenAccept(result -> System.out.println("Result: " + result));
	
	// Blocking style (same as Future.get)
	System.out.println("Blocking result: " + cf.get());
*/

/*
 - Future.get() → only blocking
 - CompletableFuture.get() → same blocking behavior, but you also get a whole async API (join(), chaining, callbacks, manual completion).
*/


