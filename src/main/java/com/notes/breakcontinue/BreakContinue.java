package com.notes.breakcontinue;

public class BreakContinue {

	public static void main(String[] args) {
		
		breakMethod();
		continueMethod();
	}

	private static void breakMethod() {

		System.out.println("\nTesting break statement");
		for (int i = 0; i < 10; i++) {
			System.out.println(String.format("@@@ Outer loop count: %d", i));
			for (int j = 0; j < 10; j++) {
				if (j == 5) {
					break;
				}
				System.out.println(String.format("Inner loop count: %d", j));
			}
			System.out.println();
		}
		System.out.println("--- End ---");	
	}

	private static void continueMethod() {

		System.out.println("\nTesting continue statement");
		for (int i = 0; i < 10; i++) {
			System.out.println(String.format("@@@ Outer loop count: %d", i));
			for (int j = 0; j < 10; j++) {
				if (j == 5) {
					continue;
				}
				System.out.println(String.format("Inner loop count: %d", j));
			}
			System.out.println();
		}
		System.out.println("--- End ---");
	}	
}
