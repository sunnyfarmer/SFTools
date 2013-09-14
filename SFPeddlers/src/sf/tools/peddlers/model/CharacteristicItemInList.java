package sf.tools.peddlers.model;

import sf.tools.peddlers.db.DataStructure.DSCharacteristicItemInList;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class CharacteristicItemInList extends Model {
	public static final String TAG = "CharacteristicItemInList";

	private CharacteristicItem mCharacteristicItem = null;
	private ShoppingList mShoppingList = null;

    public static final Parcelable.Creator<CharacteristicItemInList> CREATOR = new Parcelable.Creator<CharacteristicItemInList>() {
		public CharacteristicItemInList createFromParcel(Parcel in) {
		    return new CharacteristicItemInList(in);
		}
		
		public CharacteristicItemInList[] newArray(int size) {
		    return new CharacteristicItemInList[size];
		}
	};
	public CharacteristicItemInList(Parcel in) {
		this.mCharacteristicItem = in.readParcelable(null);
		this.mShoppingList = in.readParcelable(null);
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeParcelable(this.mCharacteristicItem, flags);
		out.writeParcelable(this.mShoppingList, flags);
	}

	public CharacteristicItemInList(CharacteristicItem characteristicItem, ShoppingList shoppingList) {
		this.setmCharacteristicItem(characteristicItem);
		this.setmShoppingList(shoppingList);
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(DSCharacteristicItemInList.COL_CHARACTERISTIC_ITEM_ID, this.mCharacteristicItem.getmCharacteristicItemId());
		values.put(DSCharacteristicItemInList.COL_SHOPPING_LIST_ID, this.mShoppingList.getmShoppingListId());
		return values;
	}

	public CharacteristicItem getmCharacteristicItem() {
		return mCharacteristicItem;
	}

	public void setmCharacteristicItem(CharacteristicItem mCharacteristicItem) {
		this.mCharacteristicItem = mCharacteristicItem;
	}

	public ShoppingList getmShoppingList() {
		return mShoppingList;
	}

	public void setmShoppingList(ShoppingList mShoppingList) {
		this.mShoppingList = mShoppingList;
	}

}
