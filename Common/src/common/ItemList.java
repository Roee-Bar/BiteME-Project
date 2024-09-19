package common;

import java.io.Serializable;

public class ItemList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TheMeal;
	private String TheDish;
	private String Ingredient;
	private Integer Quantity;
	private Integer Price;
	private Integer PackageID;
	public ItemList(String theMeal, String theDish, String ingredient, Integer quantity, Integer price,
			Integer packageID) {
		super();
		TheMeal = theMeal;
		TheDish = theDish;
		Ingredient = ingredient;
		Quantity = quantity;
		Price = price;
		PackageID = packageID;
	}
	public String getTheMeal() {
		return TheMeal;
	}
	public void setTheMeal(String theMeal) {
		TheMeal = theMeal;
	}
	public String getTheDish() {
		return TheDish;
	}
	public void setTheDish(String theDish) {
		TheDish = theDish;
	}
	public String getIngredient() {
		return Ingredient;
	}
	public void setIngredient(String ingredient) {
		Ingredient = ingredient;
	}
	public Integer getQuantity() {
		return Quantity;
	}
	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}
	public Integer getPrice() {
		return Price;
	}
	public void setPrice(Integer price) {
		Price = price;
	}
	public Integer getPackageID() {
		return PackageID;
	}
	public void setPackageID(Integer packageID) {
		PackageID = packageID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Ingredient == null) ? 0 : Ingredient.hashCode());
		result = prime * result + ((PackageID == null) ? 0 : PackageID.hashCode());
		result = prime * result + ((Price == null) ? 0 : Price.hashCode());
		result = prime * result + ((Quantity == null) ? 0 : Quantity.hashCode());
		result = prime * result + ((TheDish == null) ? 0 : TheDish.hashCode());
		result = prime * result + ((TheMeal == null) ? 0 : TheMeal.hashCode());
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
		ItemList other = (ItemList) obj;
		if (Ingredient == null) {
			if (other.Ingredient != null)
				return false;
		} else if (!Ingredient.equals(other.Ingredient))
			return false;
		if (PackageID == null) {
			if (other.PackageID != null)
				return false;
		} else if (!PackageID.equals(other.PackageID))
			return false;
		if (Price == null) {
			if (other.Price != null)
				return false;
		} else if (!Price.equals(other.Price))
			return false;
		if (Quantity == null) {
			if (other.Quantity != null)
				return false;
		} else if (!Quantity.equals(other.Quantity))
			return false;
		if (TheDish == null) {
			if (other.TheDish != null)
				return false;
		} else if (!TheDish.equals(other.TheDish))
			return false;
		if (TheMeal == null) {
			if (other.TheMeal != null)
				return false;
		} else if (!TheMeal.equals(other.TheMeal))
			return false;
		return true;
	}
	
	
	
	
}