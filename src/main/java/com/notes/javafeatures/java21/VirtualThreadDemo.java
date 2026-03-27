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
    	verifyVirtualThread();
    	
    	executeTasksWithVirtualThreadExecutor();
    	demonstrateVirtualThreadScalability();
    	
    	executeCallableWithVirtualThreads();
    	executeCompletableFutureWithVirtualThreads();

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
    // SIMPLE VIRTUAL THREAD
    // =========================================================
    private static void createSimpleVirtualThread() throws InterruptedException {
    	System.out.println("== SIMPLE VIRTUAL THREAD ==");
		Thread vt = Thread.startVirtualThread(() -> {
			// Lightweight thread managed by JVM (not OS)
			System.out.println("Simple Virtual Thread: " + Thread.currentThread());
		});
                
        /**
         * startVirtualThread() starts asynchronously and returns immediately
         *
         * Main thread does NOT wait automatically
         * So next statements may execute before virtual thread prints output
         *
         * Use join() if you want predictable execution order
         */
        
        // vt.join(); // wait for virtual thread to finish
	}
    
	// =========================================================
    // THREAD BUILDER (OPTIONAL CONTROL)
    // =========================================================
    private static void createVirtualThreadWithBuilder() throws InterruptedException {
    	System.out.println("\n== THREAD BUILDER (OPTIONAL CONTROL) ==");
		Thread vt = Thread.ofVirtual()
				.name("my-virtual-thread") // naming helps debugging
				.start(() -> {
					System.out.println("Custom Named VT: " + Thread.currentThread());
				});

        vt.join(); // wait for completion
	}
    
	// =========================================================
    // CHECK IF THREAD IS VIRTUAL
    // =========================================================
    private static void verifyVirtualThread() {
    	System.out.println("\n== CHECK IF THREAD IS VIRTUAL ==");
        Thread.startVirtualThread(() -> {
            System.out.println("Is Virtual? " + Thread.currentThread().isVirtual());
        });
	}
    
	// =========================================================
    // EXECUTOR WITH VIRTUAL THREADS
    // =========================================================
    private static void executeTasksWithVirtualThreadExecutor() {
        System.out.println("\n== EXECUTOR WITH VIRTUAL THREADS ==");

        /**
         * Virtual Thread Per Task Executor:
         *
         * - Each submitted task gets a new virtual thread
         * - Ideal for large number of blocking tasks
         * - No need to manage fixed thread pool size
         * - JVM parks/unmounts virtual threads during blocking
         * - Carrier threads remain free for other work
         * - try-with-resources ensures graceful shutdown
         */
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5; i++) {
                int taskId = i;
                executorService.submit(() -> {
                    System.out.println("Task-" + taskId + " started on " + Thread.currentThread());
                    try {
                        // Simulate blocking work such as REST call / DB query
                        /**
                         * Virtual Thread Per Task Executor:
                         * - Each submitted task gets a new virtual thread
                         * - Blocking operations are safe and scalable
                         * - All 5 tasks sleep for 1 second in parallel
                         * - Total execution time should be ~1 second (not 5 seconds)
                         * 
                         * Expected idea:
                         *  - All 5 tasks start almost immediately
                         *  - All 5 tasks complete after ~1 second
                         *  - Total Time: ~1000 ms
                         */
                        Thread.sleep(1000);
                        System.out.println("Task-" + taskId + " completed on " + Thread.currentThread());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Task-" + taskId + " interrupted");
                    }
                });
            }
        }
    }
    
    // =========================================================
    // MASSIVE SCALABILITY DEMO
    // =========================================================   
    private static void demonstrateVirtualThreadScalability() {
        System.out.println("\n== MASSIVE SCALABILITY DEMO ==");

        long start = System.currentTimeMillis();
        /**
         * Virtual threads are lightweight
         *
         * - 1000 tasks are submitted
         * - Each task blocks for 1 second
         * - JVM efficiently schedules all virtual threads
         * - Total execution time should still be around ~1 second
         *
         * Doing this with platform threads would require:
         * - Huge thread pool
         * - More memory
         * - Higher context-switch overhead
         */
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                int taskId = i;
                executorService.submit(() -> {
                    try {
                        Thread.sleep(1000); // Simulate blocking work

                        // Print only a few tasks to avoid huge console output
                        if (taskId <= 3 || taskId > 997) {
                            System.out.println("Task-" + taskId + " completed");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }
        System.out.println("Total Time: " + (System.currentTimeMillis() - start) + " ms");
    }
    
	// =========================================================
    // CALLABLE + FUTURE (RETURN VALUE)
    // =========================================================   
    private static void executeCallableWithVirtualThreads() throws InterruptedException, ExecutionException {
        System.out.println("\n== CALLABLE + FUTURE (RETURN VALUE) ==");
        long start = System.currentTimeMillis();
        
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executorService.submit(() -> {
                System.out.println("Virtual Thread started on: " + Thread.currentThread());
                /**
                 * In a virtual thread:
                 *
                 * Thread.sleep(2000);
                 *  - Virtual thread goes to sleep
                 *  - JVM unmounts it from carrier thread
                 *  - Carrier thread is reused for other work
                 *  - Blocking is cheap and scalable
                 */
                Thread.sleep(2000); // Simulate blocking work
                System.out.println("Virtual Thread completed on: " + Thread.currentThread());
                return "Result from Virtual Thread";
            });

            /**
             * future.get():
             * - Always blocks until result is ready
             * - Blocking still exists logically, BUT system does NOT waste threads
             * - Virtual threads do NOT remove blocking
             * - They make blocking lightweight and scalable
             */
            System.out.println("Waiting for result...");
            System.out.println("Future Result: " + future.get());
        }

        System.out.println("Total Time: " + (System.currentTimeMillis() - start) + " ms");
    }

    // =========================================================
    // COMPLETABLE FUTURE + VIRTUAL THREADS
    // =========================================================
    /*
     * CompletableFuture:
     * - Supports async callback chaining
     * - More powerful than Future
     * - Can transform, combine, and compose results
     *
     * Virtual Threads:
     * - Make blocking cheap and scalable
     * - JVM unmounts virtual thread during blocking
     *
     * join():
     * - Used only at end to keep JVM alive
     * - Async work still happens in background
     *
     * Progression:
     * Future -> CompletableFuture -> Structured Concurrency
     */
    private static void executeCompletableFutureWithVirtualThreads() {
        System.out.println("\n== COMPLETABLE FUTURE + VIRTUAL THREADS ==");
        long start = System.currentTimeMillis();
        
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture<String> future =
                    CompletableFuture.supplyAsync(() -> {
                        try {
                            System.out.println("Task started on: " + Thread.currentThread());

                            /**
                             * Virtual thread blocks logically
                             * JVM unmounts it from carrier thread during sleep
                             * Carrier thread is reused for other work
                             */
                            Thread.sleep(2000);
                            System.out.println("Task completed on: " + Thread.currentThread());
                            return "Hello from Virtual Thread";
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }
                    }, executorService);	// executorService -> task runs on a virtual thread instead of platform thread

            CompletableFuture<Void> callbackFuture =
                    future.thenApply(msg -> msg + " Processed")
                          .thenAccept(result -> System.out.println("Result: " + result));

            System.out.println("-- Main thread continues immediately --");
            
            /**
             * Wait only at the end so JVM does not exit early
             * Async work still happens in background
             */
            // callbackFuture.join();
        }
        
        System.out.println("Total Time: " + (System.currentTimeMillis() - start) + " ms");
    }
    
	// ------------------------------------
    // Virtual Thread Pinning (BAD ❌)
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
         * 	  - 5 virtual threads try to acquire the same lock
         * 	  - Only one thread gets the lock at a time
         * 	  - That thread sleeps for 1 second while holding the lock
         * 	  - Other threads are blocked waiting for the lock
         *
         * ❌ Problem:
         * 	  - Blocking happens INSIDE the lock
         * 	  - Virtual thread cannot unmount from carrier thread
         *    - Carrier thread gets pinned (blocked)
         *    - Tasks run sequentially instead of in parallel
         *
         * Expected total time:
         *    ~5 seconds (5 tasks × 1 second each)
         *
         * 👉 Golden Rule:
         *    Never perform blocking work while holding a lock
         */

        System.out.println("\n== VIRTUAL THREAD PINNING ==");
        long start = System.currentTimeMillis();
        var lock = new ReentrantLock();
        
        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= 5; i++) {
                int taskId = i;
                executorService.submit(() -> {
                    lock.lock();
                    try {
                        System.out.println("Task-" + taskId + " acquired lock on " + Thread.currentThread());
                        Thread.sleep(1000); // ❌ Blocking inside lock → pinning
                        System.out.println("Task-" + taskId + " completed");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Task-" + taskId + " interrupted");
                    } finally {
                        lock.unlock();
                    }
                });
            }
        }
        System.out.println("Total Time: " + (System.currentTimeMillis() - start) + " ms");
    }
    
    // --------------------------------------------
    // Virtual Thread (No Pinning) (GOOD ✅)
    // --------------------------------------------
    /**
     * WITHOUT PINNING:
     * Virtual Thread → sleep → unmount → carrier free → scalable ✅
     */   
    static void demonstrateVirtualThreadWithoutPinning() throws Exception {

        /**
         * 👉 NO PINNING SCENARIO:
         * - 5 virtual threads run independently
         * - No shared lock is used
         * - Each task sleeps for 1 second
         *
         * ✅ Behavior:
         * - Virtual threads are blocked logically
         * - JVM unmounts them from carrier threads during sleep
         * - Carrier threads are reused for other work
         * - Tasks execute in parallel
         *
         * Expected total time:
         * ~1 second (instead of ~5 seconds)
         *
         * 👉 Key Insight:
         * Blocking is safe in virtual threads
         * Only blocking inside a lock causes pinning
         */

        System.out.println("\n== VIRTUAL THREAD WITHOUT PINNING ==");
        long start = System.currentTimeMillis();

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= 5; i++) {
                int taskId = i;
                executorService.submit(() -> {
                    try {
                        System.out.println("Task-" + taskId + " started on " + Thread.currentThread());
                        Thread.sleep(1000); // ✅ Safe blocking (no lock held)
                        System.out.println("Task-" + taskId + " completed");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Task-" + taskId + " interrupted");
                    }
                });
            }
        }
        System.out.println("Total Time: " + (System.currentTimeMillis() - start) + " ms");
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
    static void migrateFromCompletableFutureToStructuredConcurrency() throws Exception {
    	System.out.println("\n== Migration from CompletableFuture ==");
    	
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
== SIMPLE VIRTUAL THREAD ==

== THREAD BUILDER (OPTIONAL CONTROL) ==
Custom Named VT: VirtualThread[#23,my-virtual-thread]/runnable@ForkJoinPool-1-worker-2
Simple Virtual Thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1

== CHECK IF THREAD IS VIRTUAL ==

== EXECUTOR WITH VIRTUAL THREADS ==
Is Virtual? true
Task-3 started on VirtualThread[#30]/runnable@ForkJoinPool-1-worker-5
Task-4 started on VirtualThread[#31]/runnable@ForkJoinPool-1-worker-6
Task-0 started on VirtualThread[#27]/runnable@ForkJoinPool-1-worker-2
Task-2 started on VirtualThread[#29]/runnable@ForkJoinPool-1-worker-4
Task-1 started on VirtualThread[#28]/runnable@ForkJoinPool-1-worker-3
Task-3 completed on VirtualThread[#30]/runnable@ForkJoinPool-1-worker-4
Task-2 completed on VirtualThread[#29]/runnable@ForkJoinPool-1-worker-2
Task-0 completed on VirtualThread[#27]/runnable@ForkJoinPool-1-worker-6
Task-1 completed on VirtualThread[#28]/runnable@ForkJoinPool-1-worker-2
Task-4 completed on VirtualThread[#31]/runnable@ForkJoinPool-1-worker-5

== MASSIVE SCALABILITY DEMO ==
Task-0 completed
Task-2 completed
Task-1 completed
Task-3 completed
Task-999 completed
Task-998 completed
Total Time: 1014 ms

== CALLABLE + FUTURE (RETURN VALUE) ==
Waiting for result...
Virtual Thread started on: VirtualThread[#1042]/runnable@ForkJoinPool-1-worker-10
Virtual Thread completed on: VirtualThread[#1042]/runnable@ForkJoinPool-1-worker-10
Future Result: Result from Virtual Thread
Total Time: 2009 ms

== COMPLETABLE FUTURE + VIRTUAL THREADS ==
Task started on: VirtualThread[#1043]/runnable@ForkJoinPool-1-worker-10
-- Main thread continues immediately --
Task completed on: VirtualThread[#1043]/runnable@ForkJoinPool-1-worker-10
Result: Hello from Virtual Thread Processed
Total Time: 2008 ms

== VIRTUAL THREAD PINNING ==
Task-1 acquired lock on VirtualThread[#1044]/runnable@ForkJoinPool-1-worker-10
Task-1 completed
Task-2 acquired lock on VirtualThread[#1045]/runnable@ForkJoinPool-1-worker-12
Task-2 completed
Task-3 acquired lock on VirtualThread[#1046]/runnable@ForkJoinPool-1-worker-10
Task-3 completed
Task-4 acquired lock on VirtualThread[#1047]/runnable@ForkJoinPool-1-worker-5
Task-4 completed
Task-5 acquired lock on VirtualThread[#1048]/runnable@ForkJoinPool-1-worker-10
Task-5 completed
Total Time: 5033 ms

== VIRTUAL THREAD WITHOUT PINNING ==
Task-3 started on VirtualThread[#1051]/runnable@ForkJoinPool-1-worker-12
Task-5 started on VirtualThread[#1053]/runnable@ForkJoinPool-1-worker-3
Task-2 started on VirtualThread[#1050]/runnable@ForkJoinPool-1-worker-5
Task-1 started on VirtualThread[#1049]/runnable@ForkJoinPool-1-worker-10
Task-4 started on VirtualThread[#1052]/runnable@ForkJoinPool-1-worker-6
Task-5 completed
Task-3 completed
Task-2 completed
Task-1 completed
Task-4 completed
Total Time: 1005 ms

== Migration from CompletableFuture ==
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