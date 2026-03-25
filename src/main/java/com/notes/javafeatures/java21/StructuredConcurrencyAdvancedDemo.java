package com.notes.javafeatures.java21;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;

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

    // -------------------------------
    // Simulated Services
    // -------------------------------

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
            throw new RuntimeException("Orders cancelled");
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

    // -------------------------------
    // Retry Logic (basic)
    // -------------------------------
    private static <T> T retry(Callable<T> task, int attempts) throws Exception {
        for (int i = 1; i <= attempts; i++) {
            try {
                return task.call();
            } catch (Exception e) {
                log("Retry " + i + " failed");
                if (i == attempts) throw e;
            }
        }
        throw new RuntimeException("Retry failed");
    }

    // -------------------------------
    // Safe Result Handling
    // -------------------------------
    private static <T> String safeGet(StructuredTaskScope.Subtask<T> task) {
        try {
            return String.valueOf(task.get());
        } catch (Exception e) {
            return "Fallback/default"; // ✅ partial response support
        }
    }

    // -------------------------------
    // Logger (uses ScopedValue)
    // -------------------------------
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
User  : Fallback/default
Orders: Fallback/default
Recs  : Fallback/default
[REQ-123] Retry 1 failed
[REQ-123] Fetching Orders...

Total Time: 7039 ms
*/