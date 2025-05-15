package com.notes.programs;

/*
 * Input : N = 10 
 * Output: 0 1 1 2 3 5 8 13 21 34 
 */
// https://www.geeksforgeeks.org/java-fibonacci-series/
public class Fibonacci {
		
	public static void main(String[] args) {
        int n = 10;
        // Print the first N numbers
        for (int i = 0; i < n; i++) {
            System.out.print(fibonacci(i) + " ");
        }
    }
	
    static int fibonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
