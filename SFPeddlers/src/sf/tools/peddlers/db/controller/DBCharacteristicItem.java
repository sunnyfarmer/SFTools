package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSCharacteristicItem;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.Model;

public class DBCharacteristicItem extends DBController {
	public static final String TAG = "DBCharacteristicItem";

	public DBCharacteristicItem(Context context) {
		super(context);
	}

	public boolean insert(CharacteristicItem characteristicItem) {
		if (characteristicItem==null ||
			characteristicItem.getmCharacteristic()==null ||
			characteristicItem.getmCharacteristicItemId()==Model.ID_UNDEFINED) {
			return false;
		}
		boolean insertRs = super.insert(characteristicItem);
		if (insertRs) {
			CharacteristicItem characteristicItemInDB = this.query(
					characteristicItem.getmCharacteristic(),
					characteristicItem.getmCharacteristicItemName());
			characteristicItem.setmCharacteristicItemId(characteristicItemInDB.getmCharacteristicItemId());
			return true;
		}
		return false;
	}

	public boolean delete(Characteristic characteristic) {
		if (characteristic==null ||
			characteristic.getmCharacteristicId()==Model.ID_UNDEFINED) {
			return false;
		}
		int rowDeleted = this.delete(
				String.format("%s=?", DSCharacteristicItem.COL_CHARACTERISTIC_ID),
				new String[] {
					String.valueOf(characteristic.getmCharacteristicId())
					});
		return rowDeleted>0 ? true : false;
	}
	public boolean delete(CharacteristicItem characteristicItem) {
		if (characteristicItem==null ||
			characteristicItem.getmCharacteristicItemId()==Model.ID_UNDEFINED ||
			characteristicItem.getmCharacteristic()==null ||
			characteristicItem.getmCharacteristicItemId()==Model.ID_UNDEFINED) {
			return false;
		}
		int rowDeleted = this.delete(
				String.format(
						"%s=? and %s=?",
						DSCharacteristicItem.COL_CHARACTERISTIC_ID,
						DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_ID
						),
				new String[] {
					String.valueOf(characteristicItem.getmCharacteristic().getmCharacteristicId()),
					String.valueOf(characteristicItem.getmCharacteristicItemId())
				});
		return rowDeleted>0 ? true : false;
	}

	public boolean update(CharacteristicItem characteristicItem) {
		if (characteristicItem==null ||
			characteristicItem.getmCharacteristicItemId()==Model.ID_UNDEFINED ||
			characteristicItem.getmCharacteristic()==null ||
			characteristicItem.getmCharacteristicItemId()==Model.ID_UNDEFINED) {
			return false;
		}
		int rowAffected = this.update(
				characteristicItem,
				String.format(
						"%s=? and %s=?",
						DSCharacteristicItem.COL_CHARACTERISTIC_ID,
						DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_ID
						),
				new String[] {
					String.valueOf(characteristicItem.getmCharacteristic().getmCharacteristicId()),
					String.valueOf(characteristicItem.getmCharacteristicItemId())
				});
		return rowAffected>0 ? true : false;
	}

	public CharacteristicItem query(int characteristicItemId) {
		CharacteristicItem characteristicItem = null;
		Cursor cursor = this.query(
				DSCharacteristicItem.COLUMNS,
				String.format(
						"%s=?",
						DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_ID),
				new String[] {
					String.valueOf(characteristicItemId)
				},
				"1");
		characteristicItem = this.parseCursor(cursor, null);
		return characteristicItem;
	}

	public CharacteristicItem query(Characteristic characteristic, String characteristicItemName) {
		if (characteristicItemName==null ||
			characteristic==null ||
			characteristic.getmCharacteristicId()==Model.ID_UNDEFINED) {
			return null;
		}
		CharacteristicItem characteristicItem = null;
		Cursor cursor = this.query(
				DSCharacteristicItem.COLUMNS,
				String.format(
						"%s=? and %s=?",
						DSCharacteristicItem.COL_CHARACTERISTIC_ID,
						DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_NAME),
				new String[] {
					String.valueOf(characteristic.getmCharacteristicId()),
					characteristicItemName
				},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			characteristicItem = this.parseCursor(cursor, characteristic);
		}
		return characteristicItem;
	}

	public ArrayList<CharacteristicItem> queryAll(Characteristic characteristic) {
		if (characteristic==null ||
			characteristic.getmCharacteristicId()==Model.ID_UNDEFINED) {
			return null;
		}

		ArrayList<CharacteristicItem> itemArray = new ArrayList<CharacteristicItem>();
		Cursor cursor = this.query(
				DSCharacteristicItem.COLUMNS,
				String.format(
						"%s=?",
						DSCharacteristicItem.COL_CHARACTERISTIC_ID
						),
				new String[] {
					String.valueOf(characteristic.getmCharacteristicId())
				},
				null);
		while (cursor!=null && cursor.moveToNext()) {
			CharacteristicItem item = this.parseCursor(cursor, characteristic);
			itemArray.add(item);
		}
		return itemArray;
	}

	private CharacteristicItem parseCursor(Cursor cursor, Characteristic characteristic) {
		CharacteristicItem characteristicItem = null;
		if (cursor!=null) {
			int id = cursor.getInt(cursor.getColumnIndex(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_ID));
			String name = cursor.getString(cursor.getColumnIndex(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_NAME));
			if (characteristic==null) {
				int characteristicId = cursor.getInt(cursor.getColumnIndex(DSCharacteristicItem.COL_CHARACTERISTIC_ID));
				characteristic = this.getDbCharacteristic().query(characteristicId);
			}
			CharacteristicItem item = new CharacteristicItem(name, characteristic);
			item.setmCharacteristicItemId(id);
		}
		return characteristicItem;
	}
}
