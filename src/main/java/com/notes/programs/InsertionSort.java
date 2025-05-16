package com.notes.programs;

// https://www.geeksforgeeks.org/insertion-sort-algorithm/
public class InsertionSort {

	public static void main(String[] args) {

		int[] arr = { 9, 8, 6, 5 };

		System.out.println("Original array:");
		printArray(arr);

		insertionSort(arr);

		System.out.println("Sorted array:");
		printArray(arr);
	}

	public static void insertionSort(int[] arr) {

		int n = arr.length;

		// Start from the second element (index 1)
		// The first element is considered as sorted
		for (int i = 1; i < n; i++) {
			// Store the current element to be compared
			int key = arr[i];

			// Initialize j to point to the previous element
			int j = i - 1;

			// Move elements that are greater than key to one position ahead of their current position
			while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j--;
			}

			// Place the key at its correct position in the sorted part
			arr[j + 1] = key;
		}
	}

	public static void printArray(int[] arr) {
		for (int value : arr) {
			System.out.print(value + " ");
		}
		System.out.println();
	}
}