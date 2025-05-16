package com.notes.programs;

public class BinarySearch {
	
    public static void main(String[] args) {
    	
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        int key = 23;
        
        // Using iterative method
        int iterativeResult = binarySearchIterative(arr, key);
        if (iterativeResult == -1) {
            System.out.println("Element " + key + " not present in array (iterative)");
        } else {
            System.out.println("Element " + key + " found at index " + iterativeResult + " (iterative)");
        }
        
        // Using recursive method
        int recursiveResult = binarySearchRecursive(arr, 0, arr.length - 1, key);
        if (recursiveResult == -1) {
            System.out.println("Element " + key + " not present in array (recursive)");
        } else {
            System.out.println("Element " + key + " found at index " + recursiveResult + " (recursive)");
        }
    }
    
    // Iterative implementation of binary search
    public static int binarySearchIterative(int[] arr, int key) {
    	
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (key < arr[mid]) {
            	high = mid - 1;
            } else if (key > arr[mid]) {
            	low = mid + 1;            	
            } else {
            	return mid;
            }
        }
        return -1;
    }
    
    // Recursive implementation of binary search
	public static int binarySearchRecursive(int[] arr, int low, int high, int key) {

		if (low > high) return -1;

		int mid = low + (high - low) / 2;
		if (key < arr[mid]) {
			return binarySearchRecursive(arr, low, mid - 1, key);
		} else if (key > arr[mid]) {
			return binarySearchRecursive(arr, mid + 1, high, key);
		} else {
			return mid;
		}
	}
}