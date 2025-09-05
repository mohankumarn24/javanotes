package com.notes.i18n;

import java.util.*;
import java.text.MessageFormat;
import java.text.*;

// https://chatgpt.com/share/68bb22a1-8f64-8004-b759-de6f9d605141
public class Main {
	
    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose language (en/fr): ");
        String lang = scanner.nextLine().trim().toLowerCase();

        Locale locale = lang.equals("fr") ? new Locale("fr", "FR") : new Locale("en", "US");
        ResourceBundle bundle = ResourceBundle.getBundle("messages_main", locale);

        // Ask user name
        System.out.println(bundle.getString("askName"));
        String name = scanner.nextLine().trim();

        // Greeting and Farewell
        String greeting = MessageFormat.format(bundle.getString("greeting"), name);
        String farewell = MessageFormat.format(bundle.getString("farewell"), name);
        System.out.println(greeting);

        // Date formatting
        Date today = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(today);
        System.out.println(MessageFormat.format(bundle.getString("todayDate"), formattedDate));

        // Currency formatting
        double balance = 12345.67;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedBalance = currencyFormat.format(balance);
        System.out.println(MessageFormat.format(bundle.getString("accountBalance"), formattedBalance));

        System.out.println(farewell);
        scanner.close();
    }
}

/*
Choose language (en/fr): en
What is your name?
Mohan
Hello Mohan!
Today is September 5, 2025.
Your account balance is $12,345.67.
Goodbye Mohan!


Choose language (en/fr): fr
Quel est votre nom ?
Mohan
Bonjour Mohan!
Aujourd'hui c'est 5 septembre 2025.
Votre solde de compte est 12 345,67 €.
Au revoir Mohan!
*/