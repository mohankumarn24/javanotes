package com.notes.javafeatures.java21;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/*
INTERVIEW TRAPS:

Do NOT use Virtual Threads for:
1. CPU heavy tasks (use parallel streams / ForkJoin)
2. Long synchronized blocks
3. Tight loops / compute-heavy work

BEST USE:
- DB calls
- REST calls
- Kafka / IO operations
*/
public class VirtualThreadDemo {
    public static void main(String[] args) throws Exception {

        // =========================================================
        // 1. SIMPLE VIRTUAL THREAD
        // =========================================================
        Thread.startVirtualThread(() -> {
            // Lightweight thread managed by JVM (not OS)
            System.out.println("Simple Virtual Thread: " + Thread.currentThread());
        });

        // =========================================================
        // 2. THREAD BUILDER (OPTIONAL CONTROL)
        // =========================================================
        Thread vt = Thread.ofVirtual()
                .name("my-virtual-thread") // naming helps debugging
                .start(() -> {
                    System.out.println("Custom Named VT: " + Thread.currentThread());
                });

        vt.join(); // wait for completion

        // =========================================================
        // 3. MOST IMPORTANT: EXECUTOR WITH VIRTUAL THREADS
        // =========================================================
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

        // =========================================================
        // 4. CALLABLE + FUTURE (RETURN VALUE)
        // =========================================================
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executorService.submit(() -> {
                Thread.sleep(500); // simulate work
                return "Result from Virtual Thread";
            });

            // get() blocks — but blocking is OK here
            System.out.println("Future Result: " + future.get());
        }

        // =========================================================
        // 5. CHECK IF THREAD IS VIRTUAL
        // =========================================================
        Thread.startVirtualThread(() -> {
            System.out.println("Is Virtual? " + Thread.currentThread().isVirtual());
        });

        // =========================================================
        // 6. MASSIVE SCALABILITY DEMO
        // =========================================================
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
        
        System.out.println("Main completed");
        
        // Extra
        virtualThreadPinning();
        migration();
    }
    
    // --------------------------------------------
    // Virtual Thread Pinning Problem
    // --------------------------------------------
    /*
     * FIX FOR PINNING (Using ReentrantLock)
     *
     * Problem with synchronized:
     * - Causes "pinning" in virtual threads
     * - Virtual thread cannot detach from carrier thread
     * - Blocks carrier thread → reduces scalability
     *
     * Why ReentrantLock:
     * - Does NOT cause pinning
     * - Allows virtual thread to unmount during blocking
     * - Better for virtual thread environments
     *
     * Best Practice:
     * - Avoid synchronized with virtual threads
     * - Prefer ReentrantLock or non-blocking designs
     */
    static void virtualThreadPinning() throws Exception {
        System.out.println("\n=== Virtual Thread (No Pinning) ===");

        var lock = new ReentrantLock();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5; i++) {
                executor.submit(() -> {
                    lock.lock();
                    try {
                        Thread.sleep(1000);
                        System.out.println("Task: " + Thread.currentThread());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                });
            }
        }
    }

    // --------------------------------------------
    // Migration from CompletableFuture
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
    static void migration() throws Exception {
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
Simple Virtual Thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1
Custom Named VT: VirtualThread[#23,my-virtual-thread]/runnable@ForkJoinPool-1-worker-2
Task 1 started on VirtualThread[#27]/runnable@ForkJoinPool-1-worker-2
Task 4 started on VirtualThread[#30]/runnable@ForkJoinPool-1-worker-5
Task 0 started on VirtualThread[#26]/runnable@ForkJoinPool-1-worker-3
Task 3 started on VirtualThread[#29]/runnable@ForkJoinPool-1-worker-4
Task 2 started on VirtualThread[#28]/runnable@ForkJoinPool-1-worker-1
Task 2 completed
Task 3 completed
Task 0 completed
Task 1 completed
Task 4 completed
Future Result: Result from Virtual Thread
Is Virtual? true
Main completed

=== Virtual Thread (No Pinning) ===
Task: VirtualThread[#1043]/runnable@ForkJoinPool-1-worker-9
Task: VirtualThread[#1044]/runnable@ForkJoinPool-1-worker-6
Task: VirtualThread[#1045]/runnable@ForkJoinPool-1-worker-9
Task: VirtualThread[#1047]/runnable@ForkJoinPool-1-worker-7
Task: VirtualThread[#1046]/runnable@ForkJoinPool-1-worker-9

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