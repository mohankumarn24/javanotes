package com.notes.multithreading.synchonization;

public class SynchronizedCounter {

    private int count = 0;

    public synchronized void increment() {
    	count++;
    }

    public synchronized int getCount() {
        return count;
    }

}