package common;

import java.io.Serializable;

public class Resturants implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String ResturantName;
	private String Status;
	private String PhoneNumber;
	private String ResturantID;
	private String Location;
	private String branchId;
	private String year;
	private String month;
	
	
	public Resturants(String resturantName, String status, String phoneNumber, String resturantID,String branchId, String location,String year,String month) {
		super();
		ResturantName = resturantName;
		Status = status;
		PhoneNumber = phoneNumber;
		ResturantID = resturantID;
		Location = location;
		this.branchId=branchId;
		this.year=year;
		this.month=month;
	}
	
	
	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getResturantName() {
		return ResturantName;
	}
	public void setResturantName(String resturantName) {
		ResturantName = resturantName;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getResturantID() {
		return ResturantID;
	}
	public void setResturantID(String resturantID) {
		ResturantID = resturantID;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Location == null) ? 0 : Location.hashCode());
		result = prime * result + ((PhoneNumber == null) ? 0 : PhoneNumber.hashCode());
		result = prime * result + ((ResturantID == null) ? 0 : ResturantID.hashCode());
		result = prime * result + ((ResturantName == null) ? 0 : ResturantName.hashCode());
		result = prime * result + ((Status == null) ? 0 : Status.hashCode());
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
		Resturants other = (Resturants) obj;
		if (Location == null) {
			if (other.Location != null)
				return false;
		} else if (!Location.equals(other.Location))
			return false;
		if (PhoneNumber == null) {
			if (other.PhoneNumber != null)
				return false;
		} else if (!PhoneNumber.equals(other.PhoneNumber))
			return false;
		if (ResturantID == null) {
			if (other.ResturantID != null)
				return false;
		} else if (!ResturantID.equals(other.ResturantID))
			return false;
		if (ResturantName == null) {
			if (other.ResturantName != null)
				return false;
		} else if (!ResturantName.equals(other.ResturantName))
			return false;
		if (Status == null) {
			if (other.Status != null)
				return false;
		} else if (!Status.equals(other.Status))
			return false;
		return true;
	}
	
	

	


}