package com.notes.i18n;

import java.util.*;
import java.text.*;

// https://chatgpt.com/share/68bb22a1-8f64-8004-b759-de6f9d605141
public class I18nFormattingExample {
	
    public static void main(String[] args) {
    	
        // Locales
        Locale[] locales = {Locale.US, Locale.FRANCE, Locale.JAPAN};

        // Sample values
        Date now = new Date();
        double number = 1234567.89;
        double balance = 98765.43;

        for (Locale locale : locales) {
            System.out.println("Locale: " + locale);

            // Date formatting
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
            System.out.println("Formatted Date: " + dateFormat.format(now));

            // Time formatting
            DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.LONG, locale);
            System.out.println("Formatted Time: " + timeFormat.format(now));

            // Number formatting
            NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
            System.out.println("Formatted Number: " + numberFormat.format(number));

            // Currency formatting
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
            System.out.println("Formatted Currency: " + currencyFormat.format(balance));

            System.out.println("------------------------------");
        }
    }
}

/*
Locale: en_US
Formatted Date: September 5, 2025
Formatted Time: 11:17:01 PM IST
Formatted Number: 1,234,567.89
Formatted Currency: $98,765.43
------------------------------
Locale: fr_FR
Formatted Date: 5 septembre 2025
Formatted Time: 23:17:01 IST
Formatted Number: 1 234 567,89
Formatted Currency: 98 765,43 €
------------------------------
Locale: ja_JP
Formatted Date: 2025年9月5日
Formatted Time: 23:17:01 IST
Formatted Number: 1,234,567.89
Formatted Currency: ￥98,765
------------------------------
*/