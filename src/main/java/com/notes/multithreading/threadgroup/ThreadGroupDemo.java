package com.notes.multithreading.threadgroup;

public class ThreadGroupDemo {
	
	public static void main(String[] args) {
		
		Runnable runnable = new ThreadGroupWorkerThread();
		ThreadGroup tg1 = new ThreadGroup("Parent ThreadGroup");
		
		Thread t1 = new Thread(tg1, runnable, "one");    
		Thread t2 = new Thread(tg1, runnable, "two");    
		Thread t3 = new Thread(tg1, runnable, "three"); 
		
		t1.start();
		t2.start();
		t3.start();
		
        System.out.println("\nThread Group Name: " + tg1.getName());
        System.out.println("\nThread Group List: ");
        tg1.list();
        System.out.println("\nThe total number of active threads are: " + tg1.activeCount()); 
        System.out.println("\nThe total number of active thread groups are: " + tg1.activeGroupCount());  
	}
}
