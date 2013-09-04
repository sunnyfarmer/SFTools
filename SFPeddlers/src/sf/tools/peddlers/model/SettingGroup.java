package sf.tools.peddlers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import sf.tools.peddlers.db.DataStructure.DSSettingGroup;
import android.content.ContentValues;

public class SettingGroup implements Serializable,Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247752270107941221L;

	public static final String TAG = "SettingGroup";

	private String mSettingGroupId = null;
	private String mSettingGroupName = null;

	//init null
	private ArrayList<FirstFeeling> mFirstFeelingArray = null;
	private ArrayList<Characteristic> mCharacteristicArray = null;
	private ArrayList<CargoType> mCargoTypeArray = null;
	private HashMap<CargoType, ArrayList<Cargo>> mCargoArray = null;

	public SettingGroup(String settingGroupName) {
		this.setmSettingGroupName(settingGroupName);
	}

	public String getmSettingGroupId() {
		return mSettingGroupId;
	}
	public void setmSettingGroupId(String mSettingGroupId) {
		this.mSettingGroupId = mSettingGroupId;
	}
	public String getmSettingGroupName() {
		return mSettingGroupName;
	}
	public void setmSettingGroupName(String mSettingGroupName) {
		this.mSettingGroupName = mSettingGroupName;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mSettingGroupId != null) {
			values.put(DSSettingGroup.COL_SETTING_GROUP_ID, this.mSettingGroupId);
		}
		values.put(DSSettingGroup.COL_SETTING_GROUP_NAME, this.mSettingGroupName);
		return values;
	}

	public ArrayList<FirstFeeling> getmFirstFeelingArray() {
		return mFirstFeelingArray;
	}

	public void setmFirstFeelingArray(ArrayList<FirstFeeling> mFirstFeelingArray) {
		this.mFirstFeelingArray = mFirstFeelingArray;
	}

	public ArrayList<Characteristic> getmCharacteristicArray() {
		return mCharacteristicArray;
	}

	public void setmCharacteristicArray(
			ArrayList<Characteristic> mCharacteristicArray) {
		this.mCharacteristicArray = mCharacteristicArray;
	}

	public ArrayList<CargoType> getmCargoTypeArray() {
		return mCargoTypeArray;
	}

	public void setmCargoTypeArray(ArrayList<CargoType> mCargoTypeArray) {
		this.mCargoTypeArray = mCargoTypeArray;
	}

	public HashMap<CargoType, ArrayList<Cargo>> getmCargoArray() {
		return mCargoArray;
	}

	public void setmCargoArray(HashMap<CargoType, ArrayList<Cargo>> mCargoArray) {
		this.mCargoArray = mCargoArray;
	}
}
