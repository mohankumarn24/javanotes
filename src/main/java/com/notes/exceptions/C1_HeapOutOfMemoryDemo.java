package com.notes.exceptions;

import java.util.ArrayList;
import java.util.List;

public class C1_HeapOutOfMemoryDemo {

	public static void main(String[] args) {
		/*
		 * - Java List has no fixed max size.
		 * - ArrayList is limited to ~2.1 billion elements theoretically, but memory constraints cause OutOfMemoryError much earlier
		 */
		List<byte[]> list = new ArrayList<>();

		try {
			while (true) {
				list.add(new byte[1024 * 1024]); // 1 MB
			}
		} catch (OutOfMemoryError e) {
			// Catching OutOfMemoryError is unreliable because the JVM may not have enough memory to execute the catch block itself
			System.out.println("OutOfMemoryError occurred: " + e);
		}
	}
}

/*
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at com.notes.exceptions.E_HeapOutOfMemoryDemo.main(E_HeapOutOfMemoryDemo.java:17)
 */

/*
public class E_HeapOutOfMemoryDemo {

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();

        try {
            while (true) {
                list.add(new int[1_000_000]); // keep allocating memory
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError occurred: " + e);
        }
    }
}
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
