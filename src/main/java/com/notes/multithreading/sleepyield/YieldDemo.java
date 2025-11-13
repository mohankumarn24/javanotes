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
