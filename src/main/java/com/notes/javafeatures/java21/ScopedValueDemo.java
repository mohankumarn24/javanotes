package com.notes.javafeatures.java21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;

/*
 * INSIGHT:
 *
 * ThreadLocal Problem:
 * - Requires manual cleanup (remove())
 * - Risk of memory leaks in thread pools
 * - Hidden data flow (hard to trace/debug)
 *
 * ScopedValue Advantage:
 * - Immutable (no accidental modification)
 * - No manual cleanup
 * - Clear scope → safer and predictable
 * - Works naturally with virtual threads
 *
 * KEY DIFFERENCE:
 * ThreadLocal → mutable + thread-bound
 * ScopedValue → immutable + scope-bound
 */
public class ScopedValueDemo {

    // Old way
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    // New way (Java 21 preview)
    @SuppressWarnings("preview")
    private static final ScopedValue<String> SCOPED_VALUE = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        System.out.println("=== ThreadLocal (Leak) Problem ===");
        threadLocalLeakDemo();

        System.out.println("\n=== ScopedValue Correct ===");
        scopedValueBasicDemo();
        
        System.out.println("\n=== ScopedValue + Virtual Threads ===");
        threadLocalWithVirtualThreadsDemo();
        scopedValueWithStructuredConcurrencyDemo();
        
        // 👉 Final Summary:
        // ThreadLocal → thread-bound, manual, error-prone (breaks with virtual threads)
        // ScopedValue → scope-bound, immutable, safe (works with structured concurrency
    }

    // -------------------------------
    // 1. ThreadLocal Leak (Problem ❌)
    // -------------------------------
    private static void threadLocalLeakDemo() throws Exception {
    	// single thread used
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // First request
        executorService.submit(() -> {
	            THREAD_LOCAL.set("REQ-1");
	            printThreadLocalValue("First Call  : ");
	
	            // ❌ Forgot to remove → value stays in thread
	            // THREAD_LOCAL.remove();
	        }).get();

        // Second request (same thread reused)
        executorService.submit(() -> {
	            // ❌ Forgot to set new value → old value (REQ-1) is still present
	            // THREAD_LOCAL.set("REQ-2");
	
        		printThreadLocalValue("Second Call : "); // ❌ Bug (Leaked). Still prints REQ-1 due to thread reuse
	
	            // ❌ Forgot to remove again → continues leaking
	            // THREAD_LOCAL.remove();
	        }).get();

        executorService.shutdown();
        
    	/*
    	 * Always use try-finally:
    	 * 
    	 *      executorService.submit(() -> {
    	 *      		try {
    	 *      			THREAD_LOCAL.set("REQ-1");
    	 *      			print("First Call");
    	 *      		} finally {
    	 *      			THREAD_LOCAL.remove(); // ✅ mandatory cleanup
    	 *      		}
    	 *      	}).get();
    	 */
    }

    private static void printThreadLocalValue(String label) {
        System.out.println(label + THREAD_LOCAL.get());
    }
    
    // -------------------------------
    // 2. ScopedValue Basic (Safe ✅)
    // -------------------------------
    @SuppressWarnings("preview")
    private static void scopedValueBasicDemo() {

    	// Bind SCOPED_VALUE to value "REQ-1" for this execution scope
        ScopedValue.where(SCOPED_VALUE, "REQ-1").run(() -> {
            // ✅ Value is available only within this scope
        	printScopedValue("First Call  : ");
        });

        // Bind SCOPED_VALUE to value "REQ-2" for this execution scope
        ScopedValue.where(SCOPED_VALUE, "REQ-2").run(() -> {
            // ✅ New scope → completely independent value
        	printScopedValue("Second Call : ");
        });

        // ❌ No value bound here → accessing will throw exception
        printScopedValue("Outside     : ");
        // SCOPED_VALUE.get();	// It throws NoSuchElementException 
    }
    
    // ------------------------------------------------------
    // 3.a ThreadLocal with Virtual Threads (Limitation ❌)
    // ------------------------------------------------------
    /**
     * 👉 ThreadLocal + Virtual Threads Limitation:
     *
     * - ThreadLocal is thread-bound → NOT inherited by virtual threads
     * - Each virtual thread is independent → must set value manually
     * - Parent thread value is NOT visible in child virtual threads
     *
     * ❌ Problem:
     * - Easy to forget setting/removing → bugs or leaks
     *
     * 👉 Key Insight:
     * ThreadLocal does NOT work naturally with structured concurrency
     */ 
    @SuppressWarnings("preview")
    private static void threadLocalWithVirtualThreadsDemo() throws Exception {

        // ❌ Setting ThreadLocal in parent thread is NOT useful for virtual threads
        // Virtual threads created by StructuredTaskScope do NOT inherit ThreadLocal values
        // This line is intentionally kept here for demonstration purpose
        THREAD_LOCAL.set("REQ-123");												// Parent thread sets value

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            /**
             * In virtual threads, ThreadLocal must be set inside each task — parent value is ignored
             * 
             * Each fork() creates a NEW virtual thread
             * ThreadLocal values are NOT shared → must be set inside each task
             */
            scope.fork(() -> {
                // ✅ Each virtual thread must set its own value
                // If below line is removed → THREAD_LOCAL.get() will return null
                THREAD_LOCAL.set("REQ-111");

                try {
                    // Each thread prints its own isolated value
                	printThreadLocalValue("Task 1: ");
                    return null;
                } finally {
                    // ✅ VERY IMPORTANT: Prevent memory leaks (especially in long-running apps)
                    THREAD_LOCAL.remove();
                }
            });

            scope.fork(() -> {
                // ✅ Independent value for another virtual thread
                // If below line is removed → THREAD_LOCAL.get() will return null            	
                THREAD_LOCAL.set("REQ-222");

                try {
                	printThreadLocalValue("Task 2: ");
                    return null;
                } finally {
                    THREAD_LOCAL.remove();
                }
            });

            // Wait for all virtual threads to complete
            scope.join();

            // If any thread failed → exception is thrown here
            scope.throwIfFailed();
        }
    }
    
    // ------------------------------------------------------------------
    // 3.b ScopedValue with Structured Concurrency (Recommended ✅)
    // ------------------------------------------------------------------
    @SuppressWarnings("preview")
    private static void scopedValueWithStructuredConcurrencyDemo() throws Exception {
    	
    	/**
    	 * 1. Bind value → SCOPED_VALUE = "REQ-123"
    	 * 2. Create structured scope
    	 * 3. Start 2 virtual threads using fork()
    	 * 4. Both threads access same ScopedValue
    	 * 5. Wait for completion
    	 * 6. Scope ends → value disappears
    	 */
    	
        // 👉 ScopedValue automatically propagates across forked virtual threads
        // because they share the same structured scope
    	
    	// Bind SCOPED_VALUE = "REQ-123" for this execution block (scope)
        ScopedValue.where(SCOPED_VALUE, "REQ-123").run(() -> {
            /**
             * StructuredTaskScope manages virtual threads in a structured way
             * StructuredTaskScope ensures:
             *  - tasks run in virtual threads
             *  - proper lifecycle management
             */
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                // fork() → creates a new virtual thread and runs this task
                scope.fork(() -> {
                    // This runs in a virtual thread
                    // ScopedValue is automatically available here
                	printScopedValue("Task 1: ");
                    return null; // required (because fork expects Callable in my PC JDK version)
                });

                // Another virtual thread
                scope.fork(() -> {
                	// Runs concurrently with Task 1
                	printScopedValue("Task 2: ");
                    return null;
                });

                // Wait for all forked tasks to complete
                scope.join();

                // If any task fails → exception will be thrown here
                scope.throwIfFailed();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // After this point:
        // ❌ SCOPED_VALUE is NO LONGER available (scope ended)
    }
    
    // Prints the value from ScopedValue
    private static void printScopedValue(String label) {
    	// Check if a value is bound in the current execution scope
    	if (SCOPED_VALUE.isBound()) {
    		// ✅ Value is available → safely read it
    	    System.out.println(label + SCOPED_VALUE.get());
    	} else {
    		// ❌ No value bound → outside scope or propagation failed
    	    System.out.println(label + "No value bound");
    	}
    }
}

/*
=== ThreadLocal (Leak) Problem ===
First Call  : REQ-1
Second Call : REQ-1

=== ScopedValue Correct ===
First Call  : REQ-1
Second Call : REQ-2
Outside     : No value bound

=== ScopedValue + Virtual Threads ===
Task 1: REQ-111
Task 2: REQ-222
Task 1: REQ-123
Task 2: REQ-123
*/
