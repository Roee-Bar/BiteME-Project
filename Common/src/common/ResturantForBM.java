package common;

import java.io.Serializable;
import java.util.Objects;

public class ResturantForBM implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String resturantName;
	private boolean isOpen;
	private String resturantPhoneNumber;
	private String branchID;
	private String location;
	private int year;
	private int month;

	public ResturantForBM(String resturantName, boolean isOpen, String resturantPhoneNumber, String id, String branchID,
			String location, int year, int month) {
		super();
		this.id = id;
		this.resturantName = resturantName;
		this.isOpen = isOpen;
		this.resturantPhoneNumber = resturantPhoneNumber;
		this.branchID = branchID;
		this.location = location;
		this.year = year;
		this.month = month;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResturantName() {
		return resturantName;
	}

	public void setResturantName(String resturantName) {
		this.resturantName = resturantName;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getResturantPhoneNumber() {
		return resturantPhoneNumber;
	}

	public void setResturantPhoneNumber(String resturantPhoneNumber) {
		this.resturantPhoneNumber = resturantPhoneNumber;
	}

	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	@Override
	public int hashCode() {
		return Objects.hash(branchID, id, isOpen, location, month, resturantName, resturantPhoneNumber, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResturantForBM other = (ResturantForBM) obj;
		return Objects.equals(branchID, other.branchID) && Objects.equals(id, other.id) && isOpen == other.isOpen
				&& Objects.equals(location, other.location) && month == other.month
				&& Objects.equals(resturantName, other.resturantName)
				&& Objects.equals(resturantPhoneNumber, other.resturantPhoneNumber) && year == other.year;
	}

	
}
