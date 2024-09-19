package common;

import java.io.Serializable;
import java.util.Objects;

public class Resturant implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String resturantName;
	private String isOpen;
	private String resturantPhoneNumber;
	private String Branch;
	private String branchId;
	private String year;
	private String month;
	public Resturant( String resturantName, String isOpen,String resturantPhoneNumber,String id,String branchId,String Branch,String year,String month) {
		super();
		this.id = id;
		this.resturantName = resturantName;
		this.resturantPhoneNumber=resturantPhoneNumber;
		this.isOpen = isOpen;
		this.Branch=Branch;
		this.branchId=branchId;
		this.year=year;
		this.month=month;
		
	}
	@Override
	public int hashCode() {
		return Objects.hash(Branch, id, isOpen, resturantName, resturantPhoneNumber);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resturant other = (Resturant) obj;
		return Objects.equals(Branch, other.Branch) && Objects.equals(id, other.id)
				&& Objects.equals(isOpen, other.isOpen) && Objects.equals(resturantName, other.resturantName)
				&& Objects.equals(resturantPhoneNumber, other.resturantPhoneNumber);
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
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getResturantPhoneNumber() {
		return resturantPhoneNumber;
	}
	public void setResturantPhoneNumber(String resturantPhoneNumber) {
		this.resturantPhoneNumber = resturantPhoneNumber;
	}
	public String getBranch() {
		return Branch;
	}
	public void setBranch(String branch) {
		Branch = branch;
	}

	

	
}