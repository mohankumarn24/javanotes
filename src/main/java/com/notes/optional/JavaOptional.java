package com.notes.optional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class JavaOptional {

	private static class Modem {
	    private Double price;

	    public Modem(Double price) {
	        this.price = price;
	    }
	    
		public Double getPrice() { return null; }
		public void setPrice(Double price) { this.price = price; }
	}
	
	public static void main(String[] args) {

		// -------------------------- //
		// A1. Optional.empty()
		Optional<String> optional = Optional.empty();
		System.out.println(optional);														// Optional.empty
			
		// A2. Optional.of(x)
		try {
			optional = Optional.of(null);
		} catch (NullPointerException ex) {
			System.out.println(ex.getMessage());											// msg: null
		}
		
		optional = Optional.of("hello");
		System.out.println(optional);														// Optional[hello]		
		
		// A3. Optional.ofNullable(x)
		System.out.println(Optional.ofNullable(null));										// Optional.empty
		
		optional = Optional.ofNullable("hello");
		System.out.println(optional);														// Optional[hello]
		
		// A4. isEmpty(), isPresent(), ifPresent(), filter()
		if (optional.isEmpty()) {
			System.out.println("empty");
		} else if (optional.isPresent()) {
			System.out.println(optional.get()); 											// hello
			optional.ifPresent(System.out::println);    									// hello
		} else if (optional.filter(t -> t.equals("world")).isPresent()) {
			System.out.println(optional.get()); 	
		}
		
		// -------------------------- //
		// B. orElse(), orElseGet(), orElseThrow()
		String data = null;
		String result = Optional.ofNullable(data).orElse("some data 1");							
		System.out.println(result);														// some data 1
		result = Optional.ofNullable(data).orElseGet(() -> "some data 2");
		System.out.println(result);														// some data 2
		try {
			result = Optional.ofNullable(data).orElseThrow(() -> new RuntimeException("RTE"));	
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());											// RTE
		}
		
		// -------------------------- //
		// C. Conditional return With filter()
		boolean isInRange = priceIsInRange1(new Modem(12d));
		System.out.println(isInRange);														// true
		
		isInRange = priceIsInRange2(new Modem(12d));
		System.out.println(isInRange);														// true
		
		// -------------------------- //
		// D. Transforming value With map()
		givenOptional_whenMapWorks_thenCorrect();											// 6
		givenOptional_whenMapWorks_thenCorrect2();											// 8
		givenOptional_whenMapWorksWithFilter_thenCorrect();
		
		// -------------------------- //
	    // E. Chaining Optionals in Java 8
	    givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturned();
	    givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturnedAndRestNotEvaluated();
	    givenTwoOptionalsReturnedByOneArgMethod_whenChaining_thenFirstNonEmptyIsReturned();
	    givenTwoEmptyOptionals_whenChaining_thenDefaultIsReturned();
		
	}
	
	// C1. Conditional return With filter()
	private static boolean priceIsInRange1(Modem modem) {
		boolean isInRange = false;

		if (modem != null && modem.getPrice() != null 
				&& (modem.getPrice() >= 10 && modem.getPrice() <= 15)) {
			isInRange = true;
		}
		return isInRange;
	}
	
	// C2. Conditional return With filter()
	private static boolean priceIsInRange2(Modem modem2) {
		return Optional.ofNullable(modem2)											// optinal[modem] or Optional.empty. if Optional.empty, map & filters are skipped
						.map(Modem::getPrice)										// optional[20]  or Optional.empty. if Optional.empty, filters are skipped
						.filter(p -> p >= 10)
						.filter(p -> p <= 15)
						.isPresent();
	}
	
	// D1. Transforming Value With map()
	private static void givenOptional_whenMapWorks_thenCorrect() {
	    List<String> companyNames = Arrays.asList("paypal", "oracle", "", "microsoft", "", "apple");
	    Optional<List<String>> listOptional = Optional.of(companyNames);

	    int size = listOptional
	    				.map(List::size)
	    				.orElse(0);
	    System.out.println(size);														// 6								
	}
	
	// D2. Transforming Value With map()
	private static void givenOptional_whenMapWorks_thenCorrect2() {
	    String name = "baeldung";
	    Optional<String> nameOptional = Optional.of(name);

	    int len = nameOptional
	    			.map(String::length)
	    			.orElse(0);
	    System.out.println(len);														// 8	
	}
	
	// D3. Transforming Value With map()
	private static void givenOptional_whenMapWorksWithFilter_thenCorrect() {
	    String password = " password ";
	    Optional<String> passwordOptional = Optional.of(password);
	    boolean correctPassword = passwordOptional
	    							.filter(pass -> pass.equals("password"))
	    							.isPresent();
	    System.out.println(correctPassword);											// false

	    correctPassword = passwordOptional
	    							.map(String::trim)
	    							.filter(pass -> pass.equals("password"))
	    							.isPresent();
	    System.out.println(correctPassword);											// true	    
	}
	
	// E1. Chaining Optionals in Java 8
	// chain several Optional objects and get the first non-empty one in Java 8
	// The downside of this approach is that all of our get methods are always executed, regardless of where a non-empty Optional appears in the Stream
	private static void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturned() {
	    Optional<String> found = Stream.of(getEmpty(), getHello(), getBye())
	      .filter(Optional::isPresent)
	      .map(Optional::get)
	      .findFirst();
	    System.out.println(found); 														// Optional[hello]
	}
	
	// E2. Chaining Optionals in Java 8
	// If we want to lazily evaluate the methods passed to Stream.of(), we need to use the method reference and the Supplier interface
	private static void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturnedAndRestNotEvaluated() {
	    Optional<String> found =
	        Stream.<Supplier<Optional<String>>>of(
	                () -> getEmpty(),
	                () -> getHello(),
	                () -> getBye()
	            )
	            .map(Supplier::get)           // call supplier lazily, one by one
	            .filter(Optional::isPresent)  // stop at first non-empty
	            .map(Optional::get)
	            .findFirst();

	    System.out.println(found); 														// Optional[hello]
	}
	
	// E3. Chaining Optionals in Java 8
	// In case we need to use methods that take arguments, we have to resort to lambda expressions
	private static void givenTwoOptionalsReturnedByOneArgMethod_whenChaining_thenFirstNonEmptyIsReturned() {
	    Optional<String> found = Stream.<Supplier<Optional<String>>>of(
	      () -> createOptional("empty"),
	      () -> createOptional("hello")
	    )
	      .map(Supplier::get)
	      .filter(Optional::isPresent)
	      .map(Optional::get)
	      .findFirst();
	    System.out.println(found);														// Optional[hello]
	}				
	
	// E4. Chaining Optionals in Java 8
	// Often, we’ll want to return a default value in case all of the chained Optionals are empty. We can do so just by adding a call to orElse() or orElseGet()
	private static void givenTwoEmptyOptionals_whenChaining_thenDefaultIsReturned() {
	    String found = Stream.<Supplier<Optional<String>>>of(
	      () -> createOptional("empty"),
	      () -> createOptional("empty")
	    )
	      .map(Supplier::get)
	      .filter(Optional::isPresent)
	      .map(Optional::get)
	      .findFirst()
	      .orElseGet(() -> "default");
	    System.out.println(found);														// default 
	}
	
	private static Optional<String> getEmpty() {
	    return Optional.empty();
	}

	private static Optional<String> getHello() {
	    return Optional.of("hello");
	}

	private static Optional<String> getBye() {
	    return Optional.of("bye");
	}

	private static Optional<String> createOptional(String input) {
	    if (input == null || "".equals(input) || "empty".equals(input)) {
	        return Optional.empty();
	    }
	    return Optional.of(input);
	}
}
