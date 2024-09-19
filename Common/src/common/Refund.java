package common;

import java.io.Serializable;

public class Refund implements Serializable{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String CustomerID;
		private String ResturantID;
		private String Refund;
		public Refund(String customerID, String resturantID, String refund) {
			super();
			CustomerID = customerID;
			ResturantID = resturantID;
			Refund = refund;
		}
		public String getCustomerID() {
			return CustomerID;
		}
		public void setCustomerID(String customerID) {
			CustomerID = customerID;
		}
		public String getResturantID() {
			return ResturantID;
		}
		public void setResturantID(String resturantID) {
			ResturantID = resturantID;
		}
		public String getRefund() {
			return Refund;
		}
		public void setRefund(String refund) {
			Refund = refund;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((CustomerID == null) ? 0 : CustomerID.hashCode());
			result = prime * result + ((Refund == null) ? 0 : Refund.hashCode());
			result = prime * result + ((ResturantID == null) ? 0 : ResturantID.hashCode());
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
			Refund other = (Refund) obj;
			if (CustomerID == null) {
				if (other.CustomerID != null)
					return false;
			} else if (!CustomerID.equals(other.CustomerID))
				return false;
			if (Refund == null) {
				if (other.Refund != null)
					return false;
			} else if (!Refund.equals(other.Refund))
				return false;
			if (ResturantID == null) {
				if (other.ResturantID != null)
					return false;
			} else if (!ResturantID.equals(other.ResturantID))
				return false;
			return true;
		}
		
		
		

}