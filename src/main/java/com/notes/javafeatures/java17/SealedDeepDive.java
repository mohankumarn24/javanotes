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

/*
 * RULE:
 * All permitted subclasses must be:
 *  - In the same package (if not using modules)
 *  - OR in the same module (if using modules)
 */

// ============================================================
// 1️. Sealed CLASS extended by CLASS
//    → final | sealed | non-sealed
// ============================================================
sealed class User permits Admin, Employee, Guest {}

// 1a. final → completely closed
final class Admin extends User {}

// 1b. sealed → restrict further
sealed class Employee extends User permits Manager {}
  final class Manager extends Employee {}												// Sealed CLASS extended by CLASS → final | sealed | non-sealed

// 1c. non-sealed → reopen hierarchy
non-sealed class Guest extends User {}
  class TemporaryGuest extends Guest {}													// Allowed because Guest is non-sealed


// ============================================================
// 2️. Sealed INTERFACE extended by INTERFACE
//    → sealed | non-sealed
// ============================================================
sealed interface TransactionState permits Pending, Completed {}

// 2a. extending sealed interface → must be sealed or non-sealed
sealed interface Pending extends TransactionState permits AwaitingApproval, AwaitingShipping {}
  sealed interface AwaitingApproval extends Pending permits ExpressShipping {}			// Sealed INTERFACE extended by INTERFACE → sealed | non-sealed. Considered sealed
    final class ExpressShipping implements AwaitingApproval {}							// Sealed INTERFACE implemented by CLASS → final | sealed | non-sealed. Considered final
  final class AwaitingShipping implements Pending {}									// Sealed INTERFACE implemented by CLASS → final | sealed | non-sealed. Considered final


// 2b. non-sealed interface → reopens hierarchy (ANY interface or class can extend/implement it)
non-sealed interface Completed extends TransactionState {}
  interface FullyCompleted extends Completed {}											// Interface extending non-sealed interface
    class OnlinePaymentCompleted implements FullyCompleted {}							// Class implementing extended interface
  class PaymentCompleted implements Completed {}										// Class implementing non-sealed interface
	

// ============================================================
// 3️. Sealed INTERFACE implemented by CLASS
//    → final | sealed | non-sealed
// ============================================================
sealed interface Payment permits CardPayment, UPIPayment, CashPayment, SpecialPayment {}

// 3a. record → implicitly final
record CardPayment(String cardNumber) implements Payment {}
// 3a. final → closed
final class CashPayment implements Payment {}

// 3b. sealed → restrict further
sealed class UPIPayment implements Payment permits PhonePe {}
  final class PhonePe extends UPIPayment {}

// 3c. non-sealed → reopen
non-sealed class SpecialPayment implements Payment {}
  class CustomPayment extends SpecialPayment {}   										// Allowed


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
//    → final | sealed | non-sealed
// ============================================================
// sealed controls WHO can extend
// abstract controls WHETHER it can be instantiated
sealed abstract class Document permits Invoice, Bill, Receipt {

    abstract String generate();
}

// final
final class Invoice extends Document {
    String generate() {
        return "Invoice generated";
    }
}

// sealed
sealed class Bill extends Document permits ElectricityBill, WaterBill {
    String generate() {
        return "Bill generated";
    }
}
final class ElectricityBill extends Bill {}
final class WaterBill extends Bill {}

// non-sealed
non-sealed class Receipt extends Document {
    String generate() {
        return "Receipt generated";
    }
}
class SpecialReceipt extends Receipt {						// allowed because Receipt is non-sealed
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
        TransactionState state = new ExpressShipping();
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