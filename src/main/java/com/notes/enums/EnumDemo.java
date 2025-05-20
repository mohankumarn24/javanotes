package com.notes.enums;

public class EnumDemo {

	public static void main(String[] args) {
		
		Day day1 = Day.MONDAY;
		System.out.println("Enum: " + day1);    							// day1, day1.name(), day1.toString(), Day.valueOf("MONDAY") all return "MONDAY"
		// System.out.println("Enum name: " + day1.name()); 				// Most programmers should use the toString method inpreference to this one, as the toString method may returna more user-friendly name.
		System.out.println("Enum name: " + day1.toString());				// preferred
		// System.out.println("Enum name: " + Day.valueOf("MONDAY")); 		// returns MONDAY. Only values provided in enum is accepted
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
