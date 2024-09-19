package common;
import java.io.Serializable;

public class Account implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
   private String ID;
   private String FirstName;
   private String LasName;
   private String PhoneNumber;
   private String Email;
   private String W4C_QrCode;
   private String Location;
public Account(String iD, String firstName, String lasName, String phoneNumber, String email, String w4c_QrCode,
		String location) {
	super();
	ID = iD;
	FirstName = firstName;
	LasName = lasName;
	PhoneNumber = phoneNumber;
	Email = email;
	W4C_QrCode = w4c_QrCode;
	Location = location;
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
public String getLasName() {
	return LasName;
}
public void setLasName(String lasName) {
	LasName = lasName;
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
public String getW4C_QrCode() {
	return W4C_QrCode;
}
public void setW4C_QrCode(String w4c_QrCode) {
	W4C_QrCode = w4c_QrCode;
}
public String getLocation() {
	return Location;
}
public void setLocation(String location) {
	Location = location;
}
   
   
}