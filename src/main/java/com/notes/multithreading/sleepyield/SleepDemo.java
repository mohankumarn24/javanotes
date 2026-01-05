package com.notes.multithreading.sleepyield;

class MyThread1 extends Thread {
	@Override
    public void run() {
        System.out.println("Thread started: " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // sleep for 2 sec
            System.out.println("Woke up: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SleepDemo {
    public static void main(String[] args) {
        new MyThread1().start();
    }
}


/*
1. Sleep:
 	- Puts the current thread into TIMED_WAITING state for a fixed amount of time.
 	- The thread definitely stops running for that duration.
 	- The CPU scheduler can pick any other thread to run.

	| Feature        | 'sleep()'                                                                    |
	| -------------- | ---------------------------------------------------------------------------- |
	| State change   | RUNNING → TIMED_WAITING                                                      |
	| Duration       | Fixed time (ms/ns)                                                           |
	| Thread stops?  | YES                                                                          |
	| Lock released? | ❌ NO (does *not* release object/class monitor)                              |
	| Interruptable? | ✔ Yes, throws 'InterruptedException'                                        |
	| Guarantee?     | Always sleeps for at least the given time (no early wake unless interrupted) |


2. Yield:
 	- Suggests (but does NOT force) the scheduler to give up the current CPU time slice.
 	- Thread goes from RUNNING → RUNNABLE (ready-queue).
 	- Scheduler may immediately schedule the same thread again.

	| Feature        | 'yield()'                     |
	| -------------- | ----------------------------- |
	| State change   | RUNNING → RUNNABLE            |
	| Duration       | 0 (just a hint)               |
	| Thread stops?  | ❌ Not guaranteed              |
	| Lock released? | ❌ NO                          |
	| Interruptable? | Not applicable                |
	| Guarantee?     | No — yield is only a **hint** |

3. sleep() vs yield() — Conceptual Difference:
	| Property               | 'sleep()'                 | 'yield()'                     |
	| ---------------------- | ------------------------- | ----------------------------- |
	| Blocks thread          | ✔ Yes                     | ❌ No                          |
	| Scheduler must switch? | ✔ Yes                     | ❌ No                          |
	| Time duration          | Fixed                     | Zero                          |
	| Releases monitor lock? | ❌ No                      | ❌ No                          |
	| Use case               | Delay, pacing, simulation | Cooperative multitasking hint |

4. When to use which?
	4a. Use sleep() when:
		 - You want actual delay or waiting.
		 - You’re simulating long-running tasks.
		 - Implement retry with delay (e.g., retry login every 1 second).
		 - Pacing background tasks.

	4b. Use yield() when:
		 - You want to politely give other threads a chance.
		 - Useful in:
			 -- Performance testing
			 -- Debugging race conditions
			 -- Tight loops where you want to reduce CPU monopolization
		⚠ But yield is rarely used in real apps. It’s too unpredictable.
*/