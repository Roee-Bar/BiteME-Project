package common;

import java.io.Serializable;

public class OptionalIngredients implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String option;
	private int price;
	private String dishID;
	private int selectionID;
	public OptionalIngredients(String option, int price, String dishID, int selectionID) {
		super();
		this.option = option;
		this.price = price;
		this.dishID = dishID;
		this.selectionID = selectionID;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDishID() {
		return dishID;
	} 
	public void setDishID(String dishID) {
		this.dishID = dishID;
	}
	public int getSelectionID() {
		return selectionID;
	}
	public void setSelectionID(int selectionID) {
		this.selectionID = selectionID;
	}
	
}