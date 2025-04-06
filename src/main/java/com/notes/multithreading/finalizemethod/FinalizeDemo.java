package com.notes.multithreading.finalizemethod;

public class FinalizeDemo {

    @Override
    protected void finalize()
    {
        System.out.println("-- Inside finalize method --");
        System.out.println("Performing clean-up before destroying the object");
        System.out.println("-- Release and close connections -- ");
    }
	
	public static void main(String[] args) {
		
		FinalizeDemo finalizeDemo = new FinalizeDemo(); 	// Creating object finalizeDemo of class FinalizeDemo
		finalizeDemo = null;								// Unrefrencing the object finalizeDemo
        System.gc(); 										// Calling garbage collector to destroy finalizeDemo
        System.out.println("Unreferenced object finalizeDemo is destroyed successfully!");
	}
}
