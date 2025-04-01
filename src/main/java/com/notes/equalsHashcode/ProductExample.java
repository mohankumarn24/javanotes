package com.notes.equalsHashcode;

import java.util.HashSet;
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
        // Create a set of products
        Set<Product> inventory = new HashSet<>();
        
        // Add a product to our inventory
        Product laptop1 = new Product("P123", "Laptop", 999.99);
        inventory.add(laptop1);
        
        // Create another product object with the same ID but different price
        Product laptop2 = new Product("P123", "Laptop", 1099.99);
        
        // Check if the product exists in our inventory
        System.out.println("Inventory size: " + inventory.size());          		 		// 1
        System.out.println("Contains laptop2: " + inventory.contains(laptop2)); 	 		// true, because of overriden equals() and hashCode() methods
        
        // Add the second laptop - it won't be added since it's considered equal
        inventory.add(laptop2);
        System.out.println("Inventory size after adding laptop2: " + inventory.size()); 	// Still 1
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
 * Let's say there is Product class (productId, name, price fields). 
 * I have two products which have same name, same name but only the price is different. Logically, the products are same
 * (Two products are equal if their productId's are same even though price differs -  as per our analogy)
 * But default implementation for equals() checks only for memory address equality ie product1 == product2. As a result, these two products are now different!
 * To resolve this, we must override equals() and hashCode()
 * We override equals() method to check for (product1.id == product2.id) instead of (product1 == product2)
 * Whenever we override equals() mtehod, we override hashCode() method as well to maintain contract between equals() and hashCode()
 *  - The most important rule: If two objects are equal (according to equals), they must have the same hash code.
 *  - If we break this rule, collections like HashSet and HashMap will behave strangely
 */
