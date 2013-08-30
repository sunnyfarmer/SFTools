package sf.tools.peddlers.db.controller;

import android.content.Context;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.model.CargoType;

public class DBCargoType extends DBController {
	public static final String TAG = "DBCargoType";

	public DBCargoType(Context context) {
		super(context);
	}

	public boolean insert(CargoType cargoType) {
		if (cargoType==null ||
			cargoType.getmSettingGroup()==null ||
			cargoType.getmSettingGroup().getmSettingGroupId()==null) {
			return false;
		}
		//TODO:
		return true;
	}
}
