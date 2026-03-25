package com.notes.javafeatures.java21;

import java.lang.ScopedValue;

public class ScopedValueTraceExample {

    // ScopedValue for traceId
    @SuppressWarnings("preview")
	private static final ScopedValue<String> TRACE_ID = ScopedValue.newInstance();

    @SuppressWarnings("preview")
	public static void main(String[] args) {

        // Request 1
        ScopedValue.where(TRACE_ID, "REQ-1").run(() -> {
            controller();
        });

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

/*
TraceId: REQ-1
TraceId: REQ-2
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
