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
				if (arr[j] > arr[j + 1]) { // (arr[j] < arr[j + 1]) --> desc order
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


/*
Summary:
 Repeatedly compare adjacent elements and swap them if they are in the wrong order. Largest elements “bubble” to the end

Time Complexity:
 Best: O(n) (already sorted with optimization)
 Average: O(n²)
 Worst: O(n²)

Space Complexity:
 O(1) (in-place)

--
=> 9 8 6 | 5		iteration 0 < n - 1 (0 < 3):
   8 9 6 | 5 		after swapping 9 & 8
   8 6 9 | 5 		after swapping 9 & 6
   8 6 5 | 9		after swapping 9 & 5
   
=> 8 6 | 5 9		iteration 1 < n - 1 (0 < 3):
   6 8 | 5 9		after swapping 8 & 6
   6 5 | 8 9		after swapping 8 & 5
   
=> 6 | 5 8 9		iteration 2 < n - 1 (0 < 3):
   5 | 6 8 9		after swapping 6 & 5
*/