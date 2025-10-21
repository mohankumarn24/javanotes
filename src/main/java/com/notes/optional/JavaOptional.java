package com.notes.optional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JavaOptional {

	private static class Modem {
	    private Double price;

	    public Modem(Double price) {
	        this.price = price;
	    }
	    
		public Double getPrice() { return price; }
		public void setPrice(Double price) { this.price = price; }
	}
	
	public static void main(String[] args) {

		// A
		String name = "hello";
		Optional<String> optional = Optional.empty();
		System.out.println(optional);														// Optional.empty
			
		try {
			optional = Optional.of(null);
		} catch (NullPointerException ex) {
			System.out.println(ex.getMessage());											// null
		}
		
		optional = Optional.ofNullable(name);
		System.out.println(optional);														// Optional[hello]
		
		if (optional.isEmpty()) {
			System.out.println("empty");
		} else if (optional.isPresent()) {
			System.out.println(optional.get()); 											// hello
			optional.ifPresent(System.out::println);    									// hello
		} else if (optional.filter(t -> t.equals("world")).isPresent()) {
			System.out.println(optional.get()); 	
		}
		
		// B
		String data = null;
		String optional2 = Optional.ofNullable(data).orElse("some data 1");							
		System.out.println(optional2);														// some data 1
		optional2 = Optional.ofNullable(data).orElseGet(() -> "some data 2");
		System.out.println(optional2);														// some data 2
		try {
			optional2 = Optional.ofNullable(data).orElseThrow(() -> new RuntimeException("RTE"));	
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());											// RTE
		}
		
		// C
		boolean isInRange = priceIsInRange1(new Modem(12d));
		System.out.println(isInRange);														// true
		
		isInRange = priceIsInRange2(new Modem(12d));
		System.out.println(isInRange);														// true
		
		// D
		givenOptional_whenMapWorks_thenCorrect();											// 6
		givenOptional_whenMapWorksWithFilter_thenCorrect();
		
	}
	
	// A1. Conditional Return With filter()
	private static boolean priceIsInRange1(Modem modem) {
		boolean isInRange = false;

		if (modem != null && modem.getPrice() != null 
				&& (modem.getPrice() >= 10 && modem.getPrice() <= 15)) {
			isInRange = true;
		}
		return isInRange;
	}
	
	// A2. Conditional Return With filter()
	private static boolean priceIsInRange2(Modem modem2) {
		return Optional.ofNullable(modem2)
						.map(Modem::getPrice)
						.filter(p -> p >= 10)
						.filter(p -> p <= 15)
						.isPresent();
	}
	
	// B1. Transforming Value With map()
	private static void givenOptional_whenMapWorks_thenCorrect() {
	    List<String> companyNames = Arrays.asList("paypal", "oracle", "", "microsoft", "", "apple");
	    Optional<List<String>> listOptional = Optional.of(companyNames);

	    int size = listOptional
	    				.map(List::size)
	    				.orElse(0);
	    System.out.println(size);														// 6								
	}
	
	// B2. Transforming Value With map()
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
}
