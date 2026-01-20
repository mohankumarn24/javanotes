package com.notes.strings;

import java.util.Arrays;

public class StringOperations {
	
	public static void main(String[] args) {
	
		/**
		 * Note: 
		 *  Never use '==' to compare wrapper objects
		 *  	Ex: Integer x = 200;
		 *  		Integer y = 200;
		 *  		System.out.println(x == y); // true (objects)
		 *  
		 *  		int x = 200;
		 *  		int y = 200;
		 *  		System.out.println(x == y); // true (primitive values)
		 *  
		 *  == compares memory references
		 *  equals() compares values
		 *  
		 *  Integer objects are cached from -128 to 127, causing '==' to behave inconsistently
		 */
		
		System.out.println("Bearer <token>".substring(0, 7));			// "Bearer "
		System.out.println("Bearer <token>".substring(7));				// "<token>"
		
		String message = "hello world";
		
		// 1. substring
		System.out.println("\n1. substring");
		System.out.println(message.substring(0, 6));					// "hello"
		System.out.println(message.substring(6));						// "world"
		
		// 2. indexOf
		System.out.println("\n2. indexOf");
		System.out.println(message.indexOf("world"));					// 6
		System.out.println(message.indexOf("world 123"));				// -1
		
		// 3. compareTo (compareToIgnoreCase)							// equals, equalsIgnoreCase, compareTo, compareToIgnoreCase
		System.out.println("\n3. compareTo");
		System.out.println(message.compareTo("hello world"));			// 0
		System.out.println(message.compareTo("hello"));					// 6
		System.out.println(message.compareTo("hello world 123"));		// -4
		
		// 4. split
		System.out.println("\n4. split");
		System.out.println("one two three".split(" "));					// ["one", "two", "three"]
		System.out.println("one, two, three".split(", "));				// ["one", "two", "three"]
		
		// 5. join
		System.out.println("\n5. join");
		System.out.println(String.format("joined: %s", String.join(" ", "one", "two", "three")));						// "one two three"
		System.out.println(String.format("joined: %s", String.join(", ", "one", "two", "three")));						// "one, two, three"
		System.out.println(String.format("joined: %s", String.join(" ", Arrays.asList("one", "two", "three"))));		// "one two three"
		
		// 6. Immutability -> Strings are immutable
		System.out.println("\n6. Immutability -> Strings are immutable");
		String s6 = "hello";
		s6.concat(" world");
		System.out.println(s6);          								// "hello" (unchanged)

		s6 = s6.concat(" world");
		System.out.println(s6);         								// "hello world"
		
		// 7. 'equals' vs '=='  -> Always use equals() for content comparison
		System.out.println("\n7. 'equals' vs '=='  -> Always use equals() for content comparison");
		String a = "java";
		String b = "java";
		String c = new String("java");
		String d = c.intern();

		System.out.println(a == b);         							// true (string pool)
		System.out.println(a == c);         							// false (heap)
		System.out.println(a.equals(c));    							// true
		System.out.println(a == d);         							// true (string pool)

		// 8. isEmpty vs isBlank (Java 11+)
		System.out.println("\n8. isEmpty vs isBlank (Java 11+)");
		String s82 = "   ";
		String s81 = "";

		System.out.println(s82.isBlank());   							// true
		System.out.println(s82.isEmpty());   							// false
		System.out.println(s81.isEmpty());   							// true	

		// 9. trim vs strip
		System.out.println("\n9. trim vs strip");
		String s9 = "  hello  ";

		// both statements prints "hello"
		System.out.println(s9.trim());   								// removes ASCII spaces
		System.out.println(s9.strip());  								// removes Unicode spaces
		
		// 10. contains, startsWith, endsWith
		System.out.println("\n10. contains, startsWith, endsWith");
		String msg = "hello world";

		System.out.println(msg.contains("world"));     					// true
		System.out.println(msg.startsWith("hello"));   					// true
		System.out.println(msg.endsWith("world"));     					// true
		
		// 11. replace vs replaceAll		-> replaceAll() uses regex
		System.out.println("\n11. replace vs replaceAll		-> replaceAll() uses regex");
		String s111 = "a1b2c3";

		System.out.println(s111.replace("1", "X"));        				// aXb2c3
		System.out.println(s111.replaceAll("\\d", "X"));   				// aXbXcX

		// 12. split edge cases
		System.out.println("\n12. split edge cases");
		String s121 = "one,,two,";

		System.out.println(Arrays.toString(s121.split(",")));      		// ["one", "", "two"]
		System.out.println(Arrays.toString(s121.split(",", -1))); 		// ["one", "", "two", ""]
		
		// 13. StringBuilder vs String (performance)
		System.out.println("\n13. StringBuilder vs String (performance)");
		String s131 = "";
		for (int i = 0; i < 5; i++) {
		    s131 += i;               									// bad (creates many objects)
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
		    sb.append(i);         										// good
		}
		System.out.println(sb.toString());
		
		// 14. String.valueOf (null-safe conversion)
		System.out.println("\n14. String.valueOf (null-safe conversion)");
		Integer s141 = null;
		System.out.println(String.valueOf(s141));   					// "null"
		
		// 15. String -> int
		System.out.println("\n15. String -> int");
		String numStr = "123";
		int num = Integer.parseInt(numStr);								// String -> int 							=> Integer.parseInt("123");
		System.out.println(num);

		// 16. String -> Integer
		System.out.println("\n16. String -> Integer");
		Integer numObj = Integer.valueOf(numStr);						// String -> Integer						=> Integer.valueOf("123");
		System.out.println(numObj);

		// 17. int -> String; Integer -> String
		System.out.println("\n17. int -> String; Integer -> String");
		int value = 456;
		String valueStr = String.valueOf(value);						// int -> String; Integer -> String			=> String.valueOf(456);
		// String valueStr = Integer.toString(value);					// int -> String
		System.out.println(valueStr);

		// 18. Handling invalid number
		System.out.println("\n18. Handling invalid number");
		String invalid = "12a";
		try {
			Integer.parseInt(invalid);
		} catch (NumberFormatException e) {
			System.out.println("Invalid number format");
		}

		// 19. Trimming before conversion
		System.out.println("\n19. Trimming before conversion");
		String spacedNumber = " 789 ";
		int trimmed = Integer.parseInt(spacedNumber.trim());
		System.out.println(trimmed);
		
		// 20. Note: Always use '.equals'
		Integer intA = Integer.valueOf("100");
		Integer intB = Integer.valueOf("100");

		System.out.println(intA == intB);        						// true (cached). Java caches Integer objects from -128 to 127. So, both 'intA' and 'intB' point to the same cached object
		System.out.println(intA.equals(intB));   						// true

		Integer intC = Integer.valueOf("200");
		Integer intD = Integer.valueOf("200");

		System.out.println(intC == intD);        						// false. No cache here → two different objects
	}
}
