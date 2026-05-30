package com.notes.programs;

import java.util.HashSet;
import java.util.Set;

public class CleaningRobot {
	
	// Returns number of unique cells cleaned (visited)
	public int numberOfCleanRooms(int[][] room) {							// String[] room
		int m = room.length;
		int n = room[0].length;												// room[0].length()
		
		int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};							// Direction offsets ordered clockwise: Right, Down, Left, Up
		
		Set<String> visitedStates = new HashSet<>();						// Stores visited states : "(row,col,direction)"
		Set<String> cleanedCells = new HashSet<>();							// Stores cleaned cells  : "(row,col)"
		
		int r = 0;															// Start at (0,0) facing Right (index 0)
		int c = 0;
		int d = 0;
		
		while (true) {
			String state = String.format("(%d,%d,%d)", r, c, d);			// Example: "(0,0,0)"
			if (visitedStates.contains(state)) {
				break;														// Cycle detected, terminate loop
			}
			
			visitedStates.add(state);
			cleanedCells.add(String.format("(%d,%d)", r, c));				// Track unique position cleaned. // Example: "(0,0)"
			
			// Calculate next step
			int nextR = r + dirs[d][0];										// if d=0 -> dirs[d] = {0,1} -> move column by +1         00 01 02
			int nextC = c + dirs[d][1];
			
			if (nextR >= 0 && nextR < m 									// Check if nextStep is: inside boundary & not obstacle
				&& nextC >= 0 && nextC < n 
				&& room[nextR][nextC] != 1) {								// room[nextR].charAt(nextC) != 'X'
				r = nextR;													// Move forward
				c = nextC;
			} else {														// RIGHT -> DOWN -> LEFT -> UP -> RIGHT         0 -> 1 -> 2 -> 3 -> 0
				d = (d + 1) % 4;											// Rotate clockwise
			}
		}
		
		return cleanedCells.size();
	}
	
	public static void main(String[] args) {
	    
	    // Test 1
	    int[][] room1 = {
	        {0}
	    };

	    // Test 2
	    int[][] room2 = {
	        {0, 0},
	        {0, 0}
	    };

	    // Test 3
	    int[][] room3 = {
	        {0, 0, 0},
	        {1, 1, 0},
	        {0, 0, 0}
	    };

	    // Test 4
	    int[][] room4 = {
	        {0, 1},
	        {1, 0}
	    };
	    
	    // Test 5
	    int[][] room5 = {
		        {0, 0, 1},
		        {0, 1, 0},
		        {0, 0, 0}
		    };	    

	    
	    CleaningRobot robot = new CleaningRobot();
	    
	    System.out.println("Number of cleaned cells for room 1: " + robot.numberOfCleanRooms(room1));	// 1
	    System.out.println("Number of cleaned cells for room 2: " + robot.numberOfCleanRooms(room2));	// 4
	    System.out.println("Number of cleaned cells for room 3: " + robot.numberOfCleanRooms(room3));	// 7
	    System.out.println("Number of cleaned cells for room 4: " + robot.numberOfCleanRooms(room4));	// 1
	    System.out.println("Number of cleaned cells for room 5: " + robot.numberOfCleanRooms(room5));	// 2
	}
}


/*

c=0   c=1   c=2

r=0   (0,0) (0,1) (0,2)

r=1   (1,0) (1,1) (1,2)

r=2   (2,0) (2,1) (2,2)


[ ][ ][ ]
[ ][R][ ]
[ ][ ][ ]




-----------------
-----------------
0 0 0
0 1 0
0 0 0

robot path:
→ → → ↓
↑     ↓
↑     ↓
← ← ← ↓


*/