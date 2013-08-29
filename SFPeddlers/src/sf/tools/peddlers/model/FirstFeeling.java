package sf.tools.peddlers.model;

import java.io.Serializable;

import sf.tools.peddlers.db.DataStructure.DSFirstFeeling;

import android.content.ContentValues;

public class FirstFeeling implements Serializable,Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6370100111874428210L;

	public static final String TAG = "FirstFeeling";

	private SettingGroup mSettingGroup = null;
	private String mFirstFeelingName = null;
	private int mFirstFeelingId = ID_UNDEFINED;

	public FirstFeeling(String firstFeeling, SettingGroup settingGroup) {
		this.setmFirstFeelingName(firstFeeling);
		this.setmSettingGroup(settingGroup);
	}

	public SettingGroup getmSettingGroup() {
		return mSettingGroup;
	}

	public void setmSettingGroup(SettingGroup mSettingGroup) {
		this.mSettingGroup = mSettingGroup;
	}

	public int getmFirstFeelingId() {
		return mFirstFeelingId;
	}

	public void setmFirstFeelingId(int mFirstFeelingId) {
		this.mFirstFeelingId = mFirstFeelingId;
	}

	public String getmFirstFeelingName() {
		return mFirstFeelingName;
	}

	public void setmFirstFeelingName(String mFirstFeeling) {
		this.mFirstFeelingName = mFirstFeeling;
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(DSFirstFeeling.COL_FIRST_FEELING_ID, this.mFirstFeelingId);
		values.put(DSFirstFeeling.COL_FIRST_FEELING_NAME, this.mFirstFeelingName);
		values.put(DSFirstFeeling.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		return values;
	}
}
