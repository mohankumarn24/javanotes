package com.notes.javafeatures.java21;

import java.util.*;
import java.util.concurrent.*;

/**
 * ========================
 * JAVA 21 - FINAL + PREVIEW
 * ========================
 *
 * KEY MESSAGE:
 * Java 21 is NOT about syntax.
 * It introduces a CONCURRENCY SHIFT:
 *
 * Virtual Threads + Structured Concurrency
 * replace thread pools & CompletableFuture complexity
 *
 * INTERVIEW INSIGHT:
 * Moves from async (CompletableFuture)
 * → structured concurrency (clean, safe, readable)
 *
 * Compile:
 * javac --enable-preview --release 21 Java21Features.java
 * java --enable-preview Java21Features
 */
public class Java21Features {

    public static void main(String[] args) throws Exception {

        patternMatchingDemo();
        recordPatternDemo();

        virtualThreadsDemo();
        structuredConcurrencyDemo();

        sequencedCollectionsDemo();

        threadLocalVsScopedValueDemo();

        stringTemplatesDemo();
        unnamedPatternDemo();
    }

    // ============================================================
    // 1. PATTERN MATCHING (Java 17 vs 21)
    // ============================================================
    static void patternMatchingDemo() {
        System.out.println("\n=== PATTERN MATCHING ===");

        Object obj = "Java 21";

        // Java 17
        if (obj instanceof String s) {
            System.out.println("Java17: Length = " + s.length());
        }

        // Java 21
        String result = switch (obj) {
            case String s  -> "Java21: Length = " + s.length();
            case Integer i -> "Integer = " + i;
            case null      -> "Null";
            default        -> "Unknown";
        };

        System.out.println(result);
    }

    // ============================================================
    // 2. RECORD PATTERNS + SEALED CLASSES
    // ============================================================
    static void recordPatternDemo() {
        System.out.println("\n=== RECORD PATTERNS ===");

        Shape shape = new Circle(4);

        // Java 17
        double area17;
        if (shape instanceof Circle c) {
            area17 = Math.PI * c.radius() * c.radius();
        } else if (shape instanceof Rectangle r) {
            area17 = r.width() * r.height();
        } else {
            area17 = 0;
        }

        // Java 21
        double area21 = switch (shape) {
            case Circle(double r) -> Math.PI * r * r;
            case Rectangle(double w, double h) -> w * h;
        };

        System.out.println("Java17 Area = " + area17);
        System.out.println("Java21 Area = " + area21);
    }

    sealed interface Shape permits Circle, Rectangle {}
    record Circle(double radius) implements Shape {}
    record Rectangle(double width, double height) implements Shape {}

    // ============================================================
    // 3. VIRTUAL THREADS (FINAL)
    // ============================================================
    static void virtualThreadsDemo() throws Exception {
        System.out.println("\n=== VIRTUAL THREADS ===");

        // Java 17 - Thread Pool required
        ExecutorService platformExecutor = Executors.newFixedThreadPool(2);
        platformExecutor.submit(() -> task("Platform Thread"));
        platformExecutor.shutdown();

        // Java 21 - No pooling needed
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> task("Virtual Thread 1"));
            executor.submit(() -> task("Virtual Thread 2"));
        }

        /*
         * Key Insight:
         * - No need for thread pools
         * - One task = one virtual thread
         * - Scales massively
         */
    }

    static void task(String msg) {
        try {
            Thread.sleep(300);
            System.out.println(msg + " -> " + Thread.currentThread());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ============================================================
    // 4. STRUCTURED CONCURRENCY (Preview)
    // ============================================================
    static void structuredConcurrencyDemo() throws Exception {
        System.out.println("\n=== STRUCTURED CONCURRENCY ===");

        // Java 17 - CompletableFuture
        CompletableFuture<String> user =
                CompletableFuture.supplyAsync(() -> fetch("User"));

        CompletableFuture<String> order =
                CompletableFuture.supplyAsync(() -> fetch("Order"));

        String result17 = user.thenCombine(order, (u, o) -> u + " + " + o).join();
        System.out.println("Java17: " + result17);

        // Java 21 - Structured Concurrency
        try (@SuppressWarnings("preview")
		var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            var userTask = scope.fork(() -> fetch("User"));
            var orderTask = scope.fork(() -> fetch("Order"));

            scope.join();
            scope.throwIfFailed();

            var userResult = userTask.get();
            var orderResult = orderTask.get();

            String result21 = userResult + " + " + orderResult;
            System.out.println("Java21: " + result21);
        }
    }

    static String fetch(String name) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return name;
    }

    // ============================================================
    // 5. SEQUENCED COLLECTIONS (List + Map)
    // ============================================================
    static void sequencedCollectionsDemo() {
        System.out.println("\n=== SEQUENCED COLLECTIONS ===");

        List<String> list = new ArrayList<>(List.of("A", "B", "C"));

        System.out.println("Java17 First = " + list.get(0));
        System.out.println("Java17 Last  = " + list.get(list.size() - 1));

        list.addFirst("START");
        list.addLast("END");

        System.out.println("Java21 First = " + list.getFirst());
        System.out.println("Java21 Last  = " + list.getLast());

        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        System.out.println("First Entry = " + map.firstEntry());
        System.out.println("Last Entry  = " + map.lastEntry());
    }

    // ============================================================
    // 6. THREADLOCAL vs SCOPED VALUE
    // ============================================================
    @SuppressWarnings("preview")
	static void threadLocalVsScopedValueDemo() {
        System.out.println("\n=== THREADLOCAL vs SCOPED VALUE ===");

        ThreadLocal<String> tl = new ThreadLocal<>();
        tl.set("User17");

        System.out.println("ThreadLocal: " + tl.get());
        tl.remove();

        ScopedValue<String> USER = ScopedValue.newInstance();

        ScopedValue.where(USER, "User21").run(() ->
                System.out.println("ScopedValue: " + USER.get())
        );
    }

    // ============================================================
    // 7. STRING TEMPLATES (Preview)
    // ============================================================
    static void stringTemplatesDemo() {
        System.out.println("\n=== STRING TEMPLATES ===");

        String name = "Mohan";
        // String msg = STR."Hello \{name}";
        String msg = "Hello " + name;

        System.out.println(msg);
    }

    // ============================================================
    // 8. UNNAMED PATTERNS (Preview)
    // ============================================================
    static void unnamedPatternDemo() {
        System.out.println("\n=== UNNAMED PATTERN ===");

        Object obj = "test";

        // if (obj instanceof String _) {
        if (obj instanceof String s) {
            System.out.println("String detected (ignored value)");
        }
    }

    /*
     * JVM Improvement:
     * Generational ZGC improves performance & latency
     */
}

/*

=== PATTERN MATCHING ===
Java17: Length = 7
Java21: Length = 7

=== RECORD PATTERNS ===
Java17 Area = 50.26548245743669
Java21 Area = 50.26548245743669

=== VIRTUAL THREADS ===
Platform Thread -> Thread[#21,pool-1-thread-1,5,main]
Virtual Thread 1 -> VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1
Virtual Thread 2 -> VirtualThread[#24]/runnable@ForkJoinPool-1-worker-2

=== STRUCTURED CONCURRENCY ===
Java17: User + Order
Java21: User + Order

=== SEQUENCED COLLECTIONS ===
Java17 First = A
Java17 Last  = C
Java21 First = START
Java21 Last  = END
First Entry = A=1
Last Entry  = C=3

=== THREADLOCAL vs SCOPED VALUE ===
ThreadLocal: User17
ScopedValue: User21

=== STRING TEMPLATES ===
Hello Mohan

=== UNNAMED PATTERN ===
String detected (ignored value)
*/