package com.notes.equalsHashcode;

import java.util.Objects;

public class Product {
	
    private final String productId;
    private String name;
    private double price;
    
    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    /*
     * auto-generated equals and hashCode
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product product = (Product) obj;
		return Objects.equals(name, product.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(product.price)
				&& Objects.equals(productId, product.productId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, price, productId);
	}
	*/

    @Override
    public boolean equals(Object obj) {
    	// 1. Check if same object reference
		if (this == obj)
			return true;
		// 2. Check if null or different class
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		// 4. Compare significant fields
        Product product = (Product) obj;
        return productId.equals(product.productId); 	// Products are equal if they have the same ID
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(productId);					// Hash code is based only on the ID
    }   
}