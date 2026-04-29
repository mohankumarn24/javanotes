package com.notes.exceptions;

// - Run this class
// - Compilation successful. But exception occurred when running the application
// - Unchecked exceptions, also known as runtime exceptions, are not checked at compile-time. 
//   These exceptions usually occur due to programming errors, such as logic errors or incorrect assumptions in the code. 
//   They do not need to be declared in the method signature using the throws keyword, making it optional to handle them
public class A1_ArithmeticExceptionRTE {

	public static void main(String[] args) {

		System.out.println("hello, world");  // executed
		
		int x = 5;
		int y = 0;
		System.out.println(x / y);
		/*
		 * 	Exception in thread "main" java.lang.ArithmeticException: / by zero
		 *			at com.notes.exceptions.ArithmeticExceptionRTE.main(ArithmeticExceptionRTE.java:16)
		 */	
	}
}
