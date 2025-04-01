package com.notes.misc;

public class BreakContinue {

	public static void main(String[] args) {
		
		breakMethod();
		continueMethod();
	}

	private static void breakMethod() {

		System.out.println("\nTesting break statement");
		for (int i = 0; i < 4; i++) {
			System.out.println();
			for (int j = 0; j < 4; j++) {
				if (j == 2) {
					break;
				}
				System.out.println(String.format("Inner loop count: %d", j));
			}
			System.out.println(String.format("Outer loop count: %d", i));
		}
		System.out.println("--- End ---");	
	}

	private static void continueMethod() {

		System.out.println("\nTesting continue statement");
		for (int i = 0; i < 4; i++) {
			System.out.println();
			for (int j = 0; j < 4; j++) {
				if (j == 2) {
					continue;
				}
				System.out.println(String.format("Inner loop count: %d", j));
			}
			System.out.println(String.format("Outer loop count: %d", i));
		}
		System.out.println("--- End ---");
	}	
}
