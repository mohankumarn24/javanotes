package com.notes.enums;

public enum Day {
	
    MONDAY("Start of the work week"),
    TUESDAY("Second day of the week"),
    WEDNESDAY("Midweek"),
    THURSDAY("Almost there"),
    FRIDAY("End of the work week"),
    SATURDAY("Weekend!"),
    SUNDAY("Rest day");

    private final String message; // field to store the message

    /**
     * This prevents instantiation outside the enum and ensures a fixed set of instances
     * It allows custom initialization of constants while still maintaining type safety and singleton behavior
     * Java implicitly makes the constructor of an enum private
     */
    // Constructor for the enum
    // Day (String message) {
    private Day (String message) {
        this.message = message;
    }

    // Method to get the message for the day
    public String getMessage() {
        return message;
    }
}