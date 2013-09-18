package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSFirstFeeling;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;

public class DBFirstFeeling extends DBController {
	public static final String TAG = "DBFirstFeeling";

	public DBFirstFeeling(Context context) {
		super(context);
		this.mTableName = DSFirstFeeling.TB_NAME;
	}

	public int upsert(FirstFeeling firstFeeling) {
		if (firstFeeling==null) {
			return SFGlobal.DB_MSG_UNKNOWN_OBJECT;
		}
		if (firstFeeling.getmSettingGroup()==null || firstFeeling.getmSettingGroup().getmSettingGroupId()==null) {
			return SFGlobal.DB_MSG_UNKNOWN_SETTING_GROUP;
		}
		FirstFeeling firstFeelingInDB = this.query(firstFeeling.getmSettingGroup(), firstFeeling.getmFirstFeelingName());
		if (firstFeelingInDB!=null) {
			return SFGlobal.DB_MSG_SAME_COLUMN;
		}
		boolean dbRs = false;
		if (firstFeeling.getmFirstFeelingId() != Model.ID_UNDEFINED) {
			dbRs = this.update(firstFeeling);
		} else {
			dbRs = this.insert(firstFeeling);
		}
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}

	public boolean insert(FirstFeeling firstFeeling) {
		if (firstFeeling==null ||
			firstFeeling.getmSettingGroup()==null ||
			firstFeeling.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}
		boolean insertRs = super.insert(firstFeeling);
		if (insertRs) {
			FirstFeeling firstFeelingInDB = this.query(firstFeeling.getmSettingGroup(), firstFeeling.getmFirstFeelingName());
			if (firstFeelingInDB!=null) {
				firstFeeling.setmFirstFeelingId(firstFeelingInDB.getmFirstFeelingId());
				return true;
			}
		}
		return false;
	}

	public boolean deleteAll(SettingGroup settingGroup) {
		if (settingGroup==null ||
				settingGroup.getmSettingGroupId()==null) {
			return false;
		}
		this.getDbShoppingList().delete(settingGroup);
		int rowDeleted = this.delete(
				String.format("%s=?", DSFirstFeeling.COL_SETTING_GROUP_ID),
				new String[] {
					settingGroup.getmSettingGroupId()
				});
		return rowDeleted>0 ? true : false;
	}
	public boolean delete(FirstFeeling firstFeeling) {
		if (firstFeeling==null ||
				firstFeeling.getmFirstFeelingId()==Model.ID_UNDEFINED ||
				firstFeeling.getmSettingGroup()==null ||
				firstFeeling.getmSettingGroup().getmSettingGroupId()==null) {
				return false;
		}
		this.getDbShoppingList().delete(firstFeeling);
		int rowDeleted = this.delete(
				String.format(
						"%s=? and %s=?",
						DSFirstFeeling.COL_FIRST_FEELING_ID,
						DSFirstFeeling.COL_SETTING_GROUP_ID
						),
				new String[] {
					String.valueOf(firstFeeling.getmFirstFeelingId()),
					firstFeeling.getmSettingGroup().getmSettingGroupId()
					}
				);
		return rowDeleted>0 ? true : false;
	}

	public boolean update(FirstFeeling firstFeeling) {
		if (firstFeeling==null ||
				firstFeeling.getmFirstFeelingId()==Model.ID_UNDEFINED ||
				firstFeeling.getmSettingGroup()==null ||
				firstFeeling.getmSettingGroup().getmSettingGroupId()==null) {
				return false;
		}
		int rowAffected = this.update(
				firstFeeling,
				String.format(
						"%s=? and %s=?",
						DSFirstFeeling.COL_FIRST_FEELING_ID,
						DSFirstFeeling.COL_SETTING_GROUP_ID
						),
				new String[] {
						String.valueOf(firstFeeling.getmFirstFeelingId()),
						firstFeeling.getmSettingGroup().getmSettingGroupId()
						}
				);
		return rowAffected>0 ? true : false;
	}

	public FirstFeeling query(int firstFeelingId) {
		FirstFeeling firstFeeling = null;
		Cursor cursor = this.query(
				DSFirstFeeling.COLUMNS,
				String.format("%s=?", DSFirstFeeling.COL_FIRST_FEELING_ID),
				new String[] {String.valueOf(firstFeelingId)},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			firstFeeling = this.parseCursor(cursor, null);
		}
		return firstFeeling;
	}

	public FirstFeeling query(SettingGroup settingGroup, String firstFeelingName) {
		if (firstFeelingName==null ||
			settingGroup==null ||
			settingGroup.getmSettingGroupId()==null) {
			return null;
		}
		FirstFeeling firstFeeling = null;
		Cursor cursor = this.query(
				DSFirstFeeling.COLUMNS,
				String.format(
						"%s=? and %s=?",
						DSFirstFeeling.COL_SETTING_GROUP_ID,
						DSFirstFeeling.COL_FIRST_FEELING_NAME),
				new String[] {
					settingGroup.getmSettingGroupId(),
					firstFeelingName
				},
				"1"
				);
		if (cursor!=null && cursor.moveToNext()) {
			firstFeeling = this.parseCursor(cursor, settingGroup);
		}
		return firstFeeling;
	}

	public ArrayList<FirstFeeling> queryAll(SettingGroup settingGroup) {
		if (settingGroup==null ||
			settingGroup.getmSettingGroupId()==null) {
			return null;
		}
		ArrayList<FirstFeeling> firstFeelingArray = new ArrayList<FirstFeeling>();
		Cursor cursor = this.query(
				DSFirstFeeling.COLUMNS,
				String.format("%s=?", DSFirstFeeling.COL_SETTING_GROUP_ID),
				new String[] {settingGroup.getmSettingGroupId()},
				null
				);
		while (cursor!=null && cursor.moveToNext()) {
			FirstFeeling firstFeeling = this.parseCursor(cursor, settingGroup);

			firstFeelingArray.add(firstFeeling);
		}
		return firstFeelingArray;
	}

	private FirstFeeling parseCursor(Cursor cursor, SettingGroup settingGroup) {
		FirstFeeling firstFeeling = null;
		if (cursor!=null) {
			int id = cursor.getInt(cursor.getColumnIndex(DSFirstFeeling.COL_FIRST_FEELING_ID));
			String name = cursor.getString(cursor.getColumnIndex(DSFirstFeeling.COL_FIRST_FEELING_NAME));

			if (settingGroup==null) {
				String settingGroupId = cursor.getString(cursor.getColumnIndex(DSFirstFeeling.COL_SETTING_GROUP_ID));
				settingGroup = this.getDbSettingGroup().queryById(settingGroupId);
			}

			firstFeeling = new FirstFeeling(name, settingGroup);
			firstFeeling.setmFirstFeelingId(id);
		}
		return firstFeeling;
	}
}
