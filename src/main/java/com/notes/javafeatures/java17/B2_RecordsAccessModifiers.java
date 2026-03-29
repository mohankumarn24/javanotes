package com.notes.javafeatures.java17;

/**
 * For top-level records: only public or default/package-private allowed
 * ie. private and protected are not allowed for top-level records
 * 
 * Ex: 
 * 		public record Employee(String name) {}
 * 		record Employee(String name) {}
 */

public class B2_RecordsAccessModifiers {

    // default / package-private nested record
    // accessible only inside same package
    record DefaultRecord(String name) {}

    // public nested record
    // accessible from anywhere
    public record PublicRecord(String name) {}

    // private nested record
    // accessible only inside outer class
    private record PrivateRecord(String name) {}

    // protected nested record
    // accessible within same package and subclasses
    protected record ProtectedRecord(String name) {}
    
    public static void main(String[] args) {

    	// DEFAULT -> SAME CLASS, SAME PACKAGE CLASSES (subclasses or non-subclasses)
    	// NOT ACCESSIBLE FROM DIFFERENT PACKAGE
    	// accessible only within same package
        DefaultRecord defaultRecord = new DefaultRecord("Default");
        System.out.println(defaultRecord.name());

        // PUBLIC -> SAME CLASS, SAME PACKAGE CLASSES (subclasses or non-subclasses), DIFFERENT PACKAGE CLASSES (subclasses or non-subclasses)
        // accessible from anywhere
        PublicRecord publicRecord = new PublicRecord("Public");
        System.out.println(publicRecord.name());

        // PRIVATE -> SAME CLASS ONLY
        // NOT ACCESSIBLE OUTSIDE CURRENT CLASS
        // accessible only inside AccessModifierDemo class
        PrivateRecord privateRecord = new PrivateRecord("Private");
        System.out.println(privateRecord.name());

        // PROTECTED -> SAME CLASS, SAME PACKAGE (subclasses or non-subclasses), DIFFERENT PACKAGE CLASSES (subclasses only)
        // accessible within same package and subclasses        
        ProtectedRecord protectedRecord = new ProtectedRecord("Protected");
        System.out.println(protectedRecord.name());
    }
}

/*
// PROTECTED -> WITHIN SAME PACKAGE + SUBCLASSES OF OUTER CLASS (same or different package)
package a;

public class Parent {
    protected record ProtectedRecord(String name) {
    }
}

package b;
import a.Parent;

public class Child extends Parent {
    public void test() {
        ProtectedRecord record = new ProtectedRecord("test");
        System.out.println(record.name());
    }
}
*/