package com.notes.multithreading.garbagecollection;

public class GarbageCollectionDemo {

	// Note: Neither finalization nor garbage collection is guaranteed
	@Override
	protected void finalize() {
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

// Below output is not guaranteed. It can print once, or twice, or not print at all
/*
object is garbage collected
object is garbage collected
*/



/*
Why finalize() is deprecated since Java 9:

1. Unpredictable execution:
   - JVM does not guarantee when or even if finalize() will run
   - Program may terminate before it gets executed

2. Performance overhead:
   - Objects with finalize() require extra GC processing
   - Slows down garbage collection

3. Resource leakage risk:
   - Critical resources (files, DB connections) may not be released on time
   - Can lead to memory/resource exhaustion

4. Security issues:
   - Object can be "resurrected" inside finalize() (assigning 'this' to a static reference)
   - Leads to unexpected behavior

5. Deprecated since Java 9:
   - Officially discouraged in modern Java

--------------------------------------------------

Recommended alternatives:

1. Try-with-resources (BEST for resource management):
   - Automatically closes resources

   Example:
   try (FileInputStream fis = new FileInputStream("file.txt")) {
       // use resource
   }

2. Explicit cleanup methods:
   - Provide a method like close() or cleanup()

   Example:
   resource.close();

3. Cleaner (replacement for finalize):
   - Introduced as safer alternative
   - Runs cleanup logic when object becomes eligible for GC

4. PhantomReference (advanced):
   - Gives more control over cleanup after GC
   - Used in low-level frameworks

--------------------------------------------------

Key takeaway:
Never rely on finalize() for important cleanup.
Use explicit or structured resource management instead.
*/