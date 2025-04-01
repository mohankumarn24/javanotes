package com.notes.enums;

public class EnumDemo {

	public static void main(String[] args) {
		
		Day d1 = Day.MONDAY;
		System.out.println("Test: " + d1);

	    // Loop through all enum values and print their message
		System.out.println();
	    for (Day day : Day.values()) {
	        System.out.println(day + ": " + day.getMessage());
	    }
	    
        // Loop through all enum values and display their info
	    System.out.println();
        for (Vehicle vehicle : Vehicle.values()) {
            System.out.println(vehicle.getVehicleInfo());
        }
	}
}
