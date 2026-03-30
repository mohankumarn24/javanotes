package com.notes.accessModifiers.package1;

public class AlphaSubSamePackage extends Alpha {
	public void testAccessModifiers() {
		// ==============================================================
		// 1. variables, methods
		// ==============================================================
		p = 1;  			// default variable
		q = 2;  			// public variable
		// r = 3;  			// private variable
		s = 4;  			// protected variable
		
		defaultMethod(); 	// default method
		publicMethod(); 	// public method
		// privateMethod();	// private method
		protectedMethod();	// protected method
		
		// ==============================================================
		// 2. object instantation -> variables, methods
		// ==============================================================
		Alpha alpha = new Alpha();
		System.out.println(String.format("default: %d", alpha.p));
		System.out.println(String.format("public: %d", alpha.q));
		// System.out.println(String.format("private: %d", alpha.r));
		System.out.println(String.format("protected: %d", alpha.s));
		
		alpha.defaultMethod();
		alpha.publicMethod();
		// alpha.privateMethod();
		alpha.protectedMethod();
		
		// ==============================================================
		// 3. records
		// ==============================================================
		DefaultRecord defaultRecord = new DefaultRecord("default");
		PublicRecord publicRecord = new PublicRecord("public");
		// PrivateRecord privateRecord = new PrivateRecord("private");
		ProtectedRecord protectedRecord = new ProtectedRecord("protected");
	}
	
	public static void main(String[] args) {
		// ==============================================================
		// 2. object instantation -> variables, methods
		// ==============================================================
		Alpha alpha = new Alpha();
		System.out.println(String.format("default: %d", alpha.p));
		System.out.println(String.format("public: %d", alpha.q));
		// System.out.println(String.format("private: %d", alpha.r));
		System.out.println(String.format("protected: %d", alpha.s));
		
		alpha.defaultMethod();
		alpha.publicMethod();
		// alpha.privateMethod();
		alpha.protectedMethod();
		
		// ==============================================================
		// 3. records
		// ==============================================================
		DefaultRecord defaultRecord = new DefaultRecord("default");
		PublicRecord publicRecord = new PublicRecord("public");
		// PrivateRecord privateRecord = new PrivateRecord("private");
		ProtectedRecord protectedRecord = new ProtectedRecord("protected");
	}
}
