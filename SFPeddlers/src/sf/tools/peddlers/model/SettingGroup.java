package sf.tools.peddlers.model;

import java.io.Serializable;

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
		values.put(DSSettingGroup.COL_SETTING_GROUP_ID, this.mSettingGroupId);
		values.put(DSSettingGroup.COL_SETTING_GROUP_NAME, this.mSettingGroupName);
		return values;
	}
}
