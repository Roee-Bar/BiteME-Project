package common;

import java.io.Serializable;
import java.util.Objects;

public class BranchQaurter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchID;
	private int year;
	private String quarter;
	private String repName;
	public BranchQaurter(String branchID, int year, String quarter,String repName) {
		super();
		this.branchID = branchID;
		this.year = year;
		this.quarter = quarter;
		this.repName=repName;
	}
	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getQuarter() {
		return quarter;
	}
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	public String getRepName() {
		return repName;
	}
	public void setRepName(String repName) {
		this.repName = repName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(branchID, quarter, repName, year);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BranchQaurter other = (BranchQaurter) obj;
		return Objects.equals(branchID, other.branchID) && Objects.equals(quarter, other.quarter)
				&& Objects.equals(repName, other.repName) && year == other.year;
	}

	
}
