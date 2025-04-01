package com.notes.enums;

public enum Vehicle {
	
    CAR(200, "Gasoline"),
    MOTORCYCLE(150, "Gasoline"),
    ELECTRIC_CAR(180, "Electric"),
    BICYCLE(30, "N/A"),
    TRUCK(120, "Diesel");

    private final int maxSpeed;    // Maximum speed of the vehicle
    private final String fuelType; // Fuel type of the vehicle

    // Constructor for the enum
    Vehicle(int maxSpeed, String fuelType) {
        this.maxSpeed = maxSpeed;
        this.fuelType = fuelType;
    }

    // Getter methods
    public int getMaxSpeed() {
        return maxSpeed;
    }

    public String getFuelType() {
        return fuelType;
    }

    // Method to display the vehicle's information
    public String getVehicleInfo() {
    	return String.format("%s: maxSpeed=%d kmph and fuelType=%s", this.name(), maxSpeed, fuelType);
    }
}
