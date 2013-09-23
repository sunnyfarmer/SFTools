package sf.tools.peddlers.db;

import sf.tools.peddlers.db.DataStructure.DSCargo;
import sf.tools.peddlers.db.DataStructure.DSCargoInList;
import sf.tools.peddlers.db.DataStructure.DSCargoType;
import sf.tools.peddlers.db.DataStructure.DSCharacteristic;
import sf.tools.peddlers.db.DataStructure.DSCharacteristicItem;
import sf.tools.peddlers.db.DataStructure.DSCharacteristicItemInList;
import sf.tools.peddlers.db.DataStructure.DSFirstFeeling;
import sf.tools.peddlers.db.DataStructure.DSSettingGroup;
import sf.tools.peddlers.db.DataStructure.DSShoppingList;
import sf.tools.peddlers.db.controller.DBCargo;
import sf.tools.peddlers.db.controller.DBCargoType;
import sf.tools.peddlers.db.controller.DBCharacteristic;
import sf.tools.peddlers.db.controller.DBFirstFeeling;
import sf.tools.peddlers.db.controller.DBSettingGroup;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String TAG = "DatabaseHelper";

	private Context mSuperContext  =null;

	public DatabaseHelper(Context context, String name) {
		super(context, name, null, SFGlobal.DATABASE_VERSION);
		this.mSuperContext = context;
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
			db.execSQL(DSCargoInList.SQL_DROP);
			db.execSQL(DSCharacteristicItemInList.SQL_DROP);

			db.execSQL(DSSettingGroup.SQL_CREATE);
			db.execSQL(DSFirstFeeling.SQL_CREATE);
			db.execSQL(DSCharacteristic.SQL_CREATE);
			db.execSQL(DSCharacteristicItem.SQL_CREATE);
			db.execSQL(DSShoppingList.SQL_CREATE);
			db.execSQL(DSCargoType.SQL_CREATE);
			db.execSQL(DSCargo.SQL_CREATE);
			db.execSQL(DSCargoInList.SQL_CREATE);
			db.execSQL(DSCharacteristicItemInList.SQL_CREATE);

			produceDefaultData(db);
		}
	}

	private void produceDefaultData(SQLiteDatabase db) {
		SettingGroup sg = new SettingGroup("悠哉服饰");
		DBSettingGroup dbSG = new DBSettingGroup(this, db);
		dbSG.upsert(sg);

		FirstFeeling ff1 = new FirstFeeling("小王", sg);
		FirstFeeling ff2 = new FirstFeeling("小李", sg);
		FirstFeeling ff3 = new FirstFeeling("小张", sg);
		FirstFeeling ff4 = new FirstFeeling("小艺", sg);
		FirstFeeling ff5 = new FirstFeeling("小戴", sg);
		FirstFeeling ff6 = new FirstFeeling("小何", sg);
		FirstFeeling ff7 = new FirstFeeling("小皮", sg);
		FirstFeeling ff8 = new FirstFeeling("小仙", sg);
		DBFirstFeeling dbFF = new DBFirstFeeling(this, db);
		dbFF.upsert(ff1);
		dbFF.upsert(ff2);
		dbFF.upsert(ff3);
		dbFF.upsert(ff4);
		dbFF.upsert(ff5);
		dbFF.upsert(ff6);
		dbFF.upsert(ff7);
		dbFF.upsert(ff8);

		Characteristic c1 = new Characteristic("身高", sg);
		CharacteristicItem ci11 = new CharacteristicItem("150以下", c1);
		CharacteristicItem ci12 = new CharacteristicItem("150-160", c1);
		CharacteristicItem ci13 = new CharacteristicItem("161-170", c1);
		CharacteristicItem ci14 = new CharacteristicItem("171-180", c1);
		CharacteristicItem ci15 = new CharacteristicItem("180以上", c1);
		c1.addCharacteristicItem(ci11);
		c1.addCharacteristicItem(ci12);
		c1.addCharacteristicItem(ci13);
		c1.addCharacteristicItem(ci14);
		c1.addCharacteristicItem(ci15);
		Characteristic c2 = new Characteristic("体型", sg);
		CharacteristicItem ci21 = new CharacteristicItem("偏瘦", c2);
		CharacteristicItem ci22 = new CharacteristicItem("中等", c2);
		CharacteristicItem ci23 = new CharacteristicItem("偏胖", c2);
		c2.addCharacteristicItem(ci21);
		c2.addCharacteristicItem(ci22);
		c2.addCharacteristicItem(ci23);
		Characteristic c3 = new Characteristic("年龄段", sg);
		CharacteristicItem ci31 = new CharacteristicItem("18岁以下", c3);
		CharacteristicItem ci32 = new CharacteristicItem("18岁-23岁", c3);
		CharacteristicItem ci33 = new CharacteristicItem("24岁-30岁", c3);
		CharacteristicItem ci34 = new CharacteristicItem("31岁-40岁", c3);
		CharacteristicItem ci35 = new CharacteristicItem("40岁以上", c3);
		c3.addCharacteristicItem(ci31);
		c3.addCharacteristicItem(ci32);
		c3.addCharacteristicItem(ci33);
		c3.addCharacteristicItem(ci34);
		c3.addCharacteristicItem(ci35);
		Characteristic c4 = new Characteristic("鞋码", sg);
		CharacteristicItem ci41 = new CharacteristicItem("35", c4);
		CharacteristicItem ci42 = new CharacteristicItem("36", c4);
		CharacteristicItem ci43 = new CharacteristicItem("37", c4);
		CharacteristicItem ci44 = new CharacteristicItem("38", c4);
		CharacteristicItem ci45 = new CharacteristicItem("39", c4);
		CharacteristicItem ci46 = new CharacteristicItem("40", c4);
		CharacteristicItem ci47 = new CharacteristicItem("41", c4);
		CharacteristicItem ci48 = new CharacteristicItem("42", c4);
		CharacteristicItem ci49 = new CharacteristicItem("43", c4);
		CharacteristicItem ci4a = new CharacteristicItem("44", c4);
		c4.addCharacteristicItem(ci41);
		c4.addCharacteristicItem(ci42);
		c4.addCharacteristicItem(ci43);
		c4.addCharacteristicItem(ci44);
		c4.addCharacteristicItem(ci45);
		c4.addCharacteristicItem(ci46);
		c4.addCharacteristicItem(ci47);
		c4.addCharacteristicItem(ci48);
		c4.addCharacteristicItem(ci49);
		c4.addCharacteristicItem(ci4a);
		DBCharacteristic dbCharacteristic = new DBCharacteristic(this, db);
		dbCharacteristic.upsert(c1);
		dbCharacteristic.upsert(c2);
		dbCharacteristic.upsert(c3);
		dbCharacteristic.upsert(c4);

		CargoType ct1 = new CargoType("鞋", sg);
		CargoType ct2 = new CargoType("包包", sg);
		CargoType ct3 = new CargoType("皮带", sg);
		CargoType ct4 = new CargoType("丝巾", sg);
		CargoType ct5 = new CargoType("披肩", sg);
		CargoType ct6 = new CargoType("体恤", sg);
		CargoType ct7 = new CargoType("短裤", sg);
		DBCargoType dbCT = new DBCargoType(this, db);
		dbCT.upsert(ct1);
		dbCT.upsert(ct2);
		dbCT.upsert(ct3);
		dbCT.upsert(ct4);
		dbCT.upsert(ct5);
		dbCT.upsert(ct6);
		dbCT.upsert(ct7);

		DBCargo dbCargo = new DBCargo(this, db);
		for(int cot = 0; cot < 10; cot++) {
			Cargo cargo1 = new Cargo(ct1.getmCargoTypeName()+cot, ct1);
			Cargo cargo2 = new Cargo(ct2.getmCargoTypeName()+cot, ct2);
			Cargo cargo3 = new Cargo(ct3.getmCargoTypeName()+cot, ct3);
			Cargo cargo4 = new Cargo(ct4.getmCargoTypeName()+cot, ct4);
			Cargo cargo5 = new Cargo(ct5.getmCargoTypeName()+cot, ct5);
			Cargo cargo6 = new Cargo(ct6.getmCargoTypeName()+cot, ct6);
			Cargo cargo7 = new Cargo(ct7.getmCargoTypeName()+cot, ct7);

			dbCargo.upsert(cargo1);
			dbCargo.upsert(cargo2);
			dbCargo.upsert(cargo3);
			dbCargo.upsert(cargo4);
			dbCargo.upsert(cargo5);
			dbCargo.upsert(cargo6);
			dbCargo.upsert(cargo7);
		}
	}
}
