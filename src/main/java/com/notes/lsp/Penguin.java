package com.notes.lsp;

// A non-flying bird
public class Penguin extends Bird {
	
    @Override
    public void makeSound() {
        System.out.println("I am a Penguin");
    }
}

/*
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
*/