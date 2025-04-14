package com.notes.multithreading.waitnotify;

// https://www.digitalocean.com/community/tutorials/java-thread-wait-notify-and-notifyall-example
public class WaitNotifyDemo {

	public static void main(String[] args) {
		
		Message msg = new Message("process it");
		Waiter waiter1 = new Waiter(msg);
		new Thread(waiter1, "waiter1").start();

		Waiter waiter2 = new Waiter(msg);
		new Thread(waiter2, "waiter2").start();

		Notifier notifier = new Notifier(msg);
		new Thread(notifier, "notifier").start();
		System.out.println("All the threads are started");
	}

}