package com.notes.multithreading.garbagecollection;

public class GarbageCollectionDemo {

	/**
	 * Note: Neither finalization nor garbage collection is guaranteed
	 */
	public void finalize() {
		System.out.println("object is garbage collected");
	}

	public static void main(String[] args) {

		GarbageCollectionDemo demo1 = new GarbageCollectionDemo();
		GarbageCollectionDemo demo2 = new GarbageCollectionDemo();
		
		demo1 = null;
		demo2 = null;
		
		System.gc();
	}
}
