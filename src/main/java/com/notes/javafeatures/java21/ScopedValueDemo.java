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
        System.out.println("=== ThreadLocal Problem ===");
        threadLocalDemo();

        System.out.println("\n=== ScopedValue Correct ===");
        scopedValueDemo();
        
        System.out.println("\n=== ScopedValue + Virtual Threads ===");
        threadLocalEquivalentDemo();
        virtualThreadScopedValueDemo();
    }

    // -------------------------------
    // ThreadLocal (problem)
    // -------------------------------
    private static void threadLocalDemo() throws Exception {
    	// single thread used
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // First request
        executorService.submit(() -> {
	            THREAD_LOCAL.set("REQ-1");
	            printThreadLocal("First Call  : ");
	
	            // ❌ Forgot to remove → value stays in thread
	            // THREAD_LOCAL.remove();
	        }).get();

        // Second request (same thread reused)
        executorService.submit(() -> {
	            // ❌ Forgot to set new value → old value (REQ-1) is still present
	            // THREAD_LOCAL.set("REQ-2");
	
        		printThreadLocal("Second Call : "); // ❌ Bug (Leaked). Still prints REQ-1 due to thread reuse
	
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

    private static void printThreadLocal(String label) {
        System.out.println(label + THREAD_LOCAL.get());
    }
    
    // -------------------------------
    // ScopedValue (safe)
    // -------------------------------
    @SuppressWarnings("preview")
    private static void scopedValueDemo() {

    	// Bind SCOPED_VALUE to value "REQ-1" for this execution scope
        ScopedValue.where(SCOPED_VALUE, "REQ-1").run(() -> {
            // ✅ Value is available only within this scope
            print("First Call  : ");
        });

        // Bind SCOPED_VALUE to value "REQ-2" for this execution scope
        ScopedValue.where(SCOPED_VALUE, "REQ-2").run(() -> {
            // ✅ New scope → completely independent value
            print("Second Call : ");
        });

        // ❌ No value bound here → accessing will throw exception
        print("Outside     : ");
        // SCOPED_VALUE.get();	// It throws NoSuchElementException 
    }

    // Prints the value from ScopedValue
    private static void print(String label) {
    	// Check if a value is bound in the current execution scope
    	if (SCOPED_VALUE.isBound()) {
    		// ✅ Value is available → safely read it
    	    System.out.println(label + SCOPED_VALUE.get());
    	} else {
    		// ❌ No value bound → outside scope or propagation failed
    	    System.out.println(label + "No value bound");
    	}
    }
    
    // -------------------------------
    // ScopedValue with Virtual Threads
    // -------------------------------
    /**
     * ScopedValue is designed for structured concurrency. Virtual threads support it naturally, but using ExecutorService can break the propagation.
     * Virtual Thread Call: No value bound
     * 
     * // ❌ Fails
     * // ScopedValue + ExecutorService       = ⚠️ unreliable
     * executor.submit(() -> print("Executor Call: ")).get();
     * 
     * // ✅ Works
     * // ScopedValue + Structured concurrency = ✅ 
     * Thread.startVirtualThread(() -> print("Direct VT Call: ")).join();
     * 
     */  
    @SuppressWarnings("preview")
    private static void threadLocalEquivalentDemo() throws Exception {

        // ❌ Setting ThreadLocal in parent thread is NOT useful for virtual threads
        // Virtual threads created by StructuredTaskScope do NOT inherit ThreadLocal values
        // This line is intentionally kept here for demonstration purpose11
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
                    printThreadLocal("Task 1: ");
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
                    printThreadLocal("Task 2: ");
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
    
    @SuppressWarnings("preview")
    private static void virtualThreadScopedValueDemo() throws Exception {
    	
    	/**
    	 * 1. Bind value → SCOPED_VALUE = "REQ-123"
    	 * 2. Create structured scope
    	 * 3. Start 2 virtual threads using fork()
    	 * 4. Both threads access same ScopedValue
    	 * 5. Wait for completion
    	 * 6. Scope ends → value disappears
    	 */
    	
    	// Bind SCOPED_VALUE = "REQ-123" for this execution block (scope)
        ScopedValue.where(SCOPED_VALUE, "REQ-123").run(() -> {
            /**
             * StructuredTaskScope manages virtual threads in a structured way
             * StructuredTaskScope ensures:
             *  - StructuredTaskScope ensures:
             *  - tasks run in virtual threads
             *  - proper lifecycle management
             */
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                // fork() → creates a new virtual thread and runs this task
                scope.fork(() -> {
                    // This runs in a virtual thread
                    // ScopedValue is automatically available here
                	print("Task 1: ");
                    return null; // required (because fork expects Callable in my PC JDK version)
                });

                // Another virtual thread
                scope.fork(() -> {
                	// Runs concurrently with Task 1
                	print("Task 2: ");
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
}

/*
=== ThreadLocal Problem ===
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



/*
public class ScopedValueTraceExample {

    // ScopedValue for traceId
    @SuppressWarnings("preview")
	private static final ScopedValue<String> TRACE_ID = ScopedValue.newInstance();

    @SuppressWarnings("preview")
	public static void main(String[] args) {

    	// For this block of code, TRACE_ID = REQ-1
        // Request 1
        ScopedValue.where(TRACE_ID, "REQ-1").run(() -> {
            controller();
        });

        // For this block of code, TRACE_ID = REQ-2
        // Request 2
        ScopedValue.where(TRACE_ID, "REQ-2").run(() -> {
            controller();
        });
    }

    static void controller() {
        service();
    }

    static void service() {
        repository();
    }

    static void repository() {
        // ✅ Always correct traceId within scope
        System.out.println("TraceId: " + TRACE_ID.get());
    }
}

// TraceId: REQ-1
// TraceId: REQ-2
*/

/*
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTraceExample {

    // Used to store traceId for logging across layers
    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        // Simulating Request 1
        executor.submit(() -> {
            TRACE_ID.set("REQ-1");

            controller();

            // ❌ CONS:
            // If we forget this → next request may see REQ-1
            // TRACE_ID.remove();
        });

        sleep(500);

        // Simulating Request 2
        executor.submit(() -> {

            // ❌ CONS:
            // No TRACE_ID set here
            // But thread may still have REQ-1 (thread reuse)
            controller();  // 🔥 Wrong traceId possible
        });

        executor.shutdown();
    }

    static void controller() {
        service();
    }

    static void service() {
        repository();
    }

    static void repository() {
        // ❌ Hidden dependency on ThreadLocal
        System.out.println("TraceId: " + TRACE_ID.get());
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }
}
*/
