package com.notes.programs;

import java.util.Arrays;

public class RadixSort {
	
    public static void main(String[] args) {
    	
    	int[] arr = { 9, 8, 6, 5 };
        
        System.out.println("Original array : " + Arrays.toString(arr));
        radixSort(arr);
        System.out.println("Sorted array   : " + Arrays.toString(arr));
    }
    
    public static void radixSort(int[] arr) {
        // Find the maximum number to know the number of digits
        int max = getMax(arr);
        
        // Do counting sort for every digit
        // exp is 10^i where i is current digit number
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }
    
    // A utility function to get maximum value in arr[]
    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    // A function to do counting sort of arr[] according to the digit represented by exp
    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n]; // output array
        int[] count = new int[10]; // count array for digits 0-9
        
        // Initialize count array
        Arrays.fill(count, 0);
        
        // Store count of occurrences of current digit
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }
        
        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }
        
        // Copy the output array to arr[]
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
}