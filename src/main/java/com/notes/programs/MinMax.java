package com.notes.programs;

public class MinMax {

	public static void main(String[] args) {
		int[] array = new int[] { 1, 2, 3, 4, 5 };

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		for (int num : array) {
			if (num < min) min = num;
			if (num > max) max = num;
		}

		System.out.println(String.format("Min: %d", min));
		System.out.println(String.format("Max: %d", max));
	}
}
