package com.notes.java17;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/*
class Student {
	
	private final int id;
	private final String name;
	
	public Student(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
}
*/

/*
// OPTION 2: Same as above and equivalent
record Student(int id, String name) {
	
}
*/

/*
 * OPTION 3: RECORD WITH ADVANCED FEATURES
 *
 * Key properties of records:
 * - All components are implicitly: private final
 * - Records are final (cannot extend other classes)
 * - Records can implement interfaces
 * - Records can have:
 *      - instance methods
 *      - static methods
 *      - static variables
 * - No getters with "get" prefix (use id(), name())
 */
record Student(int id, String name) implements Serializable, Comparable<Student> {

     // Serializable is commonly used for DTOs, caching, messaging
     // Records are value-based classes (identity is not important). Added for reference purposes 
	 static final long serialVersionUID = 1L;

	 // Static variables are allowed
	 // Access can be public / private / protected / package-private
	 // However, since records are final, protected doesn’t add much value because they can’t be subclassed
	 private static final int MAX_ALLOWED_ID = 1000;
	 // static int MAX_ALLOWED_ID;          		// ✅ allowed (package-private static, mutable)
	 // static final int MAX_ALLOWED_ID=1;  		// ✅ allowed (package-private static final)
	 // int MAX_ALLOWED_ID;                 		// ❌ not allowed (instance fields forbidden)
	 // String address;   							// ❌ Instance fields are NOT allowed. Must be part of record header if needed

    /*
     * Records do NOT support a traditional default constructor.
     * This would cause a compile-time error:
     *   "A non-canonical constructor must start with an explicit invocation to a constructor"
     *   
     *   Allowed: no-arg constructor that delegates to canonical constructor
     */
    public Student() {
        this(1, "UNKNOWN");
    }

    /*
     * Canonical constructor (explicit form)
     * - Correct but NOT recommended
     * - Requires manual assignment
     *
     * public Student(int id, String name) {
     *     if (id <= 0) throw new IllegalArgumentException("Invalid ID");
     *     if (name == null || name.isBlank()) throw new IllegalArgumentException("Invalid name");
     *     this.id = id;
     *     this.name = name;
     * }
     */

    // Compact canonical constructor (RECOMMENDED). Same as above
    // - No field assignment needed
    // - Best place to enforce invariants
    public Student {
        if (!isValidId(id)) {
            throw new IllegalArgumentException("ID must be positive and <= " + MAX_ALLOWED_ID);
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
    }

    // Records can contain behavior (not just data)

    // Static utility / validation method
    public static boolean isValidId(int id) {
        return id > 0 && id <= MAX_ALLOWED_ID;
    }

    // Instance-level validation
    public boolean isValid() {
        return isValidId(id) && !name.isBlank();
    }

    // Static factory method (preferred over overloaded constructors)
    public static Student of(int id, String name) {
        return new Student(id, name);
    }

    // Overloaded instance methods are allowed
    String greeting() {
        return "Hi, I'm " + name;
    }

    String greeting(String prefix) {
        return prefix + ", I'm " + name;
    }

    /*
     * Records do NOT support setters.
     * Instead, use "with"-style methods that return a new instance.
     */
    public Student withName(String newName) {
        return new Student(this.id, newName);
    }

    /*
     * Copy pattern for records
     * - Prefer explicit copy methods
     * - Records should NOT implement Cloneable
     */
    public Student copy() {
        return new Student(id, name);
    }

	/*
	 * NOTE:
	 * compareTo() should ideally be consistent with equals().
	 * If compareTo() returns 0 but equals() is false,
	 * TreeSet / TreeMap may drop elements unexpectedly.
	 */
	@Override
	public int compareTo(Student other) {
	    int byId = Integer.compare(this.id, other.id);
	    return byId != 0 ? byId : this.name.compareTo(other.name);
	}
	
    /*
     * NOTE:
     * - equals(), hashCode(), toString() are auto-generated
     * - Overriding them is allowed, but discouraged
     * - Records are intended to be transparent, value-based carriers
     */
}

public class RecordsDeepDive {

    public static void main(String[] args) {

        Student st1 = new Student(1, "Sachin");
        Student st2 = new Student(1, "Sachin");

        // Immutability
        // st1.id = 10; // ❌ Compile-time error

        // Accessors (no "get" prefix)
        System.out.println(st1.id());        // 1
        System.out.println(st1.name());      // Sachin

        // toString() auto-generated
        System.out.println(st1);             // Student[id=1, name=Sachin]
        System.out.println(st2);

        // Value-based equality
        System.out.println(st1.equals(st2)); // true

        // Behavior
        System.out.println(st1.isValid());   // true

        // Copy
        Student copy = st1.copy();
        System.out.println(copy);

        // Functional update (no setters)
        Student st3 = st1.withName("Dravid");
        System.out.println(st3);

        // Static factory
        Student st4 = Student.of(1, "Sachin");
        System.out.println(st4);
        
        // ===============================
        // SORTING EXAMPLES
        // ===============================

        var students = java.util.List.of(
                new Student(3, "Virat"),
                new Student(1, "Sachin"),
                new Student(2, "Dravid")
        );

        // Natural ordering — uses compareTo() (Student implements Comparable)
        List<Student> sortedByComparable = students.stream()
                .sorted()   // equivalent to: .sorted((s1, s2) -> s1.compareTo(s2))
                .toList();
        System.out.println(sortedByComparable);
        // [Student[id=1, name=Sachin], Student[id=2, name=Dravid], Student[id=3, name=Virat]]


        // Custom ordering — Comparator with method reference (sort by name)
        List<Student> sortedByName = students.stream()
                .sorted(Comparator.comparing(Student::name)) 	// .sorted(Comparator.comparing(s -> s.name()))
                .toList();
        System.out.println(sortedByName);
        // [Student[id=2, name=Dravid], Student[id=1, name=Sachin], Student[id=3, name=Virat]]   
        
        List<Student> sortedByNameAndId = students.stream()
        		.sorted(Comparator.comparing(Student::name).thenComparingInt(Student::id))
        		.toList();
        System.out.println(sortedByNameAndId);
        // [Student[id=2, name=Dravid], Student[id=1, name=Sachin], Student[id=3, name=Virat]]
    }
}


/*
 * NOTE:
 * Records are SHALLOWLY immutable.
 * If a component is mutable (e.g., List, Map),
 * the record itself remains immutable but its contents can change.
 * 
 * record Student(int id, List<String> subjects) {}
 * student.subjects().add("Math"); // ✅ allowed → breaks deep immutability
 */
