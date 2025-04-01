package com.notes.equalsHashcode;

import java.util.HashMap;
import java.util.Map;

public class StudentExample {
	
    public static void main(String[] args) {
    	
        // Create a map to store student grades
        Map<Student, String> grades = new HashMap<>();
        
        // Add a student
        Student alex1 = new Student(1001, "Alex", "Computer Science");
        grades.put(alex1, "A");
        
        // Create another student object with the same ID
        Student alex2 = new Student(1001, "Alex Johnson", "Computer Science");
        
        // Check if the student exists in our map
        System.out.println("Contains alex2: " + grades.containsKey(alex2));  // Will print true
        System.out.println("Grade for alex2: " + grades.get(alex2));         // Will print "A"
    }
}

/**
 * 
 * Purpose of hashCode and equals in Java:
 * The hashCode and equals methods serve critical purposes in Java, especially when working with collections:
 * 
 * Primary Purposes:
 * 1. Object Comparison: equals determines if two objects should be considered logically equivalent.
 * 2. Hash-Based Collections: hashCode enables efficient object storage and retrieval in collections like HashMap, HashSet, and Hashtable.
 * 
 * How They Work Together:
 * When you use hash-based collections, Java uses a two-step process:
 * 1. First, it uses hashCode to quickly find the right "bucket" to look in
 * 2. Then, it uses equals to find the exact object in that bucket
 * 
 * This makes lookups very efficient (typically O(1) time complexity).
 * 
 * 
 * Example: Student Management System
 * Let's implement a system where we consider two students equal if they have the same ID:
 * 
 * What Happens Without Proper Implementation
 * If we didn't override these methods:
 * 
 * 1. Without overriding equals: equals would use the default Object implementation (memory address comparison), so alex1.equals(alex2) would return false even though they represent the same student.
 * 2. Without overriding hashCode: The students might generate different hash codes even if equal, which would put them in different buckets in the HashMap. This would break the contract that equal objects must have equal hash codes.
 * 3. Breaking the contract: If we override only one method but not the other, we'd have inconsistent behavior in hash-based collections - objects might be equal but stored in different buckets, or vice versa.
 * 
 * This example demonstrates why proper implementation of both methods is critical when your objects need to be used as keys in collections or compared for logical equality.
 */