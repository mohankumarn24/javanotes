package com.notes.multithreading.deadlock;

import java.util.concurrent.CyclicBarrier;

/**
 * This class guarantees deadlock by synchronizing both threads before acquiring the second lock 
 */
public class A2_DeadLockGuaranteed {

    public static void main(String[] args) {

        final Object resource1 = new Object();
        final Object resource2 = new Object();

        CyclicBarrier barrier = new CyclicBarrier(2);

        Thread t1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("T1 locked resource1");
                await(barrier);
                synchronized (resource2) {
                    System.out.println("T1 locked resource2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("T2 locked resource2");
                await(barrier);
                synchronized (resource1) {
                    System.out.println("T2 locked resource1");
                }
            }
        });

        t1.start();
        t2.start();
    }

    private static void await(CyclicBarrier barrier) {
        try {
            barrier.await();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
T1 locked resource1
T2 locked resource2
*/




/*
====================================================================
WHY CyclicBarrier IS USED HERE (TO GUARANTEE DEADLOCK)
====================================================================

Problem with normal deadlock demo:
---------------------------------
Even though threads acquire locks in opposite order, deadlock is NOT guaranteed.

Reason:
- Thread scheduling is non-deterministic
- One thread may acquire BOTH locks before the other starts
- Result: program completes normally (no deadlock)

Example failure case:
- T1 locks resource1
- T1 quickly locks resource2
- T2 starts later → no deadlock

--------------------------------------------------------------------
HOW CyclicBarrier SOLVES THIS
--------------------------------------------------------------------

CyclicBarrier ensures BOTH threads reach the SAME execution point
before proceeding further.

Flow with barrier:
------------------
1. T1 locks resource1
2. T2 locks resource2

3. Both threads call barrier.await()
   → both WAIT until the other arrives

4. Once both reach barrier:
   - T1 tries to lock resource2 (held by T2)
   - T2 tries to lock resource1 (held by T1)

5. Circular wait is FORCED → DEADLOCK GUARANTEED

--------------------------------------------------------------------
KEY IDEA:
--------------------------------------------------------------------

Without barrier:
→ Deadlock is probabilistic (may or may not happen)

With barrier:
→ Deadlock is deterministic (will happen every time)

--------------------------------------------------------------------
INTERVIEW ONE-LINER:
--------------------------------------------------------------------

"CyclicBarrier is used to synchronize threads so that both hold
one lock before attempting the second, ensuring a guaranteed
circular wait and thus a deterministic deadlock."

====================================================================
*/