package com.notes.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

// https://chatgpt.com/share/68bb22a1-8f64-8004-b759-de6f9d605141
public class I18nExample {

	public static void main(String[] args) {

		// Locale localeDefault = Locale.getDefault();	// en_US
		// Locale localeUS = Locale.US;					// Use in-built Locale

		// Locale locale = new Locale("fr", "FR");
		Locale locale = new Locale("en", "US"); 		// Create any custom Locale using constructor 	
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

		String WelcomeMessagePattern = bundle.getString("welcome");
		System.out.println("Welcome message  : " + MessageFormat.format(WelcomeMessagePattern, "Mohan", 5));
		
		String farewellMessage = bundle.getString("farewell");
		System.out.println("Farewell message : " + farewellMessage);
	}
}

/*
Welcome message  : Hello Mohan, you have 5 new messages.
Farewell message : Goodbye

Welcome message  : Bonjour Mohan, vous avez 5 nouveaux messages.
Farewell message : Au revoir
*/


/*
 * Right click on project > Properties (alt + enter) > Java Builder Path > Source > Add Folder (resources). 
 * Or else, we get "Can't find bundle for base name messages" error 
 */