package com.notes.multithreading.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo2 {
	
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(5); //creating a pool of 5 threads
		Runnable r1 = new WorkerThread(String.valueOf(1));
		Runnable r2 = new WorkerThread(String.valueOf(2));
		Runnable r3 = new WorkerThread(String.valueOf(3));
		Runnable r4 = new WorkerThread(String.valueOf(4));
		Runnable r5 = new WorkerThread(String.valueOf(5));
		Runnable r6 = new WorkerThread(String.valueOf(6));
		
		executor.execute(r1);
		executor.execute(r2);
		executor.execute(r3);
		executor.execute(r4);
		executor.execute(r5);
		executor.execute(r6);
        
        executor.shutdown();  
        while (!executor.isTerminated()) {
        	
        }
        System.out.println("Finished all threads");  		
	}	

}
