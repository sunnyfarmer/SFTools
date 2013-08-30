package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSCharacteristic;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.model.SettingGroup;

public class DBCharacteristic extends DBController {
	public static final String TAG = "DBCharacteristic";

	private DBCharacteristicItem mDbCharacteristicItem = null;

	public DBCharacteristic(Context context) {
		super(context);
		this.mTableName = DSCharacteristic.TB_NAME;
		this.mDbCharacteristicItem = new DBCharacteristicItem(mContext);
	}

	public boolean insert(Characteristic characteristic) {
		if (characteristic==null ||
			characteristic.getmSettingGroup()==null ||
			characteristic.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}
		return super.insert(characteristic);
	}

	public boolean delete(Characteristic characteristic) {
		if (characteristic==null ||
			characteristic.getmCharacteristicId()==Model.ID_UNDEFINED ||
			characteristic.getmSettingGroup()==null ||
			characteristic.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}

		//先删除选项
		this.mDbCharacteristicItem.delete(characteristic);

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
			int id = cursor.getInt(cursor.getColumnIndex(DSCharacteristic.COL_CHARACTERISTIC_ID));
			characteristic = new Characteristic(characteristicName, settingGroup);
			characteristic.setmCharacteristicId(id);

			//查询选项
			ArrayList<CharacteristicItem> itemArray = this.mDbCharacteristicItem.queryAll(characteristic);
			characteristic.setmCharacteristicItemArray(itemArray);
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
			int id = cursor.getInt(cursor.getColumnIndex(DSCharacteristic.COL_CHARACTERISTIC_ID));
			String name = cursor.getString(cursor.getColumnIndex(DSCharacteristic.COL_CHARACTERISTIC_NAME));
			Characteristic characteristic = new Characteristic(name, settingGroup);
			characteristic.setmCharacteristicId(id);

			//查询选项
			ArrayList<CharacteristicItem> itemArray = this.mDbCharacteristicItem.queryAll(characteristic);
			characteristic.setmCharacteristicItemArray(itemArray);

			characteristicArray.add(characteristic);
		}

		return characteristicArray;
	}
}
