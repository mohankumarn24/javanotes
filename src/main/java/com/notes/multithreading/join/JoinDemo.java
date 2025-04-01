package com.notes.multithreading.join;

public class JoinDemo {

	public static void main(String[] args) throws InterruptedException {
		
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println("Hi");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println("Hello");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		Thread.sleep(10);
		
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Bye");
	}

}
