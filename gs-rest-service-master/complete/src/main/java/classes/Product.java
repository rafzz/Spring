package classes;

import java.util.concurrent.atomic.AtomicLong;

public class Product {

	private String name;
	private double price;
	private int id;
	
	public Product(String name, double price) {
		this.name = name;
		this.price = price;
		
	}

	public Product() {

		
	}
	
	

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object obj) {
		
		Product product = (Product) obj;
		return this.id==product.id; 
				
		
	}

	@Override
	public String toString() {
		return "Product " + this.id + ": [ " + name +" price:"+  price + " ]";
	}
	
	
	
	

}
