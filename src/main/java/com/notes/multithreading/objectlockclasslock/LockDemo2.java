package com.notes.multithreading.objectlockclasslock;

class MyClass {

    public synchronized void objectLock() {
        System.out.println("Object lock acquired by: " + Thread.currentThread().getName());
        try { Thread.sleep(2000); } catch (Exception e) {}
        System.out.println("Object lock released by: " + Thread.currentThread().getName());
    }

    public static synchronized void classLock() {
        System.out.println("Class lock acquired by: " + Thread.currentThread().getName());
        try { Thread.sleep(2000); } catch (Exception e) {}
        System.out.println("Class lock released by: " + Thread.currentThread().getName());
    }
}


public class LockDemo2 {
	public static void main(String[] args) {
		MyClass obj1 = new MyClass();
		MyClass obj2 = new MyClass();

		new Thread(obj1::objectLock, "t1").start();  		// Takes obj1 lock
		new Thread(obj1::objectLock, "t2").start();  		// Must wait for obj1 lock
		new Thread(obj2::objectLock, "t3").start();  		// Can run in parallel (different object)
		
		new Thread(MyClass::classLock, "t4").start(); 		// Must wait for class lock if taken
		new Thread(MyClass::classLock, "t5").start(); 		// Must wait for class lock if taken
	}
}

/*
Object lock acquired by: t1
Class lock acquired by: t4
Object lock acquired by: t3
Class lock released by: t4
Object lock released by: t3
Object lock released by: t1
Class lock acquired by: t5
Object lock acquired by: t2
Class lock released by: t5
Object lock released by: t2
*/