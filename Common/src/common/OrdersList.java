package common;

import java.io.Serializable;

public class OrdersList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Customer_ID;
	private String Resturant;
	private Integer OrderPackageNumber;
	private String RequestDate;
	private String OrderedDate;
	private String TotalPrice;
	private String Address ;
	private String DeleiveryService;
	private String Status;
	private String ArrivalTime;
	private String ApprovalRecieving;
	private String Branchlocation;
	private String year;
	private String month;
	private Integer day;
	private String ArrivedToCustomerTime;
	private String OrderReadyTime; 
	
	
	public OrdersList(String customer_ID, String resturant, Integer orderPackageNumber, String requestDate,
			String orderedDate, String totalPrice, String address, String deleiveryService, String status,
			String arrivalTime, String approvalRecieving, String branchlocation, String year, String month, Integer day,
			String arrivedToCustomerTime, String orderReadyTime) {
		super();
		Customer_ID = customer_ID;
		Resturant = resturant;
		OrderPackageNumber = orderPackageNumber;
		RequestDate = requestDate;
		OrderedDate = orderedDate;
		TotalPrice = totalPrice;
		Address = address;
		DeleiveryService = deleiveryService;
		Status = status;
		ArrivalTime = arrivalTime;
		ApprovalRecieving = approvalRecieving;
		Branchlocation = branchlocation;
		this.year = year;
		this.month = month;
		this.day = day;
		ArrivedToCustomerTime = arrivedToCustomerTime;
		OrderReadyTime = orderReadyTime;
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






	public Integer getDay() {
		return day;
	}



	public void setDay(Integer day) {
		this.day = day;
	}



	public String getArrivedToCustomerTime() {
		return ArrivedToCustomerTime;
	}



	public void setArrivedToCustomerTime(String arrivedToCustomerTime) {
		ArrivedToCustomerTime = arrivedToCustomerTime;
	}



	public String getOrderReadyTime() {
		return OrderReadyTime;
	}



	public void setOrderReadyTime(String orderReadyTime) {
		OrderReadyTime = orderReadyTime;
	}



	public String getCustomer_ID() {
		return Customer_ID;
	}
	public void setCustomer_ID(String customer_ID) {
		Customer_ID = customer_ID;
	}
	public String getResturant() {
		return Resturant;
	}
	public void setResturant(String resturant) {
		Resturant = resturant;
	}
	public Integer getOrderPackageNumber() {
		return OrderPackageNumber;
	}
	public void setOrderPackageNumber(Integer orderPackageNumber) {
		OrderPackageNumber = orderPackageNumber;
	}
	public String getRequestDate() {
		return RequestDate;
	}
	public void setRequestDate(String requestDate) {
		RequestDate = requestDate;
	}
	public String getOrderedDate() {
		return OrderedDate;
	}
	public void setOrderedDate(String orderedDate) {
		OrderedDate = orderedDate;
	}
	public String getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		TotalPrice = totalPrice;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getDeleiveryService() {
		return DeleiveryService;
	}
	public void setDeleiveryService(String deleiveryService) {
		DeleiveryService = deleiveryService;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getArrivalTime() {
		return ArrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		ArrivalTime = arrivalTime;
	}
	public String getApprovalRecieving() {
		return ApprovalRecieving;
	}
	public void setApprovalRecieving(String approvalRecieving) {
		ApprovalRecieving = approvalRecieving;
	}
	public String getBranchlocation() {
		return Branchlocation;
	}
	public void setBranchlocation(String branchlocation) {
		Branchlocation = branchlocation;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Address == null) ? 0 : Address.hashCode());
		result = prime * result + ((ApprovalRecieving == null) ? 0 : ApprovalRecieving.hashCode());
		result = prime * result + ((ArrivalTime == null) ? 0 : ArrivalTime.hashCode());
		result = prime * result + ((Branchlocation == null) ? 0 : Branchlocation.hashCode());
		result = prime * result + ((Customer_ID == null) ? 0 : Customer_ID.hashCode());
		result = prime * result + ((DeleiveryService == null) ? 0 : DeleiveryService.hashCode());
		result = prime * result + ((OrderPackageNumber == null) ? 0 : OrderPackageNumber.hashCode());
		result = prime * result + ((OrderedDate == null) ? 0 : OrderedDate.hashCode());
		result = prime * result + ((RequestDate == null) ? 0 : RequestDate.hashCode());
		result = prime * result + ((Resturant == null) ? 0 : Resturant.hashCode());
		result = prime * result + ((Status == null) ? 0 : Status.hashCode());
		result = prime * result + ((TotalPrice == null) ? 0 : TotalPrice.hashCode());
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
		OrdersList other = (OrdersList) obj;
		if (Address == null) {
			if (other.Address != null)
				return false;
		} else if (!Address.equals(other.Address))
			return false;
		if (ApprovalRecieving == null) {
			if (other.ApprovalRecieving != null)
				return false;
		} else if (!ApprovalRecieving.equals(other.ApprovalRecieving))
			return false;
		if (ArrivalTime == null) {
			if (other.ArrivalTime != null)
				return false;
		} else if (!ArrivalTime.equals(other.ArrivalTime))
			return false;
		if (Branchlocation == null) {
			if (other.Branchlocation != null)
				return false;
		} else if (!Branchlocation.equals(other.Branchlocation))
			return false;
		if (Customer_ID == null) {
			if (other.Customer_ID != null)
				return false;
		} else if (!Customer_ID.equals(other.Customer_ID))
			return false;
		if (DeleiveryService == null) {
			if (other.DeleiveryService != null)
				return false;
		} else if (!DeleiveryService.equals(other.DeleiveryService))
			return false;
		if (OrderPackageNumber == null) {
			if (other.OrderPackageNumber != null)
				return false;
		} else if (!OrderPackageNumber.equals(other.OrderPackageNumber))
			return false;
		if (OrderedDate == null) {
			if (other.OrderedDate != null)
				return false;
		} else if (!OrderedDate.equals(other.OrderedDate))
			return false;
		if (RequestDate == null) {
			if (other.RequestDate != null)
				return false;
		} else if (!RequestDate.equals(other.RequestDate))
			return false;
		if (Resturant == null) {
			if (other.Resturant != null)
				return false;
		} else if (!Resturant.equals(other.Resturant))
			return false;
		if (Status == null) {
			if (other.Status != null)
				return false;
		} else if (!Status.equals(other.Status))
			return false;
		if (TotalPrice == null) {
			if (other.TotalPrice != null)
				return false;
		} else if (!TotalPrice.equals(other.TotalPrice))
			return false;
		return true;
	}
	
	
	
	

}