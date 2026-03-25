package com.notes.javafeatures.java21;

import java.util.concurrent.StructuredTaskScope;

public class StructuredConcurrencyExample {
	
    @SuppressWarnings("preview")
	public static void main(String[] args) throws Exception {

        // PROBLEM THIS SOLVES:
        // ----------------------------------------
        // 1. Sequential calls are slow (additive latency)
        //    fetchUser()  -> 1 sec
        //    fetchOrders() -> 1 sec
        //    Total = 2 sec ❌
        //
        // 2. Managing parallel threads manually is hard:
        //    - Thread creation
        //    - Error handling
        //    - Cancelling other tasks if one fails
        //    - Avoiding thread leaks
        //
        // 3. CompletableFuture becomes complex:
        //    - Hard to read
        //    - Hard to debug
        //    - Error propagation is messy
        //
        // ✅ Structured Concurrency solves all of the above:
        //    - Runs tasks in parallel
        //    - Automatically cancels others on failure
        //    - Groups tasks under one lifecycle (scope)
        //    - Cleaner, readable, maintainable code

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Start task 1 in a virtual thread
            var userFuture = scope.fork(() -> fetchUser());

            // Start task 2 in another virtual thread
            var orderFuture = scope.fork(() -> fetchOrders());

            // Wait for ALL tasks to complete
            scope.join();

            // If ANY task failed:
            // - Throws exception
            // - Cancels remaining tasks
            // - Prevents partial results
            scope.throwIfFailed();

            // Safe to read results now
            String result = userFuture.get() + " | " + orderFuture.get();

            System.out.println(result);		// User Data | Order Data
        }

        // When scope closes:
        // - All threads are cleaned up
        // - No thread leaks
    }

    static String fetchUser() throws InterruptedException {
        // Simulating slow I/O operation (DB/API call)
        // With virtual threads, this does NOT block OS thread
        Thread.sleep(1000);
        return "User Data";
    }

    static String fetchOrders() throws InterruptedException {
        // Another independent I/O call
        Thread.sleep(1000);
        return "Order Data";
    }
}

/*
User Data | Order Data
*/

// Why CompletableFuture is not recommended

/*
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureBadExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // PROBLEM STATEMENT:
        // ----------------------------------------
        // We need to call 3 independent services:
        // 1. User Service
        // 2. Order Service
        // 3. Payment Service
        //
        // Goal:
        // - Run them in parallel (performance)
        // - Combine results
        // - Handle failures
        //
        // CompletableFuture CAN do this... but becomes messy as complexity grows.

        CompletableFuture<String> userFuture =
                CompletableFuture.supplyAsync(() -> fetchUser())
                        // ❌ Problem 1: Error handling is local and scattered
                        .exceptionally(ex -> {
                            System.out.println("User service failed: " + ex.getMessage());
                            return "DEFAULT_USER";
                        });

        CompletableFuture<String> orderFuture =
                CompletableFuture.supplyAsync(() -> fetchOrders())
                        // ❌ Each future needs its own error handling
                        .exceptionally(ex -> {
                            System.out.println("Order service failed: " + ex.getMessage());
                            return "DEFAULT_ORDERS";
                        });

        CompletableFuture<String> paymentFuture =
                CompletableFuture.supplyAsync(() -> fetchPayments())
                        .exceptionally(ex -> {
                            System.out.println("Payment service failed: " + ex.getMessage());
                            return "DEFAULT_PAYMENTS";
                        });

        // ❌ Problem 2: Combining multiple futures becomes hard to read
        CompletableFuture<String> combined =
                userFuture.thenCombine(orderFuture, (user, orders) -> user + " | " + orders)
                          .thenCombine(paymentFuture, (prev, payments) -> prev + " | " + payments);

        // ❌ Problem 3: Blocking call (defeats async benefits partially)
        String result = combined.join();

        System.out.println("Final Result: " + result);


        // ❌ Problem 4: No automatic cancellation
        // If paymentFuture fails early:
        // - userFuture and orderFuture KEEP RUNNING
        // - Wasted CPU / DB calls
        // - No built-in way to stop all tasks together

        // ❌ Problem 5: No lifecycle control
        // These tasks are NOT tied to a request scope
        // - They may continue even if request is cancelled
        // - Hard to manage in real systems

        // ❌ Problem 6: Debugging is painful
        // Stack traces are unclear due to async boundaries

        // ❌ Problem 7: Thread pool dependency (hidden)
        // supplyAsync() uses ForkJoinPool.commonPool()
        // - Shared across app
        // - Can cause contention under load

    }

    static String fetchUser() {
        sleep(1000);
        return "User Data";
    }

    static String fetchOrders() {
        sleep(1000);
        return "Order Data";
    }

    static String fetchPayments() {
        sleep(1000);

        // Simulate failure sometimes
        if (true) {
            throw new RuntimeException("Payment service down");
        }

        return "Payment Data";
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
*/