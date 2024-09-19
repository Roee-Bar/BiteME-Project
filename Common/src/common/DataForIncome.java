package common;

import java.io.Serializable;
import java.util.Objects;

public class DataForIncome implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String week;
	private int numberOfOrders;
	private int income;
	public DataForIncome(String week, int numberOfOrders, int income) {
		super();
		this.week = week;
		this.numberOfOrders = numberOfOrders;
		this.income = income;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getNumberOfOrders() {
		return numberOfOrders;
	}
	public void setNumberOfOrders(int numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	@Override
	public int hashCode() {
		return Objects.hash(income, numberOfOrders, week);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataForIncome other = (DataForIncome) obj;
		return income == other.income && numberOfOrders == other.numberOfOrders && Objects.equals(week, other.week);
	}
	
}
