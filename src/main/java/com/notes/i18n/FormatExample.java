package com.notes.i18n;

import java.text.NumberFormat;
import java.text.DateFormat;
import java.util.*;

// https://chatgpt.com/share/68bb22a1-8f64-8004-b759-de6f9d605141
public class FormatExample {
	
    public static void main(String[] args) {
    	
    	// Locale locale = Locale.getDefault();
        Locale localeUS = Locale.US;
        Locale localeFR = Locale.FRANCE;

        double number = 1234567.89;
        Date today = new Date();

        NumberFormat nfUS = NumberFormat.getCurrencyInstance(localeUS);
        NumberFormat nfFR = NumberFormat.getCurrencyInstance(localeFR);

        DateFormat dfUS = DateFormat.getDateInstance(DateFormat.LONG, localeUS);
        DateFormat dfFR = DateFormat.getDateInstance(DateFormat.LONG, localeFR);

        System.out.println("US Currency: " + nfUS.format(number));
        System.out.println("FR Currency: " + nfFR.format(number));

        System.out.println("US Date: " + dfUS.format(today));
        System.out.println("FR Date: " + dfFR.format(today));
    }
}

/*
US Currency: $1,234,567.89
FR Currency: 1 234 567,89 €
US Date: September 5, 2025
FR Date: 5 septembre 2025
*/