package common;

import java.io.Serializable;

public class W4C_Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String w4cCode;
	private String CreditCardNumber;
	private String AccountType;
	public W4C_Card(String w4cCode, String creditCardNumber, String accountType) {
		super();
		this.w4cCode = w4cCode;
		CreditCardNumber = creditCardNumber;
		AccountType = accountType;
	}
	public String getW4cCode() {
		return w4cCode;
	}
	public void setW4cCode(String w4cCode) {
		this.w4cCode = w4cCode;
	}
	public String getCreditCardNumber() {
		return CreditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		CreditCardNumber = creditCardNumber;
	}
	public String getAccountType() {
		return AccountType;
	}
	public void setAccountType(String accountType) {
		AccountType = accountType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AccountType == null) ? 0 : AccountType.hashCode());
		result = prime * result + ((CreditCardNumber == null) ? 0 : CreditCardNumber.hashCode());
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
		W4C_Card other = (W4C_Card) obj;
		if (AccountType == null) {
			if (other.AccountType != null)
				return false;
		} else if (!AccountType.equals(other.AccountType))
			return false;
		if (CreditCardNumber == null) {
			if (other.CreditCardNumber != null)
				return false;
		} else if (!CreditCardNumber.equals(other.CreditCardNumber))
			return false;
		if (w4cCode == null) {
			if (other.w4cCode != null)
				return false;
		} else if (!w4cCode.equals(other.w4cCode))
			return false;
		return true;
	}

	
	

}
