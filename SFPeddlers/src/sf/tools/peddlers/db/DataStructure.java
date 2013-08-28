package sf.tools.peddlers.db;

public class DataStructure {
	public static final String TAG = "DataStructure";

	public static class DSSettingGroup {
		public static final String TB_NAME = "tb_setting_group";
		public static final String COL_SETTING_GROUP_ID = "setting_group_id";
		public static final String COL_SETTING_GROUP_NAME = "setting_group_name";
		public static final String[] COLUMNS = {
			COL_SETTING_GROUP_ID,
			COL_SETTING_GROUP_NAME
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s text primary key,"+
				"%s text);",
				TB_NAME,
				COL_SETTING_GROUP_ID,
				COL_SETTING_GROUP_NAME
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
	public static class DSFirstFeeling {
		public static final String TB_NAME = "tb_first_feeling";
		public static final String COL_FIRST_FEELING_ID = "first_feeling_id";
		public static final String COL_FIRST_FEELING_NAME = "first_feeling_name";
		public static final String COL_SETTING_GROUP_ID = "setting_group_id";
		public static final String[] COLUMNS = {
			COL_FIRST_FEELING_ID,
			COL_FIRST_FEELING_NAME,
			COL_SETTING_GROUP_ID
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s integer primary key autoincrement,"+
				"%s text,"+
				"%s text);",
				TB_NAME,
				COL_FIRST_FEELING_ID,
				COL_FIRST_FEELING_NAME,
				COL_SETTING_GROUP_ID
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
	public static class DSCharacteristic {
		public static final String TB_NAME = "tb_characteristic";
		public static final String COL_CHARACTERISTIC_ID = "characteristic_id";
		public static final String COL_CHARACTERISTIC_NAME = "characteristic_name";
		public static final String COL_SETTING_GROUP_ID = "setting_group_id";
		public static final String[] COLUMNS = {
			COL_CHARACTERISTIC_ID,
			COL_CHARACTERISTIC_NAME,
			COL_SETTING_GROUP_ID
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s integer primary key autoincrement,"+
				"%s text,"+
				"%s text);",
				TB_NAME,
				COL_CHARACTERISTIC_ID,
				COL_CHARACTERISTIC_NAME,
				COL_SETTING_GROUP_ID
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
	public static class DSCharacteristicItem {
		public static final String TB_NAME = "tb_characteristic_item";
		public static final String COL_CHARACTERISTIC_ITEM_ID = "characteristic_item_id";
		public static final String COL_CHARACTERISTIC_ITEM_NAME = "characteristic_item_name";
		public static final String COL_CHARACTERISTIC_ID = "characteristic_id";
		public static final String[] COLUMNS = {
			COL_CHARACTERISTIC_ITEM_ID,
			COL_CHARACTERISTIC_ITEM_NAME,
			COL_CHARACTERISTIC_ID
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s integer primary key autoincrement,"+
				"%s text,"+
				"%s integer);",
				TB_NAME,
				COL_CHARACTERISTIC_ITEM_ID,
				COL_CHARACTERISTIC_ITEM_NAME,
				COL_CHARACTERISTIC_ID
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
	public static class DSShoppingList {
		public static final String TB_NAME = "tb_shopping_list";
		public static final String COL_SHOPPING_LIST_ID = "shopping_list_id";
		public static final String COL_FIRST_FEELING_ID = "first_feeling_id";
		public static final String COL_SETTING_GROUP_ID = "setting_group_id";
		public static final String[] COLUMNS = {
			COL_SHOPPING_LIST_ID,
			COL_FIRST_FEELING_ID,
			COL_SETTING_GROUP_ID
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s text primary key autoincrement,"+
				"%s integer,"+
				"%s text);",
				TB_NAME,
				COL_SHOPPING_LIST_ID,
				COL_FIRST_FEELING_ID,
				COL_SETTING_GROUP_ID
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
	public static class DSCargoType {
		public static final String TB_NAME = "tb_cargo_type";
		public static final String COL_CARGO_TYPE_ID = "cargo_type_id";
		public static final String COL_CARGO_TYPE_NAME = "cargo_type_name";
		public static final String COL_SETTING_GROUP_ID = "setting_group_id";
		public static final String[] COLUMNS = {
			COL_CARGO_TYPE_ID,
			COL_CARGO_TYPE_NAME,
			COL_SETTING_GROUP_ID
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s integer primary key autoincrement,"+
				"%s text,"+
				"%s text);",
				COL_CARGO_TYPE_ID,
				COL_CARGO_TYPE_NAME,
				COL_SETTING_GROUP_ID
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
	public static class DSCargo {
		public static final String TB_NAME = "tb_cargo";
		public static final String COL_CARGO_ID = "cargo_id";
		public static final String COL_CARGO_NAME = "cargo_name";
		public static final String COL_CARGO_TYPE_ID = "cargo_type_id";
		public static final String COL_SETTING_GROUP_ID = "setting_group_id";
		public static final String[] COLUMNS = {
			COL_CARGO_ID,
			COL_CARGO_NAME,
			COL_CARGO_TYPE_ID,
			COL_SETTING_GROUP_ID
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s integer primary key autoincrement,"+
				"%s text,"+
				"%s integer,"+
				"%s text);",
				TB_NAME,
				COL_CARGO_ID,
				COL_CARGO_NAME,
				COL_CARGO_TYPE_ID,
				COL_SETTING_GROUP_ID
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
	public static class DSCargoInList {
		public static final String TB_NAME = "tb_cargo_in_list";
		public static final String COL_CARGO_ID = "cargo_id";
		public static final String COL_SHOPPING_LIST_ID = "shopping_list_id";
		public static final String COL_USER_BEHAVIOR = "user_behavior";
		public static final String[] COLUMNS = {
			COL_CARGO_ID,
			COL_SHOPPING_LIST_ID,
			COL_USER_BEHAVIOR
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s integer,"+
				"%s text,"+
				"%s integer);",
				TB_NAME,
				COL_CARGO_ID,
				COL_SHOPPING_LIST_ID,
				COL_USER_BEHAVIOR
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
	public static class DSCharacteristicItemInList {
		public static final String TB_NAME = "tb_characteristic_item_in_list";
		public static final String COL_CHARACTERISTIC_ID = "characteristic_id";
		public static final String COL_CHARACTERISTIC_ITEM_ID = "characteristic_item_id";
		public static final String COL_SHOPPING_LIST_ID = "shopping_list_id";
		public static final String[] COLUMNS = {
			COL_CHARACTERISTIC_ID,
			COL_CHARACTERISTIC_ITEM_ID,
			COL_SHOPPING_LIST_ID
		};
		public static final String SQL_CREATE = String.format(
				"create table %s ("+
				"%s integer,"+
				"%s integer,"+
				"%s text);",
				TB_NAME,
				COL_CHARACTERISTIC_ID,
				COL_CHARACTERISTIC_ITEM_ID,
				COL_SHOPPING_LIST_ID
				);
		public static final String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TB_NAME);
	}
}