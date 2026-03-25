package com.notes.javafeatures.java21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * ============================================================
 * THREADLOCAL vs SCOPEDVALUE (Java 21 - Preview)
 * ============================================================
 *
 * Demonstrates:
 * 1. ThreadLocal problem with thread reuse
 * 2. ScopedValue safe alternative
 *
 * Compile:
 * javac --enable-preview --release 21 Demo.java
 * java --enable-preview Demo
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
    }

    // -------------------------------
    // ThreadLocal (problem)
    // -------------------------------
    private static void threadLocalDemo() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // First request
        executor.submit(() -> {
            THREAD_LOCAL.set("REQ-1");
            print("First Call");
            // ❌ forgot TL.remove()
        }).get();

        // Second request (same thread reused)
        executor.submit(() -> {
            print("Second Call"); // ❌ still sees REQ-1
        }).get();

        executor.shutdown();
    }

    // -------------------------------
    // ScopedValue (safe)
    // -------------------------------
    @SuppressWarnings("preview")
    private static void scopedValueDemo() {
        ScopedValue.where(SCOPED_VALUE, "REQ-1").run(() -> {
        	// value exists only inside scope
            print("First Call");
        });

        ScopedValue.where(SCOPED_VALUE, "REQ-2").run(() -> {
        	// value exists only inside scope
            print("Second Call");
        });

        print("Outside");
    }

    // -------------------------------
    // Print
    // -------------------------------
    private static void print(String msg) {
        String threadLocal = THREAD_LOCAL.get();
        String scopedValue = SCOPED_VALUE.isBound() ? SCOPED_VALUE.get() : "NOT SET";
        System.out.println(msg + " | ThreadLocal=" + threadLocal + " | ScopedValue=" + scopedValue);
    }
}

/*
ThreadLocal Output:
=== ThreadLocal Problem ===
First Call  | ThreadLocal=REQ-1 | ScopedValue=NOT SET
Second Call | ThreadLocal=REQ-1 | ScopedValue=NOT SET		<-- BUG (leaked) ie ThreadLocal=REQ-1

👉 Because:
Same thread reused from pool
You forgot remove()


ScopedValue Output:
=== ScopedValue Correct ===
First Call  | ThreadLocal=null | ScopedValue=REQ-1
Second Call | ThreadLocal=null | ScopedValue=REQ-2
Outside     | ThreadLocal=null | ScopedValue=NOT SET

👉 Because:
Value exists only inside scope
Automatically cleaned
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
