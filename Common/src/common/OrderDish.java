package common;

import java.io.Serializable;
import java.util.Objects;

public class OrderDish implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mealType;
	private String mealName;
	private String optionalIngredients;
	private int total;
	private int Quantity;
	
	public OrderDish(String mealType, String mealName, String optionalIngredients, int total, int quantity) {
		super();
		this.mealType = mealType;
		this.mealName = mealName;
		this.optionalIngredients = optionalIngredients;
		this.total = total;
		Quantity = quantity;
	}
	
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public String getMealName() {
		return mealName;
	}
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	public String getOptionalIngredients() {
		return optionalIngredients;
	}
	public void setOptionalIngredients(String optionalIngredients) {
		this.optionalIngredients = optionalIngredients;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderDish [mealType=" + mealType + ", mealName=" + mealName + ", optionalIngredients="
				+ optionalIngredients + ", total=" + total + ", Quantity=" + Quantity + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Quantity, mealName, mealType, optionalIngredients, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDish other = (OrderDish) obj;
		return Quantity == other.Quantity && Objects.equals(mealName, other.mealName)
				&& Objects.equals(mealType, other.mealType)
				&& Objects.equals(optionalIngredients, other.optionalIngredients) && total == other.total;
	}
	

}