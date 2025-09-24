package com.notes.regex;

import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;

public class RegexPractice {

    // ---------- Beginner Level ----------

    // 1. Validate Email
	// test@example.com
    public static boolean isValidEmail(String email) {
        String pattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(pattern);
    }

    // 2. Validate Phone Number (123-456-7890, (123) 456-7890, 1234567890)
    // (123) 456-7890
    public static boolean isValidPhone(String phone) {
        String pattern = "^(\\(\\d{3}\\)\\s?|\\d{3}-?)\\d{3}-?\\d{4}$";
        return phone.matches(pattern);
    }

    // 3. Extract Numbers from String
    // I have 2 apples and 15 oranges --> [2, 15]
    public static List<String> extractNumbers(String text) {
        List<String> numbers = new ArrayList<>();
        Matcher m = Pattern.compile("\\d+").matcher(text);
        while (m.find()) numbers.add(m.group());
        return numbers;
    }

    // 4. Replace Multiple Spaces with Single Space
    // This   is  a   test.  -> This is a test.
    public static String normalizeSpaces(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    // 5. Check for palindrome words
    // level madam noon civic --> [level, madam, noon, civic]
    public static List<String> findPalindromes(String text) {
        List<String> palindromes = new ArrayList<>();
        Matcher m = Pattern.compile("\\b(\\w+)\\b").matcher(text);
        while (m.find()) {
            String word = m.group(1);
            if (new StringBuilder(word).reverse().toString().equals(word)) {
                palindromes.add(word);
            }
        }
        return palindromes;
    }

    // ---------- Intermediate Level ----------

    // 6. Validate Date dd/mm/yyyy or dd-mm-yyyy
    // 24/09/2025
    public static boolean isValidDate(String date) {
        String pattern = "^(0[1-9]|[12][0-9]|3[01])[-/](0[1-9]|1[0-2])[-/](19|20)\\d\\d$";
        return date.matches(pattern);
    }

    // 7. Extract URLs from Text
    // Visit https://example.com or http://test.org  --> [https://example.com, http://test.org]
    public static List<String> extractUrls(String text) {
        List<String> urls = new ArrayList<>();
        Matcher m = Pattern.compile("https?://[\\w.-]+(?:/\\S*)?").matcher(text);
        while (m.find()) urls.add(m.group());
        return urls;
    }

    // 8. Validate Password (8+ chars, upper, lower, digit, special)
    // Abc123@!
    public static boolean isValidPassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(pattern);
    }

    // 9. Split CamelCase Words
    // thisIsCamelCase --> thisIsCamelCase
    public static String[] splitCamelCase(String text) {
        return text.split("(?<!^)(?=[A-Z])");
    }

    // 10. Extract Hashtags
    // I love #Java and #Regex --> [#Java, #Regex]
    public static List<String> extractHashtags(String text) {
        List<String> hashtags = new ArrayList<>();
        Matcher m = Pattern.compile("#\\w+").matcher(text);
        while (m.find()) hashtags.add(m.group());
        return hashtags;
    }

    // ---------- Advanced Level ----------

    // 11. Validate IPv4 Address
    // 192.168.1.1
    public static boolean isValidIPv4(String ip) {
        String pattern = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}" +
                         "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
        return ip.matches(pattern);
    }

    // 12. Extract Markdown Links [text](url)
    // Check [Google](https://google.com) and [Bing](https://bing.com)
    // Text: Google, URL: https://google.com
    // Text: Bing, URL: https://bing.com
    public static List<String[]> extractMarkdownLinks(String text) {
        List<String[]> links = new ArrayList<>();
        Matcher m = Pattern.compile("\\[([^\\]]+)\\]\\(([^)]+)\\)").matcher(text);
        while (m.find()) links.add(new String[]{m.group(1), m.group(2)});
        return links;
    }

    // 13. Parse Log Lines
    // 2025-09-24 18:00:00 INFO User logged in --> 2025-09-24, 18:00:00, INFO, User logged in
    public static String[] parseLog(String log) {
        Matcher m = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2})\\s(\\d{2}:\\d{2}:\\d{2})\\s(\\w+)\\s(.+)$").matcher(log);
        if (m.find()) return new String[]{m.group(1), m.group(2), m.group(3), m.group(4)};
        return null;
    }

    // 14. HTML Tag Validator (simple, for small snippets)
    // <div><p>Test</p></div>
    public static boolean validateHTMLTags(String html) {
        String pattern = "<([a-z]+)([^<]+)*(?:>(.*)</\\1>|\\s+/>)";
        return html.matches(pattern);
    }

    // 15. Find Repeated Words
    // This is is a test test string --> [is, test]
    public static List<String> findRepeatedWords(String text) {
        List<String> repeated = new ArrayList<>();
        Matcher m = Pattern.compile("\\b(\\w+)\\b(?=.*\\b\\1\\b)").matcher(text);
        while (m.find()) {
            String word = m.group(1);
            if (!repeated.contains(word)) repeated.add(word);
        }
        return repeated;
    }

    // ---------------- Main Method ----------------
    public static void main(String[] args) {
    	
        System.out.println("Email valid: " + isValidEmail("test@example.com"));
        System.out.println("Phone valid: " + isValidPhone("(123) 456-7890"));
        System.out.println("Numbers: " + extractNumbers("I have 2 apples and 15 oranges"));
        System.out.println("Normalized spaces: '" + normalizeSpaces("This   is  a   test.") + "'");
        System.out.println("Palindromes: " + findPalindromes("level madam noon civic"));
        System.out.println("Date valid: " + isValidDate("24/09/2025"));
        System.out.println("URLs: " + extractUrls("Visit https://example.com or http://test.org"));
        System.out.println("Password valid: " + isValidPassword("Abc123@!"));
        System.out.println("CamelCase split: " + String.join(", ", splitCamelCase("thisIsCamelCase")));
        System.out.println("Hashtags: " + extractHashtags("I love #Java and #Regex"));
        System.out.println("IPv4 valid: " + isValidIPv4("192.168.1.1"));
        System.out.println("Markdown links: ");
        for (String[] link : extractMarkdownLinks("Check [Google](https://google.com) and [Bing](https://bing.com)")) {
            System.out.println("Text: " + link[0] + ", URL: " + link[1]);
        }
        String[] logParts = parseLog("2025-09-24 18:00:00 INFO User logged in");
        System.out.println("Log parsed: " + String.join(", ", logParts));
        System.out.println("HTML valid: " + validateHTMLTags("<div><p>Test</p></div>"));
        System.out.println("Repeated words: " + findRepeatedWords("This is is a test test string"));
    }
}
