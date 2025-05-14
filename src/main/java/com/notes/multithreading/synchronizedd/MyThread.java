package com.notes.multithreading.synchronizedd;

public class MyThread extends Thread {
	
    Printer printer;  
    String str;
    
    public MyThread(Printer printer, String str) {
        this.printer = printer;  
        this.str = str;  
    }  
    
    @Override
    public void run() {
    	Printer.print(str);  
    }  
} 