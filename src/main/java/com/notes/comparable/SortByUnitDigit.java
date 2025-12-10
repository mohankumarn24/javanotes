package com.notes.comparable;

import java.util.Arrays;
import java.util.Comparator;

public class SortByUnitDigit {
	public static void main(String[] args) {
		Integer[] arr1 = { 23, 45, 12, 98, 31, 5 };
		Integer[] arr2 = { 23, 45, 12, 98, 31, 5 };
		Integer[] arr3 = { 23, 45, 12, 98, 31, 5 };

		// sort without using lambda
		Arrays.sort(arr1, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return (a % 10) - (b % 10); // compare unit digits
			}
		});
		System.out.println(Arrays.toString(arr1));						// [31, 12, 23, 45, 5, 98]

		// sort using lambda
		Arrays.sort(arr2, (a, b) -> (a % 10) - (b % 10));
		System.out.println(Arrays.toString(arr2));						// [31, 12, 23, 45, 5, 98]
		
		// sort using lambda
		Arrays.sort(arr3, (a, b) -> (a % 10) > (b % 10) ? 1 : -1);
		System.out.println(Arrays.toString(arr3));						// [31, 12, 23, 5, 45, 98]
	}
}
