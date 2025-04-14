package com.notes.multithreading.waitnotify;

public class Notifier implements Runnable {

	private Message msg;

	public Notifier(Message msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println(name + " started");
		try {
			Thread.sleep(1000);
			synchronized (msg) {
				msg.setMsg(name + " Notifier work done");
				msg.notify();
				// msg.notifyAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

/*
If we comment the notifyAll() call and uncomment the notify() call in Notifier class, below will be the output produced:
When we will invoke the above program, we will see below output but program will not complete 
because there are two threads waiting on Message object and notify() method 
has wake up only one of them, the other thread is still waiting to get notified.

If we comment the notifyAll() call and uncomment the notify() call in Notifier class, below will be the output produced:
Since notifyAll() method wake up both the Waiter threads and program completes and terminates after execution. That’s all for wait, notify and notifyAll in java.
*/
