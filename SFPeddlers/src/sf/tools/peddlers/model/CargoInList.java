package sf.tools.peddlers.model;

import sf.tools.peddlers.db.DataStructure.DSCargoInList;
import sf.tools.peddlers.model.Cargo.CUSTOMER_BEHAVIOR;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class CargoInList extends Model{
	public static final String TAG = "CargoInList";

	private Cargo mCargo;
	private ShoppingList mShoppingList = null;
	private CUSTOMER_BEHAVIOR mUserBehavior = CUSTOMER_BEHAVIOR.CB_NONE;

    public static final Parcelable.Creator<CargoInList> CREATOR = new Parcelable.Creator<CargoInList>() {
		public CargoInList createFromParcel(Parcel in) {
		    return new CargoInList(in);
		}
		
		public CargoInList[] newArray(int size) {
		    return new CargoInList[size];
		}
	};
	public CargoInList(Parcel in) {
		super(in);
		this.mCargo = in.readParcelable(null);
		this.mShoppingList = in.readParcelable(null);
		this.mUserBehavior = CUSTOMER_BEHAVIOR.values()[in.readInt()];
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeParcelable(mCargo, flags);
		out.writeParcelable(mShoppingList, flags);
		out.writeInt(mUserBehavior.ordinal());
	}
	
	public CargoInList(Cargo cargo, ShoppingList shoppingList, CUSTOMER_BEHAVIOR userBehavior) {
		this.setmCargo(cargo);
		this.setmShoppingList(shoppingList);
		this.setmUserBehavior(userBehavior);
	}

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
