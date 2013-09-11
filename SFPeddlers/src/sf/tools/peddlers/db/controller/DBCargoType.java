package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSCargoType;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.model.SettingGroup;

public class DBCargoType extends DBController {
	public static final String TAG = "DBCargoType";

	public DBCargoType(Context context) {
		super(context);
		this.mTableName = DSCargoType.TB_NAME;
	}

	public boolean insert(CargoType cargoType) {
		if (cargoType==null ||
			cargoType.getmSettingGroup()==null ||
			cargoType.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}
		boolean insertRs = super.insert(cargoType);
		if (insertRs) {
			CargoType cargoTypeInDB = this.query(cargoType.getmSettingGroup(), cargoType.getmCargoTypeName());
			cargoType.setmCargoTypeId(cargoTypeInDB.getmCargoTypeId());
			return true;
		}
		return false;
	}

	public boolean delete(CargoType cargoType) {
		if (cargoType==null ||
			cargoType.getmCargoTypeId()==Model.ID_UNDEFINED ||
			cargoType.getmSettingGroup()==null ||
			cargoType.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}
		int rowDeleted = this.delete(
				String.format(
						"%s=? and %s=?",
						DSCargoType.COL_CARGO_TYPE_ID,
						DSCargoType.COL_SETTING_GROUP_ID
						),
				new String[] {
					String.valueOf(cargoType.getmCargoTypeId()),
					cargoType.getmSettingGroup().getmSettingGroupId()
				});
		return rowDeleted>0 ? true : false;
	}

	public boolean update(CargoType cargoType) {
		if (cargoType==null ||
			cargoType.getmCargoTypeId()==Model.ID_UNDEFINED ||
			cargoType.getmSettingGroup()==null ||
			cargoType.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}
		int rowAffected = this.update(
				cargoType,
				String.format(
						"%s=? and %s=?",
						DSCargoType.COL_CARGO_TYPE_ID,
						DSCargoType.COL_SETTING_GROUP_ID
						),
				new String[] {
					String.valueOf(cargoType.getmCargoTypeId()),
					cargoType.getmSettingGroup().getmSettingGroupId()
				});
		return rowAffected>0 ? true : false;
	}

	public CargoType query(int cargoTypeId) {
		CargoType cargoType = null;
		Cursor cursor = this.query(
				DSCargoType.COLUMNS,
				String.format("%s=?", DSCargoType.COL_CARGO_TYPE_ID),
				new String[] {String.valueOf(cargoTypeId)},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			cargoType = this.parseCursor(cursor, null);
		}
		return cargoType;
	}

	public CargoType query(SettingGroup settingGroup, String cargoTypeName) {
		if (settingGroup==null ||
			settingGroup.getmSettingGroupId()==null ||
			cargoTypeName==null) {
			return null;
		}
		CargoType cargoType = null;
		Cursor cursor = this.query(
				DSCargoType.COLUMNS,
				String.format(
						"%s=? and %s=?",
						DSCargoType.COL_SETTING_GROUP_ID,
						DSCargoType.COL_CARGO_TYPE_NAME
						),
				new String[] {
					settingGroup.getmSettingGroupId(),
					cargoTypeName
				},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			cargoType = this.parseCursor(cursor, settingGroup);
		}
		return cargoType;
	}

	public ArrayList<CargoType> queryAll(SettingGroup settingGroup) {
		if (settingGroup==null ||
			settingGroup.getmSettingGroupId()==null) {
			return null;
		}
		ArrayList<CargoType> cargoTypeArray = new ArrayList<CargoType>();
		Cursor cursor = this.query(
				DSCargoType.COLUMNS,
				String.format(
						"%s=?",
						DSCargoType.COL_SETTING_GROUP_ID
						),
				new String[] {
					settingGroup.getmSettingGroupId()
				},
				null);
		while (cursor!=null && cursor.moveToNext()) {
			CargoType cargoType = this.parseCursor(cursor, settingGroup);

			cargoTypeArray.add(cargoType);
		}
		return cargoTypeArray;
	}

	private CargoType parseCursor(Cursor cursor, SettingGroup settingGroup) {
		CargoType cargoType = null;
		if (cursor!=null) {
			int id = cursor.getInt(cursor.getColumnIndex(DSCargoType.COL_CARGO_TYPE_ID));
			String name = cursor.getString(cursor.getColumnIndex(DSCargoType.COL_CARGO_TYPE_NAME));
			if (settingGroup==null) {
				String settingGroupId = cursor.getString(cursor.getColumnIndex(DSCargoType.COL_SETTING_GROUP_ID));
				settingGroup = this.getDbSettingGroup().queryById(settingGroupId);
			}
			cargoType = new CargoType(name, settingGroup);
			cargoType.setmCargoTypeId(id);
		}
		return cargoType;
	}
}
