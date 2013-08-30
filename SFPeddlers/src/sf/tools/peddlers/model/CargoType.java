package sf.tools.peddlers.model;

import java.io.Serializable;

import sf.tools.peddlers.db.DataStructure.DSCargoType;

import android.content.ContentValues;

public class CargoType implements Serializable,Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8175652174337896275L;

	public static final String TAG = "CargoType";

	private int mCargoTypeId = ID_UNDEFINED;
	private String mCargoTypeName = null;
	private SettingGroup mSettingGroup = null;

	public CargoType(String cargoTypeName, SettingGroup settingGroup) {
		this.setmCargoTypeName(cargoTypeName);
		this.setmSettingGroup(settingGroup);
	}

	public int getmCargoTypeId() {
		return mCargoTypeId;
	}

	public void setmCargoTypeId(int mCargoTypeId) {
		this.mCargoTypeId = mCargoTypeId;
	}

	public String getmCargoTypeName() {
		return mCargoTypeName;
	}

	public void setmCargoTypeName(String mCargoTypeName) {
		this.mCargoTypeName = mCargoTypeName;
	}

	public SettingGroup getmSettingGroup() {
		return mSettingGroup;
	}

	public void setmSettingGroup(SettingGroup mSettingGroup) {
		this.mSettingGroup = mSettingGroup;
	}

	@Override
	public boolean equals(Object o) {
		//名字相同视为相同
		if (o instanceof CargoType) {
			CargoType type = (CargoType) o;
			if (type.getmCargoTypeName()!=null && this.getmCargoTypeName()!=null) {
				return type.getmCargoTypeName().equals(this.getmCargoTypeName());
			}
		}
		return false;
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(DSCargoType.COL_CARGO_TYPE_ID, this.mCargoTypeId);
		values.put(DSCargoType.COL_CARGO_TYPE_NAME, this.mCargoTypeName);
		values.put(DSCargoType.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		return values;
	}
}
