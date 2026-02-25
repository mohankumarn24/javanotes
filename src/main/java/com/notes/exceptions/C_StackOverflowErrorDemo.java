package com.notes.exceptions;

// Infinite Recursion
public class C_StackOverflowErrorDemo {

	public static void recursiveCall() {
		recursiveCall(); // no base condition
	}

	public static void main(String[] args) {
		recursiveCall();
	}
}

/*
Exception in thread "main" java.lang.StackOverflowError
	at com.notes.exceptions.StackOverflowErrorDemo.recursiveCall(StackOverflowErrorDemo.java:6)
	at com.notes.exceptions.StackOverflowErrorDemo.recursiveCall(StackOverflowErrorDemo.java:6)
	at com.notes.exceptions.StackOverflowErrorDemo.recursiveCall(StackOverflowErrorDemo.java:6)
	at com.notes.exceptions.StackOverflowErrorDemo.recursiveCall(StackOverflowErrorDemo.java:6)

*/

/*
 - Method call  → stack frame created
				→ infinite frames
				→ stack limit crossed
				→ StackOverflowError
 - StackOverflowError is an Error, not an Exception → should not be caught
 - Cause:
	 -- Method keeps calling itself
	 -- No termination condition
	 -- Stack memory gets exhausted
*/