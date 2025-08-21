package com.notes.lsp;

// https://chatgpt.com/share/68a6cbe7-f210-8004-88f7-2da2898a3d32
// Demo class with main method
public class BirdDemo {
	
	public static void main(String[] args) {
		
		// Using Bird reference
		Bird sparrow = new Sparrow();
		Bird penguin = new Penguin();

		sparrow.makeSound(); // ✅ "I am a Sparrow"
		penguin.makeSound(); // ✅ "I am a Penguin"

		// Using Flyable reference for flying birds
		Flyable flyingBird = new Sparrow();
		flyingBird.fly(); // ✅ "Sparrow is flying"

		// ❌ Penguin is not Flyable, so this won't even compile:
		// Flyable penguinFly = new Penguin();
	}
}

/*
------ PROBLEM ------
// Wrong Example (Violating LSP)
// Penguin is a Bird, but it cannot fly
// If client code assumes every Bird can fly, substituting Penguin will break the program

public class Bird {

    public void makeSound() {
        System.out.println("Bird is making sound");
    }
  
    public void fly() {
        System.out.println("Bird is flying");
    }
}

public class Penguin extends Bird {

    @Override
    public void makeSound() {
        System.out.println("I am a Penguin");
    }
  
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguin's can't fly!");
    }
}

------ SOLUTION ------
// Base class
public abstract class Bird {

	public abstract void makeSound();
}

// Ability interface
// We separate the abilities into proper abstractions
public interface Flyable {

	public void fly();
}

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

// A non-flying bird
public class Penguin extends Bird {
	
    @Override
    public void makeSound() {
        System.out.println("I am a Penguin");
    }
}

// Demo class with main method
public class BirdDemo {
	
	public static void main(String[] args) {
		
		// Using Bird reference
		Bird sparrow = new Sparrow();
		Bird penguin = new Penguin();

		sparrow.makeSound(); // ✅ "I am a Sparrow"
		penguin.makeSound(); // ✅ "I am a Penguin"

		// Using Flyable reference for flying birds
		Flyable flyingBird = new Sparrow();
		flyingBird.fly(); // ✅ "Sparrow is flying"

		// ❌ Penguin is not Flyable, so this won't even compile:
		// Flyable penguinFly = new Penguin();
	}
}
*/


