package com.notes.strings;

public class B1_StringToPrimitiveDemo {

	public static void main(String[] args) {

		// ===============================
		// String to Primitive Conversions
		// ===============================

		// String -> boolean
		// Boolean.parseBoolean() never throws NumberFormatException
		// It returns true only if string is "true" ignoring case
		String booleanStr = "true";
		boolean booleanValue = Boolean.parseBoolean(booleanStr);
		System.out.println("boolean value   : " + booleanValue);

		// String -> char
		// No Character.parseCharacter() method exists
		// We use charAt(0) to get first character from String
		String charStr = "A";
		char charValue = charStr.charAt(0);
		System.out.println("char value      : " + charValue);

		// String -> byte
		String byteStr = "10";
		byte byteValue = Byte.parseByte(byteStr);
		System.out.println("byte value      : " + byteValue);

		// String -> short
		String shortStr = "100";
		short shortValue = Short.parseShort(shortStr);
		System.out.println("short value     : " + shortValue);

		// String -> int
		String intStr = "1000";
		int intValue = Integer.parseInt(intStr);
		System.out.println("int value       : " + intValue);

		// String -> long
		String longStr = "100000";
		long longValue = Long.parseLong(longStr);
		System.out.println("long value      : " + longValue);

		// String -> float
		String floatStr = "12.5";
		float floatValue = Float.parseFloat(floatStr);
		System.out.println("float value     : " + floatValue);

		// String -> double
		String doubleStr = "99.99";
		double doubleValue = Double.parseDouble(doubleStr);
		System.out.println("double value    : " + doubleValue);

		// =================================
		// String to Wrapper Object Versions
		// =================================

		Boolean booleanObj = Boolean.valueOf("true");
		Character charObj = Character.valueOf('A');
		Byte byteObj = Byte.valueOf("10");
		Short shortObj = Short.valueOf("100");
		Integer intObj = Integer.valueOf("1000");
		Long longObj = Long.valueOf("100000");
		Float floatObj = Float.valueOf("12.5");
		Double doubleObj = Double.valueOf("99.99");

		System.out.println("\nWrapper Objects:");
		System.out.println("Boolean object   : " + booleanObj);
		System.out.println("Character object : " + charObj);
		System.out.println("Byte object      : " + byteObj);
		System.out.println("Short object     : " + shortObj);
		System.out.println("Integer object   : " + intObj);
		System.out.println("Long object      : " + longObj);
		System.out.println("Float object     : " + floatObj);
		System.out.println("Double object    : " + doubleObj);

		// =================================
		// Auto-Unboxing Example
		// =================================

		int autoUnboxedInt = Integer.valueOf("500");
		System.out.println("\nAuto-unboxed int : " + autoUnboxedInt);

		// =================================
		// trim() Example
		// =================================

		String spacedNumber = " 123 ";
		int trimmedValue = Integer.parseInt(spacedNumber.trim());
		System.out.println("Trimmed int      : " + trimmedValue);

		// =================================
		// Boolean.parseBoolean() Examples
		// =================================

		System.out.println("\nBoolean Examples:");
		System.out.println(Boolean.parseBoolean("true"));   // true
		System.out.println(Boolean.parseBoolean("TRUE"));   // true
		System.out.println(Boolean.parseBoolean("false"));  // false
		System.out.println(Boolean.parseBoolean("abc"));    // false

		// =================================
		// Invalid Number Format Examples
		// =================================

		try {
			int invalidInt = Integer.parseInt("12a");
			System.out.println(invalidInt);
		} catch (NumberFormatException e) {
			System.out.println("\nInvalid integer format: 12a");
		}

		try {
			byte invalidByte = Byte.parseByte("200");
			System.out.println(invalidByte);
		} catch (NumberFormatException e) {
			System.out.println("Byte overflow error: 200 is outside byte range");
		}

		try {
			int decimalAsInt = Integer.parseInt("12.5");
			System.out.println(decimalAsInt);
		} catch (NumberFormatException e) {
			System.out.println("Cannot parse decimal string into int: 12.5");
		}
	}
}

/*
boolean value   : true
char value      : A
byte value      : 10
short value     : 100
int value       : 1000
long value      : 100000
float value     : 12.5
double value    : 99.99

Wrapper Objects:
Boolean object   : true
Character object : A
Byte object      : 10
Short object     : 100
Integer object   : 1000
Long object      : 100000
Float object     : 12.5
Double object    : 99.99

Auto-unboxed int : 500
Trimmed int      : 123

Boolean Examples:
true
true
false
false

Invalid integer format: 12a
Byte overflow error: 200 is outside byte range
Cannot parse decimal string into int: 12.5
*/