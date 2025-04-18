package com.notes.enums;

public class EnumDemo {

	public static void main(String[] args) {
		
		Day day1 = Day.MONDAY;
		System.out.println("Enum: " + day1);
		System.out.println("Enum name: " + day1.name());
		// System.out.println("Enum name: " + Day.valueOf("MONDAY")); // returns MONDAY
		System.out.println("Enum message: " + day1.getMessage());

	    // Loop through all enum values and print their message
		System.out.println();
	    for (Day day : Day.values()) {
	        // System.out.println(day + ": " + day.getMessage());
	    	System.out.println(day.name() + ": " + day.getMessage());
	    }
	    
        // Loop through all enum values and display their info
	    System.out.println();
        for (Vehicle vehicle : Vehicle.values()) {
            System.out.println(vehicle.getVehicleInfo());
        }
	}
}
