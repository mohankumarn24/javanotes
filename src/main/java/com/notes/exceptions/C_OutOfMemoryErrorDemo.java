package com.notes.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Java Heap Space
public class C_OutOfMemoryErrorDemo {
	
	public static void main(String[] args) {
		
		/*
		 * - Java List has no fixed max size.
		 * - ArrayList is limited to ~2.1 billion elements theoretically, but memory constraints cause OutOfMemoryError much earlier
		 */
		List<byte[]> list = new ArrayList<>();
		while (true) {
			list.add(new byte[1024 * 1024]); // 1 MB
		}
		
		/*
        List<String> list = new ArrayList<>();
        while (true) {
            list.add(UUID.randomUUID().toString());
        }
        */
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