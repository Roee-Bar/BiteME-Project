package common;

import java.io.Serializable;
import java.util.Objects;

public class DishForResturant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resturantId;
	private String mealType;
	private String mealName;
	private String optionalIngredients;
	private String mealId;
	private int price;

	public DishForResturant(String resturantId, String mealType, String mealName, String optionalIngredients, String mealId,
			int price) {
		super();
		this.resturantId = resturantId;
		this.mealType = mealType;
		this.mealName = mealName;
		this.optionalIngredients = optionalIngredients;
		this.mealId = mealId;
		this.price = price;
	}

	public String getResturantId() {
		return resturantId;
	}

	public void setResturantId(String resturantId) {
		this.resturantId = resturantId;
	}

	public String getMealType() {
		return mealType;
	}

	@Override 
	public String toString() {
		return "Dish [resturantId=" + resturantId + ", mealType=" + mealType + ", mealName=" + mealName
				+ ", optionalIngredients=" + optionalIngredients + ", mealId=" + mealId + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(mealId, mealName, mealType, optionalIngredients, price, resturantId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DishForResturant other = (DishForResturant) obj;
		return mealId == other.mealId && Objects.equals(mealName, other.mealName) && mealType == other.mealType
				&& Objects.equals(optionalIngredients, other.optionalIngredients) && price == other.price
				&& Objects.equals(resturantId, other.resturantId);
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

	public String getMealId() {
		return mealId;
	}

	public void setMealId(String mealId) {
		this.mealId = mealId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}