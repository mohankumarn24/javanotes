package com.notes.misc;

// This program demonstrates JVM class loading
class BillPughSingleton {
	
	// Static fields and static blocks execute in the exact order they are declared (top to bottom).
	
	private static String DUMMY = initDummy();

	static {
        System.out.println("BillPughSingleton static block");
    }

    private static String initDummy() {
        System.out.println("DUMMY initialized");
        return "dummy";
    }

    private BillPughSingleton() {
        System.out.println("Constructor called");
    }

    private static class SingletonHelper {
    	private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    	
    	static {
            System.out.println("SingletonHelper static block");
        }
    }

    public static BillPughSingleton getInstance() {
        System.out.println("getInstance() called");
        return SingletonHelper.INSTANCE;
    }
}

public class ClassLoadingDemo {
    public static void main(String[] args) {
        System.out.println("Main started");
        BillPughSingleton obj = BillPughSingleton.getInstance();
        System.out.println("Main ended");
        
        /**
         * If below line is commented:
         * 		BillPughSingleton obj = BillPughSingleton.getInstance();
         * 
         * Output:
         * 		Main started
         * 		Main ended
         */
    }
}

/*
 * Output:
 * 	Main started
 * 	DUMMY initialized
 * 	BillPughSingleton static block
 * 	getInstance() called
 * 	Constructor called
 * 	SingletonHelper static block
 * 	Main ended
*/



/*
 * 1. JVM loads ClassLoadingDemo class.
 * 
 * 2. main() starts.
 * 
 * 3. First active use of BillPughSingleton:
 *    -> BillPughSingleton class loaded
 *    -> Static members initialized in source order
 *       (DUMMY, static blocks, etc.)
 * 
 * 4. getInstance() executes.
 * 
 * 5. First active use of SingletonHelper:
 *    -> SingletonHelper class loaded
 *    -> Static members initialized in source order
 *       -> INSTANCE = new BillPughSingleton()
 *       -> Constructor executes
 *       -> Remaining static blocks execute
 * 
 * 6. INSTANCE returned to caller.
 * 
 * 7. Future calls:
 *    -> BillPughSingleton already initialized
 *    -> SingletonHelper already initialized
 *    -> Existing INSTANCE returned.
 */








