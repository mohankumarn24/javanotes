package com.notes.misc;

class A {
	
	static void show() {
		System.out.println("A static method");
	}

	void display() {
		System.out.println("A instance method");
	}
}

class B extends A {
	
	/**
	 * Method Hiding
	 *  - Method hiding occurs when a subclass declares a static method with the same signature as a static method in its superclass.
	 *  - The child method does not override the parent method. Instead, it hides it.
	 *  - Why "hiding"?
	 *      Because the subclass defines another static method with the same name and signature. 
	 *      Which method is called depends on the reference type or class name used at compile time.
	 *
	 *  - Both methods exist independently:
	 *      A.show(); // calls A's method
	 *      B.show(); // calls B's method
	 *
	 *  - Static methods belong to the class, not the object.
	 *    Therefore, they do not participate in runtime polymorphism.
	 *
	 *  - We cannot add @Override annotation to this method.
	 *    CTE: "The method show() of type B must override or implement a supertype method"
	 */
	// @Override
	static void show() { // Method Hiding
		System.out.println("B static method");
	}

	@Override
	void display() { // Method Overriding
		System.out.println("B instance method");
	}
}

public class StaticMethodHidingExample {

	public static void main(String[] args) {

		A a = new B();

		// Warning: "The static method show() from the type A should be accessed in a static way"
		// Calls A.show() because static methods are resolved using the reference type (A)
		a.show();			// A static method
		a.display();		// B instance method

		A.show();			// A static method
		B.show();			// B static method
	}
}

/*
A static method
B instance method
A static method
B static method
*/