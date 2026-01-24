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
			while (j >= 0 && arr[j] > key) { //  (j >= 0 && arr[j] < key) --> desc order
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

/*
Summary: 
 You split the cards into two groups: the sorted cards and the unsorted cards. 
 Then, you pick a card from the unsorted group and put it in the right place in the sorted group

Time Complexity:
 Best: O(n) (already sorted)
 Average: O(n²)
 Worst: O(n²)

Space Complexity:
 O(1) (in-place)

--
9 |8 6 5
save key=8. compare 9 > 8? 	shift 9 towards right, j--	    :	9 9 6 5
					 	 	j++, put 8 at correct position 	:	8 9 6 5

8 9 |6 5
save key=6. compare 9 > 6? 	shift 9 towards right, j--		:	8 9 9 5
        	compare 8 > 6? 	shift 8 towards right, j-- 		:	8 8 9 5
                     	 	j++, put 6 at correct position	:	6 8 9 5
6 8 9| 5
save key=5. compare 9 > 5? 	shift 9 towards right, j--		:	6 8 9 9
        	compare 8 > 5? 	shift 8 towards right, j-- 		:	6 8 8 9
        	compare 6 > 5? 	shift 6 towards right, j-- 		:	6 6 8 9
                     	 	j++, put 5 at correct position	:	5 6 8 9
*/