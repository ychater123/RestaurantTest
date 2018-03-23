package com.sqli.nespresso.restaurant;

public class Customer implements Comparable<Customer>{
	
	private Integer id;
	private String name;
	private Order order;
	private static Integer counter = Consts.INITIAL_VALUE;
	
	public Customer(String name, Order order) {
		this.id = ++counter;
		this.name = name;
		this.order = order;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}
	
	public int compareTo(Customer o) {
		return this.id.compareTo(o.id);
	}
}
