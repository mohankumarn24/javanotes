package com.notes.iterableIterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

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

// Immutable code

// Custom Iterable
final class NamesCollection implements Iterable<String> {
	private final String[] names;

	public NamesCollection(String[] names) {
		// this.names = names.clone();					// error prone
		// this.names = customClone(names);				// alternative approach (not recommended)
		this.names = Arrays.copyOf(names, names.length);
	}

	@Override
	public Iterator<String> iterator() {
		return new NamesIterator(names);
	}
	
	
    @SuppressWarnings("unused")
	private String[] customClone(String[] source) {
        String[] copy = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i];
        }
        return copy;
    }
}

// Custom Iterator
final class NamesIterator implements Iterator<String> {
	private final String[] names;
	private int index = 0;								// has an internal index

	public NamesIterator(String[] names) {
		this.names = Arrays.copyOf(names, names.length);
	}

	@Override
	public boolean hasNext() {							// hasNext() checks if items remain
		return index < names.length;
	}

	@Override
	public String next() {								// next() returns current value and moves index forward
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        
		return names[index++];
	}
}

// Test
public class IterableDemo {
	public static void main(String[] args) {
		String[] names = { "A", "B", "C" };

		// custom collection similar to 'List<String> list = new ArrayList<>();'
		NamesCollection namesCollection = new NamesCollection(names);

		// using iterator
		System.out.println("Using iterator:");
		Iterator<String> iterator = namesCollection.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		// using for-each loop
		System.out.println("\nUsing for-each loop:");
		for (String name : namesCollection) {
			System.out.println(name);
		}
	}
}


/**
private String[] customClone(String[] source) {
    String[] copy = new String[source.length];

    for (int i = 0; i < source.length; i++) {
        copy[i] = source[i];
    }

    return copy;
}

Equivalent enhanced for-loop version:
private String[] customClone(String[] source) {
    String[] copy = new String[source.length];

    int index = 0;
    for (String value : source) {
        copy[index++] = value;
    }

    return copy;
}

Equivalent using System arraycopy():
private String[] customClone(String[] source) {
    String[] copy = new String[source.length];
    System.arraycopy(source, 0, copy, 0, source.length);
    return copy;
}

Equivalent using Arrays copyOf():
private String[] customClone(String[] source) {
    return Arrays.copyOf(source, source.length);
}
*/


