package sf.tools.peddlers.utils;

public class SFGlobal {
	public static final String TAG = "SFGlobal";

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "peddlers";

	public static final String EXTRA_SETTING_GROUP = "extra_setting_group";
	public static final String EXTRA_FIRSTFEELING = "extra_first_feeling";
	public static final String EXTRA_CARGO = "extra_cargo";
	public static final String EXTRA_CARGOTYPE = "extra_cargo_type";
	public static final String EXTRA_CHARACTERISTIC = "extra_characteristic";
	public static final String EXTRA_SHOPPINGLIST = "extra_shopping_list";

	public static final int RS_CODE_LOAD_IMAGE = 1001;
	public static final int RS_CODE_ADD_CARGO = 1002;
	public static final int RS_CODE_EDIT_CARGO = 1003;

	public static final String SP_FILE_NAME = "sp_peddlers";
	public static final String SP_SETTING_GROUP_ID = "sp_setting_group_id";

	public static final int DB_MSG_OK = 1;
	public static final int DB_MSG_SAME_COLUMN = 2;
	public static final int DB_MSG_SETTING_GROUP_SAME_NAME = 3;
	public static final int DB_MSG_UNKNOWN_SETTING_GROUP = 4;
	public static final int DB_MSG_UNKNOWN_CARGO_TYPE = 5;
	public static final int DB_MSG_UNKNOWN_CHARACTERISTIC = 6;
	public static final int DB_MSG_UNKNOWN_OBJECT = 7;
	public static final int DB_MSG_ERROR = 256;
}
