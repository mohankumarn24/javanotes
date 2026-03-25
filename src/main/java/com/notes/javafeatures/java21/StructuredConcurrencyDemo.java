package com.notes.javafeatures.java21;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.*;

public class StructuredConcurrencyDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Old Way (Unstructured) ===");
        oldWay();

        System.out.println("\n=== Structured Concurrency (Correct) ===");
        structuredWay();
        
        System.out.println("\n=== ShutdownOnSuccess ===");
        shutdownOnSuccessDemo();
    }

    // -------------------------------
    // ❌ Old Way (Problem)
    // -------------------------------
    private static void oldWay() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<String> userFuture = executor.submit(() -> fetchUser());             	// long task (~5s)
        Future<String> orderFuture = executor.submit(() -> fetchOrderWithFailure());    // fails fast (~500ms)

        try {
            String user = userFuture.get();      										// blocks ~5s ❗
            String order = orderFuture.get();   										// ❌ exception already happened

            System.out.println("Result: " + user + " | " + order);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());

            // ❌ Problem:
            // - Failure happens early (~500ms)
            // - But we still wait ~5s for userFuture.get()
            // - No automatic cancellation → wasted time/resources
        }

        executor.shutdown();
    }

    // -------------------------------
    // ✅ Structured Concurrency
    // -------------------------------
    private static void structuredWay() throws Exception {
        try (@SuppressWarnings("preview")
        var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var user = scope.fork(() -> fetchUser());
            var order = scope.fork(() -> fetchOrderWithFailure());

            scope.join();           													// wait (supposed to wait for ~5s, but fails)
            scope.throwIfFailed();  													// fail fast

            System.out.println("Result: " + user.get() + " | " + order.get());
        } catch (Exception e) {
            System.out.println("Error handled cleanly: " + e.getMessage());

            // ✅ One fails → others cancelled immediately
        }
    }

    // -------------------------------
    // Simulated APIs
    // -------------------------------
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
    
	 // --------------------------------------------
	 // INTERVIEW: ShutdownOnSuccess example
	 // --------------------------------------------
	 @SuppressWarnings("preview")
	 static void shutdownOnSuccessDemo() throws Exception {
	     try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
	
	         scope.fork(() -> slowService());
	         scope.fork(() -> fastService()); // should win
	
	         scope.join();
	
	         String result = scope.result(); // first successful result
	         System.out.println("Winner result: " + result);
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