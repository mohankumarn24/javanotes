package com.notes.i18n;

import java.util.Locale;

// https://chatgpt.com/share/68bb22a1-8f64-8004-b759-de6f9d605141
public class LocaleExample {

	public static void main(String[] args) {

		Locale localeUS = new Locale("en", "US");
		Locale localeFR = new Locale("fr", "FR");

		System.out.println("US Locale: " + localeUS);
		System.out.println("FR Locale: " + localeFR);
	}
}

/*
US Locale: en_US
FR Locale: fr_FR
*/