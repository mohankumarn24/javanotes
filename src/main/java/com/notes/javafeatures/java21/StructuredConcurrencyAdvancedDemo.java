package com.notes.javafeatures.java21;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;

/*
 * ============================================================
 * PROGRAM PURPOSE
 * ============================================================
 *
 * This program simulates a real-world backend API (e.g. /dashboard)
 * that aggregates data from multiple services in parallel using
 * Structured Concurrency (Java 21).
 *
 * It demonstrates:
 *
 * 1. Parallel execution of independent tasks
 *    → User, Orders, Recommendations services run concurrently
 *
 * 2. Timeout handling
 *    → Wait only up to a fixed deadline (3 seconds)
 *
 * 3. Partial response (graceful degradation)
 *    → If some services fail/timeout, return fallback instead of failing entire request
 *
 * 4. Automatic cancellation
 *    → Slow/unnecessary tasks are cancelled after timeout
 *
 * 5. Retry mechanism
 *    → Failed services are retried before giving up
 *
 * 6. ScopedValue (replacement for ThreadLocal)
 *    → Used for request-scoped context (e.g. request ID)
 *
 * 7. Context-aware logging
 *    → Each log includes request ID for traceability
 *
 *
 * ============================================================
 * FLOW
 * ============================================================
 *
 * Incoming Request (/dashboard)
 *        ↓
 * Bind request context (ScopedValue)
 *        ↓
 * Start StructuredTaskScope
 *        ↓
 * Run tasks in parallel:
 *    - fetchUser()
 *    - fetchOrders() (with retry)
 *    - fetchRecommendations()
 *        ↓
 * Wait until deadline (3 seconds)
 *        ↓
 * Cancel remaining tasks (if any)
 *        ↓
 * Build partial response using safeGet()
 *        ↓
 * Return response
 *
 *
 * ============================================================
 * KEY CONCEPTS
 * ============================================================
 *
 * Structured Concurrency:
 * → Groups multiple tasks as a single unit (scope)
 * → Ensures proper lifecycle management (start, wait, cancel)
 *
 * Timeout:
 * → Prevents slow services from delaying entire request
 *
 * Partial Response:
 * → Improves user experience by returning available data
 *
 * ScopedValue:
 * → Immutable, scope-bound context (better than ThreadLocal)
 *
 *
 * ============================================================
 * REAL-WORLD USE CASES
 * ============================================================
 *
 * - Dashboard APIs
 * - Aggregator services
 * - Microservice orchestration
 * - API gateways
 *
 * ============================================================
 */

@SuppressWarnings("preview")
public class StructuredConcurrencyAdvancedDemo {

    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

    public static void main(String[] args) {

        // ✅ ScopedValue → safe request context (replaces ThreadLocal)
        ScopedValue.where(REQUEST_ID, "REQ-123").run(() -> {
            try {
                handleRequest();
            } catch (Exception e) {
                System.out.println("Final Error: " + e);
            }
        });
    }

    // -------------------------------
    // Simulates REST API: /dashboard
    // -------------------------------
    private static void handleRequest() throws Exception {
        long start = System.currentTimeMillis();

        try (var scope = new StructuredTaskScope<String>()) {

            var user = scope.fork(() -> fetchUser());
            var orders = scope.fork(() -> retry(() -> fetchOrders(), 2));
            var recs = scope.fork(() -> fetchRecommendations());

            Instant deadline = Instant.now().plusSeconds(3);

            try {
                scope.joinUntil(deadline); // ⏱️ wait until deadline
            } catch (TimeoutException e) {
                System.out.println("⚠️ Timeout reached");
            }

            // ✅ IMPORTANT: shutdown AFTER joinUntil
            scope.shutdown();

            System.out.println("\n=== PARTIAL RESPONSE ===");
            System.out.println("User  : " + safeGet(user));
            System.out.println("Orders: " + safeGet(orders));
            System.out.println("Recs  : " + safeGet(recs));
        }

        long end = System.currentTimeMillis();
        System.out.println("\nTotal Time: " + (end - start) + " ms");
    }

    private static String fetchUser() {
        try {
            log("Fetching User...");
            Thread.sleep(2000);
            return "User: Mohan";
        } catch (InterruptedException e) {
            log("User interrupted ✅");
            throw new RuntimeException("User cancelled");
        }
    }

    private static String fetchOrders() {
        try {
            log("Fetching Orders...");
            Thread.sleep(4000); // ❌ slow → will be cancelled
            return "Orders: [1,2,3]";
        } catch (InterruptedException e) {
            log("Orders interrupted ✅");
            Thread.currentThread().interrupt();   // ✅ restore interrupt flag
            throw new RuntimeException(e);        // OR rethrow InterruptedException
        }
    }

    private static String fetchRecommendations() {
        try {
            log("Fetching Recommendations...");
            Thread.sleep(1000);
            return "Recommendations: [A,B,C]";
        } catch (InterruptedException e) {
            log("Recs interrupted ✅");
            throw new RuntimeException("Recs cancelled");
        }
    }

    // -------------------
    // Retry Logic (basic)
    // -------------------
    private static <T> T retry(Callable<T> task, int attempts) throws Exception {
        for (int i = 1; i <= attempts; i++) {
            // ✅ check BEFORE retry
            if (Thread.currentThread().isInterrupted()) {
                log("Task cancelled before retry ❌");
                throw new InterruptedException("Cancelled");
            }

            try {
                return task.call();

            } catch (InterruptedException e) {
                log("Retry interrupted ❌");
                throw e; // ✅ STOP

            } catch (Exception e) {

                // ✅ detect wrapped interruption
                if (Thread.currentThread().isInterrupted()) {
                    log("Detected interrupt after failure ❌");
                    throw new InterruptedException("Cancelled");
                }

                log("Retry " + i + " failed");

                if (i == attempts) throw e;
            }
        }
        throw new RuntimeException("Retry failed");
    }

    // --------------------
    // Safe Result Handling
    // --------------------
    private static <T> String safeGet(StructuredTaskScope.Subtask<T> task) {
        try {
            return String.valueOf(task.get()); // ✅ single source of truth
        } catch (Exception e) {
            return switch (task.state()) {
            case UNAVAILABLE 	-> "Timeout/Cancelled";
            case FAILED 		-> "Failed";
            default 			-> "Fallback";
            };
        }
    }

    // -------------------------
    // Logger (uses ScopedValue)
    // -------------------------
    private static void log(String msg) {
        System.out.println("[" + REQUEST_ID.get() + "] " + msg);
    }
}

/*
[REQ-123] Fetching Orders...
[REQ-123] Fetching Recommendations...
[REQ-123] Fetching User...
⚠️ Timeout reached

=== PARTIAL RESPONSE ===
[REQ-123] Orders interrupted ✅
[REQ-123] Detected interrupt after failure ❌
User  : Fallback
Orders: Timeout/Cancelled
Recs  : Fallback

Total Time: 3026 ms
*/