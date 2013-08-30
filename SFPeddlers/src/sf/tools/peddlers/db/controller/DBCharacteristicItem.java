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
		return super.insert(characteristicItem);
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
			int id = cursor.getInt(cursor.getColumnIndex(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_ID));
			characteristicItem = new CharacteristicItem(characteristicItemName, characteristic);
			characteristicItem.setmCharacteristicItemId(id);
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
			int id = cursor.getInt(cursor.getColumnIndex(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_ID));
			String name = cursor.getString(cursor.getColumnIndex(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_NAME));
			CharacteristicItem item = new CharacteristicItem(name, characteristic);
			item.setmCharacteristicItemId(id);
			itemArray.add(item);
		}
		return itemArray;
	}
}
