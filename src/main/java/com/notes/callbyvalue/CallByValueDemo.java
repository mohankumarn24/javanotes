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
		int x = 10;
		modifyPrimitives(x);
		System.out.println(x); // Output: 10
		// ✔️ x is not changed in the main method because only a copy of x is modified.
		
		String str = "Original string";
		modifyString(str);
		System.out.println(str);
		
		// 2.a. Call by Value (Object References):
		Person person1 = new Person("Alice");
		modifyObject(person1);
		System.out.println(person1.getName()); // Output: Bob
		// ✔️ Object content is changed because both the method and caller share the same object reference.
				
		// 2.b. Call by Value (Object References):
        Person person2 = new Person("Alice");
        reassignObject(person2);
        System.out.println(person2.getName()); // Output: Alice
        // ❌ The original reference p in main() is unchanged because the method only changed its copy of the reference.
        
        // 3. collections
        System.out.println();
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Alice"));
        modifyCollections(persons);
        persons.forEach(person -> System.out.println(person.toString()));
        
	}

	static void modifyPrimitives(int num) {
		num = 20; // This only modifies the local copy
	}
	
	static void modifyString(String str) {
		str = "Modified string!!"; // This only modifies the local copy
	}
	
	static void modifyObject(Person person) {
		person.setName("Bob"); // Modifies the object that the reference points to
	}
	
    static void reassignObject(Person person) {
        person = new Person(); // Reassigning reference (has no effect outside)
        person.setName("Bob");
    }
    
    static void modifyCollections(List<Person> persons) {
    	persons.add(new Person("Bob"));
    }
}

/*
10
Original string
Bob
Alice

Person [name=Alice]
Person [name=Bob]
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
