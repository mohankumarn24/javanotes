package com.notes.multithreading.semaphores;

import java.util.concurrent.Semaphore;

/**
 * - A Semaphore in Java controls how many threads can access a shared resource at the same time
 * - For example, if a system allows only 3 concurrent users, a semaphore initialized with 3 can enforce this limit
 */
class ParkingLot {
    private final Semaphore parkingSlots;
    //  private final Semaphore parkingSlots = new Semaphore(3);

    public ParkingLot(int slotCount) {
        this.parkingSlots = new Semaphore(slotCount);
    }

    public void parkCar(String carName) {
        System.out.println(carName + " is trying to park...");
        try {
            parkingSlots.acquire(); 										// occupy one slot
            System.out.println(carName + " parked successfully!");
            Thread.sleep(2000); 											// simulate parking time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(carName + " was interrupted.");
        } finally {
            System.out.println(carName + " is leaving the parking lot.");
            parkingSlots.release(); 										// always release
        }
    }
}

public class SemaphoreExample {
    private static final int PARKING_SLOTS = 1;
	private static final int NUMBER_OF_CARS = 2;

	public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(PARKING_SLOTS);

        // 2 cars trying to park but only 1 can at a time
        for (int i = 0; i < NUMBER_OF_CARS; i++) {
            String carName = "Car-" + (i + 1);
            new Thread(() -> parkingLot.parkCar(carName), carName).start();
        }
    }
}

/*
1 ParkingLot + 2 Cars:
	Car-2 is trying to park...
	Car-2 parked successfully!
	Car-1 is trying to park...
	Car-2 is leaving the parking lot.
	Car-1 parked successfully!
	Car-1 is leaving the parking lot.
 */

/*
Concept:
 - A Semaphore in Java controls how many threads can access a shared resource at the same time.
 - Think of it as a bouncer at a club:
 - The club has limited seats (say, 3 people allowed).
 - If the club is full, new people (threads) must wait.
 - When someone leaves, another person can enter.
*/

/*
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class ParkingLot {
    private final Semaphore parkingSlots;

    public ParkingLot(int slotCount) {
        this.parkingSlots = new Semaphore(slotCount);
    }

    public void parkCar(String carName) {
        System.out.println(carName + " is trying to park...");
        try {
            parkingSlots.acquire(); // occupy one slot
            System.out.println(carName + " parked successfully!");
            Thread.sleep(2000); // simulate parking time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(carName + " was interrupted.");
        } finally {
            System.out.println(carName + " is leaving the parking lot.");
            parkingSlots.release(); // always release
        }
    }
}

class Car implements Runnable {
    private final ParkingLot parkingLot;
    private final String carName;

    public Car(ParkingLot parkingLot, String carName) {
        this.parkingLot = parkingLot;
        this.carName = carName;
    }

    @Override
    public void run() {
        parkingLot.parkCar(carName);
    }
}

public class SemaphoreExample {
    public static void main(String[] args) throws InterruptedException {
        ParkingLot parkingLot = new ParkingLot(3);
        List<Thread> threads = new ArrayList<>();

        // Create and start car threads
        for (int i = 1; i <= 6; i++) {
            String carName = "Car-" + i;
            Thread carThread = new Thread(new Car(parkingLot, carName), carName);
            threads.add(carThread);
            carThread.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("All cars have finished parking!");
    }
}
*/