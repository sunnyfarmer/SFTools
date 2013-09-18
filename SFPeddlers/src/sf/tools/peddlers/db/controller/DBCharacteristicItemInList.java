package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSCharacteristicItemInList;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.CharacteristicItemInList;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.model.ShoppingList;

public class DBCharacteristicItemInList extends DBController {
	public static final String TAG = "DBCharacteristicItemInList";

	public DBCharacteristicItemInList(Context context) {
		super(context);
		this.mTableName = DSCharacteristicItemInList.TB_NAME;
	}

	public boolean insert(CharacteristicItemInList characteristicItemInList) {
		if (characteristicItemInList==null ||
			characteristicItemInList.getmCharacteristicItem()==null ||
			characteristicItemInList.getmCharacteristicItem().getmCharacteristicItemId()==Model.ID_UNDEFINED ||
			characteristicItemInList.getmShoppingList()==null ||
			characteristicItemInList.getmShoppingList().getmShoppingListId()==null) {
			return false;
		}
		return super.insert(characteristicItemInList);
	}

	public boolean delete(ShoppingList shoppingList) {
		if (shoppingList==null ||
			shoppingList.getmShoppingListId()==null) {
			return false;
		}
		int rowDeleted = this.delete(
				String.format(
						"%s=?",
						DSCharacteristicItemInList.COL_SHOPPING_LIST_ID
						),
				new String[] {shoppingList.getmShoppingListId()}
				);
		return rowDeleted>0 ? true : false;
	}

	public boolean delete(CharacteristicItemInList characteristicItemInList) {
		if (characteristicItemInList==null ||
			characteristicItemInList.getmCharacteristicItem()==null ||
			characteristicItemInList.getmCharacteristicItem().getmCharacteristicItemId()==Model.ID_UNDEFINED ||
			characteristicItemInList.getmShoppingList()==null ||
			characteristicItemInList.getmShoppingList().getmShoppingListId()==null) {
			return false;
		}
		int rowDeleted = this.delete(
				String.format(
						"%s=? and %s=?",
						DSCharacteristicItemInList.COL_CHARACTERISTIC_ITEM_ID,
						DSCharacteristicItemInList.COL_SHOPPING_LIST_ID),
				new String[] {
					String.valueOf(characteristicItemInList.getmCharacteristicItem().getmCharacteristicItemId()),
					characteristicItemInList.getmShoppingList().getmShoppingListId()
				});
		return rowDeleted>0 ? true : false;
	}

	public ArrayList<CharacteristicItemInList> queryAll(CharacteristicItem characteristicItem) {
		if (characteristicItem!=null && characteristicItem.getmCharacteristicItemId()==Model.ID_UNDEFINED) {
			return null;
		}
		Cursor cursor = this.query(
				DSCharacteristicItemInList.COLUMNS,
				String.format("%s=?", DSCharacteristicItemInList.COL_CHARACTERISTIC_ITEM_ID),
				new String[] {String.valueOf(characteristicItem.getmCharacteristicItemId())},
				null);
		ArrayList<CharacteristicItemInList> characteristicItemInListArray = new ArrayList<CharacteristicItemInList>();
		while (cursor!=null && cursor.moveToNext()) {
			CharacteristicItemInList characteristicItemInList = this.parseCursor(cursor, null);
			characteristicItemInListArray.add(characteristicItemInList);
		}
		return characteristicItemInListArray;
	}

	public ArrayList<CharacteristicItemInList> queryAll(ShoppingList shoppingList) {
		if (shoppingList==null ||
			shoppingList.getmShoppingListId()==null) {
			
		}
		ArrayList<CharacteristicItemInList> characteristicItemInListArray = new ArrayList<CharacteristicItemInList>();
		Cursor cursor = this.query(
				DSCharacteristicItemInList.COLUMNS,
				String.format("%s=?", DSCharacteristicItemInList.COL_SHOPPING_LIST_ID),
				new String[] {shoppingList.getmShoppingListId()},
				null);
		while (cursor!=null && cursor.moveToNext()) {
			CharacteristicItemInList characteristicItemInList = this.parseCursor(cursor, shoppingList);
			characteristicItemInListArray.add(characteristicItemInList);
		}
		return characteristicItemInListArray;
	}

	private CharacteristicItemInList parseCursor(Cursor cursor, ShoppingList shoppingList) {
		CharacteristicItemInList characteristicItemInList = null;
		if (cursor!=null) {
			int characteristicItemId = cursor.getInt(cursor.getColumnIndex(DSCharacteristicItemInList.COL_CHARACTERISTIC_ITEM_ID));
			CharacteristicItem characteristicItem = this.getDbCharacteristicItem().query(characteristicItemId);

			if (shoppingList==null) {
				String shoppingListId = cursor.getString(cursor.getColumnIndex(DSCharacteristicItemInList.COL_SHOPPING_LIST_ID));
				shoppingList = this.getDbShoppingList().query(shoppingListId);
			}
			characteristicItemInList = new CharacteristicItemInList(characteristicItem, shoppingList);
		}
		return characteristicItemInList;
	}
}
