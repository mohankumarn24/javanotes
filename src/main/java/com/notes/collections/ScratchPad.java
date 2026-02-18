package com.notes.collections;

import java.util.*;

public class ScratchPad {

	public static void main(String[] args) {
	
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		queue.add(22);
		queue.add(11);
		queue.add(33);
		
		System.out.println(queue.peekLast());
		
		System.out.println();
		Iterator<Integer> itr = queue.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}

	}
}
