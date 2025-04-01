package com.notes.multithreading.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleThreadExecutorDemo {

	public static void main(String[] args) {
		Executor executor = Executors.newSingleThreadExecutor();

		Runnable task = () -> {
			System.out.println("Task is executing...");
		};

		executor.execute(task);
	}

}
