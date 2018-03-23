package com.sqli.nespresso.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Restaurant {
	private List<Table> tables = new ArrayList<Table>();
	private static Integer counter = Consts.INITIAL_VALUE;

	public int initTable(Integer placeNumber) {
		counter++;
		Table table = new Table(counter, placeNumber);
		tables.add(table);
		return counter;
	}

	public void customerSays(int tableId, String str) {
		Table table = getTableById(tableId);
		if(table != null) {
			String[] customer_order = str.split(Consts.SEPARATOR);
			Customer customer = table.getCustomerByName(customer_order[Consts.FIRST_ELT]);
			
			if(customer == null) {
				table.addCustomer(customer_order[Consts.FIRST_ELT], customer_order[Consts.SECOND_ELT]);
			} else {
				table.modifyCustomerOrder(customer, customer_order[Consts.SECOND_ELT]);
			}
		}
	}

	public String createOrder(int tableId) {
		Table table = getTableById(tableId);
		return table.createOrder();
	}
	
	private Table getTableById(Integer tableId) {
		for (Table table : tables) {
			if(table.getId() == tableId) return table;
		}
		return null;
	}

}
