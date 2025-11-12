package com.notes.multithreading.semaphores;

import java.util.concurrent.Semaphore;

/**
 * - Semaphore helps control access to shared resources — by using permits. 
 * - For example, if a system allows only 3 concurrent users, a semaphore initialized with 3 can enforce this limit
 */
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

class Car extends Thread {
    private final ParkingLot parkingLot;

    public Car(ParkingLot parkingLot, String name) {
        super(name); // set thread name
        this.parkingLot = parkingLot;
    }

    @Override
    public void run() {
    	parkingLot.parkCar(getName());
    }
}

public class SemaphoreExample {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(3);

        for (int i = 1; i <= 6; i++) {
            Car car = new Car(parkingLot, "Car-" + i);
            car.start();
        }
    }
}

/*
1 ParkingLot + 2 Cars:
	Car-2 is trying to park...
	Car-1 is trying to park...
	Car-2 parked successfully!
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
 
Key Points: 
 - new Semaphore(3) → allows 3 permits (threads) at once.
 - acquire() → blocks the thread if no permits available.
 - release() → gives back a permit (like leaving the club).
 - If initialized with new Semaphore(1), it works like a lock or mutex.
*/

/*
// simplified version using lambda
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

public class SemaphoreExample {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(3);

        for (int i = 1; i <= 6; i++) {
            String carName = "Car-" + i;
            new Thread(() -> parkingLot.parkCar(carName), carName).start();
        }
    }
}
*/