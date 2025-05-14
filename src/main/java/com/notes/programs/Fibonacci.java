package com.notes.programs;

/*
 * Input : N = 10 
 * Output: 0 1 1 2 3 5 8 13 21 34 
 */
// https://www.geeksforgeeks.org/java-fibonacci-series/
public class Fibonacci {
	
    // Function to print the fibonacci series
    static int fibonacci(int n) {
    	
        // Base Case
        if (n <= 1) return n;
        // Recursive call
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
		
	public static void main(String[] args) {
		
        // Given Number N
        int n = 5;

        // Print the first N numbers
        for (int i = 0; i < n; i++) {

            System.out.print(fibonacci(i) + " ");
        }
    }
}
