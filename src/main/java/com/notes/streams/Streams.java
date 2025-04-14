package com.notes.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {

	public static void main(String[] args) {

		// A
		System.out.println("---A---");
		List<Integer> numlist = Arrays.asList(1, 2, 3, 3, 4, 4, 5, 5);
		List<Integer> result = numlist.stream()
										.filter(t -> t > 2)
										.map(t -> t * 1)
										// .sorted(Comparator.reverseOrder()).distinct().limit(2)
										.sorted().distinct().limit(2)
										.peek(t -> System.out.println("Debugging: " + t))
										.collect(Collectors.toList()); // collect, count, foreach
		
		long count = result.stream().count();  // long count();
		System.out.println(String.format("count: %d", count));	
		
		result.stream().forEach(n -> System.out.println(n));
		
		boolean anyMatch = result.stream().anyMatch(t -> t > 8); // any one must match
		System.out.println(String.format("anyMatch: %b", anyMatch));
		
		boolean allMatch = result.stream().allMatch(t -> t > 8); // all must match
		System.out.println(String.format("allMatch: %b", allMatch));
		
		boolean noneMatch = result.stream().noneMatch(t -> t > 1000); // none must match
		System.out.println(String.format("noneMatch: %b", noneMatch));	
		
		Optional<Integer> minOptional = result.stream().min((a, b) -> a.compareTo(b));
		System.out.println(String.format("min: %d", minOptional.get()));
		
		Optional<Integer> maxOptional = result.stream().max((a, b) -> a.compareTo(b));
		System.out.println(String.format("max: %d", maxOptional.get()));
		
		// B
		System.out.println("\n---B---");
		List<String> stringList = Arrays.asList("Armani", "Balenciaga", "Chanel");
		stringList.stream()
					.map(x -> x.toLowerCase())
					// .map(x -> x.toUpperCase())
					.forEach(n -> System.out.println(n));
		System.out.println();
		
		stringList.stream()
					.map(t -> {return t.toLowerCase();})
					.map(t -> {return t.toUpperCase();})
					.map(t -> {return t.substring(0,3);})	
					.forEach(n -> System.out.println(n));
		
		// C
		System.out.println("\n---C---");
		List<List<String>> strlist =  Arrays.asList(
				Arrays.asList("Armani 1", "Balenciaga 1", "Chanel 1"),
				Arrays.asList("Armani 2", "Balenciaga 2", "Chanel 2"),
				Arrays.asList("Armani 3", "Balenciaga 3", "Chanel 3"));
		strlist.stream()
				.flatMap(t -> t.stream()) // or .flatMap(Collection::stream)
				.map(t -> t.toUpperCase())
				.forEach(n -> System.out.println(n));
		System.out.println();
		
		// D
		System.out.println("\n---D---");
		List<String> stringList2 = Arrays.asList("Armani", "Balenciaga", "Chanel");
		String value = stringList2.stream().findAny().get();
		System.out.println(String.format("findAny: %s", value));
		
		value = stringList2.stream().findFirst().get();
		System.out.println(String.format("findFirst: %s", value));
		
		// E
		System.out.println("\n---E---");
		/*
		Optional<String> reduced = Stream.of("one", "two", "three")
											.reduce((str, combinedValue) -> {
												return combinedValue + " " + str;
											});
		*/
		Optional<String> reduced = Stream.of("one", "two", "three")
										 .reduce((combinedValue, str) -> {
											 return combinedValue + " " + str;
										 });
		System.out.println(String.format("reduced: %s", reduced.get()));
		
		// F
		System.out.println("\n---F---");
		List<String> a = Arrays.asList("a1", "b1", "c1");
		List<String> b = Arrays.asList("a2", "b2", "c2");
		Stream<String> concatStream = Stream.concat(a.stream(), b.stream());
		concatStream.map(t -> t.toUpperCase()).forEach(t -> System.out.println(t));
	}

}
