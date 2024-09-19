package common;

import java.io.Serializable;
import java.util.Objects;

public class HoumanResources implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String CompanyName;

	
	public HoumanResources(String iD, String companyName) {
		super();
		ID = iD;
		CompanyName = companyName;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getCompanyName() {
		return CompanyName;
	}


	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}


	@Override
	public String toString() {
		return "HoumanResources [ID=" + ID + ", CompanyName=" + CompanyName + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(CompanyName, ID);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoumanResources other = (HoumanResources) obj;
		return Objects.equals(CompanyName, other.CompanyName) && Objects.equals(ID, other.ID);
	}
	
	
	
	
	
}