package com.notes.programs;

// https://www.geeksforgeeks.org/bubble-sort-algorithm/
public class BubbleSort {

	public static void main(String args[]) {

		int[] arr = { 9, 8, 6, 5 };

		System.out.println("Original array:");
		printArray(arr);

		bubbleSort(arr);

		System.out.println("Sorted array:");
		printArray(arr);
	}

	public static void bubbleSort(int arr[]) {

		int n = arr.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					// swap
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}

	public static void printArray(int[] arr) {
		for (int value : arr) {
			System.out.print(value + " ");
		}
		System.out.println();
	}
}