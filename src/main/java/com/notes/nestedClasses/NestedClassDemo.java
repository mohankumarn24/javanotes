package com.notes.nestedClasses;

public class NestedClassDemo {
	
	public static void main(String[] args) {
		
		// -- Accesing non-static variables/methods --
		// Outer class
		Outer outer = new Outer();
		System.out.println("Calling outer class non-static variable: " + outer.x);
		outer.displayOuter();

		// Inner class
		Outer.Inner inner = outer.new Inner();
		// Outer.Inner inner = (new Outer()).new Inner();
		System.out.println("Calling inner non-static class non-static variable: " + inner.y);
		inner.displayInner();
		
		// Inner static class
		Outer.InnerStatic innerStatic = new Outer.InnerStatic();
		System.out.println("Calling inner static class non-static variable: " + innerStatic.z);
		innerStatic.displayInner();
		System.out.println();
		
		
		// -- Accesing static variables/methods --
		System.out.println("Calling outer class static variable: " + Outer.xx);
		Outer.displayOuterStatic();
		
		System.out.println("Calling inner non-static class static variable: " + Outer.Inner.yy);
		Outer.Inner.displayInnerStatic();
		
		System.out.println("Calling inner static class static variable: " + Outer.InnerStatic.zz);		
		Outer.InnerStatic.displayInnerStatic();
	}
}
