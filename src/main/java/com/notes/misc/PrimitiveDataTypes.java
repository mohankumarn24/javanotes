package com.notes.misc;

public class PrimitiveDataTypes {

	public static void main(String[] args) {
		
		primitiveDataTypes();
	}
	
	private static void primitiveDataTypes() {
		
		// values overflows
		boolean booleanFlag = true;
		char charA = 'A';   				// alphabetA = 65;
		byte byteNum = (byte) 128; 			// cannot assign 128 without casting
		short shortNum = (short) 32768;		// cannot assign 32768 without casting
		int intNum = 2147483647 + 1; 		// cannot assign 2^31=2147483648. Gives Out of Range Compile Time error
		long longNum = 100L;  				// 100 or 100L or 100l
		float floatNum = 100f;				// 100 or 100F or 100f	
		double doubleNum = 100d;			// 100 or 100D or 100d
		
		System.out.println(String.format("boolean %b", booleanFlag));
		System.out.println(String.format("char %c", charA));
		System.out.println(String.format("byte %d", byteNum));
		System.out.println(String.format("short %d", shortNum));
		System.out.println(String.format("int %d", intNum));
		System.out.println(String.format("long %d", longNum));
		System.out.println(String.format("float %f", floatNum));
		System.out.println(String.format("double %f", doubleNum));
	}
}
