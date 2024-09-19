package common;

import java.io.Serializable;
import java.util.Objects;

public class waiting_account implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String FirstName;
	private String LastName;
	private String PhoneNumber;
	private String Email;
	private String CompanyName;
	private String EmplyerID;
	private String CreditCard;
	private int ceiling;
	private String Address;

	public waiting_account(String iD, String firstName, String lastName, String phoneNumber, String email,
			String companyName, String emplyerID, String creditCard, int ceiling, String address) {
		super();
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		PhoneNumber = phoneNumber;
		Email = email;
		CompanyName = companyName;
		EmplyerID = emplyerID;
		CreditCard = creditCard;
		this.ceiling = ceiling;
		Address = address;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getEmplyerID() {
		return EmplyerID;
	}

	public void setEmplyerID(String emplyerID) {
		EmplyerID = emplyerID;
	}

	public String getCreditCard() {
		return CreditCard;
	}

	public void setCreditCard(String creditCard) {
		CreditCard = creditCard;
	}

	public int getCeiling() {
		return ceiling;
	}

	public void setCeiling(int ceiling) {
		this.ceiling = ceiling;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public String toString() {
		return "waiting_account [ID=" + ID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", PhoneNumber="
				+ PhoneNumber + ", Email=" + Email + ", CompanyName=" + CompanyName + ", EmplyerID=" + EmplyerID
				+ ", CreditCard=" + CreditCard + ", ceiling=" + ceiling + ", Address=" + Address + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Address, CompanyName, CreditCard, Email, EmplyerID, FirstName, ID, LastName, PhoneNumber,
				ceiling);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		waiting_account other = (waiting_account) obj;
		return Objects.equals(Address, other.Address) && Objects.equals(CompanyName, other.CompanyName)
				&& Objects.equals(CreditCard, other.CreditCard) && Objects.equals(Email, other.Email)
				&& Objects.equals(EmplyerID, other.EmplyerID) && Objects.equals(FirstName, other.FirstName)
				&& Objects.equals(ID, other.ID) && Objects.equals(LastName, other.LastName)
				&& Objects.equals(PhoneNumber, other.PhoneNumber) && ceiling == other.ceiling;
	}
	
	
	

}