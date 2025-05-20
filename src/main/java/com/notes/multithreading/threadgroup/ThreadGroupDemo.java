package com.notes.multithreading.threadgroup;

public class ThreadGroupDemo {
	
	public static void main(String[] args) {
		
		Runnable runnable = new ThreadGroupWorkerThread();
		ThreadGroup threadGroup = new ThreadGroup("Parent ThreadGroup");
		
		Thread t1 = new Thread(threadGroup, runnable, "one");    
		Thread t2 = new Thread(threadGroup, runnable, "two");    
		Thread t3 = new Thread(threadGroup, runnable, "three"); 
		
		t1.start();
		t2.start();
		t3.start();
		
        System.out.println("\nThread Group Name: " + threadGroup.getName());
        System.out.println("\nThread Group List: ");
        threadGroup.list();
        System.out.println("\nThe total number of active threads are: " + threadGroup.activeCount()); 
        System.out.println("\nThe total number of active thread groups are: " + threadGroup.activeGroupCount());  
	}
}
