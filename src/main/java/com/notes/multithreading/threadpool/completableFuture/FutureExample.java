package com.notes.multithreading.threadpool.completableFuture;

import java.util.concurrent.*;

// https://chatgpt.com/share/68b1a945-fce4-8004-970f-175e01ef7440
public class FutureExample {

	public static void main(String[] args) throws Exception {

		ExecutorService executor = Executors.newFixedThreadPool(2);

		// Submit async task
		Future<String> future = executor.submit(() -> {
			Thread.sleep(2000);
			return "Hello from Future!";
		});

		// Doing something else...
		System.out.println("Main thread working...");

		// Blocking - must wait
		String result = future.get();
		System.out.println("Result: " + result);

		executor.shutdown();
	}
}
