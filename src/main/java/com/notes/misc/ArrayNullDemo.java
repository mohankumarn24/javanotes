package com.notes.misc;

import java.util.Arrays;

public class ArrayNullDemo {
	
	public static void main(String[] args) {

		/**
		 * CONCEPT 1: The entire array reference is null
		 */
		int[] nums = null;
		System.out.println("--- Concept 1: Null Array Reference ---");
		if (nums == null) {
			System.out.println("The array variable 'nums' is null. It points to nothing.");
		}

		
		/**
		 * CONCEPT 2: Primitive int elements cannot be null (they default to 0)
		 */
		System.out.println("\n--- Concept 2: Primitive int Array Elements ---");
		nums = new int[3]; // Instantiating the array

		// nums[0] = null; // UNCOMMENTING THIS WILL CAUSE A COMPILER ERROR
		nums[0] = 25;

		for (int i = 0; i < nums.length; i++) {
			System.out.println("nums[" + i + "] = " + nums[i]);
		}

		
		/**
		 * CONCEPT 3: Wrapper Integer elements CAN be null
		 */
		System.out.println("\n--- Concept 3: Wrapper Integer Array Elements ---");
		Integer[] wrapperNums = new Integer[3]; // Defaults to null, not 0

		// At this exact moment, before any assignments:
		// wrapperNums[0] is null
		// wrapperNums[1] is null
		// wrapperNums[2] is null
		
		wrapperNums[0] = 99;
		wrapperNums[1] = null; // Completely valid here!
		wrapperNums[2] = -7;

		for (int i = 0; i < wrapperNums.length; i++) {
			System.out.println("wrapperNums[" + i + "] = " + wrapperNums[i]);
		}
		
		
		/**
		 * CONCEPT 4: The trap of Autounboxing (NullPointerException)
		 */
		System.out.println("\n--- Concept 4: Safe Unboxing ---");

		// This line would CRASH the program because wrapperNums[1] is null:
		// int primitiveValue = wrapperNums[1]; 

		// The correct, safe way to handle it:
		if (wrapperNums[1] != null) {
		    int safePrimitive = wrapperNums[1];
		    System.out.println("Safe primitive value: " + safePrimitive);
		} else {
		    System.out.println("Cannot convert wrapperNums[1] to primitive because it is null.");
		}

		
		/**
		 * CONCEPT 5: Quick Initialization to avoid default nulls
		 */
		System.out.println("\n--- Concept 5: Filling Wrapper Arrays ---");
		Integer[] populatedWrapper = new Integer[3];

		// Fills all slots with 0 instead of leaving them null
		Arrays.fill(populatedWrapper, 0); 

		System.out.println("populatedWrapper[0] = " + populatedWrapper[0]); // Prints 0
	}
}


/*
--- Concept 1: Null Array Reference ---
The array variable 'nums' is null. It points to nothing.

--- Concept 2: Primitive int Array Elements ---
nums[0] = 25
nums[1] = 0
nums[2] = 0

--- Concept 3: Wrapper Integer Array Elements ---
wrapperNums[0] = 99
wrapperNums[1] = null
wrapperNums[2] = -7

--- Concept 4: Safe Unboxing ---
Cannot convert wrapperNums[1] to primitive because it is null.

--- Concept 5: Filling Wrapper Arrays ---
populatedWrapper[0] = 0
*/