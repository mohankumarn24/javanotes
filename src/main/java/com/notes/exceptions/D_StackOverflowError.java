package com.notes.exceptions;

public class D_StackOverflowError {

	public static void recursiveCall() {
		recursiveCall(); // no base condition -> will cause StackOverflowError
	}

	public static void main(String[] args) {
		try {
			recursiveCall();
		} catch (StackOverflowError e) {
			System.out.println("StackOverflowError occurred: " + e);
		}
	}
}

/*
StackOverflowError occurred: java.lang.StackOverflowError
*/