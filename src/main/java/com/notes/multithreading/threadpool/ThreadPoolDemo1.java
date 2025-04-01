package com.notes.multithreading.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo1 {

	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(5); //creating a pool of 5 threads
        for (int i = 0; i < 10; i++) {  
            Runnable runnable = new WorkerThread(String.valueOf(i));  
            executor.execute(runnable); //calling execute method of ExecutorService 
         }  
        
        executor.shutdown();  
        while (!executor.isTerminated()) {
        	
        }
        System.out.println("Finished all threads");  		
	}
}
