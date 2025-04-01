package com.notes.exceptions;

public class RunTimeException {

    private static void methodC() {
    	
        System.out.println("Method C start");
        // System.out.println("Method C");
        // RT Exception handled automatically. Adding try-catch/throws is optional. But it's compulsory for CT exceptions
        System.out.println(Integer.valueOf(5/0));
		// throw new RuntimeException(); -> no need to add try-catch/throws
        System.out.println("Method C end");
    }

    private static void methodB() {
    	
        System.out.println("Method B start");
        methodC(); // Adding try-catch/throws is optional. But it's compulsory for CT exceptions
        System.out.println("Method B end");
    }

    private static void methodA() {
    	
        System.out.println("Method A start");
        methodB(); // Adding try-catch/throws is optional. But it's compulsory for CT exceptions
        System.out.println("Method A end");
    }
    
    public static void main(String[] args) {
    	
        System.out.println("Main start");
        // methodA(); // Adding try-catch/throws is optional. But it's compulsory for CT exceptions. Exception propogates for RT exception
        try {
			methodA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Main end");
    }
}
