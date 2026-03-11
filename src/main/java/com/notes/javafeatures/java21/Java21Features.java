package com.notes.javafeatures.java21;

import java.util.*;
import java.util.concurrent.*;

/**
 * ============================================================ JAVA 21 NEW
 * FEATURES (NO PREVIEW APIs) Safe for production & interviews
 * ============================================================
 *
 * Covers ONLY FINAL features: 1. Pattern Matching for switch (Object) 2. Record
 * Patterns 3. Sealed Classes (exhaustive switch) 4. Virtual Threads (FINAL) 5.
 * Sequenced Collections
 */
public class Java21Features {

	public static void main(String[] args) throws Exception {
		switchPatternDemo();
		recordPatternDemo();
		sequencedCollectionDemo();
		virtualThreadDemo();
	}

// ══════════════════════════════════════════════════════════
// 1. PATTERN MATCHING FOR switch (FINAL)
// ══════════════════════════════════════════════════════════
	static void switchPatternDemo() {
		System.out.println("\n=== 1. SWITCH PATTERN MATCHING (Java 21) ===");

		Object obj = "Java 21";

		// BEFORE (Java 17) — instanceof chain
		if (obj instanceof String s) {
			System.out.println("Before length: " + s.length());
		}

		// AFTER (Java 21) — switch on Object
		String result = switch (obj) {
		case String s -> "String length = " + s.length();
		case Integer i -> "Integer value = " + i;
		case null -> "Null value";
		default -> "Unknown type";
		};

		System.out.println("After: " + result);

		/*
		 * INTERVIEW TIPS: - switch now works on Object - instanceof + cast + branching
		 * in ONE construct - null can be handled explicitly
		 */
	}

// ══════════════════════════════════════════════════════════
// 2. RECORD PATTERNS (FINAL)
// ══════════════════════════════════════════════════════════
	static void recordPatternDemo() {
		System.out.println("\n=== 2. RECORD PATTERNS ===");

		Shape shape = new Circle(4);

		// BEFORE (Java 17)
		double areaBefore;
		if (shape instanceof Circle c) {
			areaBefore = Math.PI * c.radius() * c.radius();
		} else if (shape instanceof Rectangle r) {
			areaBefore = r.width() * r.height();
		} else {
			areaBefore = 0;
		}

		// AFTER (Java 21) — record destructuring
		double areaAfter = switch (shape) {
		case Circle(double r) -> Math.PI * r * r;
		case Rectangle(double w, double h) -> w * h;
		};

		System.out.println("Area before = " + areaBefore);
		System.out.println("Area after  = " + areaAfter);

		/*
		 * INTERVIEW TIPS: - Record patterns extract components directly - No accessor
		 * calls like circle.radius() - Best used with sealed hierarchies
		 */
	}

// ══════════════════════════════════════════════════════════
// 3. SEALED CLASSES (EXHAUSTIVE SWITCH)
// ══════════════════════════════════════════════════════════
	sealed interface Shape permits Circle, Rectangle {
	}

	record Circle(double radius) implements Shape {
	}

	record Rectangle(double width, double height) implements Shape {
	}

	/*
	 * INTERVIEW TIPS: - switch becomes exhaustive for sealed types - No default
	 * needed - Compiler enforces completeness
	 */

// ══════════════════════════════════════════════════════════
// 4. SEQUENCED COLLECTIONS (NEW)
// ══════════════════════════════════════════════════════════
	static void sequencedCollectionDemo() {
		System.out.println("\n=== 4. SEQUENCED COLLECTIONS ===");

		List<String> names = new ArrayList<>(List.of("Sachin", "Dravid", "Virat"));

		// BEFORE (Java 17)
		System.out.println("First (old): " + names.get(0));
		System.out.println("Last  (old): " + names.get(names.size() - 1));

		// AFTER (Java 21)
		System.out.println("First (new): " + names.getFirst());
		System.out.println("Last  (new): " + names.getLast());

		names.addFirst("START");
		names.addLast("END");

		System.out.println("After addFirst/addLast: " + names);

		/*
		 * INTERVIEW TIPS: - New interfaces: SequencedCollection, SequencedSet,
		 * SequencedMap - Cleaner first/last access - Works with List, Deque,
		 * LinkedHashMap
		 */
	}

// ══════════════════════════════════════════════════════════
// 5. VIRTUAL THREADS (FINAL)
// ══════════════════════════════════════════════════════════
	static void virtualThreadDemo() throws Exception {
		System.out.println("\n=== 5. VIRTUAL THREADS ===");

		// BEFORE — platform threads
		ExecutorService platformExecutor = Executors.newFixedThreadPool(2);
		platformExecutor.submit(() -> blockingTask("Platform thread"));
		platformExecutor.shutdown();

		// AFTER — virtual threads
		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			executor.submit(() -> blockingTask("Virtual thread 1"));
			executor.submit(() -> blockingTask("Virtual thread 2"));
		}

		/*
		 * INTERVIEW TIPS: - Virtual threads are lightweight (millions possible) - Ideal
		 * for I/O-heavy workloads - Existing blocking code works unchanged
		 */
	}

	static void blockingTask(String msg) {
		try {
			Thread.sleep(300);
			System.out.println(msg + " running on " + Thread.currentThread());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}