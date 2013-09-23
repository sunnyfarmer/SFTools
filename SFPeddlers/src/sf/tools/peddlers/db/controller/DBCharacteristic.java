package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DatabaseHelper;
import sf.tools.peddlers.db.DataStructure.DSCharacteristic;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;

public class DBCharacteristic extends DBController {
	public static final String TAG = "DBCharacteristic";

	public DBCharacteristic(DatabaseHelper databaseHelper, SQLiteDatabase db) {
		super(databaseHelper, db);
		this.mTableName = DSCharacteristic.TB_NAME;
	}
	public DBCharacteristic(Context context) {
		super(context);
		this.mTableName = DSCharacteristic.TB_NAME;
	}

	public int upsert(Characteristic characteristic) {
		if (characteristic==null) {
			return SFGlobal.DB_MSG_UNKNOWN_OBJECT;
		}
		if (characteristic.getmSettingGroup()==null || characteristic.getmSettingGroup().getmSettingGroupId()==null) {
			return SFGlobal.DB_MSG_UNKNOWN_SETTING_GROUP;
		}
		Characteristic characteristicInDB = this.query(characteristic.getmSettingGroup(), characteristic.getmCharacteristicName());
		if (characteristicInDB!=null) {
			return SFGlobal.DB_MSG_SAME_COLUMN;
		}
		boolean dbRs = false;
		if (characteristic.getmCharacteristicId()!=Model.ID_UNDEFINED) {
			dbRs = this.update(characteristic);
		} else {
			dbRs = this.insert(characteristic);
		}
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}

	public boolean insert(Characteristic characteristic) {
		if (characteristic==null ||
			characteristic.getmSettingGroup()==null ||
			characteristic.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}
		//先插入特征
		boolean insertRs = super.insert(characteristic);
		if (insertRs) {
			Characteristic characteristicInDB = this.query(characteristic.getmSettingGroup(), characteristic.getmCharacteristicName());
			characteristic.setmCharacteristicId(characteristicInDB.getmCharacteristicId());

			//后插入选项
			for (CharacteristicItem characteristicItem : characteristic.getmCharacteristicItemArray()) {
				characteristicItem.setmCharacteristic(characteristic);
				this.getDbCharacteristicItem().insert(characteristicItem);
			}
			return true;
		}
		return false;
	}

	public boolean deleteAll(SettingGroup settingGroup) {
		if (settingGroup==null || settingGroup.getmSettingGroupId()==null) {
			return false;
		}
		ArrayList<Characteristic> characteristicArray = this.queryAll(settingGroup);
		for (Characteristic characteristic : characteristicArray) {
			this.delete(characteristic);
		}
		return true;
	}
	public boolean delete(Characteristic characteristic) {
		if (characteristic==null ||
			characteristic.getmCharacteristicId()==Model.ID_UNDEFINED ||
			characteristic.getmSettingGroup()==null ||
			characteristic.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}

		//先删除选项
		this.getDbCharacteristicItem().delete(characteristic);

		//再删除特征
		int rowDeleted = this.delete(
				String.format(
						"%s=? and %s=?",
						DSCharacteristic.COL_CHARACTERISTIC_ID,
						DSCharacteristic.COL_SETTING_GROUP_ID
						),
				new String[] {
					String.valueOf(characteristic.getmCharacteristicId()),
					characteristic.getmSettingGroup().getmSettingGroupId()
				}
			);
		return rowDeleted>0 ? true : false;
	}

	public boolean update(Characteristic characteristic) {
		if (characteristic==null ||
			characteristic.getmCharacteristicId()==Model.ID_UNDEFINED ||
			characteristic.getmSettingGroup()==null ||
			characteristic.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}

		int rowAffected = this.update(
				characteristic,
				String.format(
						"%s=? and %s=?",
						DSCharacteristic.COL_CHARACTERISTIC_ID,
						DSCharacteristic.COL_SETTING_GROUP_ID
						),
				new String[]{
					String.valueOf(characteristic.getmCharacteristicId()),
					characteristic.getmSettingGroup().getmSettingGroupId()
				}
			);

		return rowAffected>0 ? true : false;
	}

	public Characteristic query(int characteristicid) {
		Characteristic characteristic = null;
		Cursor cursor = this.query(
				DSCharacteristic.COLUMNS,
				String.format("%s=?", DSCharacteristic.COL_CHARACTERISTIC_ID),
				new String[] {String.valueOf(characteristicid)},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			characteristic = this.parseCursor(cursor, null);
		}
		return characteristic;
	}

	public Characteristic query(SettingGroup settingGroup, String characteristicName) {
		if (settingGroup==null ||
			settingGroup.getmSettingGroupId()==null ||
			characteristicName==null) {
			return null;
		}
		Characteristic characteristic = null;
		Cursor cursor = this.query(
				DSCharacteristic.COLUMNS,
				String.format(
						"%s=? and %s=?",
						DSCharacteristic.COL_SETTING_GROUP_ID,
						DSCharacteristic.COL_CHARACTERISTIC_NAME),
				new String[] {
					settingGroup.getmSettingGroupId(),
					characteristicName
				},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			characteristic = this.parseCursor(cursor, settingGroup);
		}
		return characteristic;
	}

	public ArrayList<Characteristic> queryAll(SettingGroup settingGroup) {
		if (settingGroup==null || settingGroup.getmSettingGroupId()==null) {
			return null;
		}
		ArrayList<Characteristic> characteristicArray = new ArrayList<Characteristic>();

		Cursor cursor = this.query(
				DSCharacteristic.COLUMNS,
				String.format("%s=?", DSCharacteristic.COL_SETTING_GROUP_ID),
				new String[] {settingGroup.getmSettingGroupId()},
				null);

		while (cursor!=null && cursor.moveToNext()) {
			Characteristic characteristic = this.parseCursor(cursor, settingGroup);

			characteristicArray.add(characteristic);
		}

		return characteristicArray;
	}

	private Characteristic parseCursor(Cursor cursor, SettingGroup settingGroup) {
		Characteristic characteristic = null;
		if (cursor!=null) {
			int id = cursor.getInt(cursor.getColumnIndex(DSCharacteristic.COL_CHARACTERISTIC_ID));
			String name = cursor.getString(cursor.getColumnIndex(DSCharacteristic.COL_CHARACTERISTIC_NAME));
			
			if (settingGroup==null) {
				String settingGroupId = cursor.getString(cursor.getColumnIndex(DSCharacteristic.COL_SETTING_GROUP_ID));
				settingGroup = this.getDbSettingGroup().queryById(settingGroupId);
			}

			characteristic = new Characteristic(name, settingGroup);
			characteristic.setmCharacteristicId(id);

			//查询选项
			ArrayList<CharacteristicItem> itemArray = this.getDbCharacteristicItem().queryAll(characteristic);
			characteristic.setmCharacteristicItemArray(itemArray);

		}
		return characteristic;
	}
}
