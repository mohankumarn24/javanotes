package com.notes.programs;

public class MinMax {

	public static void main(String[] args) {
		int[] nums = new int[] { 9, 8, 6, 5 };

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		for (int num : nums) {
			if (num < min) min = num;
			if (num > max) max = num;
		}

		System.out.println(String.format("Min: %d", min));
		System.out.println(String.format("Max: %d", max));
	}
}
