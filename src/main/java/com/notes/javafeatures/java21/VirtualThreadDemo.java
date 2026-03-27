package com.notes.javafeatures.java21;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/*
 * TRAPS:
 * 
 * Do NOT use Virtual Threads for:
 *  - CPU heavy tasks (use parallel streams / ForkJoin)
 *  - Long synchronized blocks
 *  - Tight loops / compute-heavy work
 * 
 * BEST USE:
 *  - DB calls
 *  - REST calls
 *  - Kafka / IO operations
*/
public class VirtualThreadDemo {
    public static void main(String[] args) throws Exception {

    	createSimpleVirtualThread();
    	createVirtualThreadWithBuilder();
    	
    	executeTasksWithVirtualThreadExecutor();
    	executeCallableWithVirtualThreads();
    	verifyVirtualThread();
    	
    	demonstrateVirtualThreadScalability();
    	demonstrateVirtualThreadPinning();
    	demonstrateVirtualThreadWithoutPinning();
    	
    	migrateFromCompletableFutureToStructuredConcurrency();
        
        /**
         * 
         * | Thread Type     | JVM waits?  |
         * | --------------- | ----------  |
         * | Platform thread | ✅ Yes      |
         * | Virtual thread  | ✅ Yes      |
         * | Daemon thread   | ❌ No       |
         * 
         */
        
        /*
         * Virtual Threads Execution Behavior (Java 21)
         *
         * - Virtual threads are NOT daemon threads.
         * - They behave like normal user threads → JVM waits for them to complete.
         *
         * - Using try-with-resources:
         *      try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) { ... }
         *   → executor.close() is called automatically
         *   → waits for all submitted tasks to finish before exiting block
         *
         * - Even without try-with-resources:
         *   → JVM will NOT exit until all virtual threads complete
         *
         * - Tasks terminate early ONLY if:
         *      executor.shutdownNow() is called
         *      OR threads are explicitly created as daemon
         *
         * - Best Practice:
         *      Always use try-with-resources for ExecutorService
         *      → ensures proper shutdown and no thread leaks
         *
         * - Key takeaway:
         *      Virtual threads = lightweight, but still "user threads" (safe, not killed early)
         */
    }
    
	// =========================================================
    // 1. SIMPLE VIRTUAL THREAD
    // =========================================================
    private static void createSimpleVirtualThread() {
        Thread.startVirtualThread(() -> {
            // Lightweight thread managed by JVM (not OS)
            System.out.println("Simple Virtual Thread: " + Thread.currentThread());
        });
		
	}
    
	// =========================================================
    // 2. THREAD BUILDER (OPTIONAL CONTROL)
    // =========================================================
    private static void createVirtualThreadWithBuilder() throws InterruptedException {
        Thread vt = Thread.ofVirtual()
                .name("my-virtual-thread") // naming helps debugging
                .start(() -> {
                    System.out.println("Custom Named VT: " + Thread.currentThread());
                });

        vt.join(); // wait for completion
	}
    
	// =========================================================
    // 3. MOST IMPORTANT: EXECUTOR WITH VIRTUAL THREADS
    // =========================================================
    private static void executeTasksWithVirtualThreadExecutor() {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            // Each task gets its own virtual thread
            for (int i = 0; i < 5; i++) {
                int taskId = i;

                executorService.submit(() -> {
                    System.out.println("Task " + taskId + " started on " + Thread.currentThread());

                    try {
                        // Blocking is SAFE with virtual threads
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.out.println("Task " + taskId + " completed");
                });
            }
        } // Auto-closes executor
	}
    
	// =========================================================
    // 4. CALLABLE + FUTURE (RETURN VALUE)
    // =========================================================
    private static void executeCallableWithVirtualThreads() throws InterruptedException, ExecutionException {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executorService.submit(() -> {
                Thread.sleep(500); // simulate work
                return "Result from Virtual Thread";
            });

            // get() blocks — but blocking is OK here
            System.out.println("Future Result: " + future.get());
        }
	}
    
	// =========================================================
    // 5. CHECK IF THREAD IS VIRTUAL
    // =========================================================
    private static void verifyVirtualThread() {
        Thread.startVirtualThread(() -> {
            System.out.println("Is Virtual? " + Thread.currentThread().isVirtual());
        });
	}
    
    // =========================================================
    // 6. MASSIVE SCALABILITY DEMO
    // =========================================================
    private static void demonstrateVirtualThreadScalability() {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
            	executorService.submit(() -> {
                    // Thousands of threads are cheap
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); }
                });
            }
        }
	}

	// ------------------------------------
    // 7.a. Virtual Thread Pinning (BAD ❌)
    // ------------------------------------
    /*
     * PINNING IN VIRTUAL THREADS:
     *
     * Problem:
     * - Pinning occurs when a virtual thread blocks while holding a lock
     * - The virtual thread cannot unmount from its carrier thread
     * - Carrier thread gets blocked → reduces scalability
     *
     * Important:
     * - This can happen with BOTH synchronized and ReentrantLock
     * - The issue is NOT the lock itself, but blocking while holding it
     *
     * Best Practices:
     * - Avoid blocking operations while holding locks
     * - Minimize lock scope
     * - Prefer non-blocking or lock-free designs when possible
     * - Let virtual threads block WITHOUT holding locks (safe unmount)
     */
    
    /**
     * WITH PINNING:
     * Virtual Thread → holds lock → cannot unmount → carrier blocked ❌
     * 
     * 👉 Golden Rule:
     * NEVER perform blocking operations (sleep, IO, DB calls) while holding a lock
     */
    static void demonstrateVirtualThreadPinning() throws Exception {
    	
        /**
         * 👉 PINNING SCENARIO:
         * - Multiple virtual threads compete for the same lock
         * - One thread acquires the lock
         * - That thread performs a blocking operation (sleep)
         * - Other threads wait for the lock
         *
         * ❌ Problem:
         * - Virtual thread is BLOCKED while holding the lock
         * - It CANNOT unmount from carrier thread
         * - Carrier (OS thread) gets pinned → blocked
         * - Execution becomes sequential (~5 sec)
         *
         * 👉 Root Cause:
         * Blocking operation INSIDE lock
         *
         * 👉 Golden Rule:
         * NEVER perform blocking (sleep, IO, DB calls) while holding a lock
         */
    	
        System.out.println("\n=== Virtual Thread (Pinning) ===");
        long start = System.currentTimeMillis();
        
        var lock = new ReentrantLock();
        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5; i++) {
                executorService.submit(() -> {
                    lock.lock();
                    try {
                        Thread.sleep(1000);				// ❌ blocking inside lock → pinning
                        System.out.println("Task: " + Thread.currentThread());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                });
            }
        }

        System.out.println("Time taken: " + (System.currentTimeMillis() - start) + " ms");
    }
    
    // --------------------------------------------
    // 7.b. Virtual Thread (No Pinning) (GOOD ✅)
    // --------------------------------------------
    /**
     * WITHOUT PINNING:
     * Virtual Thread → sleep → unmount → carrier free → scalable ✅
     */
    static void demonstrateVirtualThreadWithoutPinning() throws Exception {
    	
        /**
         * 👉 NO PINNING SCENARIO:
         * - Multiple virtual threads run independently
         * - No lock is held during blocking
         * - Each thread sleeps (blocking)
         *
         * ✅ Behavior:
         * - JVM UNMOUNTS virtual threads during blocking
         * - Carrier threads are reused
         * - Tasks execute in parallel (~1 sec)
         *
         * 👉 Key Insight:
         * Blocking is SAFE in virtual threads
         * ONLY unsafe when combined with locks
         */
   	
        System.out.println("\n=== Virtual Thread (No Pinning) ===");
        long start = System.currentTimeMillis();

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5; i++) {
            	executorService.submit(() -> {
                    try {
                        Thread.sleep(1000);				// ✅ safe (no lock held)
                        System.out.println("Task: " + Thread.currentThread());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }

        System.out.println("Time taken: " + (System.currentTimeMillis() - start) + " ms");
    }

    // --------------------------------------------
    // 8. Migration from CompletableFuture
    // --------------------------------------------
    /*
     * MIGRATION: CompletableFuture → Structured Concurrency
     *
     * OLD (CompletableFuture):
     * - Uses async chaining (thenCombine, thenApply, etc.)
     * - Harder to read and debug
     * - Error handling is complex
     * - No automatic cancellation of other tasks
     *
     * NEW (Structured Concurrency):
     * - Synchronous, readable style (looks like normal code)
     * - Tasks are grouped as one unit (scope)
     * - Fail-fast: if one fails → others are cancelled automatically
     * - Easier debugging and better error propagation
     *
     * KEY IDEA:
     * Replace async pipelines with structured, scoped concurrency
     *
     * INSIGHT:
     * Structured Concurrency simplifies async code by making it readable,
     * safer, and automatically handling cancellation.
     */
    static void migrateFromCompletableFutureToStructuredConcurrency() throws Exception {
    	System.out.println("\n=== Migration ===");
    	
        CompletableFuture<String> user = CompletableFuture.supplyAsync(() -> fetch("User"));
        CompletableFuture<String> order = CompletableFuture.supplyAsync(() -> fetch("Order"));
        String oldResult = user.thenCombine(order, (u, o) -> u + o).join();

        System.out.println("Old: " + oldResult);

        try (@SuppressWarnings("preview")
        var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var u = scope.fork(() -> fetch("User"));
            var o = scope.fork(() -> fetch("Order"));

            scope.join();
            scope.throwIfFailed();

            String newResult = u.get() + o.get();
            System.out.println("New: " + newResult);
        }
    }

    // Helper method required by migrationExample
    static String fetch(String name) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return name;
    }
}

/*
Custom Named VT: VirtualThread[#23,my-virtual-thread]/runnable@ForkJoinPool-1-worker-2
Simple Virtual Thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1
Task 4 started on VirtualThread[#30]/runnable@ForkJoinPool-1-worker-5
Task 1 started on VirtualThread[#27]/runnable@ForkJoinPool-1-worker-1
Task 3 started on VirtualThread[#29]/runnable@ForkJoinPool-1-worker-4
Task 0 started on VirtualThread[#26]/runnable@ForkJoinPool-1-worker-3
Task 2 started on VirtualThread[#28]/runnable@ForkJoinPool-1-worker-2
Task 2 completed
Task 1 completed
Task 4 completed
Task 3 completed
Task 0 completed
Future Result: Result from Virtual Thread
Is Virtual? true

=== Virtual Thread (Pinning) ===
Task: VirtualThread[#1044]/runnable@ForkJoinPool-1-worker-6
Task: VirtualThread[#1043]/runnable@ForkJoinPool-1-worker-7
Task: VirtualThread[#1045]/runnable@ForkJoinPool-1-worker-6
Task: VirtualThread[#1046]/runnable@ForkJoinPool-1-worker-7
Task: VirtualThread[#1047]/runnable@ForkJoinPool-1-worker-6
Time taken: 5054 ms

=== Virtual Thread (No Pinning) ===
Task: VirtualThread[#1049]/runnable@ForkJoinPool-1-worker-12
Task: VirtualThread[#1048]/runnable@ForkJoinPool-1-worker-7
Task: VirtualThread[#1050]/runnable@ForkJoinPool-1-worker-9
Task: VirtualThread[#1052]/runnable@ForkJoinPool-1-worker-11
Task: VirtualThread[#1051]/runnable@ForkJoinPool-1-worker-6
Time taken: 1004 ms

=== Migration ===
Old: UserOrder
New: UserOrder
*/


/*
| Scenario         | Before (≤ Java 17)                | After (Java 21)                               | Replace?          |
| ---------------- | --------------------------------- | --------------------------------------------- | ----------------- |
| Start thread     | `new Thread(r).start()`           | `Thread.startVirtualThread(r)`                | ✅ Yes            |
| Many tasks (I/O) | `Executors.newFixedThreadPool(n)` | `Executors.newVirtualThreadPerTaskExecutor()` | ✅ Yes            |
| Cached threads   | `newCachedThreadPool()`           | ❌ Not needed                                 | ✅ Yes            |
| Single thread    | `newSingleThreadExecutor()`       | Same                                          | ❌ No             |
| Task definition  | `Runnable / Callable`             | Same                                          | ❌ No             |
| Return result    | `Future`                          | Same                                          | ❌ No             |
| Async chaining   | `CompletableFuture`               | Optional                                      | ⚠️ Reduce usage   |
| ThreadFactory    | Custom                            | `Thread.ofVirtual().factory()`                | ⚠️ Optional       |
| Blocking calls   | Avoid                             | Safe                                          | ✅ Mindset change |
| Thread tuning    | Required                          | Not needed                                    | ✅ Eliminated     |
*/