package common;

import java.io.Serializable;

public class ItemInCart implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  TypeMeal;
	private String Dishes;
	private String Extras;
	private Integer TotalPrice;
	private Integer quantity;
	public ItemInCart(String typeMeal, String dishes, String extras, Integer totalPrice, Integer quantity) {
		super();
		TypeMeal = typeMeal;
		Dishes = dishes;
		Extras = extras;
		TotalPrice = totalPrice;
		this.quantity = quantity;
	}
	public String getTypeMeal() {
		return TypeMeal;
	}
	public void setTypeMeal(String typeMeal) {
		TypeMeal = typeMeal;
	}
	public String getDishes() {
		return Dishes;
	}
	public void setDishes(String dishes) {
		Dishes = dishes;
	}
	public String getExtras() {
		return Extras;
	}
	public void setExtras(String extras) {
		Extras = extras;
	}
	public Integer getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		TotalPrice = totalPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}