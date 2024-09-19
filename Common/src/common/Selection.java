package common;

import java.io.Serializable;

public class Selection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Dish_ID;
	private String Selction_ID;
	private String Selction;
	private Integer SelectionPrice;
	public Selection(String dish_ID, String selction_ID, String selction,Integer selectionprice) {
		super();
		Dish_ID = dish_ID;
		Selction_ID = selction_ID;
		Selction = selction;
		SelectionPrice=selectionprice;
	}
	
	public Integer getSelectionPrice() {
		return SelectionPrice;
	}
	
	public void setSelectionPrice(Integer selectionprice) {
	    SelectionPrice=selectionprice;
	}
	
	
	
	
	public String getDish_ID() {
		return Dish_ID;
	}
	public void setDish_ID(String dish_ID) {
		Dish_ID = dish_ID;
	}
	public String getSelction_ID() {
		return Selction_ID;
	}
	public void setSelction_ID(String selction_ID) {
		Selction_ID = selction_ID;
	}
	public String getSelction() {
		return Selction;
	}
	public void setSelction(String selction) {
		Selction = selction;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Dish_ID == null) ? 0 : Dish_ID.hashCode());
		result = prime * result + ((Selction == null) ? 0 : Selction.hashCode());
		result = prime * result + ((Selction_ID == null) ? 0 : Selction_ID.hashCode());
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
		Selection other = (Selection) obj;
		if (Dish_ID == null) {
			if (other.Dish_ID != null)
				return false;
		} else if (!Dish_ID.equals(other.Dish_ID))
			return false;
		if (Selction == null) {
			if (other.Selction != null)
				return false;
		} else if (!Selction.equals(other.Selction))
			return false;
		if (Selction_ID == null) {
			if (other.Selction_ID != null)
				return false;
		} else if (!Selction_ID.equals(other.Selction_ID))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return (Selction+"-Price:"+SelectionPrice);
	}
	

	
}
