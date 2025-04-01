package com.notes.equalsHashcode;

import java.util.HashMap;
import java.util.Map;

/*
 * Understanding the Basics
 * In Java, hashCode() and equals() are methods defined in the Object class that are crucial for object comparison and hashing operations, 
 * especially when using collections like HashMap, HashSet, etc.
 * 
 * equals():
 * - Used to compare two objects for equality
 * - Default implementation in Object class compares memory addresses (same as ==)
 * - Should be overridden to provide meaningful equality comparison
 * 
 * hashCode():
 *  - Returns an integer representation of the object
 *  - Used by hash-based collections to determine bucket location
 *  - Must follow the contract: if two objects are equal, they must have the same hash code
 * 
 * The Contract Between equals() and hashCode():
 *  - Consistency: Multiple invocations of hashCode() should return the same value if object state doesn't change
 *  - Equality implication: If a.equals(b) is true, then a.hashCode() == b.hashCode()
 *  - Non-equality: If a.equals(b) is false, a.hashCode() doesn't have to be different from b.hashCode(), but it's better for performance if they are different
 *  
 *  Best Practices:
 *  - Always override both equals() and hashCode() together
 *  - Use the same set of fields in both methods
 *  - For immutable objects, consider caching the hash code
 *  - Use utility methods like Objects.equals() and Objects.hash() for cleaner code
 *  - Make sure your equality comparison is consistent with business requirements
 *  
 *  Common Pitfalls:
 *  - Forgetting to override hashCode() when overriding equals()
 *  - Using mutable fields in hash code computation (can cause problems in collections)
 *  - Not maintaining the contract between the two methods
 *  - Inconsistent equality comparison (e.g., case-sensitive vs case-insensitive)
*/

public class EmployeeExample {
	
    public static void main(String[] args) {
    	
        Employee emp1 = new Employee(1, "John Doe", "Engineering");
        Employee emp2 = new Employee(1, "John Doe", "Engineering");
        Employee emp3 = new Employee(2, "Jane Smith", "Marketing");
        
        // Testing equals()
        System.out.println("emp1 equals emp2: " + emp1.equals(emp2)); // true if equals is overrriden
        System.out.println("emp1 equals emp3: " + emp1.equals(emp3)); // false
        
        // Testing hashCode()
        System.out.println("emp1 hashCode: " + emp1.hashCode());
        System.out.println("emp2 hashCode: " + emp2.hashCode());
        System.out.println("emp3 hashCode: " + emp3.hashCode());
        
        // HashMap example
        Map<Employee, String> employeeMap = new HashMap<>();
        employeeMap.put(emp1, "Project A");
        
        System.out.println("Get emp2's project: " + employeeMap.get(emp2)); // "Project A"
        System.out.println("Get emp3's project: " + employeeMap.get(emp3)); // null
    }
}