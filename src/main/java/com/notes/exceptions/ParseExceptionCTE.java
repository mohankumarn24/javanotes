package com.notes.exceptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// - Run this class
// - Exception occurred during compile time. Cannot run the class. Solution: use try-catch or throws
// - Checked exceptions are the exceptions that are checked at compile-time. This means that the compiler verifies that the code handles these
//   exceptions either by catching them or declaring them in the method signature using the throws keyword. 
public class ParseExceptionCTE  {

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
			System.out.println(dateCTEhandled);  // Sun Jan 01 00:00:00 IST 2023
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Date dateCTENotHandled = dateFormat.parse(dateString);
		/*
			Exception in thread "main" java.lang.Error: Unresolved compilation problem: 
					Unhandled exception type ParseException
					at com.notes.exceptions.ParseExceptionCTE.main(ParseExceptionCTE.java:23)
		*/
	}
}	


