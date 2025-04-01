package com.notes.accessModifiers.package2;

import com.notes.accessModifiers.package1.Alpha;

public class AlphaSub extends Alpha {
	
	public void testAccessModifiers() {
		
		// p = 1;  // default variable
		q = 2;  // public variable
		// r = 3;  // private variable
		s = 4;  // protected variable
		
		// defaultMethod(); 	// default method
		publicMethod(); 	// public method
		// privateMethod();	// private method
		protectedMethod();	// protected method
		
		Alpha alpha = new Alpha();
		// System.out.println(String.format("defualt: %d", alpha.p));
		System.out.println(String.format("public: %d", alpha.q));
		// System.out.println(String.format("private: %d", alpha.r));
		// System.out.println(String.format("protected: %d", alpha.s));
		
		// alpha.defaultMethod();
		alpha.publicMethod();
		// alpha.privateMethod();
		// alpha.protectedMethod();
	}
	
	public static void main(String[] args) {
		
		Alpha alpha = new Alpha();
		// System.out.println(String.format("defualt: %d", alpha.p));
		System.out.println(String.format("public: %d", alpha.q));
		// System.out.println(String.format("private: %d", alpha.r));
		// System.out.println(String.format("protected: %d", alpha.s));		
	}
}
