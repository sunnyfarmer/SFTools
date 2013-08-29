package sf.tools.peddlers.model;

import java.io.Serializable;

import sf.tools.peddlers.db.DataStructure.DSCargoInList;
import sf.tools.peddlers.model.Cargo.CUSTOMER_BEHAVIOR;

import android.content.ContentValues;

public class CargoInList implements Model, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7762934483046650254L;
	public static final String TAG = "CargoInList";

	private Cargo mCargo;
	private ShoppingList mShoppingList = null;
	private CUSTOMER_BEHAVIOR mUserBehavior = CUSTOMER_BEHAVIOR.CB_NONE;

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(DSCargoInList.COL_CARGO_ID, this.mCargo.getmCargoId());
		values.put(DSCargoInList.COL_SHOPPING_LIST_ID, this.mShoppingList.getmShoppingListId());
		values.put(DSCargoInList.COL_USER_BEHAVIOR, this.mUserBehavior.ordinal());
		return values;
	}

	public Cargo getmCargo() {
		return mCargo;
	}

	public void setmCargo(Cargo mCargo) {
		this.mCargo = mCargo;
	}

	public ShoppingList getmShoppingList() {
		return mShoppingList;
	}

	public void setmShoppingList(ShoppingList mShoppingList) {
		this.mShoppingList = mShoppingList;
	}

	public CUSTOMER_BEHAVIOR getmUserBehavior() {
		return mUserBehavior;
	}

	public void setmUserBehavior(CUSTOMER_BEHAVIOR mUserBehavior) {
		this.mUserBehavior = mUserBehavior;
	}
	
}
