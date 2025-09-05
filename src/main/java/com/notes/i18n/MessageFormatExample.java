package com.notes.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

// https://chatgpt.com/share/68bb22a1-8f64-8004-b759-de6f9d605141
public class MessageFormatExample {

	public static void main(String[] args) {

		Locale localeUS = new Locale("en", "US");
		ResourceBundle bundle = ResourceBundle.getBundle("messages_message_format", localeUS);

		String pattern = bundle.getString("welcome");
		String message = MessageFormat.format(pattern, "Mohan", 5);
		System.out.println(message);
	}
}

// Hello Mohan, you have 5 new messages.