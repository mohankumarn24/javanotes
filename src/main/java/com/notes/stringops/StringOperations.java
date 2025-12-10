package com.notes.stringops;

import java.util.Arrays;

public class StringOperations {
	
	public static void main(String[] args) {
	
		String message = "hello world";
		
		System.out.println(message.substring(0, 6));					// "hello"
		System.out.println(message.substring(6));						// "world"
		
		System.out.println(message.indexOf("world"));					// 6
		System.out.println(message.indexOf("world 123"));				// -1
		
		System.out.println(message.compareTo("hello world"));			// 0
		System.out.println(message.compareTo("hello"));					// 6
		System.out.println(message.compareTo("hello world 123"));		// -4
		
		System.out.println("one two three".split(" "));					// ["one", "two", "three"]
		System.out.println("one, two, three".split(", "));				// ["one", "two", "three"]
		
		System.out.println(String.format("joined: %s", String.join(" ", "one", "two", "three")));						// "one two three"
		System.out.println(String.format("joined: %s", String.join(", ", "one", "two", "three")));						// "one, two, three"
		System.out.println(String.format("joined: %s", String.join(" ", Arrays.asList("one", "two", "three"))));		// "one two three"
	}
}
