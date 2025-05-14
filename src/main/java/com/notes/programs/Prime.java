package com.notes.programs;

public class Prime {

	static boolean isPrime(int n) {
		for (int x = 2; x <= Math.sqrt(n); x++) {
			if (n % x == 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {

		System.out.println("0 and 1 are not primes\n");
		
		for (int i = 2; i < 10; i++) {
			System.out.println(String.format("%d: %b", i, isPrime(i)));
		}
	}
}
