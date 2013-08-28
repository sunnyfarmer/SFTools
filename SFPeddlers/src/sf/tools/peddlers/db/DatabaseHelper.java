package sf.tools.peddlers.db;

import sf.tools.peddlers.db.DataStructure.DSCargo;
import sf.tools.peddlers.db.DataStructure.DSCargoType;
import sf.tools.peddlers.db.DataStructure.DSCharacteristic;
import sf.tools.peddlers.db.DataStructure.DSCharacteristicItem;
import sf.tools.peddlers.db.DataStructure.DSCharacteristicItemInList;
import sf.tools.peddlers.db.DataStructure.DSFirstFeeling;
import sf.tools.peddlers.db.DataStructure.DSSettingGroup;
import sf.tools.peddlers.db.DataStructure.DSShoppingList;
import sf.tools.peddlers.utils.SFGlobal;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
			db.execSQL(DSSettingGroup.SQL_DROP);
			db.execSQL(DSFirstFeeling.SQL_DROP);
			db.execSQL(DSCharacteristic.SQL_DROP);
			db.execSQL(DSCharacteristicItem.SQL_DROP);
			db.execSQL(DSShoppingList.SQL_DROP);
			db.execSQL(DSCargoType.SQL_DROP);
			db.execSQL(DSCargo.SQL_DROP);
			db.execSQL(DSCharacteristicItemInList.SQL_DROP);

			db.execSQL(DSSettingGroup.SQL_CREATE);
			db.execSQL(DSFirstFeeling.SQL_CREATE);
			db.execSQL(DSCharacteristic.SQL_CREATE);
			db.execSQL(DSCharacteristicItem.SQL_CREATE);
			db.execSQL(DSShoppingList.SQL_CREATE);
			db.execSQL(DSCargoType.SQL_CREATE);
			db.execSQL(DSCargo.SQL_CREATE);
			db.execSQL(DSCharacteristicItemInList.SQL_CREATE);
		}
	}
}
