package com.notes.multithreading.objectlockclasslock;

// chatgpt.com/share/6915f004-4930-8004-87f7-576b2599cadf
class BankAccount {

	private int balance = 100;

	// Instance method (object/instance lock) - protects object data
	public synchronized void deposit(int amount) {
		// object lock (this)
		balance += amount;
		System.out.println(Thread.currentThread().getName() + " deposited. Balance = " + balance);
		try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }
		
		/*
	    // object lock using synchronized block
	    synchronized (this) {
	        // this is using object lock
	    }
	    */
		
		/*
		// Manually added class lock to non-static method for global synchronization
	    synchronized (LockDemo.class) {
	        // this is using class lock
	    }
	    */
	}

	// Static method (class/static lock) - protects static shared data
	public static synchronized void showBankPolicy() {
		// class lock (Demo.class)
		System.out.println(Thread.currentThread().getName() + " reading bank policy");
		try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }
	}
}

public class LockDemo1 {
	public static void main(String[] args) {

		BankAccount acc1 = new BankAccount();
		BankAccount acc2 = new BankAccount();

		Thread t1 = new Thread(() -> acc1.deposit(50), "T1");		// If 2 threads use the same account object, only one can deposit at a time.
		Thread t2 = new Thread(() -> acc1.deposit(30), "T2");		// If 2 threads use the same account object, only one can deposit at a time.
		Thread t3 = new Thread(() -> acc2.deposit(20), "T3");		// If they use different accounts, both can deposit simultaneously.

		Thread t4 = new Thread(BankAccount::showBankPolicy, "T4");	// Only one thread in the entire JVM can execute this at a time.
		Thread t5 = new Thread(BankAccount::showBankPolicy, "T5");	// Even if you have 100 account objects, they all share the class lock.

		t1.start(); 		// object lock of acc1 used
		t2.start(); 		// must WAIT for acc1's lock
		t3.start(); 		// can run immediately (acc2's lock)
		t4.start(); 		// class lock used
		t5.start(); 		// must WAIT for class lock
	}
}

/* DO NOT REPLACE
T4 reading bank policy			(class lock)
T1 deposited. Balance = 150		(acc1 lock)
T3 deposited. Balance = 120		(acc2 lock)   // runs in parallel
T5 reading bank policy			(waited for class lock)
T2 deposited. Balance = 180		(waited for acc1 lock)
*/

/*
| Feature                                              | Object Lock                                           | Class Lock                                                     |
| ---------------------------------------------------- | ----------------------------------------------------- | -------------------------------------------------------------- |
| Applies to                                           | Single instance                                       | Entire class                                                   |
| Used by                                              | `synchronized` instance methods / blocks using `this` | `static synchronized` methods / blocks using `ClassName.class` |
| Number of locks                                      | One per instance                                      | One per class                                                  |
| Can threads access different objects simultaneously? | ✔ Yes                                                 | ✖ No (class lock is shared)                                   |
| Are class lock and object lock independent?          | ✔ Yes – they **do NOT block each other**              |                                                                |



| Situation                           | Type of Lock |
| ----------------------------------- | ------------ |
| Each object has separate data       | Object Lock  |
| All objects share data              | Class Lock   |
| Method is non-static                | Object Lock  |
| Method is static                    | Class Lock   |
| Want concurrency between objects    | Object Lock  |
| Want to block entire class globally | Class Lock   |

*/