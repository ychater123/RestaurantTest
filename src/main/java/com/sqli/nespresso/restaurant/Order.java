package com.sqli.nespresso.restaurant;

public class Order {
	private String name;
	private Integer maxPlace;
	private Integer fullPlace = 1;
	
	public Order(String name, Integer maxPlace) {
		this.name = name;
		this.maxPlace = maxPlace;
	}
	
	public void incrementPlace() {
		fullPlace++;
	}
	
	// just if the user change an order with maxPlace > 1
	public void decrementPlace() {
		fullPlace--;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getMaxPlace() {
		return maxPlace;
	}
	
	public Integer getFullPlace() {
		return fullPlace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
