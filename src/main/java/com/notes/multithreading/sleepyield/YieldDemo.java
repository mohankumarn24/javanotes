package com.notes.multithreading.sleepyield;

class MyThread2 extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " -> " + i);
            Thread.yield(); // give chance to other threads. Not guaranteed. Yield is not a synchronization tool
        }
    }
}

public class YieldDemo {
    public static void main(String[] args) {
        new MyThread2().start();
        new MyThread2().start();
    }
}

/*
Thread-1 -> 1
Thread-1 -> 2
Thread-1 -> 3
Thread-1 -> 4
Thread-0 -> 1
Thread-1 -> 5
Thread-0 -> 2
Thread-0 -> 3
Thread-0 -> 4
Thread-0 -> 5
*/