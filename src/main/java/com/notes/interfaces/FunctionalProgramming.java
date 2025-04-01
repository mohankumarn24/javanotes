package com.notes.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
		 * Predicate<T> 	   		T  -> boolean
		 * Consumer<T>		   		T  -> void 
		 * Function<T, R> 	   		T -> R
		 * 
		 * Supplier<T>  	   		() -> T
		 * UnaryOperator<T>   		T -> T
		 * BinaryOperator<T>  		(T, T) -> T
		 * 
		 * BiPredicate<L, R> 	   	(L, R)  -> boolean
		 * BiConsumer<T, U>			(T, U)  -> void 
		 * BiFunction<T, U, R> 		(T, U) -> R
		 */
		
		/*
		List<String> cities = Arrays.asList("Delhi", "Mumbai", "Goa", "Pune");
	    cities.stream()                  					// supplier    	Supplier<String[]> citySupplierShort = () -> new String[]{"Mumbai", "Delhi", "Goa", "Pune"};
  	  			.filter(t -> t.equalsIgnoreCase("Mumbai"))	// Predicate	Predicate<String> filterCity = city -> city.equals("Mumbai");
  	  			.map(t -> t.toUpperCase())					// function		Function<String, String> getCodes = city -> city.toUpperCase();	  
  	  			.map(t -> t.substring(0, 3))				// function		Function<String, String> getCodes = city -> city.substring(0, 3);	  
  	  			.forEach(System.out::println);				// consumer		Consumer<String> printConsumer = city -> System.out.println(city);
	    */
		
		testPredicate();
		testConsumer();	
		testFunction();	
		testSupplier();		
	}

	private static void testPredicate() {
		
		// boolean test(T value);
		System.out.println("Predicate:");	
		Predicate<String> filterCity = city -> city.equals("Mumbai");
		
		List<String> cities = Arrays.asList("Delhi", "Mumbai", "Goa", "Pune");
		cities.stream().filter(filterCity).forEach(System.out::println);
	}

	private static void testConsumer() {
		
		// void accept(T value);
		System.out.println("\nConsumer:");	
	    Consumer<String> printConsumer = city -> System.out.println(city);    

		List<String> cities = Arrays.asList("Delhi", "Mumbai", "Goa", "Pune");
	    cities.forEach(printConsumer);
	}

	private static void testFunction() {

		// R apply(T var1);
		System.out.println("\nFunction:");	
	    Function<String, Character> getFirstChar = city -> {
	    														return city.charAt(0);
	    													};
        Function<String, Character> getFirstCharShort = city -> city.charAt(0);
	    Function<String, String> getCodes = city -> city.toUpperCase().substring(0, 3);	    															
	    
	    List<String> cities = Arrays.asList("Delhi", "Mumbai", "Goa", "Pune");
	    cities.stream()
	    	  // .map(getFirstChar)
	          // .map(getFirstCharShort)
	    	  .map(getCodes)
	    	  .forEach(System.out::println);		
		
	}
	
	private static void testSupplier() {

		// T get();
		System.out.println("\nSupplier:");
	    Supplier<String[]> citySupplier = () -> {
	        										return new String[]{"Mumbai", "Delhi", "Goa", "Pune"};
	    										};
	    Supplier<String[]> citySupplierShort = () -> new String[]{"Mumbai", "Delhi", "Goa", "Pune"};
	    
	    // String[] strings = citySupplierShort.get();
	    List<String> cities = Arrays.asList(citySupplierShort.get());
	    cities.stream()
	    	  .forEach(System.out::println);
	}
}
