package sf.tools.peddlers.db.controller;
import java.util.ArrayList;

import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSSettingGroup;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;
import sf.utils.SFUtils;
import android.content.Context;
import android.database.Cursor;

public class DBSettingGroup extends DBController {
	public static final String TAG = "DBSettingGroup";

	public DBSettingGroup(Context context) {
		super(context);
		this.mTableName = DSSettingGroup.TB_NAME;
	}

	public int upsert(SettingGroup settingGroup) {
		//先检查是否存在的settinggroup
		SettingGroup settingGroupInDB = this.query(settingGroup.getmSettingGroupName());
		if (settingGroupInDB!=null) {
			return SFGlobal.DB_MSG_SAME_COLUMN;
		}
		if (settingGroup.getmSettingGroupId()!=null) {
			this.update(settingGroup);
		} else {
			String id = this.insert(settingGroup.getmSettingGroupName());
			settingGroup.setmSettingGroupId(id);
		}
		return SFGlobal.DB_MSG_OK;
	}

	/**
	 * 插入单个
	 * @param settingGroupName
	 * @return
	 */
	public String insert(String settingGroupName) {
		if (settingGroupName==null || settingGroupName.trim().equals("")) {
			return null;
		}
		SettingGroup settingGroup = new SettingGroup(settingGroupName);
		settingGroup.setmSettingGroupId(SFUtils.produceUniqueId());
		boolean insertRS = this.insert(settingGroup);
		if (insertRS) {
			return settingGroup.getmSettingGroupId();
		} else {
			return null;
		}
	}

	/**
	 * 删除单个
	 * @param settingGroupName
	 * @return
	 */
	public boolean delete(String settingGroupName) {
		SettingGroup settingGroup = this.query(settingGroupName);
		if (settingGroup!=null) {
			this.getDbFirstFeeling().deleteAll(settingGroup);
			this.getDbCharacteristic().deleteAll(settingGroup);
			this.getDbCargoType().deleteAll(settingGroup);
		}
		
		int rowDeleted = this.delete(
				String.format("%s=?", DSSettingGroup.COL_SETTING_GROUP_NAME),
				new String[] {settingGroupName});
		return rowDeleted>0 ? true : false;
	}

	/**
	 * 更新单个
	 * @param settingGroup
	 * @return
	 */
	public boolean update(SettingGroup settingGroup) {
		if (settingGroup.getmSettingGroupId()==null) {
			return false;
		}
		int rowAffected = this.update(
				settingGroup, 
				String.format("%s=?", DSSettingGroup.COL_SETTING_GROUP_ID),
				new String[] {settingGroup.getmSettingGroupId()});
		return rowAffected>0 ? true : false;
	}

	public SettingGroup queryById(String settingGroupId) {
		SettingGroup settingGroup = null;
		Cursor cursor = this.query(
				DSSettingGroup.COLUMNS,
				String.format("%s=?", DSSettingGroup.COL_SETTING_GROUP_ID),
				new String[] {settingGroupId},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			settingGroup = this.parseCursor(cursor);
		}
		return settingGroup;
	}

	/**
	 * 查询单个
	 * @param settingGroupName
	 * @return
	 */
	public SettingGroup query(String settingGroupName) {
		SettingGroup settingGroup = null;
		Cursor cursor = this.query(
				DSSettingGroup.COLUMNS,
				String.format("%s=?", DSSettingGroup.COL_SETTING_GROUP_NAME),
				new String[] {settingGroupName},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			settingGroup = this.parseCursor(cursor);
		}
		return settingGroup;
	}

	/**
	 * 查询所有
	 * @return
	 */
	public ArrayList<SettingGroup> queryAll() {
		ArrayList<SettingGroup> settingGroupArray = new ArrayList<SettingGroup>();
		Cursor cursor = this.query(
				DSSettingGroup.COLUMNS,
				null,
				null,
				null);
		while (cursor!=null && cursor.moveToNext()) {
			SettingGroup settingGroup = this.parseCursor(cursor);

			settingGroupArray.add(settingGroup);
		}
		return settingGroupArray;
	}

	private SettingGroup parseCursor(Cursor cursor) {
		SettingGroup settingGroup = null;

		String id = cursor.getString(cursor.getColumnIndex(DSSettingGroup.COL_SETTING_GROUP_ID));
		String name = cursor.getString(cursor.getColumnIndex(DSSettingGroup.COL_SETTING_GROUP_NAME));
		settingGroup = new SettingGroup(name);
		settingGroup.setmSettingGroupId(id);

		return settingGroup;
	}
}
