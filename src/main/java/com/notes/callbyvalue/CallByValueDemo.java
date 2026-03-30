package com.notes.callbyvalue;

import java.util.ArrayList;
import java.util.List;

public class CallByValueDemo {
	
	private static class Person {
		
		private String name;

		public Person() {
		}

		public Person(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + "]";
		}
	}
	
	public static void main(String[] args) {
		
		// 1. Call by Value in Java (for Primitives):
		// NOT MODIFIED
		int x = 10;
		modifyPrimitives(x);
		System.out.println(x); 												// Output: 10
		// ✔️ x is not changed in the main method because only a copy of x is modified.
		
		// 2. string
		// NOT MODIFIED
		String str = "hello";
		modifyString(str);
		System.out.println(str);											// Output: "hello"
		
		// 3.a. Call by Value (Object References):
		// MODIFIED
		Person person1 = new Person("Alice");
		modifyObject(person1);
		System.out.println(person1.getName()); 								// Output: "Alice Modified"
		// ✔️ Object content is changed because both the method and caller share the same object reference.
				
		// 3.b. Call by Value (Object References):
		// NOT MODIFIED
        Person person2 = new Person("Bob");
        reassignObject(person2);
        System.out.println(person2.getName()); 								// Output: "Bob"
        // ❌ The original reference p in main() is unchanged because the method only changed its copy of the reference.
        
        // 4. collections
        // MODIFIED
        System.out.println();
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Alice"));
        modifyCollections(persons);
        persons.forEach(person -> System.out.println(person.toString()));	// Person [name=Alice]
        																	// Person [name=Bob new]   
		// 5. string array
		// MODIFIED
		// String[] strArray = {"Delhi", "Mumbai", "Bombay", "Goa"};	  	// same as below
		System.out.println();
		String[] strArray = new String[] {"Delhi", "Mumbai", "Bombay", "Goa"};
		modifyStringArray(strArray);
		System.out.println(String.join(", ", strArray));					// Output: "Delhi Modified, Mumbai, Bombay, Goa"
	}

	static void modifyPrimitives(int num) {
		num = 100; 															// This only modifies the local copy
	}
	
	static void modifyString(String str) {
		str = "hello modified"; 											// This only modifies the local copy
	}
	
	static void modifyObject(Person person) {
		person.setName("Alice Modified");									// MODIFIES the object that the reference points to
	}
	
    static void reassignObject(Person person) {
        person = new Person(); 												// Reassigning reference (has no effect outside)
        person.setName("Bob Modified");
    }
    
    static void modifyCollections(List<Person> persons) {
    	persons.add(new Person("Bob new"));									// MODIFIES collection
    }
    
	static void modifyStringArray(String[] strArray) {
		strArray[0] = "Delhi Modified"; 									// MODIFIES the string index value
	}
}

/*
10
hello
Alice Modified
Bob

Person [name=Alice]
Person [name=Bob new]

Delhi Modified, Mumbai, Bombay, Goa

 */


/* 
 * In Java, all method calls are "call by value", but this often leads to confusion because of how object references work.
 * 
 * 1. Call by Value in Java (for Primitives):
 *  	- When you pass primitive data types (like int, double, char, etc.) or String to a method, Java passes a copy of the value, not the actual variable.
 *  	- ✔️ x is not changed in the main method because only a copy of x is modified.
 *  
 * 2. Call by Value (Object References):
 *  	- When you pass an object to a method, Java still passes the value of the reference (i.e., a copy of the reference), not the actual object itself.
 *  	- So, 
 *  		-- You can modify the contents of the object via the reference.
 *  		-- But if you reassign the object inside the method, the change won’t reflect outside.
 * 
 * */

/*
| Type      | Call Type                    | Can modify original?                 | Explanation                                                                        |
| --------- | ---------------------------- | -----------------------------------   | ---------------------------------------------------------------------------------- |
| Primitive | Call by Value                | ❌ No                                 | Only a copy of the value is passed                                                 |
| Object    | Call by Value (of reference) | ✅ Yes (contents) ❌ No (reassigning) | You can modify the object’s internal state but not reassign the original reference |
*/
