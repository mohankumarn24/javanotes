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
        
        Day day2 = Day.valueOf("MONDAY");
        System.out.println("\nUsed in RequestBody: " + day2);				// User sends "monday" in request body and we must ensure that it is valid value
	}
}

/*
Enum: MONDAY
Enum name: MONDAY
Enum message: Start of the work week

MONDAY: Start of the work week
TUESDAY: Second day of the week
WEDNESDAY: Midweek
THURSDAY: Almost there
FRIDAY: End of the work week
SATURDAY: Weekend!
SUNDAY: Rest day

BICYCLE: maxSpeed=30 kmph and fuelType=N/A
MOTORCYCLE: maxSpeed=150 kmph and fuelType=Gasoline
CAR: maxSpeed=200 kmph and fuelType=Gasoline
TRUCK: maxSpeed=120 kmph and fuelType=Diesel
ELECTRIC_MOTORCYCLE: maxSpeed=100 kmph and fuelType=Electric
ELECTRIC_CAR: maxSpeed=180 kmph and fuelType=Electric

Used in RequestBody: MONDAY
*/