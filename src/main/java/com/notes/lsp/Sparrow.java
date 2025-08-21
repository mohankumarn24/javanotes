package com.notes.lsp;

// A flying bird
public class Sparrow extends Bird implements Flyable {
	
	@Override
	public void makeSound() {
		System.out.println("I am a Sparrow");
	}

	@Override
	public void fly() {
		System.out.println("Sparrow is flying");
	}
}