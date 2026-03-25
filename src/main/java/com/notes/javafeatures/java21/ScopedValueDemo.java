package com.notes.javafeatures.java21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        ScopedValue.where(SCOPED_VALUE, "REQ-1").run(() -> {
            // ✅ Value is available only within this scope
            print("First Call  : ");
        });

        ScopedValue.where(SCOPED_VALUE, "REQ-2").run(() -> {
            // ✅ New scope → completely independent value
            print("Second Call : ");
        });

        // ❌ No value bound here → accessing will throw exception
        print("Outside     : ");
        // SCOPED_VALUE.get();	// It throws NoSuchElementException 
    }

    private static void print(String label) {
        try {
            System.out.println(label + SCOPED_VALUE.get());
        } catch (Exception e) {
            System.out.println(label + " No value bound");
        }
    }
    
    // -------------------------------
    // ScopedValue with Virtual Threads
    // -------------------------------
    @SuppressWarnings("preview")
    private static void virtualThreadScopedValueDemo() throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            ScopedValue.where(SCOPED_VALUE, "REQ-VT").run(() -> {
                try {
                    executor.submit(() -> {
                        // ✅ ScopedValue propagates correctly
                        print("Virtual Thread Call: ");
                    }).get(); // 👈 ensures execution completes before exiting
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}

/*
=== ThreadLocal Problem ===
First Call  : REQ-1
Second Call : REQ-1

=== ScopedValue Correct ===
First Call  : REQ-1
Second Call : REQ-2
Outside     :  No value bound

=== ScopedValue + Virtual Threads ===
Virtual Thread Call:  No value bound
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
