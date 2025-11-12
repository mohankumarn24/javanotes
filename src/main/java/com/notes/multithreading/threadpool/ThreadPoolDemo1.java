package com.notes.multithreading.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo1 {

	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(5); 	//creating a pool of 5 threads
        for (int i = 0; i < 10; i++) {  
            Runnable runnable = new WorkerThread(String.valueOf(i));  
            executorService.execute(runnable); 								//calling execute method of ExecutorService 
         }  
        
        // Graceful shutdown
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            	executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
        	executorService.shutdownNow();
            Thread.currentThread().interrupt();
        } 
        System.out.println("Finished all threads");  		
	}
}
