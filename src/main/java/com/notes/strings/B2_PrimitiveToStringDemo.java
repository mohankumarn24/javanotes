package com.notes.strings;

public class B2_PrimitiveToStringDemo {

	public static void main(String[] args) {

		// ===============================
		// Primitive Values
		// ===============================

		boolean booleanValue = true;
		char charValue = 'A';
		byte byteValue = 10;
		short shortValue = 100;
		int intValue = 1000;
		long longValue = 100000L;
		float floatValue = 12.5f;
		double doubleValue = 99.99;

		// ==========================================
		// Primitive -> String using String.valueOf()
		// ==========================================

		String booleanStr = String.valueOf(booleanValue);
		String charStr = String.valueOf(charValue);
		String byteStr = String.valueOf(byteValue);
		String shortStr = String.valueOf(shortValue);
		String intStr = String.valueOf(intValue);
		String longStr = String.valueOf(longValue);
		String floatStr = String.valueOf(floatValue);
		String doubleStr = String.valueOf(doubleValue);

		System.out.println("boolean to String : " + booleanStr);
		System.out.println("char to String    : " + charStr);
		System.out.println("byte to String    : " + byteStr);
		System.out.println("short to String   : " + shortStr);
		System.out.println("int to String     : " + intStr);
		System.out.println("long to String    : " + longStr);
		System.out.println("float to String   : " + floatStr);
		System.out.println("double to String  : " + doubleStr);

		// ==========================================
		// Primitive -> String using Wrapper Methods
		// ==========================================

		String booleanStr2 = Boolean.toString(booleanValue);
		String charStr2 = Character.toString(charValue);
		String byteStr2 = Byte.toString(byteValue);
		String shortStr2 = Short.toString(shortValue);
		String intStr2 = Integer.toString(intValue);
		String longStr2 = Long.toString(longValue);
		String floatStr2 = Float.toString(floatValue);
		String doubleStr2 = Double.toString(doubleValue);

		System.out.println("\nUsing Wrapper toString() Methods:");
		System.out.println("Boolean.toString()  : " + booleanStr2);
		System.out.println("Character.toString(): " + charStr2);
		System.out.println("Byte.toString()     : " + byteStr2);
		System.out.println("Short.toString()    : " + shortStr2);
		System.out.println("Integer.toString()  : " + intStr2);
		System.out.println("Long.toString()     : " + longStr2);
		System.out.println("Float.toString()    : " + floatStr2);
		System.out.println("Double.toString()   : " + doubleStr2);

		// ==========================================
		// Primitive -> String using Concatenation
		// ==========================================

		String intConcat = intValue + "";
		String doubleConcat = doubleValue + "";

		System.out.println("\nUsing Concatenation:");
		System.out.println("int + \"\"      : " + intConcat);
		System.out.println("double + \"\"   : " + doubleConcat);

		// ==========================================
		// Notes
		// ==========================================

		// String.valueOf() is most commonly used
		// WrapperClass.toString() is also clean and explicit
		// Concatenation with "" works but is less preferred
	}
}

/*
boolean to String : true
char to String    : A
byte to String    : 10
short to String   : 100
int to String     : 1000
long to String    : 100000
float to String   : 12.5
double to String  : 99.99

Using Wrapper toString() Methods:
Boolean.toString()  : true
Character.toString(): A
Byte.toString()     : 10
Short.toString()    : 100
Integer.toString()  : 1000
Long.toString()     : 100000
Float.toString()    : 12.5
Double.toString()   : 99.99

Using Concatenation:
int + ""      : 1000
double + ""   : 99.99

*/