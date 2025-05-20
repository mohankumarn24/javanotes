package com.notes.zzz;

public class ScratchPad {
	
	public static void main(String[] args) {
		int[] nums = new int[] {1, 2, 3, 4, 5};
		int target = 4;
		int index = binarySearch(nums, target);
		System.out.println(String.format("Target %d is found at position %d", target, index));
		
	}
	
    public static int binarySearch(int[] nums, int target) {
    	
        int low = 0;
        int high = nums.length - 1;
    
        while (low <= high) {
            // int mid = (low + high) / 2;
        	int mid = low + (high - low) / 2;
            if (target > nums[mid]) {
                low = mid + 1;
            } else if (target < nums[mid]) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        
        return -1;
    }

}
