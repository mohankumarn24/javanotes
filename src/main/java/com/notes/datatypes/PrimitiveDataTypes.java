package com.notes.datatypes;

public class PrimitiveDataTypes {

	public static void main(String[] args) {
		
		// values overflows
		boolean booleanFlag = true;
		char charA = 'A';   					// char charA = 65;  -> char A
												// 0 -> 48, A -> 65, a -> 97
		byte byteNum = (byte) 128; 				// cannot assign 128 without casting
		short shortNum = (short) 32768;			// cannot assign 32768 without casting
		int intNum = 2147483647 + 1; 			// cannot assign 2^31=2147483648. Gives Out of Range Compile Time error
		long longNum = 100L;  					// 100 or 100L or 100l
		float floatNum = 100.0f;				// 100 or 100F or 100f	
		double doubleNum = 100.0d;				// 100 or 100D or 100d
		
		System.out.println(String.format("boolean %b", booleanFlag));
		System.out.println(String.format("char %c", charA));
		System.out.println(String.format("byte %d", byteNum));
		System.out.println(String.format("short %d", shortNum));
		System.out.println(String.format("int %d", intNum));
		System.out.println(String.format("long %d", longNum));
		System.out.println(String.format("float %f", floatNum));
		System.out.println(String.format("double %f", doubleNum));
		
		// calculate power: 2^7 = 128
		System.out.println(Math.pow(2, 7));		// 128.0
	}
}

/*
boolean true
char A
byte -128
short -32768
int -2147483648
long 100
float 100.000000
double 100.000000

128.0
*/