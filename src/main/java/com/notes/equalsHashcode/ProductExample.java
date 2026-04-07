package com.notes.equalsHashcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * In this example:

  * - We create a Product class where products are considered equal if they have the same productId
  * - We add a laptop to a HashSet
  * - We create a second laptop object with the same ID but different price
  * - Because we've implemented equals to compare only the ID, and hashCode to hash based only on the ID, the set recognizes the second laptop as a duplicate
  * - When we try to add the second laptop, the set size remains 1 because a HashSet doesn't allow duplicates
  * 
  * This demonstrates how equals and hashCode control how objects are identified in collections. 
  * If we hadn't overridden these methods, Java would consider the two laptop objects as different (based on memory location), and both would be added to the set.
 */

public class ProductExample {
    public static void main(String[] args) {    	
        // List 1
    	System.out.println("List1:");
        Set<Product> set = new HashSet<>();								// can't use 'List' as it allows duplicates
        Product p1 = new Product("1", " Reynolds Pen", 10);
        Product p2 = new Product("1", " Reynolds Pen", 12);
        set.add(p1);        
        System.out.println("p1.equals(p2)	 : " + p1.equals(p2));		// true. override default implementation of 'equals()' to check 'id' instead of '=='
        System.out.println("set.contains(p2) : " + set.contains(p2));	// true. As per hash contract between 'equals()' and h'ashCode()', whenever two objects are equals, then their hash code must be equal.
        																// So, override 'hashCode()' as well
        
        // Map 1
        System.out.println("\nMap1:");
        Map<Product, String> map1 = new HashMap<>();
        map1.put(p1, "Value1");
        map1.put(p2, "Value2");
        System.out.println("p1.equals(p2) 	: " + p1.equals(p2));		// true
        System.out.println("map1.size()   	: " + map1.size());     	// 1
        System.out.println("map1.get(p1)  	: " + map1.get(p1));    	// "Value2"
        System.out.println("map1.get(p2)  	: " + map1.get(p2));    	// "Value2"
        /*
         * Why does p2 overwrite p1? Because in a HashMap:
		 *   - It hashes the key -> finds the bucket
         *   - It checks equals() to see whether the key already exists
         *   - If key exists, it updates the value
         *   - If not, it inserts a new entry
         *   - Since p1.equals(p2) -> same key, the second put() overwrites the first
		 *
         * If 'Product' is used as a key in a map:
         *   - Never allow mutable fields inside equals() or hashCode().
         *   - In your design, you used only id (final) -> safe.
         *   If you used name or price (mutable), and they changed after insertion, the map could lose the key permanently.
        */        
        
        // Map 2
        // What if Product were used as a Map value? Then Product.equals() doesn’t matter because keys matter, not values
        System.out.println("\nMap2:");
        Map<String, Product> map2 = new HashMap<>();
        map2.put("A", p1);
        map2.put("B", p2);
        System.out.println("map2.size()  	: " + map2.size());     	// 2
    }
}

/*
 * How hashCode and equals Work in Java: The Simple Version
 * 
 * 1.a. equals:
 * Think of equals as your way to tell Java when two objects should be considered "the same thing."
 * By default, Java thinks objects are the same only if they are literally the same object in computer memory. But often, you want objects to be considered "the same" based on their content.
 * For example, two Product objects with ID "ABC123" might be different objects in memory, but logically they represent the same product.
 * 
 * 1.b. hashCode:
 * Think of hashCode as creating an "address label" for your object.
 * When Java needs to store many objects (like in HashMaps or HashSets), it uses these labels to organize them into "buckets" - making it much faster to find them later.
 * 
 * 2. How They Work Together
 * Imagine a library where:
 *  - hashCode tells you which shelf to look on
 *  - equals helps you find the exact book on that shelf
 * If two objects are equal, they must have the same hash code (same shelf). But multiple objects can share the same hash code (be on the same shelf) without being equal.
 * 
 * 3. Real-World Analogy
 * It's like a post office:
 * Your zip code (hashCode) gets mail to the right neighborhood
 * Your full address (equals) identifies the exact house
 * 
 * 4. The Contract:
 * The most important rule: If two objects are equal (according to equals), they must have the same hash code.
 * If you break this rule, collections like HashSet and HashMap will behave strangely - items might "disappear" because Java looks for them on the wrong "shelf."
*/


/*
 * My Analysis:
 * Let's say there is Product class (id, name, price fields). 
 * I will create two products product1, product2 which have same id, same name but only the price is different. Logically, the products are same
 * 	-- refer above code
 * (Two products are equal if their productId's are same even though price differs -  as per our analogy)
 * But default implementation for equals() checks only for memory address equality ie product1 == product2. As a result, these two products are now different!
 * To resolve this, we must override equals()
 * We override equals() method to check for (product1.id == product2.id) instead of (product1 == product2)
 * Whenever we override equals() mtehod, we override hashCode() method as well to maintain contract between equals() and hashCode()
 *  - The most important rule: If two objects are equal (according to equals), they must have the same hash code.
 *  - If we break this rule, collections like HashSet and HashMap will behave strangely
 */
