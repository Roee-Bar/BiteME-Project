package common;

import java.io.Serializable;
import java.util.Objects;

public class BranchManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchID;
	private String userName;
	public BranchManager(String branchID, String userName) {
		super();
		this.branchID = branchID;
		this.userName = userName;
	}
	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(branchID, userName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BranchManager other = (BranchManager) obj;
		return Objects.equals(branchID, other.branchID) && Objects.equals(userName, other.userName);
	}
	
}
