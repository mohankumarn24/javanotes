package com.notes.multithreading.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * The Callable interface is similar to Runnable, but it can return a result or throw an exception. 
 * The Future interface represents the result of an asynchronous computation and provides methods to retrieve the result or handle exceptions.
 * 
 * In this example, we create a Callable task that sleeps for 2 seconds and returns the value 42. 
 * We submit the task to an executor, and then we use the get method Future to wait for the result of the computation.
 */
public class CallableAndFutureExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		Callable<Integer> task = () -> {
			Thread.sleep(2000);
			return 42;
		};

		Future<Integer> future = executorService.submit(task);

		System.out.println("Waiting for the result...");
		Integer result = future.get(); // Waits if necessary for the computation to complete, and thenretrieves its result.
		System.out.println("Result: " + result);

		executorService.shutdown();
	}
}

