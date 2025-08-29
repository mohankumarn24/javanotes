package com.notes.multithreading.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// TODO
// https://medium.com/javarevisited/java-completablefuture-c47ca8c885af
// https://chatgpt.com/share/68b1a945-fce4-8004-970f-175e01ef7440
public class CompletableFutureExample {
	
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