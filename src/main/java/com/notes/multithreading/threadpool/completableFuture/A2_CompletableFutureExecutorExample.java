package com.notes.multithreading.threadpool.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class A2_CompletableFutureExecutorExample {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2); // user threads

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return "Hello from CompletableFuture!";
        }, executorService);

        CompletableFuture<Void> callbackFuture = future
        		.thenApply(msg -> msg + " Processed")
        		.thenAccept(result -> System.out.println(result));

        System.out.println("-- Main thread (start) --");

        /**
         * Option 1: No join()
         * - JVM will NOT exit immediately
         * - Because executor uses user (non-daemon) threads
         * - Async task WILL complete
         */
        // callbackFuture.join(); // optional now

        /**
         * IMPORTANT:
         * Must shutdown executor, otherwise JVM may not exit
         */
        executorService.shutdown();

        System.out.println("-- Main thread (end) --");
    }
}

/*
-- Main thread (start) --
-- Main thread (end) --
Hello from CompletableFuture! Processed
 */

/*
 * 1. main thread starts
 * 2. task submitted to ExecutorService (user thread)
 * 3. callback chain attached
 * 4. main prints: start
 * 5. main prints: end
 * 6. main thread TERMINATES
 * 7. JVM checks:
 *    - Any user threads alive? ✅ YES (executor threads)
 * 8. JVM stays alive
 * 9. async task completes after ~2 sec
 * 10. thenApply + thenAccept execute
 * 11. result gets printed
 * 12. executor.shutdown() allows JVM to exit cleanly
*/