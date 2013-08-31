package sf.tools.peddlers.model;

import java.io.Serializable;

import sf.tools.peddlers.db.DataStructure.DSCharacteristicItemInList;

import android.content.ContentValues;

public class CharacteristicItemInList implements Model, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5860043230744655455L;
	public static final String TAG = "CharacteristicItemInList";

	private CharacteristicItem mCharacteristicItem = null;
	private ShoppingList mShoppingList = null;

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
