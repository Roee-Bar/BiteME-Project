package common;

import java.io.Serializable;
import java.util.Objects;

public class HistogramValues implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resturantName;
	private int numberOfOrdes;
	private int income;
	public HistogramValues(String resturantName, int numberOfOrdes, int income) {
		super();
		this.resturantName = resturantName;
		this.numberOfOrdes = numberOfOrdes;
		this.income = income;
	}
	public String getResturantName() {
		return resturantName;
	}
	public void setResturantName(String resturantName) {
		this.resturantName = resturantName;
	}
	public int getNumberOfOrdes() {
		return numberOfOrdes;
	}
	public void setNumberOfOrdes(int numberOfOrdes) {
		this.numberOfOrdes = numberOfOrdes;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	@Override
	public int hashCode() {
		return Objects.hash(income, numberOfOrdes, resturantName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistogramValues other = (HistogramValues) obj;
		return income == other.income && numberOfOrdes == other.numberOfOrdes
				&& Objects.equals(resturantName, other.resturantName);
	}
	
}
