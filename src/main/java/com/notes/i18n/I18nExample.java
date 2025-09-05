package com.notes.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

// https://chatgpt.com/share/68bb22a1-8f64-8004-b759-de6f9d605141
public class I18nExample {

	public static void main(String[] args) {

		Locale localeUS = new Locale("en", "US");
		Locale localeFR = new Locale("fr", "FR");

		ResourceBundle bundleUS = ResourceBundle.getBundle("messages", localeUS);
		ResourceBundle bundleFR = ResourceBundle.getBundle("messages", localeFR);

		System.out.println("US Greeting: " + bundleUS.getString("greeting"));
		System.out.println("FR Greeting: " + bundleFR.getString("greeting"));
	}
}

/*
US Greeting: Hello
FR Greeting: Bonjour
 */


/*
 * Right click on project > Properties (alt + enter) > Java Builder Path > Source > Add Folder (resources). 
 * Or else, we get "Can't find bundle for base name messages" error 
 */