package sf.tools.peddlers.db;

import sf.log.SFLog;
import sf.tools.peddlers.db.controller.DBCargo;
import sf.tools.peddlers.db.controller.DBCargoInList;
import sf.tools.peddlers.db.controller.DBCargoType;
import sf.tools.peddlers.db.controller.DBCharacteristic;
import sf.tools.peddlers.db.controller.DBCharacteristicItem;
import sf.tools.peddlers.db.controller.DBCharacteristicItemInList;
import sf.tools.peddlers.db.controller.DBFirstFeeling;
import sf.tools.peddlers.db.controller.DBSettingGroup;
import sf.tools.peddlers.db.controller.DBShoppingList;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.utils.SFGlobal;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DBController {
	public static final String TAG = "DBController";

	protected SQLiteDatabase mDatabase = null;
	private DatabaseHelper mDatabaseHelper = null;
	protected Context mContext = null;

	protected String mTableName = null;

	private DBCargo mDbCargo = null;
	private DBCargoInList mDbCargoInList = null;
	private DBCargoType mDbCargoType = null;
	private DBCharacteristic mDbCharacteristic = null;
	private DBCharacteristicItem mDbCharacteristicItem = null;
	private DBFirstFeeling mDbFirstFeeling = null;
	private DBSettingGroup mDbSettingGroup = null;
	private DBCharacteristicItemInList mDbCharacteristicItemInList = null;
	private DBShoppingList mDbShoppingList = null;

	public DBController(Context context) {
		this.mContext = context;
		this.getmDatabase();
	}
	

	public SQLiteDatabase getmDatabase() {
		if (this.mDatabase==null) {
			this.mDatabase = this.getmDatabaseHelper().getWritableDatabase();
		}
		return mDatabase;
	}

	public DatabaseHelper getmDatabaseHelper() {
		if (mDatabaseHelper==null) {
			mDatabaseHelper = new DatabaseHelper(this.mContext, SFGlobal.DATABASE_NAME);
		}
		return mDatabaseHelper;
	}

	/**
	 * 插入新对象
	 * @param model
	 * @return
	 */
	public boolean insert(Model model) {
		if (model==null) {
			return false;
		}
		if (this.mTableName==null) {
			SFLog.e(TAG, String.format("%s : mTableName is not set", this.getClass().getName()));
			return false;
		}
		long rs = this.mDatabase.insert(this.mTableName, null, model.getContentValues());
		return rs!=-1 ? true : false;
	}
	/**
	 * 删除对象
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int delete(String whereClause, String[] whereArgs) {
		if (this.mTableName==null) {
			SFLog.e(TAG, String.format("%s : mTableName is not set", this.getClass().getName()));
			return -1;
		}
		int rowDeleted = this.mDatabase.delete(this.mTableName, whereClause, whereArgs);

		return rowDeleted;
	}
	/**
	 * 更新对象
	 * @param model
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int update(Model model, String whereClause, String[] whereArgs) {
		if (model==null) {
			return -1;
		}
		if (this.mTableName==null) {
			SFLog.e(TAG, String.format("%s : mTableName is not set", this.getClass().getName()));
			return -1;
		}
		int rowAffected = this.mDatabase.update(this.mTableName, model.getContentValues(), whereClause, whereArgs);
		return rowAffected;
	}

	/**
	 * 查询对象
	 * @param distinct
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return
	 */
	public Cursor query(boolean distinct, String[] columns,
            String selection, String[] selectionArgs, String groupBy,
            String having, String orderBy, String limit) {
		if (this.mTableName==null) {
			SFLog.e(TAG, String.format("%s : mTableName is not set", this.getClass().getName()));
			return null;
		}
		Cursor cursor = this.mDatabase.query(distinct, this.mTableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

		return cursor;
	}
	/**
	 * 查询对象
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param limit
	 * @return
	 */
	public Cursor query(String[] columns, String selection, String[] selectionArgs, String limit) {
		if (this.mTableName==null) {
			SFLog.e(TAG, String.format("%s : mTableName is not set", this.getClass().getName()));
			return null;
		}
		Cursor cursor = this.query(false, columns, selection, selectionArgs, null, null, null, limit);

		return cursor;
	}

	protected DBCargo getDbCargo() {
		if (this.mDbCargo==null) {
			if (this instanceof DBCargo) {
				this.mDbCargo = (DBCargo) this;
			} else {
				this.mDbCargo = new DBCargo(this.mContext);
			}
		}
		return this.mDbCargo;
	}

	protected DBCargoInList getDbCargoInList() {
		if (this.mDbCargoInList==null) {
			if (this instanceof DBCargoInList) {
				this.mDbCargoInList = (DBCargoInList)this;
			} else {
				this.mDbCargoInList = new DBCargoInList(this.mContext);
			}
		}
		return this.mDbCargoInList;
	}

	protected DBCargoType getDbCargoType() {
		if (this.mDbCargoType==null) {
			if (this instanceof DBCargoType) {
				this.mDbCargoType = (DBCargoType) this;
			} else {
				this.mDbCargoType = new DBCargoType(this.mContext);
			}
		}
		return this.mDbCargoType;
	}

	protected DBCharacteristic getDbCharacteristic() {
		if (this.mDbCharacteristic==null) {
			if (this instanceof DBCharacteristic) {
				this.mDbCharacteristic = (DBCharacteristic)this;
			} else {
				this.mDbCharacteristic = new DBCharacteristic(this.mContext);
			}
		}
		return this.mDbCharacteristic;
	}

	protected DBCharacteristicItem getDbCharacteristicItem() {
		if (this.mDbCharacteristicItem==null) {
			if (this instanceof DBCharacteristicItem) {
				this.mDbCharacteristicItem = (DBCharacteristicItem) this;
			} else {
				this.mDbCharacteristicItem = new DBCharacteristicItem(this.mContext);
			}
		}
		return mDbCharacteristicItem;
	}

	protected DBFirstFeeling getDbFirstFeeling() {
		if (this.mDbFirstFeeling==null) {
			if (this instanceof DBFirstFeeling) {
				this.mDbFirstFeeling = (DBFirstFeeling)this;
			} else {
				this.mDbFirstFeeling = new DBFirstFeeling(this.mContext);
			}
		}
		return this.mDbFirstFeeling;
	}

	protected DBSettingGroup getDbSettingGroup() {
		if (this.mDbSettingGroup==null) {
			if (this instanceof DBSettingGroup) {
				this.mDbSettingGroup = (DBSettingGroup)this;
			} else {
				this.mDbSettingGroup = new DBSettingGroup(this.mContext);
			}
		}
		return this.mDbSettingGroup;
	}


	public DBCharacteristicItemInList getDbCharacteristicItemInList() {
		if (this.mDbCharacteristicItemInList==null) {
			if (this instanceof DBCharacteristicItemInList) {
				this.mDbCharacteristicItemInList = (DBCharacteristicItemInList)this;
			} else {
				this.mDbCharacteristicItemInList = new DBCharacteristicItemInList(this.mContext);
			}
		}
		return mDbCharacteristicItemInList;
	}


	public DBShoppingList getDbShoppingList() {
		if (this.mDbShoppingList==null) {
			if (this instanceof DBShoppingList) {
				this.mDbShoppingList = (DBShoppingList)this;
			} else {
				this.mDbShoppingList = new DBShoppingList(this.mContext);
			}
		}
		return mDbShoppingList;
	}
}
