package common;

import java.io.Serializable;

public class Business implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String w4cCode;
	private String EmployerId;
	private String EmployerName;
	private String Ceiling;
	public Business(String w4cCode, String employerId, String employerName, String ceiling) {
		super();
		this.w4cCode = w4cCode;
		EmployerId = employerId;
		EmployerName = employerName;
		Ceiling = ceiling;
	}
	public String getW4cCode() {
		return w4cCode;
	}
	public void setW4cCode(String w4cCode) {
		this.w4cCode = w4cCode;
	}
	public String getEmployerId() {
		return EmployerId;
	}
	public void setEmployerId(String employerId) {
		EmployerId = employerId;
	}
	public String getEmployerName() {
		return EmployerName;
	}
	public void setEmployerName(String employerName) {
		EmployerName = employerName;
	}
	public String getCeiling() {
		return Ceiling;
	}
	public void setCeiling(String ceiling) {
		Ceiling = ceiling;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Ceiling == null) ? 0 : Ceiling.hashCode());
		result = prime * result + ((EmployerId == null) ? 0 : EmployerId.hashCode());
		result = prime * result + ((EmployerName == null) ? 0 : EmployerName.hashCode());
		result = prime * result + ((w4cCode == null) ? 0 : w4cCode.hashCode());
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
		Business other = (Business) obj;
		if (Ceiling == null) {
			if (other.Ceiling != null)
				return false;
		} else if (!Ceiling.equals(other.Ceiling))
			return false;
		if (EmployerId == null) {
			if (other.EmployerId != null)
				return false;
		} else if (!EmployerId.equals(other.EmployerId))
			return false;
		if (EmployerName == null) {
			if (other.EmployerName != null)
				return false;
		} else if (!EmployerName.equals(other.EmployerName))
			return false;
		if (w4cCode == null) {
			if (other.w4cCode != null)
				return false;
		} else if (!w4cCode.equals(other.w4cCode))
			return false;
		return true;
	}
	
	

}
