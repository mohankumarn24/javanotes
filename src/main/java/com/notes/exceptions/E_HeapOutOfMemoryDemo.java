package com.notes.exceptions;

import java.util.ArrayList;
import java.util.List;

public class E_HeapOutOfMemoryDemo {

	public static void main(String[] args) {
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
