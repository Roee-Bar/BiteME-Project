package common;

import java.io.Serializable;

public class TybeMeal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resturantID;
	private String tybeMealID;
	private String typeMeal;
	

	public String getResturantID() {
		return resturantID;
	}
	public void setResturantID(String resturantID) {
		this.resturantID = resturantID;
	}
	public String getTybeMealID() {
		return tybeMealID;
	}
	public void setTybeMealID(String tybeMealID) {
		this.tybeMealID = tybeMealID;
	}
	public String getTypeMeal() {
		return typeMeal;
	}
	public void setTypeMeal(String typeMeal) {
		this.typeMeal = typeMeal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resturantID == null) ? 0 : resturantID.hashCode());
		result = prime * result + ((typeMeal == null) ? 0 : typeMeal.hashCode());
		result = prime * result + ((tybeMealID == null) ? 0 : tybeMealID.hashCode());
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
		TybeMeal other = (TybeMeal) obj;
		if (resturantID == null) {
			if (other.resturantID != null)
				return false;
		} else if (!resturantID.equals(other.resturantID))
			return false;
		if (typeMeal == null) {
			if (other.typeMeal != null)
				return false;
		} else if (!typeMeal.equals(other.typeMeal))
			return false;
		if (tybeMealID == null) {
			if (other.tybeMealID != null)
				return false;
		} else if (!tybeMealID.equals(other.tybeMealID))
			return false;
		return true;
	}

	public TybeMeal(String resturant_ID, String tybeMeal_ID, String tybeMeal) {
		super();
		resturantID = resturant_ID;
		tybeMealID = tybeMeal_ID;
		typeMeal = tybeMeal;
	}

}
