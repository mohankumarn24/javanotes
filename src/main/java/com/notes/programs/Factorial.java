package com.notes.programs;

import java.math.BigInteger;

/*
 * 5! = 5 * 4 * 3 * 2 * 1 = 120
 * 4! = 4 * 3 * 2 * 1 = 24
 * 3! = 3 * 2 * 1 = 6
 * 2! = 2 * 1 = 2
 * 1! = 1
 * 0! = 1
 */
public class Factorial {
	 
	public static void main(String[] args) {   
		int n = 5;
		System.out.println(String.format("Factorial of %d is %d", n, factorial(n)));
		System.out.println(String.format("Factorial of %d is %d", n, factorialIterative(n)));
		System.out.println(String.format("Factorial of %d is %d", n, factorialBigInteger(n)));
	}

	private static int factorial(int n) {
		if (n == 0) return 1;
		return n * factorial(n - 1);     
	}
	
    public static long factorialIterative(int n) {
        long res = 1;
        for (int i = 2; i <= n; i++) {
            res = res * i;
        }
        return res;
    }
	
    public static BigInteger factorialBigInteger(int n) {
        BigInteger res = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }
}
