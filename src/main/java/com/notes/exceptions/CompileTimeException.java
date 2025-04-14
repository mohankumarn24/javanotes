package com.notes.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileTimeException {

    @SuppressWarnings("unused")
	private static void methodExtra1() throws IOException {
    	
        System.out.println("Method methodExtra1 start");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
			bufferedReader.readLine(); // throws exception here. Adding try-catch/throws is compulsory. Used throws
		} catch (IOException e) {
			// e.printStackTrace();
			// throw new IOException(); // -> must add try-catch/throws
		} finally {
			bufferedReader.close(); // use this statement within try-catch or use throws. Used throws
		}
        System.out.println("Method methodExtra1 end");
    }
    
    @SuppressWarnings("unused")
	private static void methodExtra2() throws IOException {
    	
        System.out.println("Method methodExtra2 start");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
        	bufferedReader.readLine();
        } catch (IOException e) {
			e.printStackTrace();
			// throw new IOException(); // -> must add try-catch/throws
			// if we throw CT Exception, we must use try-catch or throws. It is not required for throwing RT exception (ArithmeticException())
		}
        System.out.println("Method methodExtra2 end");
    }
    
    @SuppressWarnings("unused")
	private static void methodExtra3() throws IOException {
    	
        System.out.println("Method methodExtra3 start");
        try {
			int x = 5/0;
		} catch (ArithmeticException e) {
			System.out.println("ArithmeticException in methodExtra3");
			return; // even thoigh it returns, finally block is still executed.
		} finally {
			System.out.println("finally block executed in methodExtra3");
		}
        System.out.println("Method methodExtra3 end"); // not executed on return in exception block
    }
    
    private static void methodC() throws IOException, NumberFormatException {
    	
        System.out.println("Method C start");
        // System.out.println("Method C");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        // Exception is thrown in below line. It is not propogated either for throws, but it is propogated for try-catch
        // System.out.println("Entered value: " + Integer.parseInt(bufferedReader.readLine()));
        try {
			System.out.println("Entered value: " + Integer.parseInt(bufferedReader.readLine()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // throws exception here. Adding try-catch/throws is compulsory. Used throws
        System.out.println("Method C end");
    }

    private static void methodB() throws IOException {
    	
        System.out.println("Method B start");
        methodC(); // Adding try-catch/throws is compulsory. Used throws
        System.out.println("Method B end");
    }

    private static void methodA() throws IOException {
    	
        System.out.println("Method A start");
        methodB(); // Adding try-catch/throws is compulsory. Used throws
        System.out.println("Method A end");
    }
    
    // Note: By default, Checked Exceptions are not forwarded in calling chain (propagated).
    public static void main(String[] args) throws IOException {
    	
        System.out.println("Main start");
        // methodA(); // Adding try-catch/throws is compulsory. Used throws
        try {
        	methodA(); // Adding try-catch/throws is compulsory. Used try-catch block
        } catch (IOException e) {
        	System.out.println("Exception occurred");
        }
        
        // methodExtra3();
        System.out.println("Main end");
    }
}

/*
 * Rule: If the superclass method does not declare an exception
 *  - subclass overridden method cannot declare the checked exception but can declare unchecked exception
 * Rule: If the superclass method declares an exception
 * 	- subclass overridden method can declare the same subclass exception or no exception but cannot declare parent exception
 * Rule: For each try block there can be zero or more catch blocks, but only one finally block
 * Rule: The finally block will not be executed if the program exits (either by calling System.exit() or by causing a fatal error that causes the process to abort)
 * Rule: If we throw an unchecked exception from a method, it is not required to handle the exception or declare it in throws clause. However, for checked exceptions, handling or declaration in the throws clause is mandatory."
 * Rule: Every subclass of Error and RuntimeException is an unchecked exception in Java. A checked exception is everything else under the Throwable class
 * Rule: By default Unchecked Exceptions are forwarded in calling chain (propagated)
 * Rule: By default, Checked Exceptions are not forwarded in calling chain (propagated)
 * Rule: If we are calling a method that declares an exception, we must either caught or declare the exception
 */