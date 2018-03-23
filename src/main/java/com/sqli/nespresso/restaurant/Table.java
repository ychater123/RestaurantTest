package com.sqli.nespresso.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Table {
	private Integer id;
	private Integer placeNumber;
	private SortedSet<Customer> customers = new TreeSet<Customer>();
	private List<Order> orders = new ArrayList<Order>();
	private Order lastOrder;

	private final static String MISSING_MESSAGE1 = "MISSING %d";
	private final static String MISSING_MESSAGE2 = "MISSING %d for %s";

	public Table(Integer id, Integer placeNumber) {
		this.id = id;
		this.placeNumber = placeNumber;
	}
	
	public Customer addCustomer(String name, String orderName) {
		Customer customer;
		if(orderName.equals("Same")) {
			customer = new Customer(name, lastOrder);
		} else {
			Order order = createOrderFromName(orderName);
			customer = new Customer(name, order);
			lastOrder = order;
		}
		customers.add(customer);
		return customer;
	}
	
	public Customer modifyCustomerOrder(Customer customer, String orderName) {
		// just if the user change an order with maxPlace > 1 // to check
		if(customer.getOrder().getMaxPlace() > Consts.DEFAULT_MAX_PLACE && !customer.getOrder().getName().equals(orderName)) {
			customer.getOrder().decrementPlace();
		}
		// end
		
		if(orderName.equals("Same")) {
			customer.setOrder(lastOrder);
		} else {
			Order order = createOrderFromName(orderName);
			customer.setOrder(order);
			lastOrder = order;
		}
		return customer;
	}
	
	public Order createOrderFromName(String name) {
		Order order = getOrderByName(name);
		if(order == null) {
			if(name.contains(Consts.ORDER_SEPERATOR)) order = new Order(name, Integer.parseInt(name.split(Consts.ORDER_SEPERATOR)[Consts.SECOND_ELT]));
			else order = new Order(name, Consts.DEFAULT_MAX_PLACE);
			orders.add(order);
		} else {
			if(name.contains(Consts.ORDER_SEPERATOR)) order.incrementPlace();
		}
		return order;
	}
	
	public Boolean isEveryoneOrdered() {
		return customers.size() == placeNumber;
	}
	
	public void setLastOrder(Order lastOrder) {
		this.lastOrder = lastOrder;
	}

	public Integer getId() {
		return id;
	}
	
	public Customer getCustomerByName(String name) {
		for (Customer customer : customers) {
			if(customer.getName().equals(name)) return customer;
		}
		return null;
	}
	
	public Order getOrderByName(String name) {
		for (Order order : orders) {
			if(order.getName().equals(name)) return order;
		}
		return null;
	}

	
	public String createOrder() {
		if(!isEveryoneOrdered()) {
			return String.format(MISSING_MESSAGE1, placeNumber - customers.size());
		} else {
			List<Order> orders = customers.stream().map(Customer::getOrder).collect(Collectors.toList());
			for (Order order : orders) {
				if(order.getFullPlace() < order.getMaxPlace()) return String.format(MISSING_MESSAGE2, order.getMaxPlace() - order.getFullPlace(), order.getName());
			}
			List<String> ordersName = orders.stream().map(Order::getName).collect(Collectors.toList());
			return ordersName.stream().collect(Collectors.joining(Consts.ORDER_NAME_JOINER));
		}
	}
}
