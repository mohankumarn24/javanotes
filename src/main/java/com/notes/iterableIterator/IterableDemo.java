package com.notes.iterableIterator;

import java.util.Iterator;

/*
public interface Iterable<T> {
    Iterator<T> iterator();
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) { action.accept(t); }
    }
    default Spliterator<T> spliterator() { return Spliterators.spliteratorUnknownSize(iterator(), 0); }
}
*/

/*
public interface Iterator<E> {
    boolean hasNext();
    E next();
    default void remove() { throw new UnsupportedOperationException("remove"); }
    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}
*/

// Custom Iterable
class NameCollection implements Iterable<String> {
	private String[] names;

	public NameCollection(String[] names) {
		this.names = names;
	}

	@Override
	public Iterator<String> iterator() {
		return new NameIterator(names);
	}
}

// Custom Iterator
class NameIterator implements Iterator<String> {
	private String[] data;
	private int index = 0;								// Has an internal index

	public NameIterator(String[] data) {
		this.data = data;
	}

	@Override
	public boolean hasNext() {							// hasNext() checks if items remain
		return index < data.length;
	}

	@Override
	public String next() {								// next() returns current value and moves index forward
		return data[index++];
	}
}

// Test
public class IterableDemo {
	public static void main(String[] args) {
		String[] names = { "Mohan", "John", "Sam" };

		// custom collection similar to 'List<String> list = new ArrayList<>();'
		NameCollection nameCollection = new NameCollection(names);

		// using iterator
		System.out.println("Using iterator:");
		Iterator<String> iterator = nameCollection.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		// using for-each loop
		System.out.println("\nUsing for-each loop:");
		for (String name : nameCollection) {
			System.out.println(name);
		}
	}
}
