package com.notes.programs;

public class MergeSort {
	
	public static void main(String[] args) {
		
		int[] arr = { 9, 8, 6, 5 };
		System.out.println("Original array:");
		printArray(arr);

		mergeSort(arr);

		System.out.println("Sorted array:");
		printArray(arr);
	}
	
	// Main method to sort an array
	public static void mergeSort(int[] array) {
		if (array == null || array.length <= 1) {
			return; // Already sorted
		}

		int[] temp = new int[array.length];
		mergeSort(array, temp, 0, array.length - 1);
	}

	// Recursive method to divide the array
	private static void mergeSort(int[] array, int[] temp, int left, int right) {
		
		if (left < right) {
			int middle = left + (right - left) / 2;

			// Sort first and second halves
			mergeSort(array, temp, left, middle);
			mergeSort(array, temp, middle + 1, right);

			// Merge the sorted halves
			merge(array, temp, left, middle, right);
		}
	}

	// Merge two subarrays
	private static void merge(int[] array, int[] temp, int left, int middle, int right) {
		
		// Copy data to temporary arrays
		for (int i = left; i <= right; i++) {
			temp[i] = array[i];
		}

		int i = left; // Initial index of first subarray
		int j = middle + 1; // Initial index of second subarray
		int k = left; // Initial index of merged subarray

		// Merge the two subarrays
		while (i <= middle && j <= right) {
			if (temp[i] <= temp[j]) {
				array[k] = temp[i];
				i++;
			} else {
				array[k] = temp[j];
				j++;
			}
			k++;
		}

		// Copy the remaining elements of left subarray, if any
		while (i <= middle) {
			array[k] = temp[i];
			i++;
			k++;
		}

		// No need to copy the remaining elements of right subarray
		// as they are already in their correct position
	}

	public static void printArray(int[] array) {
		
		for (int i : array) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
