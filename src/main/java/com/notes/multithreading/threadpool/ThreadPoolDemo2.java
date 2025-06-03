package com.notes.multithreading.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo2 {
	
	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(5); //creating a pool of 5 threads
		Runnable r1 = new WorkerThread(String.valueOf(1));
		Runnable r2 = new WorkerThread(String.valueOf(2));
		Runnable r3 = new WorkerThread(String.valueOf(3));
		Runnable r4 = new WorkerThread(String.valueOf(4));
		Runnable r5 = new WorkerThread(String.valueOf(5));
		Runnable r6 = new WorkerThread(String.valueOf(6));
		
		executorService.execute(r1);
		executorService.execute(r2);
		executorService.execute(r3);
		executorService.execute(r4);
		executorService.execute(r5);
		executorService.execute(r6);
        
		executorService.shutdown();  
        while (!executorService.isTerminated()) {
        	
        }
        System.out.println("Finished all threads");  		
	}	

}
