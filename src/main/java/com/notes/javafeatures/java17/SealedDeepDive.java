package com.notes.javafeatures.java17;

/*
 * ============================================================
 * SEALED COMPLETE DEMO — CLEAN & ORDERED
 * ============================================================
 *
 * Order:
 * 1. Sealed CLASS → final | sealed | non-sealed
 * 2. Sealed INTERFACE → INTERFACE
 * 3. Sealed INTERFACE → CLASS
 * 4. Nested sealed (no explicit permits needed)
 * 5. Sealed + Abstract
 *
 */

// ============================================================
// 1️. Sealed CLASS extended by CLASS
//    → final | sealed | non-sealed
// ============================================================

sealed class User permits Admin, Employee, Guest {}

// final → completely closed
final class Admin extends User {}

// sealed → restrict further
sealed class Employee extends User permits Manager {}

// non-sealed → reopen hierarchy
non-sealed class Guest extends User {}

final class Manager extends Employee {}

// allowed because Guest is non-sealed
class TemporaryGuest extends Guest {}


// ============================================================
// 2️. Sealed INTERFACE extended by INTERFACE
//    → sealed | non-sealed
// ============================================================

sealed interface TransactionState permits Pending, Completed {}

// extending sealed interface → must be sealed or non-sealed
sealed interface Pending extends TransactionState permits AwaitingApproval {}

// non-sealed interface → reopens hierarchy
non-sealed interface Completed extends TransactionState {}

final class AwaitingApproval implements Pending {}


// ============================================================
// 3️. Sealed INTERFACE implemented by CLASS
//    → final | sealed | non-sealed
// ============================================================

sealed interface Payment permits CardPayment, UPIPayment, CashPayment, SpecialPayment {}

// record → implicitly final
record CardPayment(String cardNumber) implements Payment {}

// final → closed
final class CashPayment implements Payment {}

// sealed → restrict further
sealed class UPIPayment implements Payment permits PhonePe {}

// non-sealed → reopen
non-sealed class SpecialPayment implements Payment {}

final class PhonePe extends UPIPayment {}

class CustomPayment extends SpecialPayment {}   // allowed


// ============================================================
// 4️. Nested sealed (no explicit permits needed)
// ============================================================

sealed class Vehicle {

    // Nested classes automatically permitted
    final class Car extends Vehicle {}
    final class Bike extends Vehicle {}
}

// ❌ External class cannot extend Vehicle
// class Truck extends Vehicle {}  // Compile-time error


// ============================================================
// 5️. Sealed + Abstract
// ============================================================

// sealed controls WHO can extend
// abstract controls WHETHER it can be instantiated
sealed abstract class Document permits Invoice, Receipt {

    abstract String generate();
}

final class Invoice extends Document {
    String generate() {
        return "Invoice generated";
    }
}

non-sealed class Receipt extends Document {
    String generate() {
        return "Receipt generated";
    }
}

// allowed because Receipt is non-sealed
class SpecialReceipt extends Receipt {
    String generate() {
        return "Special receipt generated";
    }
}


// ============================================================
// MAIN CLASS
// ============================================================

public class SealedDeepDive {

    public static void main(String[] args) {

        // 1️. Sealed class demo
        User user = new Admin();
        System.out.println("User type: " + user.getClass().getSimpleName());

        // 2️. Interface → Interface demo
        TransactionState state = new AwaitingApproval();
        System.out.println("State type: " + state.getClass().getSimpleName());

        // 3️. Interface → Class demo
        Payment payment = new CardPayment("1234-5678");
        System.out.println("Payment type: " + payment.getClass().getSimpleName());

        // 4️. Nested sealed demo
        Vehicle vehicle = new Vehicle().new Car();
        System.out.println("Vehicle type: " + vehicle.getClass().getSimpleName());

        // 5️. Sealed + Abstract demo
        Document doc = new Invoice();
        System.out.println(doc.generate());

        /*
         * ======================================================
         * FINAL RULES (MEMORY VERSION)
         * ======================================================
         *
         * Sealed CLASS extended by CLASS →
         *      final | sealed | non-sealed
         *
         * Sealed INTERFACE extended by INTERFACE →
         *      sealed | non-sealed
         *
         * Sealed INTERFACE implemented by CLASS →
         *      final | sealed | non-sealed
         *
         * record → implicitly final
         *
         * sealed → restrict WHO can extend
         * final → stop extension
         * non-sealed → reopen extension
         * abstract → cannot instantiate
         *
         */
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