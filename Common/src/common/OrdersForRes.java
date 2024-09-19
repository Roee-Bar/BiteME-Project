package common;

import java.io.Serializable;
import java.util.Objects;

public class OrdersForRes implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private String Customer_ID;
	private String resturant;
	private int orderNumber;
	private String RequestTime;
	private String OrderTime;
	private String TotalPrice;
	private String address;
	private String DeliveryType;
	private String Status;
	private String ArrivalTime;
	private String ApprovalStatus;
	private String Branchlocation;
	private String year;
	private String month;
	private Integer day;
	private String ArrivedToCustomerTime;
	private String OrderReadyTime; 
	
	
	
	public OrdersForRes(String customer_ID, String resturant, int orderNumber, String requestTime, String orderTime,
			String totalPrice, String address, String deliveryType, String status, String arrivalTime,
			String approvalStatus,String Branchlocation,String year,String month,Integer day,String ArrivedToCustomerTime,String OrderReadyTime) {
		super();
		Customer_ID = customer_ID;
		this.resturant = resturant;
		this.orderNumber = orderNumber;
		RequestTime = requestTime;
		OrderTime = orderTime;
		TotalPrice = totalPrice;
		this.address = address;
		DeliveryType = deliveryType;
		Status = status;
		ArrivalTime = arrivalTime;
		ApprovalStatus = approvalStatus;
		this.Branchlocation = Branchlocation;
		this.year = year;
		this.month = month;
		this.day = day;
		this.ArrivedToCustomerTime = ArrivedToCustomerTime;
		this.OrderReadyTime = ArrivedToCustomerTime;
	}



	public String getCustomer_ID() {
		return Customer_ID;
	}



	public void setCustomer_ID(String customer_ID) {
		Customer_ID = customer_ID;
	}



	public String getResturant() {
		return resturant;
	}



	public void setResturant(String resturant) {
		this.resturant = resturant;
	}



	public int getOrderNumber() {
		return orderNumber;
	}



	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}



	public String getRequestTime() {
		return RequestTime;
	}



	public void setRequestTime(String requestTime) {
		RequestTime = requestTime;
	}



	public String getOrderTime() {
		return OrderTime;
	}



	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}



	public String getTotalPrice() {
		return TotalPrice;
	}



	public void setTotalPrice(String totalPrice) {
		TotalPrice = totalPrice;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getDeliveryType() {
		return DeliveryType;
	}



	public void setDeliveryType(String deliveryType) {
		DeliveryType = deliveryType;
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



	public String getApprovalStatus() {
		return ApprovalStatus;
	}



	public void setApprovalStatus(String approvalStatus) {
		ApprovalStatus = approvalStatus;
	}



	@Override
	public int hashCode() {
		return Objects.hash(ApprovalStatus, ArrivalTime, Customer_ID, DeliveryType, OrderTime, RequestTime, Status,
				TotalPrice, address, orderNumber, resturant);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdersForRes other = (OrdersForRes) obj;
		return Objects.equals(ApprovalStatus, other.ApprovalStatus) && Objects.equals(ArrivalTime, other.ArrivalTime)
				&& Objects.equals(Customer_ID, other.Customer_ID) && Objects.equals(DeliveryType, other.DeliveryType)
				&& Objects.equals(OrderTime, other.OrderTime) && Objects.equals(RequestTime, other.RequestTime)
				&& Objects.equals(Status, other.Status) && Objects.equals(TotalPrice, other.TotalPrice)
				&& Objects.equals(address, other.address) && orderNumber == other.orderNumber
				&& Objects.equals(resturant, other.resturant);
	}



	@Override
	public String toString() {
		return "Order [Customer_ID=" + Customer_ID + ", resturant=" + resturant + ", orderNumber=" + orderNumber
				+ ", RequestTime=" + RequestTime + ", OrderTime=" + OrderTime + ", TotalPrice=" + TotalPrice
				+ ", address=" + address + ", DeliveryType=" + DeliveryType + ", Status=" + Status + ", ArrivalTime="
				+ ArrivalTime + ", ApprovalStatus=" + ApprovalStatus + "]";
	}
	
	
	
	
	
	
	
	

	

}