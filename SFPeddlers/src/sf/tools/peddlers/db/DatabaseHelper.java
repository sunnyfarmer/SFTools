package sf.tools.peddlers.db;

import sf.tools.peddlers.utils.SFGlobal;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context, String name) {
		super(context, name, null, SFGlobal.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.updateDB(db, 0, SFGlobal.DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.updateDB(db, oldVersion, newVersion);
	}

	private void updateDB(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			
		}
	}
}
