package com.notes.nestedClasses;

// top-level class cannot be declared as static. It is either public or default
public class Outer {
	public int x = 100;
	public void displayOuter() {
		System.out.println("Calling outer class non-static method");
	}
	
	public static int xx = 1000;												// OuterClass.xx;
	public static void displayOuterStatic() {
		System.out.println("Calling outer class static method");				// OuterClass.displayOuterStatic();
	}	

	// Inner class
	public class Inner {
		public int y = 200;
		public void displayInner() {
			System.out.println("Calling inner non-static class non-static method");
		}
		
		public static int yy = 2000;											// OuterClass.InnerClass.yy;
		public static void displayInnerStatic() {
			System.out.println("Calling inner non-static class static method"); // OuterClass.InnerClass.displayInnerStatic();
		}
	}
	
	// Inner static class
	public static class InnerStatic {
		public int z = 300;
		public void displayInner() {
			System.out.println("Calling inner static class non-static method");
		}
		
		public static int zz = 3000;											// OuterClass.InnerStaticClass.zz;
		public static void displayInnerStatic() {
			System.out.println("Calling inner static class static method"); 	// OuterClass.InnerStaticClass.displayInnerStatic();
		}
	}
}
