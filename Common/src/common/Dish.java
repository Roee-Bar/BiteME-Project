package common;

import java.io.Serializable;

public class Dish implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TybeMeal_ID;
	private String Dish_ID;
	private String Dish;
	private Integer DishPrice;
	
	
	public Dish(String tybeMeal_ID, String dish_ID, String dish,int dishPrice) {
		super();
		TybeMeal_ID = tybeMeal_ID;
		Dish_ID = dish_ID;
		Dish = dish;
		DishPrice=dishPrice;
	}

	public int getDishPrice() {
		return DishPrice;
	}
	
	public int setDishPrice() {
		return DishPrice;
	}
	
	
	public String getTybeMeal_ID() {
		return TybeMeal_ID;
	}


	public void setTybeMeal_ID(String tybeMeal_ID) {
		TybeMeal_ID = tybeMeal_ID;
	}


	public String getDish_ID() {
		return Dish_ID;
	}


	public void setDish_ID(String dish_ID) {
		Dish_ID = dish_ID;
	}


	public String getDish() {
		return Dish;
	}


	public void setDish(String dish) {
		Dish = dish;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Dish == null) ? 0 : Dish.hashCode());
		result = prime * result + ((Dish_ID == null) ? 0 : Dish_ID.hashCode());
		result = prime * result + ((TybeMeal_ID == null) ? 0 : TybeMeal_ID.hashCode());
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
		Dish other = (Dish) obj;
		if (Dish == null) {
			if (other.Dish != null)
				return false;
		} else if (!Dish.equals(other.Dish))
			return false;
		if (Dish_ID == null) {
			if (other.Dish_ID != null)
				return false;
		} else if (!Dish_ID.equals(other.Dish_ID))
			return false;
		if (TybeMeal_ID == null) {
			if (other.TybeMeal_ID != null)
				return false;
		} else if (!TybeMeal_ID.equals(other.TybeMeal_ID))
			return false;
		return true;
	}
	
	
	
	
}