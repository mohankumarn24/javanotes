package com.notes.stringops;

public class StringOperations {
	
	public static void main(String[] args) {
	
		String message = "hello world";
		System.out.println(message.substring(0, 6));			// hello
		System.out.println(message.substring(6));				// world
		System.out.println(message.indexOf("world"));			// 6
	}
}
