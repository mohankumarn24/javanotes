package com.notes.programs;

// https://www.geeksforgeeks.org/selection-sort-algorithm-2/
public class SelectionSort {

	public static void main(String[] args) {

		int[] arr = { 9, 8, 6, 5 };

		System.out.println("Original array:");
		printArray(arr);

		selectionSort(arr);

		System.out.println("Sorted array:");
		printArray(arr);
	}

	public static void selectionSort(int[] arr) {

		int n = arr.length;

		// One by one move boundary of unsorted subarray
		for (int i = 0; i < n - 1; i++) {
			// a. Find the minimum element in unsorted array
			int minIndex = i;					// Assume the current position holds the minimum element
			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[minIndex]) {   // (arr[j] > arr[minIndex]) -- desc order
					minIndex = j; 				// Update min_idx if a smaller element is found
				}
			}

			// b. Swap the found minimum element with the first element
			int temp = arr[minIndex];
			arr[minIndex] = arr[i];
			arr[i] = temp;
		}
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}

/*
Summary: 
 Repeatedly find the minimum element from the unsorted portion and place it at the correct position

Time Complexity:
 Best: O(n²)
 Average: O(n²)
 Worst: O(n²)

Space Complexity:
 O(1) (in-place)

--
Assume minIndex, find minIndex towards right, swap:
9 |8 6 5
i=0, assumed minIndex=0, actual minIndex=3. swap 9 & 5 -> 5 8 6 9

5 8 |6 9
i=1, assumed minIndex=1, actual minIndex=2. swap 8 & 6 -> 5 6 8 9

5 6 8| 9
i=2, assumed minIndex=2, actual minIndex=2. no swap
*/