package com.notes.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/*
 * Default functional interfaces:
 * Runnable -> run()
 * Callable -> call()
 * Comparable -> compareTo(T o)
 * Comparator -> compare(T o1, T o2)
 * Consumer -> accept(T t)
 * Predicate -> test(T t)
 * Supplier -> get()
 */
public class FunctionalProgramming {
	
	public static void main(String[] args) {	
		
		/*
		 * Predicate<T> 	   		T  -> boolean			test
		 * Consumer<T>		   		T  -> void 				accept
		 * Function<T, R> 	   		T -> R					apply
		 * 
		 * Supplier<T>  	   		() -> T					get
		 * UnaryOperator<T>   		T -> T					apply
		 * BinaryOperator<T>  		(T, T) -> T				apply
		 * 
		 * BiPredicate<L, R> 	   	(L, R)  -> boolean		test
		 * BiConsumer<T, U>			(T, U)  -> void 		accept
		 * BiFunction<T, U, R> 		(T, U) -> R				apply
		 */
		
		List<String> cities = Arrays.asList("Delhi", "Mumbai", "Goa", "Pune");
	    cities.stream()                  					// supplier    		Supplier<String[]> citySupplierShort = () -> new String[]{"Mumbai", "Delhi", "Goa", "Pune"};
  	  			.filter(t -> t.equalsIgnoreCase("Mumbai"))	// Predicate		Predicate<String> filterCity = city -> city.equals("Mumbai");
  	  			.map(t -> t.toLowerCase())					// function			Function<String, String> getCodes = city -> city.toLowerCase();
  	  			.map(t -> t.toUpperCase().trim())			// unaryOperator 	UnaryOperator<String> stringProcessor = s -> s.toUpperCase().trim();
  	  			.map(t -> t.substring(0, 3))				// function			Function<String, String> getCodes = city -> city.substring(0, 3);
  	  			.forEach(t -> System.out.println(t));		// consumer			Consumer<String> printConsumer = city -> System.out.println(city);
		
		testPredicate();   		// test
		testConsumer();	   		// accept
		testFunction();	   		// apply
		
		testSupplier();			// get
		testUnaryOperator();	// apply
		testBinaryOperator();	// apply	
		
		testBiPredicate();		// test
		testBiConsumer();		// accept
		testBiFunction();		// apply
	}

	private static void testPredicate() {
		
		// T  -> boolean
		System.out.println("\n---Predicate---:");	
		Predicate<String> filterCity = city -> city.equals("Mumbai");
		
		// single value
		System.out.println("Is equal to Mumbai: " + filterCity.test("Mumbai"));
		
		// multiple values
		List<String> cities = Arrays.asList("Delhi", "Mumbai", "Goa", "Pune");
		cities.stream().filter(filterCity).forEach(System.out::println);
	}

	private static void testConsumer() {
		
		// T  -> void
		System.out.println("\n---Consumer---:");	
	    Consumer<String> consumer = city -> System.out.println(city);    
	    
		// single value
	    consumer.accept("Sample data");

	    // multiple values
		List<String> cities = Arrays.asList("Delhi", "Mumbai", "Goa", "Pune");
	    cities.forEach(consumer);
	}

	private static void testFunction() {

		// T -> R
		System.out.println("\n---Function---:");	
	    Function<String, Character> getFirstChar = city -> {
	    														return city.charAt(0);
	    													};
        Function<String, Character> getFirstCharShort = city -> city.charAt(0);
	    Function<String, String> getCodes = city -> city.toUpperCase().substring(0, 3);	    															
	    
	    // single value
	    System.out.println("Single value: " + getCodes.apply("Sample data"));
	    
	    // multiple values
	    List<String> cities = Arrays.asList("Delhi", "Mumbai", "Goa", "Pune");
	    cities.stream()
	    	  // .map(getFirstChar)
	          // .map(getFirstCharShort)
	    	  .map(getCodes)
	    	  .forEach(System.out::println);		
		
	}
	
	private static void testSupplier() {

		// T get();
		// () -> T
		System.out.println("\n---Supplier---:");
	    Supplier<String[]> citySupplier = () -> {
	        										return new String[]{"Mumbai", "Delhi", "Goa", "Pune"};
	    										};
	    Supplier<String[]> citySupplierShort = () -> new String[]{"Mumbai", "Delhi", "Goa", "Pune"};
	    
	    // String[] strings = citySupplierShort.get();
	    List<String> cities = Arrays.asList(citySupplierShort.get());
	    cities.stream()
	    	  .forEach(System.out::println);
	}
	
	private static void testUnaryOperator() {
		
		// T -> T
		System.out.println("\n---UnaryOperator---:");
        UnaryOperator<Integer> incrementByOne = x -> x + 1;
        
        // single value
        System.out.println("Test single value: " + incrementByOne.apply(1));
        
        // multiple values
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> results = list.stream()
                .map(incrementByOne)
                .collect(Collectors.toList());
        System.out.println("Original values: " + list);
        System.out.println("Results after incrementing by one: " + results);
	}
	
	private static void testBinaryOperator() {

		// (T, T) -> T
		System.out.println("\n---BinaryOperator---:");
        BinaryOperator<Integer> func = (x1, x2) -> x1 + x2;
        Integer result = func.apply(2, 3);
        System.out.println(result);
	}
	
	private static void testBiPredicate() {
		
        // (L, R)  -> boolean
		System.out.println("\n---BiPredicate---:");
		BiPredicate<String, String> biPredicate = (a, b) -> a.equalsIgnoreCase(b);
		System.out.println(biPredicate.test("A", "A"));
	}	
	
	private static void testBiConsumer() {
		
        // (T, U)  -> void 
		System.out.println("\nBiConsumer:");
        BiConsumer<Integer, Integer> func = (x1, x2) -> System.out.println(x1 + x2);;
        func.accept(2, 3);
	}	
	
	private static void testBiFunction() {
		
        // (T, U) -> R
		System.out.println("\nBiFunction:");
        BiFunction<Integer, Integer, Integer> func = (x1, x2) -> x1 + x2;
        Integer result = func.apply(2, 3);
        System.out.println(result);
	}	
}
