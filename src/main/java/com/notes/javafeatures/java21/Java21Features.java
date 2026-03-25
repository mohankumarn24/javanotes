package com.notes.javafeatures.java21;

import java.util.*;
import java.util.concurrent.*;

/*
========================================
JAVA 21 — CHEAT SHEET
========================================

1. VIRTUAL THREADS
- Lightweight threads managed by JVM
- One task = one thread (no pooling needed)
- Best for I/O bound work (DB, API calls)
- NOT for CPU-heavy tasks

IMPORTANT:
- Uses carrier threads internally (ForkJoinPool)
- Blocking is cheap unless PINNING happens

PINNING happens when:
- synchronized block
- native calls
- long-running CPU tasks

→ Avoid synchronized, prefer ReentrantLock

----------------------------------------

2. STRUCTURED CONCURRENCY (Preview)
- Treats multiple threads as ONE unit
- Improves cancellation + error handling

Variants:
- ShutdownOnFailure → fail fast (all cancelled)
- ShutdownOnSuccess → first result wins

----------------------------------------

3. SCOPED VALUE (Preview)
- Immutable alternative to ThreadLocal
- No memory leak
- Works with virtual threads

----------------------------------------

4. WHEN TO USE WHAT
- Virtual Threads → replace thread pools
- Structured Concurrency → replace CompletableFuture
- ScopedValue → replace ThreadLocal

----------------------------------------

5. PRODUCTION NOTE
- StructuredConcurrency & ScopedValue are PREVIEW
- Require --enable-preview

========================================
*/
public class Java21Features {
    public static void main(String[] args) throws Exception {
        patternMatchingDemo();
        recordPatternDemo();
        
        threadLocalVsScopedValueDemo();
        structuredConcurrencyDemo();
        virtualThreadsDemo();
        
        sequencedCollectionsDemo();
        stringTemplatesDemo();
        unnamedPatternDemo();
        
        fastestResponseDemo();
        virtualThreadPinningDemo();
        migrationExample();
    }

    // ============================================================
    // PATTERN MATCHING (Java 17 vs 21)
    // ============================================================
    /*
     * PATTERN MATCHING FOR SWITCH (Java 21)
     *
     * Basic Idea:
     * - Allows type-based pattern matching directly inside switch
     * - Replaces instanceof + cast + if-else chains
     *
     * Before (Java 17):
     * - Use instanceof with explicit casting and if-else
     *
     * Example:
     *      if (obj instanceof String s) {
     *          s.length();
     *      }
     *
     * Problems:
     * 1. Multiple if-else blocks for different types
     * 2. Manual casting required
     * 3. Less readable and harder to maintain
     *
     * After (Java 21):
     * - Combine type check + cast + branching in one construct
     *
     * Example:
     *      switch (obj) {
     *          case String s  -> ...
     *          case Integer i -> ...
     *          case null      -> ...
     *          default        -> ...
     *      }
     *
     * Key Benefits:
     * 1. Cleaner and more concise code
     * 2. No explicit casting required
     * 3. Built-in null handling (case null)
     * 4. Supports guards (case String s when condition)
     * 5. Works with record patterns (destructuring)
     * 6. Enables exhaustive checks with sealed classes
     *
     * Insight:
     * Pattern matching in switch unifies type checking, casting,
     * and control flow into a single, expressive construct.
     */
    static void patternMatchingDemo() {
        System.out.println("=== PATTERN MATCHING ===");

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
    // RECORD PATTERNS + SEALED CLASSES
    // ============================================================
    /*
     * RECORD PATTERNS (Java 21)
     *
     * Basic Idea:
     * - Allows destructuring of record objects directly in pattern matching
     * - Extracts values without calling getters
     *
     * Before (Java 17):
     * - Use instanceof + manual getter calls
     *
     * Example:
     *      if (shape instanceof Circle c) {
     *          c.radius(); // explicit accessor
     *      }
     *
     * Problems:
     * 1. Verbose code
     * 2. Requires manual extraction using getters
     * 3. Less readable with nested structures
     *
     * After (Java 21):
     * - Direct destructuring inside pattern
     *
     * Example:
     *      switch (shape) {
     *          case Circle(double r) -> ...
     *          case Rectangle(double w, double h) -> ...
     *      }
     *
     * Key Benefits:
     * 1. No need to call getters (auto extraction)
     * 2. Cleaner and more readable
     * 3. Works seamlessly with switch pattern matching
     * 4. Supports nested destructuring (advanced use)
     *
     * Works Best With:
     * - Records
     * - Sealed classes (for exhaustive switch)
     *
     * Insight:
     * Record patterns extend pattern matching by enabling direct data extraction,
     * making code more declarative and reducing boilerplate.
     */
    static void recordPatternDemo() {
        System.out.println("\n=== RECORD PATTERNS ===");

        // Shape can only be Circle or Rectangle (sealed)
        Shape shape = new Circle(4);

        // Java 17 → instanceof pattern matching
        double area17;
        if (shape instanceof Circle c) {
            area17 = Math.PI * c.radius() * c.radius();
        } else if (shape instanceof Rectangle r) {
            area17 = r.width() * r.height();
        } else {
            area17 = 0; // needed (no exhaustiveness check)
        }

        // Java 21 → switch with record patterns
        double area21 = switch (shape) {
            case Circle(double r) -> Math.PI * r * r;
            case Rectangle(double w, double h) -> w * h;
            // no default → sealed ensures all cases handled
        };

        System.out.println("Java17 Area = " + area17);
        System.out.println("Java21 Area = " + area21);
    }

    /**
     * Sealed interface → defines allowed types, not behavior
     * No methods in Shape → using pattern matching, not polymorphism
     * Logic is outside objects, not inside
     */
    sealed interface Shape permits Circle, Rectangle {}

    // Records → simple data holders
    record Circle(double radius) implements Shape {}
    record Rectangle(double width, double height) implements Shape {}

    // ============================================================
    // SCOPED VALUE
    // ============================================================
    /*
     * THREADLOCAL vs SCOPED VALUE (Java 21 - Preview)
     *
     * Basic Idea:
     * - Both are used to pass contextual data across methods/threads
     * - ScopedValue is the modern, safer alternative to ThreadLocal
     *
     * ThreadLocal (Java 17):
     * - Stores mutable data per thread
     *
     * Example:
     *      ThreadLocal<String> tl = new ThreadLocal<>();
     *      tl.set("User");
     *      tl.get();
     *      tl.remove(); // must clean manually
     *
     * Problems:
     * 1. Memory leaks if not removed properly
     * 2. Mutable state → hard to reason about
     * 3. Not suitable for virtual threads (can cause unexpected behavior)
     *
     * ScopedValue (Java 21 - Preview):
     * - Immutable, context-bound value
     * - Scoped to a block of execution
     *
     * Example:
     *      ScopedValue<String> USER = ScopedValue.newInstance();
     *
     *      ScopedValue.where(USER, "User").run(() -> {
     *          USER.get();
     *      });
     *
     * Key Benefits:
     * 1. Immutable → safer
     * 2. No memory leaks (no manual cleanup)
     * 3. Works naturally with virtual threads
     * 4. Clear scope → better readability
     *
     * When to Use:
     * - ThreadLocal → legacy / existing code
     * - ScopedValue → modern applications (especially with virtual threads)
     *
     * Insight:
     * ScopedValue is designed to replace ThreadLocal in modern Java,
     * especially in structured concurrency and virtual thread environments.
     */
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
    // STRUCTURED CONCURRENCY (Preview)
    // ============================================================
    /*
     * STRUCTURED CONCURRENCY (Java 21 - Preview)
     *
     * Basic Idea:
     * - Treats multiple concurrent tasks as a single unit of work
     * - Improves readability, error handling, and cancellation
     *
     * Before (Java 17 - CompletableFuture):
     * - Asynchronous programming with chaining
     *
     * Example:
     *      CompletableFuture<String> user =
     *          CompletableFuture.supplyAsync(() -> fetch("User"));
     *
     *      CompletableFuture<String> order =
     *          CompletableFuture.supplyAsync(() -> fetch("Order"));
     *
     *      user.thenCombine(order, ...).join();
     *
     * Problems:
     * 1. Hard to read and maintain
     * 2. Error handling is complex
     * 3. No automatic cancellation of dependent tasks
     * 4. Debugging is difficult
     *
     * After (Java 21 - Structured Concurrency):
     * - Tasks are grouped and executed together
     *
     * Example:
     *      try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
     *
     *          var userTask  = scope.fork(() -> fetch("User"));
     *          var orderTask = scope.fork(() -> fetch("Order"));
     *
     *          scope.join();          // wait for all tasks
     *          scope.throwIfFailed(); // fail fast if any task fails
     *
     *          var result = userTask.get() + " + " + orderTask.get();
     *      }
     *
     * Key Benefits:
     * 1. Cleaner, synchronous-style code
     * 2. Automatic cancellation on failure
     * 3. Better error propagation
     * 4. Easier debugging and reasoning
     *
     * Important Notes:
     * - Preview feature → requires --enable-preview
     * - Works best with Virtual Threads
     *
     * Insight:
     * Structured Concurrency simplifies async programming by replacing
     * complex CompletableFuture chains with clear, scoped task execution.
     */
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
    // VIRTUAL THREADS (FINAL)
    // ============================================================
    /*
     * VIRTUAL THREADS (Java 21)
     *
     * Basic Idea:
     * - Lightweight threads managed by JVM (not OS)
     * - Designed to handle massive concurrency efficiently
     *
     * Before (Java 17):
     * - Platform (OS) threads are heavy
     * - Requires thread pools to limit resource usage
     * - Example: Executors.newFixedThreadPool()
     *
     * Problems:
     * - Limited scalability (~10k threads max)
     * - Thread pool tuning is complex
     * - Blocking calls waste threads
     *
     * After (Java 21):
     * - Virtual threads are extremely lightweight
     * - No need for thread pools
     * - One task = one virtual thread
     *
     * Example:
     * Executors.newVirtualThreadPerTaskExecutor()
     *
     * Key Benefits:
     * 1. Millions of threads possible
     * 2. Simplifies concurrency (no pool management)
     * 3. Works with existing blocking code (no rewrite needed)
     * 4. Better resource utilization
     *
     * Important Notes:
     * - Best suited for I/O-bound tasks (DB calls, API calls)
     * - Not ideal for CPU-heavy parallel computation
     *
     * Insight:
     * Virtual threads change the concurrency model from:
     * "thread pool + async programming"
     * → to
     * "simple synchronous code with massive scalability"
     */
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
    // SEQUENCED COLLECTIONS (List + Map)
    // ============================================================
    /*
     * SEQUENCED COLLECTIONS (Java 21)
     *
     * Basic Idea:
     * - Introduces a unified way to access elements in a defined order
     * - Adds first/last operations across List, Set, and Map
     *
     * New Interfaces:
     * - SequencedCollection
     * - SequencedSet
     * - SequencedMap
     *
     * Before (Java 17):
     * - No standard way to get first/last elements
     * - List:
     *      list.get(0)
     *      list.get(list.size() - 1)
     * - Map:
     *      No direct first/last entry access
     *
     * After (Java 21):
     * - Direct methods available:
     *
     * List:
     *      list.getFirst()
     *      list.getLast()
     *      list.addFirst()
     *      list.addLast()
     *
     * Map (LinkedHashMap):
     *      map.firstEntry()
     *      map.lastEntry()
     *
     * Key Benefits:
     * 1. Cleaner and more readable code
     * 2. No index-based access required
     * 3. Consistent API across collections
     * 4. Reduces boilerplate logic
     *
     * Works With:
     * - List (ArrayList, LinkedList)
     * - Deque
     * - LinkedHashMap (for ordered maps)
     *
     * Insight:
     * Sequenced Collections standardize order-based operations,
     * removing the need for manual index handling and improving API consistency.
     */
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
    // STRING TEMPLATES (Preview)
    // ============================================================
    /*
     * STRING TEMPLATES (Java 21 - Preview)
     *
     * Basic Idea:
     * - Provides a safer and cleaner way to build strings
     * - Similar to f-strings (Python) or template literals (JS)
     *
     * Before (Java 17):
     * - String concatenation using +
     *      "Hello " + name
     *
     * Problems:
     * - Hard to read for complex strings
     * - Error-prone
     * - No validation of embedded expressions
     *
     * After (Java 21 - Preview):
     * - Template-based string construction
     *
     * Example:
     *      String msg = STR."Hello \{name}";
     *
     * Key Benefits:
     * 1. Better readability
     * 2. Safer string construction
     * 3. Easier to embed expressions
     * 4. Reduces concatenation clutter
     *
     * Important Notes:
     * - This is a PREVIEW feature
     * - Requires --enable-preview to compile and run
     *
     * Production Note:
     * - If preview is not enabled, fallback to:
     *      String msg = "Hello " + name;
     *
     * Insight:
     * String Templates improve developer productivity by making
     * string creation more readable, expressive, and less error-prone.
     */
    static void stringTemplatesDemo() {
        System.out.println("\n=== STRING TEMPLATES ===");

        String name = "Mohan";
        // String msg = STR."Hello \{name}";
        String msg = "Hello " + name;

        System.out.println(msg);
    }

    // ============================================================
    // UNNAMED PATTERNS (Preview)
    // ============================================================
    /*
     * UNNAMED PATTERNS (Java 21 - Preview)
     *
     * Basic Idea:
     * - Allows ignoring variables in pattern matching
     * - Useful when we only care about the type, not the value
     *
     * Before (Java 17 / without preview):
     *      if (obj instanceof String s) {
     *          // variable 's' is declared but may not be used
     *      }
     *
     * Problem:
     * - Unused variables reduce code clarity
     *
     * After (Java 21 - Preview):
     *      if (obj instanceof String _) {
     *          // value is ignored
     *      }
     *
     * Key Benefits:
     * 1. Cleaner code (no unused variables)
     * 2. Expresses intent clearly (type check only)
     * 3. Works with switch and pattern matching
     *
     * Important Notes:
     * - '_' is reserved (cannot be used as variable name anymore)
     * - Requires --enable-preview
     *
     * Production Alternative:
     *      if (obj instanceof String s) {
     *          // just don't use 's'
     *      }
     *
     * Insight:
     * Unnamed patterns improve readability by removing unnecessary variables
     * when only type checking is required.
     */
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
    
 // ============================================================
 // FASTEST RESPONSE (ShutdownOnSuccess)
 // ============================================================
 @SuppressWarnings("preview")
 static void fastestResponseDemo() throws Exception {
     System.out.println("\n=== FASTEST RESPONSE ===");

     try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {

         scope.fork(() -> {
             Thread.sleep(2000);
             return "Slow API";
         });

         scope.fork(() -> {
             Thread.sleep(500);
             return "Fast API";
         });

         scope.join();

         System.out.println("Winner: " + scope.result());
     }
 }

 // ============================================================
 // VIRTUAL THREAD PINNING
 // ============================================================
 static void virtualThreadPinningDemo() throws Exception {
     System.out.println("\n=== VIRTUAL THREAD PINNING ===");

     Object lock = new Object();

     try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
         for (int i = 0; i < 3; i++) {
             executor.submit(() -> {
                 synchronized (lock) { // ❌ causes pinning
                     try {
                         Thread.sleep(1000);
                         System.out.println("Pinned: " + Thread.currentThread());
                     } catch (InterruptedException e) {
                         Thread.currentThread().interrupt();
                     }
                 }
             });
         }
     }
 }

 // ============================================================
 // MIGRATION (CompletableFuture → Structured)
 // ============================================================
 @SuppressWarnings("preview")
 static void migrationExample() throws Exception {

     System.out.println("\n=== MIGRATION ===");

     // OLD WAY
     CompletableFuture<String> user =
             CompletableFuture.supplyAsync(() -> fetch("User"));

     CompletableFuture<String> order =
             CompletableFuture.supplyAsync(() -> fetch("Order"));

     String oldResult = user.thenCombine(order, (u, o) -> u + o).join();
     System.out.println("Old: " + oldResult);

     // NEW WAY
     try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

         var u = scope.fork(() -> fetch("User"));
         var o = scope.fork(() -> fetch("Order"));

         scope.join();
         scope.throwIfFailed();

         String newResult = u.get() + o.get();
         System.out.println("New: " + newResult);
     }
 }
}

/*
=== PATTERN MATCHING ===
Java17: Length = 7
Java21: Length = 7

=== RECORD PATTERNS ===
Java17 Area = 50.26548245743669
Java21 Area = 50.26548245743669

=== THREADLOCAL vs SCOPED VALUE ===
ThreadLocal: User17
ScopedValue: User21

=== STRUCTURED CONCURRENCY ===
Java17: User + Order
Java21: User + Order

=== VIRTUAL THREADS ===
Platform Thread -> Thread[#28,pool-1-thread-1,5,main]
Virtual Thread 2 -> VirtualThread[#30]/runnable@ForkJoinPool-1-worker-1
Virtual Thread 1 -> VirtualThread[#29]/runnable@ForkJoinPool-1-worker-2

=== SEQUENCED COLLECTIONS ===
Java17 First = A
Java17 Last  = C
Java21 First = START
Java21 Last  = END
First Entry = A=1
Last Entry  = C=3

=== STRING TEMPLATES ===
Hello Mohan

=== UNNAMED PATTERN ===
String detected (ignored value)

=== FASTEST RESPONSE ===
Winner: Fast API

=== VIRTUAL THREAD PINNING ===
Pinned: VirtualThread[#34]/runnable@ForkJoinPool-1-worker-2
Pinned: VirtualThread[#36]/runnable@ForkJoinPool-1-worker-3
Pinned: VirtualThread[#35]/runnable@ForkJoinPool-1-worker-1

=== MIGRATION ===
Old: UserOrder
New: UserOrder
*/