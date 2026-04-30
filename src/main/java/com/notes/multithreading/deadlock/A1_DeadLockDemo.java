package com.notes.multithreading.deadlock;
       
/**
 * NOTE: 
 *  - Deadlock not guaranteed by this code 
 *  - This class demonstrates deadlock with high probability using sleep to increase contention
 *  - Refer "DeadLockGuaranteed.java"     
 */
public class A1_DeadLockDemo {
	
	// private static final Object resource1 = new Object();
	// private static final Object resource2 = new Object();
	
	public static void main(String[] args) {
		
		/**
		 * final String resource1 = "foo";
		 * Dont use 'String' as Strings are interned in Java. 
		 * Other parts of JVM might use same lock → unpredictable issues
		 */
		final Object resource1 = new Object();
		final Object resource2 = new Object();

		// t1 tries to lock resource1 then resource2
		Thread t1 = new Thread(() -> {
			synchronized (resource1) {
				System.out.println("Thread 1: locked resource 1");
				try { 
					Thread.sleep(100); 
				} catch (InterruptedException e) { 
					Thread.currentThread().interrupt(); 
				}
				synchronized (resource2) {
					System.out.println("Thread 1: locked resource 2");
				}
			}
		});
		
		// t2 tries to lock resource2 then resource1
		Thread t2 = new Thread(() -> {
				synchronized (resource2) {
					System.out.println("Thread 2: locked resource 2");
					try { 
						Thread.sleep(100); 
					} catch (InterruptedException e) { 
						Thread.currentThread().interrupt(); 
					}
					synchronized (resource1) {
						System.out.println("Thread 2: locked resource 1");
					}
				}
		});
		
		t1.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); 
		}
		t2.start();
	}
}

/*
Thread 2: locked resource 2
Thread 1: locked resource 1
*/





/*
======================== DEADLOCK PREVENTION ========================

Deadlock occurs when:
Thread A holds Lock 1 and waits for Lock 2
Thread B holds Lock 2 and waits for Lock 1

--------------------------------------------------------------------
1. CONSISTENT LOCK ORDERING (MOST IMPORTANT)
--------------------------------------------------------------------
Rule: Always acquire locks in the same global order.

Example:

final Object lock1 = new Object();
final Object lock2 = new Object();

Thread t1 = new Thread(() -> {
    synchronized (lock1) {
        synchronized (lock2) {
            System.out.println("T1 acquired both locks");
        }
    }
});

Thread t2 = new Thread(() -> {
    synchronized (lock1) {   // SAME ORDER
        synchronized (lock2) {
            System.out.println("T2 acquired both locks");
        }
    }
});

--------------------------------------------------------------------
2. USE SINGLE LOCK (WHEN POSSIBLE)
--------------------------------------------------------------------
If resources are always used together, use one lock.

final Object lock = new Object();

synchronized (lock) {
    // access resource1 and resource2 safely
}

--------------------------------------------------------------------
3. USE tryLock() WITH TIMEOUT (ReentrantLock)
--------------------------------------------------------------------
Avoid infinite waiting by attempting lock with timeout.

ReentrantLock lock1 = new ReentrantLock();
ReentrantLock lock2 = new ReentrantLock();

Thread t = new Thread(() -> {
    try {
        if (lock1.tryLock()) {
            try {
                if (lock2.tryLock()) {
                    try {
                        System.out.println("Acquired both locks");
                    } finally {
                        lock2.unlock();
                    }
                }
            } finally {
                lock1.unlock();
            }
        }
    } catch (Exception e) {
        Thread.currentThread().interrupt();
    }
});

--------------------------------------------------------------------
4. AVOID NESTED LOCKS (REDUCE LOCK DEPENDENCY)
--------------------------------------------------------------------
Do not hold one lock while acquiring another.

BAD:
synchronized (lock1) {
    synchronized (lock2) {
        // risky
    }
}

BETTER:
synchronized (lock1) {
    // do work
}
synchronized (lock2) {
    // do work
}

--------------------------------------------------------------------
5. USE HIGH-LEVEL CONCURRENCY UTILITIES
--------------------------------------------------------------------
Prefer built-in thread-safe classes instead of manual locks.

Examples:
- ConcurrentHashMap instead of HashMap + synchronized
- BlockingQueue instead of wait/notify
- ExecutorService instead of manual thread management

ExecutorService executor = Executors.newFixedThreadPool(2);

executor.submit(() -> {
    System.out.println("Task executed safely");
});

executor.shutdown();

--------------------------------------------------------------------
IMPORTANT NOTE:
--------------------------------------------------------------------
Avoid using String literals as locks:

BAD:
final String lock = "LOCK";

GOOD:
final Object lock = new Object();

Reason:
String literals are interned and may be shared across JVM,
causing unintended lock contention.

====================================================================
*/