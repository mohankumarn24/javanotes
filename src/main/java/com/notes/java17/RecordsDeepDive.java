package com.notes.java17;

/**
 * ============================================================
 *  RECORDS DEEP DIVE — Java 17
 *  Covers: access modifiers, equals/hashCode, final, extend,
 *          override, overload, constructors, static, interfaces
 * ============================================================
 */
public class RecordsDeepDive {

    public static void main(String[] args) {
        accessModifiersDemo();
        overrideEqualsHashCodeDemo();
        overrideToStringDemo();
        overrideAccessorDemo();
        overloadMethodsDemo();
        constructorsDemo();
        finalAndExtendDemo();
        implementsInterfaceDemo();
        staticFieldsMethodsDemo();
        instanceFieldsDemo();
    }


    // ══════════════════════════════════════════════════════════
    // SUPPORTING TYPES (declared outside methods — top level inner)
    // ══════════════════════════════════════════════════════════

    // Basic record for reference
    record Person(String name, int age) {}

    // Record with custom equals/hashCode
    record PersonCustomEquals(String name, int age) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PersonCustomEquals p)) return false;
            return this.name.equalsIgnoreCase(p.name); // only name, case-insensitive
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(name.toLowerCase()); // must match equals fields
        }
    }

    // Record with custom toString
    record PersonCustomToString(String name, int age) {
        @Override
        public String toString() {
            return name + " (age " + age + ")"; // instead of PersonCustomToString[name=..., age=...]
        }
    }

    // Record with overridden accessor
    record PersonUpperName(String name, int age) {
        @Override
        public String name() {
            return name.toUpperCase(); // always return uppercase
        }
    }

    // Record with overloaded methods
    record PersonWithMethods(String name, int age) {
        String greeting() {
            return "Hi, I'm " + name;
        }

        String greeting(String prefix) {       // overloaded — different params
            return prefix + ", I'm " + name;
        }

        boolean isAdult() {
            return age >= 18;
        }
    }

    // Record with all constructor types
    record PersonConstructors(String name, int age) {

        // 1. Compact constructor — no param list, runs BEFORE field assignment
        //    Use for: validation, normalisation
        PersonConstructors {
            if (age < 0) throw new IllegalArgumentException("Age cannot be negative: " + age);
            name = name.trim(); // can modify params here — they get assigned after this block
        }

        // 2. Overloaded constructor — MUST call canonical constructor via this(...)
        PersonConstructors(String name) {
            this(name, 0); // delegate to canonical constructor (name, age)
        }
    }

    // Interface for record to implement
    interface Printable {
        void print();
    }

    // Record implementing interface
    record Dog(String name) implements Printable {
        @Override
        public void print() {
            System.out.println("Dog: " + name);
        }
    }

    // Record with static fields and methods
    record PersonWithStatic(String name, int age) {
        static final String SPECIES = "Human";   // ✅ static final constant — allowed

        static PersonWithStatic unknown() {       // ✅ static factory method — allowed
            return new PersonWithStatic("Unknown", -1);
        }
    }


    // ══════════════════════════════════════════════════════════
    // 1. ACCESS MODIFIERS
    // ══════════════════════════════════════════════════════════
    static void accessModifiersDemo() {
        System.out.println("\n=== 1. ACCESS MODIFIERS ===");

        // Components are ALWAYS private final — you cannot change this
        //   record Person(private String name) {}  <- COMPILE ERROR
        //   record Person(public String name)  {}  <- COMPILE ERROR
        // The component is always: private final String name;

        // Accessors are ALWAYS public — you cannot change this
        //   They are auto-generated as: public String name() { return name; }

        Person p = new Person("Alice", 30);
        System.out.println(p.name()); // public accessor — always
        System.out.println(p.age());  // public accessor — always

        // The record CLASS itself can have access modifiers
        //   public record Person(...)    <- visible everywhere
        //   (package-private) record Person(...) <- visible in same package
        //   private record Person(...)   <- only inside enclosing class

        System.out.println("Components: always private final");
        System.out.println("Accessors:  always public");

        /*
         * TIPS:
         *  Q: Can I make a record field public?
         *  A: No. Record components are always private final. Period.
         *
         *  Q: Can I make an accessor private?
         *  A: No. Auto-generated accessors are always public.
         *     You CAN override the accessor method but its signature stays public.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 2. OVERRIDING equals() AND hashCode()
    // ══════════════════════════════════════════════════════════
    static void overrideEqualsHashCodeDemo() {
        System.out.println("\n=== 2. OVERRIDING equals() AND hashCode() ===");

        // Default record behaviour — compares ALL fields
        Person p1 = new Person("Alice", 30);
        Person p2 = new Person("Alice", 30);
        Person p3 = new Person("Alice", 99); // different age
        System.out.println("Default equals (same fields):     " + p1.equals(p2)); // true
        System.out.println("Default equals (different age):   " + p1.equals(p3)); // false

        // Custom equals — only compares name (case-insensitive), ignores age
        PersonCustomEquals c1 = new PersonCustomEquals("alice", 30);
        PersonCustomEquals c2 = new PersonCustomEquals("ALICE", 99); // different age, different case
        System.out.println("Custom equals (name only):        " + c1.equals(c2)); // true

        // Proving hashCode contract is maintained
        System.out.println("Custom hashCode same:             " + (c1.hashCode() == c2.hashCode())); // true

        // Works correctly in HashMap/HashSet because contract is maintained
        java.util.Set<PersonCustomEquals> set = new java.util.HashSet<>();
        set.add(c1);
        System.out.println("Set contains ALICE (diff case):   " + set.contains(c2)); // true

        /*
         * TIPS:
         *  - Default record equals() compares ALL components — field by field
         *  - Override when you need partial equality or case-insensitive comparison
         *  - GOLDEN RULE: if you override equals(), you MUST override hashCode()
         *  - Both must use the SAME fields — else HashMap/HashSet breaks
         *
         *  Q: What does default record equals() do?
         *  A: Compares all components using Objects.equals() for objects, == for primitives.
         *
         *  Q: When would you override equals() in a record?
         *  A: When equality should ignore some fields, or do case-insensitive comparison, etc.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 3. OVERRIDING toString()
    // ══════════════════════════════════════════════════════════
    static void overrideToStringDemo() {
        System.out.println("\n=== 3. OVERRIDING toString() ===");

        Person p = new Person("Alice", 30);
        System.out.println("Default toString:  " + p);
        // Output: Person[name=Alice, age=30]

        PersonCustomToString pc = new PersonCustomToString("Alice", 30);
        System.out.println("Custom toString:   " + pc);
        // Output: Alice (age 30)

        /*
         * TIPS:
         *  - Default record toString() prints: RecordName[field1=val1, field2=val2]
         *  - Override when you need a specific format (e.g. for logging, display)
         */
    }


    // ══════════════════════════════════════════════════════════
    // 4. OVERRIDING AUTO-GENERATED ACCESSOR
    // ══════════════════════════════════════════════════════════
    static void overrideAccessorDemo() {
        System.out.println("\n=== 4. OVERRIDING ACCESSOR ===");

        // Default accessor — returns value as-is
        Person p = new Person("alice", 30);
        System.out.println("Default accessor: " + p.name()); // alice

        // Overridden accessor — adds logic
        PersonUpperName pu = new PersonUpperName("alice", 30);
        System.out.println("Custom accessor:  " + pu.name()); // ALICE

        /*
         * TIPS:
         *  - Auto-generated accessor: public String name() { return name; }
         *  - You can override it to add formatting, masking, computation, etc.
         *  - But you CANNOT change the return type or make it non-public
         *
         *  Q: Can I rename the accessor?
         *  A: No. The accessor name is always the same as the component name.
         *     You can ADD extra methods with different names.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 5. OVERLOADING METHODS
    // ══════════════════════════════════════════════════════════
    static void overloadMethodsDemo() {
        System.out.println("\n=== 5. OVERLOADING METHODS ===");

        PersonWithMethods p = new PersonWithMethods("Alice", 30);

        System.out.println(p.greeting());           // Hi, I'm Alice
        System.out.println(p.greeting("Hey"));      // Hey, I'm Alice
        System.out.println(p.greeting("Good day")); // Good day, I'm Alice
        System.out.println(p.isAdult());             // true

        /*
         * TIPS:
         *  - Records can have ANY number of custom instance methods
         *  - Overloading (same name, different params) works normally
         *  - You just CANNOT add extra instance fields
         */
    }


    // ══════════════════════════════════════════════════════════
    // 6. CONSTRUCTORS IN RECORDS
    // ══════════════════════════════════════════════════════════
    static void constructorsDemo() {
        System.out.println("\n=== 6. CONSTRUCTORS ===");

        // 1. Canonical constructor — auto-generated, takes all components
        PersonConstructors p1 = new PersonConstructors("  Alice  ", 30);
        System.out.println("Canonical (trimmed): '" + p1.name() + "'"); // 'Alice' — trimmed by compact constructor

        // 2. Compact constructor — validation/normalisation runs
        try {
            PersonConstructors bad = new PersonConstructors("Bob", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Compact validation caught: " + e.getMessage());
        }

        // 3. Overloaded constructor — calls canonical via this(...)
        PersonConstructors p2 = new PersonConstructors("Charlie");
        System.out.println("Overloaded (age defaults to 0): " + p2.age());

        /*
         * TIPS:
         *
         *  Three types of constructors in a record:
         *
         *  1. CANONICAL  — auto-generated, matches all components
         *                  You rarely write it unless you need full control
         *
         *  2. COMPACT    — no parameter list, runs BEFORE field assignment
         *                  PersonConstructors { ... }  <- no () at all
         *                  Can modify params (name = name.trim())
         *                  Best for: validation, normalisation
         *
         *  3. OVERLOADED — different params, MUST call this(...) first
         *                  Cannot do any work before calling this(...)
         *
         *  Q: What is a compact constructor?
         *  A: A constructor with no parameter list. It runs before the auto-generated
         *     field assignments. You can validate or normalise the incoming values.
         *
         *  Q: Can overloaded constructor do work before calling this(...)?
         *  A: No. this(...) must be the FIRST statement — same rule as regular classes.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 7. FINAL AND EXTENDING RECORDS
    // ══════════════════════════════════════════════════════════
    static void finalAndExtendDemo() {
        System.out.println("\n=== 7. FINAL AND EXTENDING RECORDS ===");

        // Records are IMPLICITLY final
        // Writing 'final' is allowed but redundant:
        //   final record Person(String name, int age) {}  <- OK but unnecessary

        // You CANNOT extend a record:
        //   class Employee extends Person {}  <- COMPILE ERROR
        //   record Manager extends Person {}  <- COMPILE ERROR

        // You CANNOT have one record extend another:
        //   record Manager(String name, int age, String dept) extends Person(name, age) {} <- COMPILE ERROR

        // Records implicitly extend java.lang.Record — no room left for another parent

        System.out.println("Records are implicitly final — cannot be subclassed.");
        System.out.println("Records implicitly extend java.lang.Record.");
        System.out.println("You cannot extend another class from a record.");

        /*
         * TIPS:
         *  Q: Can a record extend another class?
         *  A: No. Records implicitly extend java.lang.Record. Java has single inheritance,
         *     so there is no room to extend anything else.
         *
         *  Q: Can a record extend another record?
         *  A: No. Records are final — they cannot be extended by anyone.
         *
         *  Q: Can you make a record abstract?
         *  A: No. Records are final — abstract and final contradict each other.
         *
         *  Q: Is writing 'final record' valid?
         *  A: Yes, it compiles, but it is redundant since records are already final.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 8. IMPLEMENTING INTERFACES
    // ══════════════════════════════════════════════════════════
    static void implementsInterfaceDemo() {
        System.out.println("\n=== 8. IMPLEMENTING INTERFACES ===");

        // Records CANNOT extend classes — but CAN implement interfaces
        Dog dog = new Dog("Rex");
        dog.print();                              // Dog: Rex

        // Useful pattern — interface gives the "type", record gives the "data"
        Printable printable = new Dog("Buddy");
        printable.print();                        // Dog: Buddy

        /*
         * TIPS:
         *  - Records can implement multiple interfaces (same as any class)
         *  - This is the main way to add polymorphism to records
         *  - Common use: implement Comparable, Serializable, custom interfaces
         *
         *  Q: Can a record implement Serializable?
         *  A: Yes. record Person(String name, int age) implements Serializable {}
         *     Note: serialVersionUID is not required but recommended.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 9. STATIC FIELDS AND METHODS
    // ══════════════════════════════════════════════════════════
    static void staticFieldsMethodsDemo() {
        System.out.println("\n=== 9. STATIC FIELDS AND METHODS ===");

        // Static constants — allowed
        System.out.println("Species: " + PersonWithStatic.SPECIES); // Human

        // Static factory method — allowed and common pattern
        PersonWithStatic unknown = PersonWithStatic.unknown();
        System.out.println("Unknown: " + unknown); // PersonWithStatic[name=Unknown, age=-1]

        /*
         * TIPS:
         *  - static final fields (constants) are allowed in records
         *  - Non-final static fields are NOT allowed:
         *      static int count = 0;  <- COMPILE ERROR
         *  - Static methods are allowed — great for factory methods
         *
         *  Q: Can records have mutable static state?
         *  A: No. Only static final (constants) are allowed.
         */
    }


    // ══════════════════════════════════════════════════════════
    // 10. EXTRA INSTANCE FIELDS — NOT ALLOWED
    // ══════════════════════════════════════════════════════════
    static void instanceFieldsDemo() {
        System.out.println("\n=== 10. EXTRA INSTANCE FIELDS ===");

        // This is NOT allowed:
        //   record Person(String name, int age) {
        //       private String nickname;   <- COMPILE ERROR
        //       int score;                 <- COMPILE ERROR
        //   }
        // ALL state in a record must come from its components.
        // This enforces immutability and keeps records as pure data carriers.

        System.out.println("Extra instance fields are NOT allowed in records.");
        System.out.println("All state must be declared as record components.");

        /*
         * TIPS:
         *  Q: Why can't records have extra instance fields?
         *  A: Records are designed to be transparent, immutable data carriers.
         *     All state must be visible in the component list.
         *     Hidden fields would break this contract.
         *
         *  Q: What if I need extra computed state?
         *  A: Add a method that computes it on the fly:
         *       record Circle(double radius) {
         *           double area() { return Math.PI * radius * radius; }
         *       }
         *     Or use a static final field (constant, not per-instance).
         */
    }


    // ══════════════════════════════════════════════════════════
    // COMPLETE SUMMARY
    // ══════════════════════════════════════════════════════════
    /*
     * +------------------------------+-----------+---------------------------+
     * | Feature                      | Allowed?  | Notes                     |
     * +------------------------------+-----------+---------------------------+
     * | Access modifier on component | NO        | Always private final      |
     * | Access modifier on accessor  | NO        | Always public             |
     * | Access modifier on record    | YES       | public/package/private    |
     * | Override equals()            | YES       | Default compares ALL      |
     * | Override hashCode()          | YES       | Must match equals fields  |
     * | Override toString()          | YES       | Default: Name[f1=v1, ...] |
     * | Override accessor            | YES       | Must keep public + type   |
     * | Add custom methods           | YES       | Any number                |
     * | Overload methods             | YES       | Normal rules apply        |
     * | Compact constructor          | YES       | Validation/normalisation  |
     * | Overloaded constructor       | YES       | Must call this(...) first |
     * | Is it final?                 | YES       | Implicitly, always        |
     * | Extend a class               | NO        | Implicitly extends Record |
     * | Extend another record        | NO        | Records are final         |
     * | Implement interfaces         | YES       | Multiple allowed          |
     * | Static final fields          | YES       | Constants only            |
     * | Mutable static fields        | NO        | Not allowed               |
     * | Static methods               | YES       | Factory methods, utils    |
     * | Extra instance fields        | NO        | All state in components   |
     * +------------------------------+-----------+---------------------------+
     *
     * NOTE:
     * "A record is implicitly final, implicitly extends java.lang.Record,
     *  all components are private final, all accessors are public.
     *  You can override auto-generated methods, add custom methods,
     *  implement interfaces, and use static constants/methods.
     *  You cannot extend other classes, add instance fields, or subclass a record."
     */
}
