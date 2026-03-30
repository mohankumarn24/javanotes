package com.notes.accessModifiers.package1;

public class Alpha {
	// 1. variables
	int p = 1;
	public int q = 2;
	private int r = 3;
	protected int s = 4;
	
	// 2. methods
	void defaultMethod() {System.out.println("default method");}
	public void publicMethod() {System.out.println("public method");}
	private void privateMethod() {System.out.println("private method");}
	protected void protectedMethod() {System.out.println("protected method");}
	
	// 3. records
    record DefaultRecord(String name) {}
    public record PublicRecord(String name) {}				// Industry standard - only this is used most of the times
    private record PrivateRecord(String name) {}
    protected record ProtectedRecord(String name) {}
	
	// test method
	public void testAccessModifiers() {
		// ==============================================================
		// 1. variables, methods
		// ==============================================================
		p = 1;  			// default variable
		q = 2;  			// public variable
		r = 3;  			// private variable
		s = 4;  			// protected variable
		
		defaultMethod(); 	// default method
		publicMethod(); 	// public method
		privateMethod();	// private method
		protectedMethod();	// protected method
		
		// ==============================================================
		// 2. object instantation -> variables, methods
		// ==============================================================
		Alpha alpha = new Alpha();
		System.out.println(String.format("default: %d", alpha.p));
		System.out.println(String.format("public: %d", alpha.q));
		System.out.println(String.format("private: %d", alpha.r));
		System.out.println(String.format("protected: %d", alpha.s));
		
		alpha.defaultMethod();
		alpha.publicMethod();
		alpha.privateMethod();
		alpha.protectedMethod();
		
		// ==============================================================
		// 3. records
		// ==============================================================
		DefaultRecord defaultRecord = new DefaultRecord("default");
		PublicRecord publicRecord = new PublicRecord("public");
		PrivateRecord privateRecord = new PrivateRecord("private");
		ProtectedRecord protectedRecord = new ProtectedRecord("protected");
	}
	
	public static void main(String[] args) {
		// ==============================================================
		// 2. object instantation -> variables, methods
		// ==============================================================
		Alpha alpha = new Alpha();
		System.out.println(String.format("default: %d", alpha.p));
		System.out.println(String.format("public: %d", alpha.q));
		System.out.println(String.format("private: %d", alpha.r));
		System.out.println(String.format("protected: %d", alpha.s));	
		
		alpha.defaultMethod();
		alpha.publicMethod();
		alpha.privateMethod();
		alpha.protectedMethod();
		
		// Local variables cannot have access modifiers (Access modifiers apply to members of a class, not inside a method)
		// Inside methods, variables belong to the stack, so visibility control doesn't apply.
		
		// String str = "hello";							// valid
		// final String str = "hello";						// valid
		// static String str = "hello";						// CTE: Illegal modifier for parameter str; only final is permitted
		// private final String str = "hello";				// CTE: Illegal modifier for parameter str; only final is permitted
		// private static final String str = "hello";		// CTE: Illegal modifier for parameter str; only final is permitted
		
		// ==============================================================
		// 3. records
		// ==============================================================
		DefaultRecord defaultRecord = new DefaultRecord("default");
		PublicRecord publicRecord = new PublicRecord("public");
		PrivateRecord privateRecord = new PrivateRecord("private");
		ProtectedRecord protectedRecord = new ProtectedRecord("protected");
	}
}
