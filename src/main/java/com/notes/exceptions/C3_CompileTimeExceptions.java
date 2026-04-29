package com.notes.exceptions;

import java.io.*;
import java.util.*;


//---------- Helper class for constructor error ----------
class A {
	A(int x) {
	}
}

public class C3_CompileTimeExceptions {

    public static void main(String[] args) {

        // ---------- CASE 1: Syntax Error ----------
        // System.out.println("Hello")  // ❌ missing semicolon

        // ---------- CASE 2: Cannot find symbol ----------
        // System.out.println(value);  // ❌ variable not declared

        // ---------- CASE 3: Type mismatch ----------
        // int num = "abc";  // ❌ String to int

        // ---------- CASE 4: Duplicate variable ----------
        // int a = 10;
        // int a = 20;  // ❌ duplicate

        // ---------- CASE 5: Method not found ----------
        // testMethod();  // ❌ no such method

        // ---------- CASE 6: Unreachable code ----------
        // return;
        // System.out.println("Unreachable"); // ❌

        // ---------- CASE 8: Unhandled checked exception ----------
        // FileReader fr = new FileReader("file.txt"); // ❌ must handle IOException

        // ---------- CASE 9: Final variable reassignment ----------
        // final int x = 10;
        // x = 20; // ❌ cannot reassign

        // ---------- CASE 10: Static vs non-static ----------
        // System.out.println(instanceVar); // ❌ non-static access

        // ---------- CASE 11: Constructor not found ----------
        // A obj = new A(); // ❌ no default constructor

        // ---------- CASE 12: Generics type mismatch ----------
        // List<String> list = new ArrayList<>();
        // list.add(100); // ❌ Integer not allowed

        System.out.println("Fix one error at a time and recompile");
    }

    int instanceVar = 100;
}

// ---------- CASE 7: Missing return ----------
/*
public static int getNumber() {
    // ❌ No return statement
}
*/
