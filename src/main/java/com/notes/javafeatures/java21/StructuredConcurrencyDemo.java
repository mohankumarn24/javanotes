package com.notes.javafeatures.java21;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.*;

public class StructuredConcurrencyDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Old Way (Unstructured) ===");
        oldWay();

        System.out.println("\n=== Structured Concurrency (Correct) ===");
        shutdownOnFailureDemo();
        
        System.out.println("\n=== ShutdownOnSuccess ===");
        shutdownOnSuccessDemo();
        
        // 👉 Summary:
        // Unstructured → tasks run independently (no coordination)
        // Structured 	→ tasks are scoped (start, fail, cancel together)
    }

    // ----------------------------
    // 1. Old Way (No Coordination)
    // ----------------------------
    private static void oldWay() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        Future<String> userFuture = executorService.submit(() -> fetchUser());             		// long task (~5s)
        Future<String> orderFuture = executorService.submit(() -> fetchOrderWithFailure());    	// fails (~500ms)

        try {
            String user = userFuture.get();      												// ❗ blocks ~5s even though other already failed. Also, no coordination → order failure is ignored until later
            String order = orderFuture.get();   												// ❌ exception already happened

            System.out.println("Result: " + user + " | " + order);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());

            // ❌ Problem:
            // - Failure happens early (~500ms)
            // - But we still wait ~5s for userFuture.get()
            // - No automatic cancellation → wasted time/resources
        } finally {
            executorService.shutdown();
        }
    }

    // -----------------------------------------------------
    // 2.a. Structured Concurrency - Fail Fast (All succeed)
    // -----------------------------------------------------
    /**
     * A StructuredTaskScope that shuts down when any subtask fails.
     *
     * Behavior:
     * - Forks multiple concurrent subtasks.
     * - If any subtask throws an exception:
     *   - The scope is shut down immediately.
     *   - All remaining running tasks are cancelled (interrupted).
     * - After joining, use throwIfFailed() to propagate the failure.
     *
     * Use Case:
     * - When all tasks must succeed to produce a valid result.
     * - Suitable for aggregating results where partial completion is not acceptable.
     *
     * Example:
     * - Fetching data from multiple services where every response is required.
     *
     * Key Idea:
     * - "Fail fast on first error."
     */
    // Tasks are scoped → they succeed, fail, and cancel together (unlike Future)
    @SuppressWarnings("preview")
    private static void shutdownOnFailureDemo() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var user = scope.fork(() -> fetchUser());
            var order = scope.fork(() -> fetchOrderWithFailure());

            scope.join();              // waits until all complete OR stops early on failure (fail-fast)
            scope.throwIfFailed();     // throws if any task failed (central failure check)
            						   // fail-fast: propagates first failure immediately

            System.out.println("Result: " + user.get() + " | " + order.get());
        } catch (Exception e) {
            System.out.println("Error handled cleanly: " + e.getMessage());
            // ✅ One fails → others cancelled immediately
        }
    }

    private static String fetchUser() {
        try {
            Thread.sleep(5000); // long task
            System.out.println("Fetched User");
            return "User-123";
        } catch (InterruptedException e) {
            System.out.println("User task interrupted ✅");
            throw new RuntimeException("User task cancelled");
        }
    }

    private static String fetchOrderWithFailure() {
        try {
            Thread.sleep(500); // fail FAST
            System.out.println("Fetching Order...");
            throw new RuntimeException("Order service failed ❌");
        } catch (InterruptedException e) {
            System.out.println("Order task interrupted ✅");
            throw new RuntimeException("Order task cancelled");
        }
    }
    
    // ------------------------------------------------
    // 2.b. Structured Concurrency - First Success Wins
    // ------------------------------------------------
    /**
     * StructuredTaskScope.ShutdownOnSuccess<T> -> A StructuredTaskScope that shuts down when the first subtask succeeds.
     *
     * Behavior:
     * - Forks multiple concurrent subtasks.
     * - As soon as one subtask completes successfully:
     *   - The scope is shut down.
     *   - All other running tasks are cancelled (interrupted).
     * - The successful result can be retrieved using result().
     *
     * Use Case:
     * - When only one successful result is needed.
     * - Suitable for racing multiple alternatives to get the fastest response.
     *
     * Example:
     * - Querying multiple redundant services and using the first valid response.
     *
     * Key Idea:
     * - "Return the first successful result and stop the rest."
     */     
	 static void shutdownOnSuccessDemo() throws Exception {
	     try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
	         scope.fork(() -> slowService());
	         scope.fork(() -> fastService()); 							// should win
	
	         scope.join();												// wait until one succeeds			// ⛔ returns when first success happens
	
	         String result = scope.result(); 							// first successful result			// 🏆 winner
	         System.out.println("Winner result: " + result);
	     } catch (Exception e) {
	         System.out.println("Error handled cleanly: " + e.getMessage());
	     }
	 }
	
	 static String slowService() throws InterruptedException {
	     Thread.sleep(3000);
	     return "Slow result";
	 }
	
	 static String fastService() throws InterruptedException {
	     Thread.sleep(500);
	     return "Fast result";
	 }
}

/*
=== Old Way (Unstructured) ===
Fetching Order...
Fetched User
Error occurred: java.lang.RuntimeException: Order service failed ❌

=== Structured Concurrency (Correct) ===
Fetching Order...
User task interrupted ✅
Error handled cleanly: java.lang.RuntimeException: Order service failed ❌

=== ShutdownOnSuccess ===
Winner result: Fast result
*/