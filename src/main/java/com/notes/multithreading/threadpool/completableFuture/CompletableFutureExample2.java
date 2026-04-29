package com.notes.multithreading.threadpool.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample2 {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {

		// Run async task
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000); // simulate delay
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Hello from async task!";
		});

		// Attach callback
		CompletableFuture<String> resultFuture = future.thenApply(message -> message + " Processed!");

		// Non-blocking - do something else here
		System.out.println("Doing other work...");

		// Block and get result (for demo)
		String result = resultFuture.get();
		System.out.println("Result: " + result);
	}
}