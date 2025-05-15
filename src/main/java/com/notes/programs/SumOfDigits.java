package com.notes.programs;

public class SumOfDigits {
	
	public static void main(String[] args) {
		int num = 123;
		System.out.println(String.format("sum of digits of %d is %d", num, sumDigits(num)));
	}

	private static Object sumDigits(int num) {
		int sum = 0;
		while (num > 0) {
			sum += num % 10;
			num /= 10;
		}
		return sum;
	}
}
