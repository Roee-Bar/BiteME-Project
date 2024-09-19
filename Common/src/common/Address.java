package common;

import java.io.Serializable;

public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String City;
	private String Street;
	private String HouseNumber;
	public Address(String city, String street, String houseNumber) {
		super();
		City = city;
		Street = street;
		HouseNumber = houseNumber;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getHouseNumber() {
		return HouseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		HouseNumber = houseNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((City == null) ? 0 : City.hashCode());
		result = prime * result + ((HouseNumber == null) ? 0 : HouseNumber.hashCode());
		result = prime * result + ((Street == null) ? 0 : Street.hashCode());
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
		Address other = (Address) obj;
		if (City == null) {
			if (other.City != null)
				return false;
		} else if (!City.equals(other.City))
			return false;
		if (HouseNumber == null) {
			if (other.HouseNumber != null)
				return false;
		} else if (!HouseNumber.equals(other.HouseNumber))
			return false;
		if (Street == null) {
			if (other.Street != null)
				return false;
		} else if (!Street.equals(other.Street))
			return false;
		return true;
	}
	public String toString() {
		return (City+","+Street+","+HouseNumber);
	}
	
	

}
