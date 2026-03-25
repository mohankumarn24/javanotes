package com.notes.collections;

import java.util.concurrent.ConcurrentHashMap;

public class E_ConcurrentMapDemo {
	
    public static void main(String[] args) {

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // =========================
        // 1. putIfAbsent
        // =========================
        System.out.println("=== putIfAbsent ===");

        map.putIfAbsent("A", 10);
        map.putIfAbsent("A", 20); 												// will NOT overwrite

        System.out.println("A value: " + map.get("A")); 						// 10


        // =========================
        // 2. computeIfAbsent
        // =========================
        System.out.println("\n=== computeIfAbsent ===");

        // key NOT present → run fn → store → return new value
        map.computeIfAbsent("B", k -> {
            System.out.println("Computing value for B");
            return 100;
        });

        // key present → SKIP fn → return existing value
        map.computeIfAbsent("B", k -> {
            System.out.println("This should NOT execute");
            return 200;
        });																		// returns existing value i.e. 100, since key is already present

        System.out.println("B value: " + map.get("B")); 						// 100

        
        /**
         * Integer v21 = map.computeIfAbsent("B", k -> 100);
         * Integer v22 = map.computeIfAbsent("B", k -> 200);
         * 
         * System.out.println(v21); 											// 100
         * System.out.println(v22); 											// 100
         * 
         */

        
        // =========================
        // 3. computeIfPresent
        // =========================
        System.out.println("\n=== computeIfPresent ===");

        map.computeIfPresent("A", (k, v) -> {
            System.out.println("Updating A");
            return v + 5;
        });

        map.computeIfPresent("C", (k, v) -> {
            System.out.println("This should NOT execute");
            return 50;
        });

        System.out.println("A value after update: " + map.get("A")); 			// 15

        /**
         * Integer v31 = map.computeIfPresent("A", (k, v) -> v + 5);
         * Integer v32 = map.computeIfPresent("C", (k, v) -> 50);
         * 
         * System.out.println(v31); 											// 15
         * System.out.println(v32); 											// null
         * 
         */
        
        
        // =========================
        // 4. Multithreading demo ⭐
        // =========================
        System.out.println("\n=== Multithreading computeIfAbsent ===");

        Runnable task = () -> {
            String value = String.valueOf(
                map.computeIfAbsent("D", k -> {
                    System.out.println(Thread.currentThread().getName() + " computing D");
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    return 500;
                })
            );
            System.out.println(Thread.currentThread().getName() + " got value: " + value);
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
    }
}
