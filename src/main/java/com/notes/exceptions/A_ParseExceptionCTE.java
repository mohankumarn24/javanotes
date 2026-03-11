package com.notes.exceptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// - Run this class
// - Exception occurred during compile time. Cannot run the class. Solution: use try-catch or throws
// - Checked exceptions are the exceptions that are checked at compile-time. This means that the compiler verifies that the code handles these
//   exceptions either by catching them or declaring them in the method signature using the throws keyword. 
public class A_ParseExceptionCTE  {

	public static void main(String[] args) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = "2023/01/01";
		// String dateString = "2023-01-01"; 
		
		/*
		 * - Link: https://rollbar.com/blog/how-to-fix-java-text-parseexception/
		 * - The java.text.ParseException is a checked exception in Java that signals an unexpected error while parsing an input. 
		 *   This typically happens when the input does not match the expected format.
		 * - Since ParseException is a checked exception, it must be explicitly handled in methods that can throw this exception  
		 *   either by using a try-catch block or by throwing it using the throws clause.
		 */
		try {
			Date dateCTEhandled = dateFormat.parse(dateString); 
			System.out.println(dateCTEhandled);  				// Sun Jan 01 00:00:00 IST 2023
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// throw e;											// add "throws ParseException"
																// don't swallow the exception. Instead, rethrow to bubble up
		} finally {
			System.out.println("Finally block executed");		// always runs
		}
		
		// Uncomment below line and check: if you uncomment below line, you cannot compile
		// Date dateCTENotHandled = dateFormat.parse(dateString);
		
		/*
		 * Output:
		 *	Exception in thread "main" java.lang.Error: Unresolved compilation problem: 
		 *			Unhandled exception type ParseException
		 *			at com.notes.exceptions.ParseExceptionCTE.main(ParseExceptionCTE.java:23)
		 *  Finally block executed
		*/
	}
}	

/*
------------------------------------------------------------
 Topic: What happens to return value if exception occurs?
------------------------------------------------------------

Case 1: Exception occurs BEFORE return statement
----------------------------------------------------
If an exception is thrown before the 'return' statement is executed,
the method exits immediately — it never reaches the return statement.

Example:
    public Object getData() {
        Object obj = new Object();
        if (true) {
            throw new RuntimeException("Something went wrong!");
        }
        return obj; // ❌ never reached
    }

Result:
    - The method does NOT return anything.
    - The exception propagates to the caller (unless caught)

------------------------------------------------------------

Case 2: Exception is CAUGHT inside the method
----------------------------------------------------
If the method catches the exception, behavior depends on what happens next.

 Option 1: Return a fallback value
    public Object getData() {
        try {
            throw new RuntimeException("Oops!");
        } catch (Exception e) {
            System.out.println("Caught exception");
            return null;  // or some default object
        }
    }

    → Caller gets null (or whatever fallback is returned)

 Option 2: Rethrow exception
    public Object getData() {
        try {
            throw new RuntimeException("Oops!");
        } catch (Exception e) {
            throw e; // rethrow
        }
    }

    → Caller gets the exception. No return value is produced

------------------------------------------------------------

Case 3: finally block and return
----------------------------------------------------
If both 'try' and 'finally' are present, and return is inside 'try',
the return value is computed first, then 'finally' executes.

Example:
    public int test() {
        try {
            return 1;
        } finally {
            System.out.println("finally runs");
        }
    }

Output:
    finally runs
Return value:
    1

However, if 'finally' itself throws an exception:
    - The return value is DISCARDED.
    - The new exception from 'finally' propagates to the caller.

------------------------------------------------------------
Case 4: finally block and return
        If both try and finally have return statements, the finally return overrides the try return because finally executes just before the method completes.
----------------------------------------------------
If both 'try' and 'finally' are present, and return is inside 'try',
the return value is computed first, then 'finally' executes.

Example 1:
	public int test() {
	    try {
	        return 1;
	    } finally {
	        return 2;
	    }
	}

Notes: 
    try block runs.
    return 1 is prepared.
    Before returning, the finally block executes.
    finally has return 2, which overrides the earlier return.
    Method returns 2.
Output:
    2
Return value:
    2

--
Example 2:
	public int test() {
	    try {
	        int x = 10 / 0; // ArithmeticException
	        return 1;
	    } finally {
	    	// Best practise: Avoid returning in finally
	        return 2;
	    }
	}

Notes:
	Returning from finally is considered bad practice because:
	 - It suppresses exceptions.
	 - It overrides return values from try or catch.
Output:
	Even though an exception occurs, the method still returns: 2
Return value:
    2

------------------------------------------------------------

 Summary Table
------------------------------------------------------------
| Situation                        | Return Value         | Exception Behavior           |
|----------------------------------|----------------------|------------------------------|
| Exception before return          | No return            | Exception propagates         |
| Exception caught, fallback value | Fallback value       | None (handled)               |
| Exception caught, rethrown       | No return            | Exception propagates         |
| finally after return (no error)  | Return executes      | finally runs normally        |
| Exception inside finally         | Return discarded     | finally exception propagates |

------------------------------------------------------------
In short:
- If an exception happens before reaching 'return', the return never happens.
- If exception is caught, you control what to return.
- finally always runs, but if it throws, it overrides any pending return.
------------------------------------------------------------
*/

