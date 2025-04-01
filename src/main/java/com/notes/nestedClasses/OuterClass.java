package com.notes.nestedClasses;

// top-level class cannot be declared as static. It is either public or default
public class OuterClass {

	public int x = 100;
	public void displayOuter() {
		System.out.println("Calling outer class non-static method");
	}
	
	public static int xx = 1000;
	public static void displayOuterStatic() {
		System.out.println("Calling outer class static method");
	}	

	public class InnerClass {

		public int y = 200;
		public void displayInner() {
			System.out.println("Calling inner non-static class non-static method");
		}
		
		public static int yy = 2000;
		public static void displayInnerStatic() {
			System.out.println("Calling inner non-static class static method");
		}
	}
	
	public static class InnerStaticClass {

		public int z = 300;
		public void displayInner() {
			System.out.println("Calling inner static class non-static method");
		}
		
		public static int zz = 3000;
		public static void displayInnerStatic() {
			System.out.println("Calling inner static class static method");
		}
	}

}
