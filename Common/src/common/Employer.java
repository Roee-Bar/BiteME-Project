package common;

import java.io.Serializable;
import java.util.Objects;

public class Employer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String employerID;
	private String companyName;
	private String hRID;
	private boolean isApproved;
	
	public Employer(String employerID, String companyName, String hRID, boolean isApproved) {
		super();
		this.employerID = employerID;
		this.companyName = companyName;
		this.hRID = hRID;
		this.isApproved = isApproved;
	}
	@Override
	public int hashCode() {
		return Objects.hash(companyName, employerID, hRID, isApproved);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employer other = (Employer) obj;
		return Objects.equals(companyName, other.companyName) && Objects.equals(employerID, other.employerID)
				&& Objects.equals(hRID, other.hRID) && isApproved == other.isApproved;
	}
	public String getEmployerID() {
		return employerID;
	}
	public void setEmployerID(String employerID) {
		this.employerID = employerID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String gethRID() {
		return hRID;
	}
	public void sethRID(String hRID) {
		this.hRID = hRID;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}


	

}
