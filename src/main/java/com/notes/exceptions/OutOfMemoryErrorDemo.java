package com.notes.exceptions;

import java.util.ArrayList;
import java.util.List;

// Java Heap Space
public class OutOfMemoryErrorDemo {

	public static void main(String[] args) {
		List<byte[]> list = new ArrayList<>();

		while (true) {
			list.add(new byte[1024 * 1024]); // 1 MB
		}
	}
}

/*
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
		at com.notes.exceptions.OutOfMemoryErrorDemo.main(OutOfMemoryErrorDemo.java:12)

*/

/*
 - Cause:
	 -- Objects created continuously
	 -- GC cannot reclaim memory
	 -- Heap gets exhausted
 
 - Real world causes:
	 -- Memory leaks
	 -- Large cache without eviction
	 -- Loading huge data in memory 
	 
 - Debug OutOfMemoryError in production:
	 -- Enable heap dump
	 -- Analyze via VisualVM / MAT
	 -- Identify leak
	 -- Tune JVM options
	 -- Fix code
*/