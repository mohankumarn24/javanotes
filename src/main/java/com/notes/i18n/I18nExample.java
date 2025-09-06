package com.notes.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

// https://chatgpt.com/share/68bb22a1-8f64-8004-b759-de6f9d605141
public class I18nExample {

	public static void main(String[] args) {

		// Locale localeDefault = Locale.getDefault(); 		// en_US
		Locale localeUS = new Locale("en", "US"); 			// Locale localeUS = Locale.US;
		ResourceBundle bundleUS = ResourceBundle.getBundle("messages", localeUS);
		System.out.println("US Locale: " + localeUS);
		System.out.println("US Greeting: " + bundleUS.getString("greeting"));
		
		System.out.println();
		Locale localeFR = new Locale("fr", "FR"); 			// Locale localeFR = Locale.FRANCE;
		ResourceBundle bundleFR = ResourceBundle.getBundle("messages", localeFR);
		System.out.println("FR Locale: " + localeFR);
		System.out.println("FR Greeting: " + bundleFR.getString("greeting"));
	}
}

/*
US Locale: en_US
US Greeting: Hello

FR Locale: fr_FR
FR Greeting: Bonjour
 */


/*
 * Right click on project > Properties (alt + enter) > Java Builder Path > Source > Add Folder (resources). 
 * Or else, we get "Can't find bundle for base name messages" error 
 */