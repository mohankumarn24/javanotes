package com.notes.exceptions;

public class C4_RunTimeExceptions{

	public static void main(String[] args) {

		// 1. NullPointerException
		try {
			String name = null;
			System.out.println(name.length());
		} catch (NullPointerException e) {
			System.out.println("Handled NullPointerException");
		}

		// 2. ArithmeticException
		try {
			int result = 10 / 0;
		} catch (ArithmeticException e) {
			System.out.println("Handled ArithmeticException");
		}

		// 3. ArrayIndexOutOfBoundsException
		try {
			int[] arr = { 1, 2, 3 };
			System.out.println(arr[5]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Handled ArrayIndexOutOfBoundsException");
		}

		// 4. NumberFormatException
		try {
			int num = Integer.parseInt("abc");
		} catch (NumberFormatException e) {
			System.out.println("Handled NumberFormatException");
		}

		// 5. ClassCastException
		try {
			Object obj = "Hello";
			Integer num = (Integer) obj;
		} catch (ClassCastException e) {
			System.out.println("Handled ClassCastException");
		}

		System.out.println("Program completed without crashing");
	}
}