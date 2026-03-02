package com.notes.javafeatures.java17;

/**
 * ============================================================
 *  JAVA 17 NEW FEATURES
 *  Only TWO versions: BEFORE (Java 8/11)  vs  AFTER (Java 17)
 * ============================================================
 */

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;

public class Java17Features {

	// var someNum = 100; 						// CTE: 'var' is not allowed here
	
    public static void main(String[] args) {
    	
        textBlockDemo();
        recordDemo();
        sealedClassDemo();
        varDemo();
        switchDemo();
        instanceofDemo();
        patternMatchingDemo();
        helpfulNullPointerDemo();
        
        // not Java 17 features
        optionalDemo();
        immutableCollectionsDemo();
        mapEnhancementDemo();
        nullHandlingUtilityDemo();
    }


    // ══════════════════════════════════════════════════════════
    // 1. TEXT BLOCKS
    // ══════════════════════════════════════════════════════════
    static void textBlockDemo() {
        System.out.println("\n=== 1. TEXT BLOCKS ===");

        // BEFORE (Java 8/11) — escape characters, concatenation, hard to read
        String jsonBefore = "{\n" +
                            "  \"name\": \"Alice\",\n" +
                            "\n" +
                            "  \"age\": 30\n" +
                            "}";

        // AFTER (Java 17) — triple quotes, write exactly what you see
        String jsonAfter = """
                {
                  "name": "Alice",
                  
                  "age": 30
                }
                """;

        System.out.println("Before:\n" + jsonBefore);
        System.out.println("After:\n"  + jsonAfter);

        /*
         * TIPS:
         *  - Opening """ must NOT have text after it (must be on its own line)
         *  - Indentation stripped based on closing """ position
         *  - No need to escape double quotes inside
         *  - Great for: JSON, SQL, HTML, XML embedded in Java
         */
    }


    // ══════════════════════════════════════════════════════════
    // 2. RECORDS
    // ══════════════════════════════════════════════════════════

    // BEFORE (Java 8/11) — massive boilerplate for a simple data class
    static class PersonOld {
        private final String name;
        private final int age;

        PersonOld(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int    getAge()  { return age;  }

        @Override
        public String toString() {
            return "Person{name=" + name + ", age=" + age + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PersonOld)) return false;
            PersonOld p = (PersonOld) o;
            return age == p.age && Objects.equals(name, p.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

    // AFTER (Java 17) — one line replaces everything above
    record PersonNew(String name, int age) {
        // Optional: compact constructor for validation
        PersonNew {
            if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        }
        // Can still add custom methods
        String greeting() { return "Hi, I'm " + name; }
    }

    static void recordDemo() {
        System.out.println("\n=== 2. RECORDS ===");

        PersonOld before = new PersonOld("Alice", 30);
        PersonNew after  = new PersonNew("Alice", 30);

        System.out.println(before);           // Person{name=Alice, age=30}
        System.out.println(after);            // PersonNew[name=Alice, age=30]
        System.out.println(after.name());     // no "get" prefix — just name()
        System.out.println(after.age());
        System.out.println(after.greeting());

        /*
         * TIPS:
         *  - Records are IMMUTABLE — all fields are final automatically
         *  - Auto-generates: constructor, accessors (no "get"), equals, hashCode, toString
         *  - Accessor is name() not getName()  <- IMPORTANT
         *  - Cannot extend another class (implicitly extends java.lang.Record)
         *  - CAN implement interfaces
         *  - Best for: DTOs, value objects, API response models
         */
    }


    // ══════════════════════════════════════════════════════════
    // 3. SEALED CLASSES
    // ══════════════════════════════════════════════════════════

    // BEFORE (Java 8/11) — anyone, anywhere can extend Shape. No control.
    //   abstract class Shape {}
    //   class Circle    extends Shape {}   // fine
    //   class Hexagon   extends Shape {}   // you can't stop this
    //   class UFO       extends Shape {}   // from any package!

    // AFTER (Java 17) — only Circle, Rectangle, Triangle are allowed. Period.
    //
    // Each permitted subtype MUST be one of:
    //
    //   1. record (implicitly final) — most common for data carriers
    //          record Circle(double radius) implements Shape {}
    //          // Records are implicitly final and CANNOT be extended
    //
    //   2. sealed class — further restricted with its own permits list
    //          sealed class Triangle implements Shape permits IsoscelesTriangle {}
    //
    //   3. non-sealed class — hierarchy opened back up
    //          non-sealed class Triangle implements Shape {}
    //
    // NOTE:
    // - record CANNOT be final / sealed / non-sealed explicitly
    // - record CANNOT extend another class (it already extends java.lang.Record)
    //
    // Ex: Can be used to permit only a few payment gateways instead of everything
    
    sealed interface Shape permits Circle, Rectangle, Triangle {}		// can be interface or class

    // 1. record — implicitly final (most common for simple data shapes). We cannot extend these classes
    record Circle   (double radius)         implements Shape {}
    record Rectangle(double w, double h)    implements Shape {}
    record Triangle (double base, double h) implements Shape {}

	// 2. sealed subtype example
	// sealed class Triangle implements Shape permits IsoscelesTriangle {}
	// final class IsoscelesTriangle extends Triangle {}
	//
	// ✔ If EXTENDING a sealed CLASS:
    //        The subclass MUST be declared as one of:
    //          - final       (cannot be extended further)
    //          - sealed      (restricted again with permits)
    //          - non-sealed  (opened for extension)
	//
	// ✔ If IMPLEMENTING a sealed INTERFACE (CLASS case):
    //        The implementing CLASS MUST be declared as one of:
    //          - final
    //          - sealed
    //          - non-sealed
	//
	// ✔ If IMPLEMENTING a sealed INTERFACE (INTERFACE case):
    //        The implementing INTERFACE MUST be declared as:
    //          - sealed
    //          - non-sealed
	//
	// ❌ Interfaces themselves cannot be final
    //       (Only classes can be final)
	//
	// ❌ Records cannot be sealed or non-sealed
    //       (Records are implicitly final)
	//
	// ⚠️ IMPORTANT:
    //      - final classes do NOT need a permits clause
    //      - sealed classes / interfaces MUST declare permits (unless nested)
	//
	// Example:
	// sealed interface Shape permits Triangle {}
	//
	// final class Triangle implements Shape {} 				// ✔ valid
	// sealed class Triangle implements Shape {} 				// ✔ valid
	// non-sealed class Triangle implements Shape {} 			// ✔ valid
	//
	// sealed interface Triangle extends Shape {} 				// ✔ valid
	// non-sealed interface Triangle extends Shape {} 			// ✔ valid
	//
	//
	// 3. non-sealed subtype example (opens hierarchy back up):
	// 		non-sealed class Triangle implements Shape {}
	// 		class AnyTriangle extends Triangle {} 				// ✔ allowed

    static void sealedClassDemo() {
        System.out.println("\n=== 3. SEALED CLASSES ===");

        Shape s = new Circle(5.0);
        System.out.println("Shape: " + s);

        /*
         * TIPS:
         *  - sealed = you control the entire class hierarchy
         *  - Permitted subclasses must be in same package (or same file)
         *  - Each subclass must be: final | sealed | non-sealed
         *      final      = no further extension at all
         *      sealed     = further restricted extension
         *      non-sealed = opened back up (anyone can extend again)
         *
         *  Q: Difference between final and sealed?
         *  A: final = nobody can extend.
         *     sealed = only permitted classes can extend.
         */
    }

    
    // ════════════════════════════════════════════════════════════════════════════
    // 4. var — Local Variable Type Inference (LVTI). Type assigned at compile time
    // ════════════════════════════════════════════════════════════════════════════
    static void varDemo() {
        System.out.println("\n=== 7. VAR ===");

        // int a;											// allowed. int a = 8; -> optional
        // var b;											// CTE.     var b = 8; -> mandatory
        
        // int a[] = new int[] {1, 2, 3};					// allowed
        // var b[] = new int[] {1, 2, 3};					// CTE: 'var' is not allowed as an element type of an array
        
        // String var = "hello";							// variable names allowed, but class names not allowed
        // Java17Features var = new Java17Features();		// allowed
        
        // BEFORE (Java 8/11) — type repeated on both sides
        List<String> listBefore = new ArrayList<String>();
        listBefore.add("Java 8");
        System.out.println("Before: " + listBefore);

        // AFTER (Java 17) — compiler infers type from right-hand side
        var listAfter = new ArrayList<String>(); // inferred as ArrayList<String>
        listAfter.add("Java 17");

        // Most useful with long generic types — saves repetition on the left side
        // Before:
        Map<String, List<Map<Integer, String>>> complexBefore = new HashMap<>();

        // After — right side already tells the compiler the type:
        var complexAfter = new HashMap<String, List<Map<Integer, String>>>();

        // Put something in both so they're "used" — avoids compiler warnings
        complexBefore.put("key", new ArrayList<>());
        complexAfter.put("key", new ArrayList<>());

        for (var item : listAfter) {
            System.out.println("After item: " + item);
        }

        /*
         * TIPS:
         *  - var is compile-time inference — NOT dynamic typing like JavaScript
         *  - Type locked at declaration:  var a = "hi"; a = 2; <- COMPILE ERROR
         *  - CANNOT use for: method params, return types, class fields
         *  - var a;         <- ERROR (no initializer)
         *  - var a = null;  <- ERROR (null has no type to infer)
         *  - var is not a keyword — it's a reserved type name
         *
         *  Q: Is var dynamic typing?
         *  A: NO. Type is fixed at compile time. Java is still statically typed.
         *
         *  Q: Can you use var for method parameters?
         *  A: NO. Method signatures are API contracts — types must be explicit.
         */
    }
    
    
    // ══════════════════════════════════════════════════════════
    // 5. SWITCH — Statement (Java 8) vs Expression (Java 17)
    // ══════════════════════════════════════════════════════════
    enum Season { SPRING, SUMMER, AUTUMN, WINTER }

    static void switchDemo() {
        System.out.println("\n=== 4. SWITCH ===");

        String day = "MONDAY";

        // ── BEFORE (Java 8/11): Switch STATEMENT ──────────────
        //   Does NOT return a value
        //   Fall-through: forgetting break = silent bug
        //   Multi-label = repeat case lines
        int numLettersBefore;
        switch (day) {
            case "MONDAY":
            case "FRIDAY":
            case "SUNDAY":
                numLettersBefore = 6;
                break;            // <- forget this -> falls into TUESDAY case!
            case "TUESDAY":
                numLettersBefore = 7;
                break;
            case "THURSDAY":
            case "SATURDAY":
                numLettersBefore = 8;
                break;
            case "WEDNESDAY":
                numLettersBefore = 9;
                break;
            default:
                numLettersBefore = -1;
        }
        System.out.println("Before: " + numLettersBefore);

        // ── AFTER (Java 17): Switch EXPRESSION ────────────────
        //   Returns a value directly
        //   Arrow (->) = NO fall-through, NO break needed
        //   Multi-label with comma: case "A", "B" ->
        int numLettersAfter = switch (day) {
            case "MONDAY", "FRIDAY", "SUNDAY"  -> 6;
            case "TUESDAY"                     -> 7;
            // case "TUESDAY"                  -> { yield 7; }
            case "THURSDAY", "SATURDAY"        -> 8;
            case "WEDNESDAY"                   -> 9;
            default                            -> -1;
        };
        System.out.println("After (->): " + numLettersAfter);
        
        int numLettersAfter2 = switch (day) {				// if using ':' use 'yield 6'
        case "MONDAY", "FRIDAY", "SUNDAY"  : yield 6;
        case "TUESDAY"                     : yield 7;
        case "THURSDAY", "SATURDAY"        : yield 8;
        case "WEDNESDAY"                   : yield 9;
        default                            : yield -1;
        };
        System.out.println("After (yield): " + numLettersAfter2);

        // ── yield: return value from a block {} inside switch expression ──
        String result = switch (day) {
            case "MONDAY" -> "Start of week";
            case "FRIDAY" -> {
                System.out.println("  (processing Friday...)");
                yield "End of week";  // <- yield = return inside a switch expression block
            }
            default -> "Midweek";
        };
        System.out.println("Result: " + result);

        // ── Switch on ENUM — Java 17 verifies all values covered, no default needed
        Season season = Season.SUMMER;

        // Before (Java 8)
        String activityBefore;
        switch (season) {
            case SPRING: activityBefore = "Plant";   break;
            case SUMMER: activityBefore = "Swim";    break;
            case AUTUMN: activityBefore = "Harvest"; break;
            default:     activityBefore = "Stay in"; // <- needed in Java 8
        }

        // After (Java 17) — compiler knows all 4 enum values are covered
        String activityAfter = switch (season) {
            case SPRING -> "Plant";
            case SUMMER -> "Swim";
            case AUTUMN -> "Harvest";
            case WINTER -> "Stay in";  // <- no default needed!. 
            						   // If all values not mentioned, we get CTE: "A Switch expression should cover all possible values"
        };
        System.out.println("Season before: " + activityBefore + " | after: " + activityAfter);

        /*
         * TIPS:
         * +-----------------------+---------------------+----------------------+
         * |                       | Statement (Java 8)  | Expression (Java 17) |
         * +-----------------------+---------------------+----------------------+
         * | Returns a value       | No                  | Yes                  |
         * | Fall-through          | Yes (bug risk)      | No (arrow ->)        |
         * | break needed          | Yes                 | No                   |
         * | Multi-label           | Repeat case lines   | case "A", "B" ->     |
         * | Block return keyword  | N/A                 | yield                |
         * | Enum exhaustiveness   | No                  | Yes (no default)     |
         * +-----------------------+---------------------+----------------------+
         *
         *  Q: What is yield?
         *  A: Returns a value from a block {} in a switch expression.
         *     Like return, but ONLY for switch expressions.
         *
         *  Q: What types can you switch on in Java 17?
         *  A: int/Integer, byte, short, char, String, enum.
         *     Switching on Object/types -> Java 21 only.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 6. PATTERN MATCHING FOR instanceof
    // ══════════════════════════════════════════════════════════
    static void instanceofDemo() {
        System.out.println("\n=== 5. PATTERN MATCHING instanceof ===");

        Object obj = "Hello, Java 17!";

        // BEFORE (Java 8/11) — check then cast — two redundant steps
        if (obj instanceof String) {
            String s = (String) obj;   // <- you already know it's a String, yet cast again
            System.out.println("Before length: " + s.length());
        }

        // AFTER (Java 17) — check + cast + bind in ONE step
        if (obj instanceof String s) { // 's' is bound here, no cast needed
            System.out.println("After  length: " + s.length());
        }

        // Can combine with condition in same line
        if (obj instanceof String s && s.startsWith("Hello")) {
            System.out.println("Starts with Hello: " + s.toUpperCase());
        }

        // Negation pattern
        if (!(obj instanceof String s)) {
            System.out.println("Not a string");
        } else {
            System.out.println("Is a string: " + s);
        }

        /*
         * TIPS:
         *  - Binding variable 's' scope = only where pattern is guaranteed true
         *  - Eliminates ClassCastException — cast is safe by design
         *
         *  Q: What if obj is null?
         *  A: instanceof always returns false for null — no NPE, 's' never bound.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 7. PATTERN MATCHING — instanceof chain (Java 17 way)
    //    Note: switch on Object/types is Java 21, NOT Java 17
    // ══════════════════════════════════════════════════════════
    static void patternMatchingDemo() {
        System.out.println("\n=== 6. PATTERN MATCHING — instanceof chain ===");

        Object obj = new Circle(3.0);

        // BEFORE (Java 8/11) — check then manually cast every single time
        double areaBefore;
        if (obj instanceof Circle) {
            Circle c = (Circle) obj;                          // <- manual cast
            areaBefore = Math.PI * c.radius() * c.radius();
        } else if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle) obj;                    // <- manual cast
            areaBefore = r.w() * r.h();
        } else if (obj instanceof Triangle) {
            Triangle t = (Triangle) obj;                      // <- manual cast
            areaBefore = 0.5 * t.base() * t.h();
        } else {
            areaBefore = 0;
        }

        // AFTER (Java 17) — no manual cast, binding variable inline
        double areaAfter;
        if (obj instanceof Circle c) {
            areaAfter = Math.PI * c.radius() * c.radius();
        } else if (obj instanceof Rectangle r) {
            areaAfter = r.w() * r.h();
        } else if (obj instanceof Triangle t) {
            areaAfter = 0.5 * t.base() * t.h();
        } else {
            areaAfter = 0;
        }

        System.out.printf("Area before = %.2f%n", areaBefore);
        System.out.printf("Area after  = %.2f%n", areaAfter);

        /*
         * TIPS:
         *  - This is the Java 17 way to handle type-based branching
         *
         *  Q: Why not use switch for this in Java 17?
         *  A: Java 17 switch only supports int/String/enum.
         *     switch(obj) { case Circle c -> ... } needs Java 21.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 8. HELPFUL NullPointerExceptions
    // ══════════════════════════════════════════════════════════
    static void helpfulNullPointerDemo() {
        System.out.println("\n=== 8. HELPFUL NullPointerExceptions ===");

        /*
         * BEFORE (Java 8/11):
         *   Exception in thread "main" java.lang.NullPointerException
         *     at Java17Features.java:250
         *   <- Just the line number. No idea WHAT was null.
         *   <- With: person.getAddress().getCity().toUpperCase()  -- good luck!
         *
         * AFTER (Java 17):
         *   Cannot invoke "String.toUpperCase()" because the return value
         *   of "Address.getCity()" is null
         *   <- Tells you EXACTLY: which call returned null AND what you tried to do
         *
         * No code changes required — pure JVM improvement.
         *
         * TIPS:
         *  - Huge debugging time saver, especially for chained calls
         *  - Works for: method calls, field access, array access on null
         *  - Enabled by default in Java 17 (no flag needed)
         */

        System.out.println("NPE messages in Java 17 describe exactly what was null.");
    }


    // ══════════════════════════════════════════════════════════
    // 9. OPTIONAL — Best Practices (Java 8+, good to know)
    //    NOTE: Optional is NOT a Java 17 feature.
    //    Included here as a best practice comparison.
    // ══════════════════════════════════════════════════════════
    static void optionalDemo() {
        System.out.println("\n=== 9. OPTIONAL (Java 8+ Best Practice) ===");

        String value = null;

        // BAD — isPresent() + get() is an antipattern (just a verbose null check)
        Optional<String> optBad = Optional.ofNullable(value);
        if (optBad.isPresent()) {
            System.out.println(optBad.get()); // you might as well have done: if (value != null)
        }

        // GOOD — idiomatic Optional usage
        Optional<String> opt = Optional.ofNullable(value);

        opt.ifPresent(System.out::println);                          // runs only if present

        String result     = opt.orElse("default");                   // value or fallback
        String lazyResult = opt.orElseGet(() -> "computed lazily");  // lazy — preferred

        System.out.println("orElse:    " + result);
        System.out.println("orElseGet: " + lazyResult);

        /*
         * TIPS:
         *  Q: Difference between orElse() and orElseGet()?
         *  A: orElse("x")       — "x" is ALWAYS evaluated even if value is present
         *     orElseGet(() -> x) — lambda runs ONLY if value is absent (lazy, preferred)
         *
         *  Q: When should you NOT use Optional?
         *  A: - As method parameters    (use overloading instead)
         *     - As class fields         (use null or a sentinel value)
         *     - For collections         (return empty list, not Optional<List>)
         *
         *  Q: isPresent() + get() — bad practice?
         *  A: Yes. It is just a verbose null check. Use ifPresent/orElse/map instead.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 10. IMMUTABLE COLLECTIONS (Java 9+, good to know)
    //     NOTE: List.of(), Set.of(), Map.of() are Java 9 features.
    //     Included here as a best practice comparison.
    // ══════════════════════════════════════════════════════════
    static void immutableCollectionsDemo() {
        System.out.println("\n=== 10. IMMUTABLE COLLECTIONS (Java 9+) ===");

        // BEFORE (Java 8) — mutable by default, verbose to make immutable
        List<String> mutableList = new ArrayList<>();
        mutableList.add("A");
        mutableList.add("B");
        List<String> immutableBefore = java.util.Collections.unmodifiableList(mutableList);
        System.out.println("Before (unmodifiable): " + immutableBefore);

        // AFTER (Java 9+) — concise, truly immutable from creation
        List<String> immutableList = List.of("A", "B", "C");
        Map<String, Integer> immutableMap = Map.of("one", 1, "two", 2);
        System.out.println("After List.of: " + immutableList);
        System.out.println("After Map.of:  " + immutableMap);

        // immutableList.add("D"); // <- UnsupportedOperationException at runtime

        /*
         * TIPS:
         *  Q: Difference between Collections.unmodifiableList() and List.of()?
         *  A: unmodifiableList() wraps an existing list — original can still be mutated.
         *     List.of() creates a truly immutable list from the start.
         *
         *  Q: Can List.of() contain null?
         *  A: NO — throws NullPointerException. Use Arrays.asList() if you need nulls.
         *
         *  Q: Is List.of() ordered?
         *  A: Yes for List. No for Set.of() — iteration order not guaranteed.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 11. MAP ENHANCEMENTS — computeIfAbsent (Java 8+, good to know)
    //     NOTE: computeIfAbsent is a Java 8 feature.
    //     Included here as a best practice comparison.
    // ══════════════════════════════════════════════════════════
    static void mapEnhancementDemo() {
        System.out.println("\n=== 11. MAP ENHANCEMENTS (Java 8+) ===");

        Map<String, List<String>> map = new HashMap<>();

        // ─────────────────────────────────────────────
        // BEFORE — verbose null-check pattern
        // ─────────────────────────────────────────────
        if (!map.containsKey("roles")) {
            map.put("roles", new ArrayList<>());
        }
        map.get("roles").add("ADMIN");

        // ─────────────────────────────────────────────
        // AFTER
        // 1. putIfAbsent (EAGER creation)
        // 	  Value is created EVEN if key already exists
        // ─────────────────────────────────────────────
        List<String> auditLog = new ArrayList<>();
        auditLog.add("LOGIN");
        
        map.putIfAbsent("audit", auditLog);              										// inserted
        map.putIfAbsent("audit", new ArrayList<>());     										// new list created, but ignored
        System.out.println("audit:       " + map.get("audit"));									// audit:       [LOGIN]
        // ⚠️ Example of eager execution
        // map.putIfAbsent("x", expensiveMethod()); 											// expensiveMethod() ALWAYS runs
        
        // ─────────────────────────────────────────────
        // 2. computeIfPresent
        //    Executes ONLY if key already exists
        // ─────────────────────────────────────────────
        map.computeIfPresent("roles", (k, v) -> {
            v.add("USER");   																	// modify existing value as key "roles=[ADMIN]" is already present
            return v;        																	// must return updated value
        });
        System.out.println("roles after computeIfPresent (add): " + map.get("roles"));			// roles after computeIfPresent (add): [ADMIN, USER]

        // ─────────────────────────────────────────────
        // computeIfPresent — removal use case
        // Returning null REMOVES the entry
        // ─────────────────────────────────────────────
        map.computeIfPresent("roles", (k, v) -> {
            v.remove("USER");
            return v.isEmpty() ? null : v;
        });
        System.out.println("roles after computeIfPresent (remove): " + map.get("roles"));		// roles after computeIfPresent (remove): [ADMIN]
        
        // ─────────────────────────────────────────────
        // 3. computeIfAbsent (LAZY creation)
        //    Creates value ie., 'new ArrayList<>()' ONLY if key is absent
        // ─────────────────────────────────────────────
        map.computeIfAbsent("permissions", k -> new ArrayList<>()).add("READ");
        map.computeIfAbsent("permissions", k -> new ArrayList<>()).add("WRITE"); 				// reuses existing list
        System.out.println("permissions after computeIfAbsent: " + map.get("permissions"));		// permissions after computeIfAbsent: [READ, WRITE]
        
        map.computeIfPresent("permissions", (k, v) -> {
            v.remove("WRITE");
            return v.isEmpty() ? null : v;
        });
        System.out.println("permissions after computeIfPresent: " + map.get("permissions"));	// permissions after computeIfPresent: [READ]

        // ─────────────────────────────────────────────
        // 4. getOrDefault
        // ─────────────────────────────────────────────
        // BEFORE
        List<String> rolesBefore = map.containsKey("roles") ? map.get("roles") : new ArrayList<>();

        // AFTER
        List<String> rolesAfter = map.getOrDefault("roles", new ArrayList<>());
        System.out.println("getOrDefault: " + rolesAfter);										// getOrDefault: [ADMIN]

        /*
         * TIPS:
         *
         * putIfAbsent(k, v)
         *   - Value created eagerly
         *   - Simple default insertion
         *
         * computeIfAbsent(k, k -> v)
         *   - Value created lazily
         *   - BEST for Map<K, List<V>> grouping
         *
         * computeIfPresent(k, (k, v) -> newV)
         *   - Runs only if key exists
         *   - Can update OR remove (return null)
         *
         * getOrDefault(k, default)
         *   - Read-only fallback
         *
         * Classic pattern:
         * map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
         */
    }


    // ══════════════════════════════════════════════════════════
    // 12. NULL HANDLING UTILITIES (Java 9+, good to know)
    //     NOTE: Objects.requireNonNullElse is a Java 9 feature.
    //     Included here as a best practice comparison.
    // ══════════════════════════════════════════════════════════
    static void nullHandlingUtilityDemo() {
        System.out.println("\n=== 12. NULL HANDLING UTILITIES (Java 9+) ===");

        String value = null;

        // BEFORE (Java 8) — ternary null check
        String before = value != null ? value : "default";

        // AFTER (Java 9+) — expressive utility methods
        String afterElse    = Objects.requireNonNullElse(value, "default");
        String afterElseGet = Objects.requireNonNullElseGet(value, () -> "computed default");

        System.out.println("ternary:               " + before);
        System.out.println("requireNonNullElse:    " + afterElse);
        System.out.println("requireNonNullElseGet: " + afterElseGet);

        // Objects.requireNonNull — throws NPE with clear message (use in constructors)
        try {
            String name = null;
            Objects.requireNonNull(name, "name must not be null");
        } catch (NullPointerException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Objects.isNull / nonNull — cleaner in streams and lambdas
        System.out.println("isNull:  " + Objects.isNull(value));   // true
        System.out.println("nonNull: " + Objects.nonNull(value));  // false

        /*
         * TIPS:
         *  Q: requireNonNullElse vs requireNonNullElseGet?
         *  A: requireNonNullElse(v, "x")       — "x" always evaluated
         *     requireNonNullElseGet(v, () -> x) — lambda only runs if v is null (lazy)
         *
         *  Q: When to use Objects.requireNonNull()?
         *  A: At the start of constructors/methods to fail fast with a clear message
         *     instead of getting a cryptic NPE deep inside the code.
         */
    }


    // ══════════════════════════════════════════════════════════
    // QUICK REFERENCE
    // ══════════════════════════════════════════════════════════
    /*
     *  Q: What is a Record?
     *  A: Immutable data class. Auto-generates constructor, accessors (no "get"),
     *     equals, hashCode, toString. Cannot extend classes. Can implement interfaces.
     *
     *  Q: What are Sealed Classes?
     *  A: Restricts who can extend a class to a fixed permitted set.
     *     Each permitted class must be: final, sealed, or non-sealed.
     *
     *  Q: Switch statement vs Switch expression?
     *  A: Statement = no return value, fall-through, needs break.
     *     Expression = returns value, arrow (->), no fall-through, uses yield in blocks.
     *
     *  Q: What is pattern matching instanceof?
     *  A: Combines type check + cast into one step.
     *     if (obj instanceof String s) — no explicit cast needed.
     *
     *  Q: Is var dynamic typing?
     *  A: No. Compile-time only. Cannot use for params, fields, or null init.
     *
     *  Q: What improved in NullPointerException?
     *  A: Now tells you WHAT was null and WHAT you tried to call on it.
     *
     *  Q: What types can switch on in Java 17?
     *  A: int, String, enum. NOT Object/types — that requires Java 21.
     *
     *  Q: orElse() vs orElseGet() in Optional?
     *  A: orElse always evaluates the fallback. orElseGet is lazy — preferred.
     *
     *  Q: List.of() vs Collections.unmodifiableList()?
     *  A: List.of() is truly immutable from creation. unmodifiableList wraps
     *     an existing list — the original can still be mutated underneath.
     *
     *  Q: computeIfAbsent vs putIfAbsent?
     *  A: putIfAbsent always creates the value. computeIfAbsent is lazy — preferred.
     */
}

/*
/*
 * ============================================================
 *  JAVA 17 — MAJOR ENHANCEMENTS (Compared to Java 11)
 * ============================================================
 *
 * Java 17 is an LTS release and is the most common upgrade
 * target from Java 8 / Java 11 in enterprise projects.
 *
 * ------------------------------------------------------------
 * 1. LANGUAGE ENHANCEMENTS
 * ------------------------------------------------------------
 *
 * ▶ Records
 *   - Introduced immutable data carriers with minimal boilerplate
 *   - Auto-generates constructor, accessors, equals, hashCode, toString
 *   - All components are implicitly private final
 *   - Records are implicitly final and extend java.lang.Record
 *   - Best for DTOs, value objects, API models
 *
 * ▶ Sealed Classes & Interfaces
 *   - Allows restricting which classes/interfaces can extend or implement a type
 *   - Improves domain modeling and exhaustiveness checks
 *   - Permitted subclasses must be final, sealed, or non-sealed
 *
 * ▶ Pattern Matching for instanceof
 *   - Combines type check and cast into one step
 *   - Eliminates redundant casts and ClassCastException risk
 *     Example: if (obj instanceof String s) { ... }
 *
 * ▶ Switch Expressions
 *   - switch can now return values
 *   - Arrow syntax (->) prevents fall-through bugs
 *   - Supports multi-label cases and yield keyword
 *   - Compiler enforces exhaustiveness for enums
 *
 * ▶ Text Blocks
 *   - Multi-line string literals using triple quotes (""")
 *   - Greatly improves readability for JSON, SQL, XML, HTML
 *
 * ▶ Local Variable Type Inference (var)
 *   - Reduces verbosity for local variables
 *   - Compile-time type inference (NOT dynamic typing)
 *   - Not allowed for fields, method params, or return types
 *
 * ------------------------------------------------------------
 * 2. JVM & RUNTIME IMPROVEMENTS
 * ------------------------------------------------------------
 *
 * ▶ Helpful NullPointerExceptions
 *   - JVM now tells exactly which variable or method call was null
 *   - No code changes required
 *   - Huge debugging productivity improvement
 *
 * ▶ Strong Encapsulation of JDK Internals
 *   - Internal JDK APIs are strongly encapsulated
 *   - Illegal reflective access is no longer allowed by default
 *   - Encourages use of supported public APIs
 *
 * ▶ Improved Garbage Collectors
 *   - G1 GC is the default and more optimized
 *   - ZGC and Shenandoah are production-ready (low-latency)
 *
 * ------------------------------------------------------------
 * 3. API ENHANCEMENTS
 * ------------------------------------------------------------
 *
 * ▶ Stream API improvements
 *   - toList() produces an unmodifiable list
 *   - Better performance and clarity
 *
 * ▶ Optional API enhancements
 *   - ifPresentOrElse()
 *   - or()
 *   - stream()
 *
 * ▶ Collection Factory Methods (since Java 9, widely used in 17)
 *   - List.of(), Set.of(), Map.of()
 *   - Create immutable collections easily
 *
 * ------------------------------------------------------------
 * 4. SECURITY & MAINTENANCE
 * ------------------------------------------------------------
 *
 * ▶ Security Defaults Improved
 *   - Stronger TLS defaults
 *   - More secure cryptographic algorithms
 *
 * ▶ Deprecated / Removed Features
 *   - Applets removed
 *   - Security Manager deprecated
 *   - RMI Activation removed
 *
 * ------------------------------------------------------------
 * 5. WHY JAVA 17 MATTERS
 * ------------------------------------------------------------
 *
 * ▶ Long-Term Support (LTS)
 * ▶ Massive reduction in boilerplate code
 * ▶ Safer, more expressive language constructs
 * ▶ Better performance and debugging
 * ▶ Industry standard baseline for modern Java
 *
 * ------------------------------------------------------------
 * INTERVIEW ONE-LINER:
 *
 * "Java 17 introduced records, sealed classes, pattern matching for instanceof,
 *  switch expressions, text blocks, and major JVM improvements, making Java
 *  more expressive, safer, and easier to maintain compared to Java 11."
 *
 * ============================================================
 */