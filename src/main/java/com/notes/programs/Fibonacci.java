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
            System.out.println(String.format("Fibonacci(%d): %d", i, fibonacci(i)));
        }
    }
	
    static int fibonacci(int n) {
    	
        if (n == 0 || n == 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}

/*
Fibonacci(0): 0
Fibonacci(1): 1

Fibonacci(2): 1
Fibonacci(3): 2
Fibonacci(4): 3
Fibonacci(5): 5
Fibonacci(6): 8
Fibonacci(7): 13
Fibonacci(8): 21
Fibonacci(9): 34
*/