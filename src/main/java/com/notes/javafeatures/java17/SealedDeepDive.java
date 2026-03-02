package com.notes.javafeatures.java17;

//============================================================
//SEALED CLASSES & INTERFACES — COMPLETE, CORRECT DEMO
//============================================================

//─────────────────────────────────────────────
//1. SEALED CLASS HIERARCHY
//─────────────────────────────────────────────

//Sealed base class
//- Can extend ONE class and implement interfaces
//- Permitted subclasses must be in same package or module
//sealed class A extends Thread implements Cloneable permits B, C {}
sealed class A permits B, C {
}

//non-sealed explicitly RE-OPENS the hierarchy
//Anyone can extend B now
non-sealed class B extends A {
}

//final completely CLOSES the hierarchy
//No subclass of C allowed
final class C extends A {
}

//Allowed ONLY because B is non-sealed
class D extends B {
}

//❌ NOT allowed
//class E extends C {}   // Compile-time error (C is final)

//─────────────────────────────────────────────
//2. SEALED INTERFACE HIERARCHY (CORRECTED)
//─────────────────────────────────────────────

//Sealed interface
//Controls ONLY its direct subtypes
sealed interface X permits Y, P {
}

//Sealed interface extending another sealed interface
//NOTE: Since Y extends X, Y MUST be listed in X's permits
sealed interface Y extends X permits Z {
}

//Class implementing sealed interface
//MUST be final / sealed / non-sealed
final class Z implements Y {
}

//❌ NOT allowed
//class W implements Y {}   // Compile-time error (must declare final/sealed/non-sealed)

//─────────────────────────────────────────────
//3. INTERFACE → INTERFACE (SEALED RULE)
//─────────────────────────────────────────────

//✔ Interfaces implementing a sealed interface
//MUST be declared as sealed or non-sealed
sealed interface P extends X permits Q {
}

//✔ Allowed
non-sealed interface Q extends P {
}

//❌ NOT allowed
//final interface R extends X {}   // Compile-time error (interfaces cannot be final)

//─────────────────────────────────────────────
//4. NESTED SEALED TYPES (NO permits NEEDED)
//─────────────────────────────────────────────

sealed class Vehicle {

	// Nested permitted subtypes
	// No explicit permits clause required
	final class Car extends Vehicle {
	}

	final class Bike extends Vehicle {
	}
}

//❌ NOT allowed
//class Truck extends Vehicle {}   // Compile-time error

//─────────────────────────────────────────────
//5. RECORDS + SEALED (COMMON INTERVIEW TRAP)
//─────────────────────────────────────────────

//Records are implicitly final
//They CAN be permitted subtypes
sealed interface Payment permits Card, Cash {
}

//✔ Valid — records are implicitly final
record Card(String number) implements Payment {
}

//✔ Valid
record Cash(double amount) implements Payment {
}

//❌ NOT allowed
//non-sealed record UPI(String id) implements Payment {}
//❌ Records cannot be sealed or non-sealed

//─────────────────────────────────────────────
//6. ABSTRACT + SEALED (VERY IMPORTANT)
//─────────────────────────────────────────────

//sealed controls WHO can extend
//abstract controls WHETHER it can be instantiated
sealed abstract class Shape permits Circle, Rectangle {
	abstract double area();
}

//final subtype
final class Circle extends Shape {
	double area() {
		return Math.PI * 5 * 5;
	}
}

//non-sealed subtype re-opens hierarchy
non-sealed class Rectangle extends Shape {
	double area() {
		return 10 * 5;
	}
}

//Allowed because Rectangle is non-sealed
class Square extends Rectangle {
	double area() {
		return 4 * 4;
	}
}


//============================================================
//SUMMARY (INTERVIEW GOLD)
//============================================================
/*
* 1. Sealed types restrict ONLY direct subtypes.
*
* 2. Every direct subtype MUST declare:
*      - final
*      - sealed
*      - non-sealed
*
* 3. Classes implementing sealed interfaces:
*      - final | sealed | non-sealed
*
* 4. Interfaces implementing sealed interfaces:
*      - sealed | non-sealed (final NOT allowed)
*
* 5. Records:
*      - implicitly final
*      - allowed as permitted subtypes
*      - cannot be sealed or non-sealed
*
* 6. Nested permitted types:
*      - do NOT require explicit permits clause
*
* INTERVIEW ONE-LINER:
* "Sealed types give compile-time control over inheritance,
*  and every direct subtype must explicitly declare its openness."
*/


public class SealedDeepDive {
 public static void main(String[] args) {
     // No runtime logic needed — sealed is a compile-time feature
 }
}


/*
// 1. CLASSES
// Sealed base class
// Permitted subclasses must be in the same package or module
// sealed class A extends Thread implements Cloneable permits B, C { // 'extends' and 'implements' allowed
sealed class A permits B, C {
}

// non-sealed re-opens the hierarchy. Any class can now extend B
non-sealed class B extends A {
}

// final completely closes the hierarchy. No subclass of C allowed
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

// Class implementing a sealed interface. Must be final / sealed / non-sealed
final class Z implements Y {
}

public class SealedDeepDive {
	public static void main(String[] args) {
		
	}
}
*/