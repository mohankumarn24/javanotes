package com.notes.nestedClasses;

public class NestedClassDemo {

	public static void main(String[] args) {
		
		nestedClasses();
	}

	private static void nestedClasses() {

		// Accesing non-static variables/methods
		// Outer class
		OuterClass outerClass = new OuterClass();
		System.out.println("Calling outer class non-static variable: " + outerClass.x);
		outerClass.displayOuterNonStatic();

		// Inner class
		OuterClass.InnerClass innerClass = outerClass.new InnerClass();
		// OuterClass.InnerClass innerClass = (new OuterClass()).new InnerClass();
		System.out.println("Calling inner non-static class non-static variable: " + innerClass.y);
		innerClass.displayInnerNonStatic();
		
		// Inner static class
		OuterClass.InnerStaticClass innerStaticClass = new OuterClass.InnerStaticClass();
		System.out.println("Calling inner static class non-static variable: " + innerStaticClass.z);
		innerStaticClass.displayInnerNonStatic();
		
		// Accesing static variables/methods
		System.out.println();
		System.out.println("Calling outer class static variable: " + OuterClass.xx);
		OuterClass.displayOuterStatic();
		
		System.out.println("Calling inner non-static class static variable: " + OuterClass.InnerClass.yy);
		OuterClass.InnerClass.displayInnerStatic();
		
		System.out.println("Calling inner static class static variable: " + OuterClass.InnerStaticClass.zz);		
		OuterClass.InnerStaticClass.displayInnerStatic();
	}
}
