package com.notes.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexEmailExample {

	public static void main(String[] args) {

		// Define the regular expression for an email
		String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		String digitPattern = "^[0-9]+$";

		testRegex(emailPattern, "test@gmail.com");
		testRegex(emailPattern, "test@gmail");
		
		testRegex(digitPattern, "123");
		testRegex(digitPattern, "a1");
		
		anotherExample();
	}

	private static void testRegex(String patternString, String text) {

		// Create a Pattern object
		Pattern pattern = Pattern.compile(patternString);

		// Create a matcher object
		Matcher matcher = pattern.matcher(text);

		// boolean matches = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE).matcher(text).matches();
		
		// Check if the email matches the pattern
		if (matcher.matches()) {
			System.out.println(String.format("PASS: %s matched with regex: ", text));
		} else {
			System.out.println(String.format("FAIL: %s not matched with regex: ", text));
		}
	}
	
    public static void anotherExample() {
    	
    	System.out.println();
        String text = "This is a sample text with numbers 123 and 456.";
        System.out.println(String.format("Text: %s", text));
        String patternString = "\\d+"; // matches one or more digits

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        // to find all matches
        while (matcher.find()) {
            System.out.println("Found match: " + matcher.group()); // to retrieve the matched substrings
        }

        // to replace all matches with the string "numbers"
        String replacementText = "********";
        System.out.println(String.format("Replaced text: %s", matcher.replaceAll(replacementText)));

        // to check if the input string contains the word "sample"
        String patternString2 = "sample";
        boolean doesMatch = Pattern.compile(patternString2).matcher(text).find();
        System.out.println(String.format("Does the text contain %s: %b", patternString2, doesMatch));
    }
}

/*
	String phoneRegex = "^\\(\\d{3}\\) \\d{3}-\\d{4}$";
	Ex:  (555) 123-4567
	
	^		: Start of the string
	\\(		: Match the literal opening parenthesis (
	\\d{3}	: Match exactly three digits
	\\)		: Match the literal closing parenthesis )
	\\d{3}	: Match three digits
	-		: Match the literal hyphen
	\\d{4}$	: Match four digits at the end of the string
*/

/*
	String pattern = "^[0-9]+$"; 
	Matches a string with one or more digits only
*/
