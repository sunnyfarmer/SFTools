package sf.tools.peddlers.db;

import sf.log.SFLog;
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
}
