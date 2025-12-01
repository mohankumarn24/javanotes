package com.notes.comparable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
@FunctionalInterface
public interface Comparable<T> {
	public int compareTo(T o);
}

@FunctionalInterface
public interface Comparator<T> {
	int compare(T o1, T o2);
}
*/

// Natural sorting
class Student implements Comparable<Student> {
    int id;
    String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Student other) {
        // return this.id > other.id ? 1 : -1;
    	return this.id - other.id;  						// ascending order
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

// Custom sorting
// lambda:  Comparator<String> comparator = (s1, s2) -> s1.compareTo(s2);
class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return a.name.compareTo(b.name);
    }
}

// Comparator class for ID Descending
class IdDescComparator implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return Integer.compare(b.id, a.id);  // reverse
    }
}

// Test
public class ComparableExample {
    public static void main(String[] args) {
        List<Student> list = Arrays.asList(
                new Student(3, "Mohan"),
                new Student(1, "Rahul"),
                new Student(2, "Arjun")
        );

        // 1. Comparable (Natural Sorting)
        System.out.println("Comparable − natural sorting (by ID ascending)");
        Collections.sort(list);						// uses compareTo()
        System.out.println(list);

        // 2. Comparator using separate class
        System.out.println("\nComparator − Name Ascending (using Comparator class)");
        Collections.sort(list, new NameComparator());
        System.out.println(list);

        // 3. Comparator using separate class (ID desc)
        System.out.println("\nComparator − ID Desc (using Comparator class)");
        Collections.sort(list, new IdDescComparator());
        System.out.println(list);

        // 4. Comparator using Lambda
        System.out.println("\nComparator − Name Ascending (Lambda)");
        Collections.sort(list, (a, b) -> a.name.compareTo(b.name));
        System.out.println(list);

        // 5. Comparator using Lambda (ID descending)
        System.out.println("\nComparator − ID Desc (Lambda)");
        Collections.sort(list, (a, b) -> b.id - a.id);
        System.out.println(list);
        
        // 6a. Comparator using Lambda (ID ascending)
        System.out.println("\nComparator − ID Desc (Lambda)");
        Collections.sort(list, (a, b) -> a.id > b.id ? 1 : -1);
        System.out.println(list);

        // 6b. Comparator using Lambda (ID descending)
        System.out.println("\nComparator − ID Desc (Lambda)");
        Collections.sort(list, (a, b) -> b.id > a.id ? 1 : -1);
        System.out.println(list);
    }
}

/*
When to use what?
Use Comparable:
 - When the class has a default natural order
   Example: String, Integer, LocalDate already implement Comparable.

Use Comparator:
 - When you want multiple sorting options, custom sorting
 - When you cannot modify the class (e.g., class from 3rd party library)
 - When you want clean separation between object structure and sorting logic
*/