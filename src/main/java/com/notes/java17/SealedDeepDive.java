package com.notes.java17;

// 1. CLASSES

// Sealed base class
// Permitted subclasses must be in the same package or module
// sealed class A extends Thread implements Cloneable permits B, C { // 'extends' and 'implements' allowed
sealed class A permits B, C {
}

// non-sealed re-opens the hierarchy
non-sealed class B extends A {
}

// final completely closes the hierarchy
final class C extends A {
}

// Allowed because B is non-sealed
class D extends B {
}

// 2. INTERFACES

sealed interface X permits Y {
}

// Sealed interface must also declare permits
sealed interface Y extends X permits Z {
}

// Final implementation of sealed interface
final class Z implements Y {
}

public class SealedDeepDive {
	public static void main(String[] args) {
		
	}
}