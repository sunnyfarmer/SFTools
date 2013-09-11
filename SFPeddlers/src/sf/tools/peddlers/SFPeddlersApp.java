package sf.tools.peddlers;

import java.util.ArrayList;
import java.util.HashMap;

import sf.tools.peddlers.db.controller.DBCargo;
import sf.tools.peddlers.db.controller.DBCargoInList;
import sf.tools.peddlers.db.controller.DBCargoType;
import sf.tools.peddlers.db.controller.DBCharacteristic;
import sf.tools.peddlers.db.controller.DBCharacteristicItem;
import sf.tools.peddlers.db.controller.DBCharacteristicItemInList;
import sf.tools.peddlers.db.controller.DBFirstFeeling;
import sf.tools.peddlers.db.controller.DBSettingGroup;
import sf.tools.peddlers.db.controller.DBShoppingList;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SFPeddlersApp extends Application {
	public static final String TAG = "SFPeddlersApp";

	private DBSettingGroup mDBSettingGroup = null;
	private DBCargo mDBCargo = null;
	private DBCargoInList mDBCargoInList = null;
	private DBCargoType mDBCargoType = null;
	private DBCharacteristic mDBCharacteristic = null;
	private DBCharacteristicItem mDBCharacteristicItem = null;
	private DBCharacteristicItemInList mDBCharacteristicItemInList = null;
	private DBFirstFeeling mDBFirstFeeling = null;
	private DBShoppingList mDBShoppingList = null;

	private String mSettingGroupId = null;
	private SettingGroup mSettingGroup = null;

	private SettingGroup mEditingSettingGroup = null;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public SharedPreferences getSharedPreferences() {
		return this.getSharedPreferences(SFGlobal.SP_FILE_NAME, Context.MODE_PRIVATE);
	}

	public String getSettingGroupId() {
		if (this.mSettingGroupId==null) {
			SharedPreferences sp = this.getSharedPreferences();
			this.mSettingGroupId = sp.getString(SFGlobal.SP_SETTING_GROUP_ID, null);
		}

		return this.mSettingGroupId;
	}

	public SettingGroup getSettingGroup() {
		if (this.mSettingGroup==null || !this.mSettingGroup.getmSettingGroupId().equals(this.getSettingGroupId())) {
			String settingGroupId = this.getSettingGroupId();
			if (settingGroupId!=null) {
				this.mSettingGroup = this.mDBSettingGroup.queryById(settingGroupId);

				ArrayList<FirstFeeling> firstFeelingArray = this.mDBFirstFeeling.queryAll(this.mSettingGroup);
				ArrayList<Characteristic> characteristicArray = this.mDBCharacteristic.queryAll(this.mSettingGroup);
				ArrayList<CargoType> cargoTypeArray = this.mDBCargoType.queryAll(this.mSettingGroup);
				HashMap<CargoType, ArrayList<Cargo>> cargoHashMap = new HashMap<CargoType, ArrayList<Cargo>>();
				for (CargoType cargoType : cargoTypeArray) {
					ArrayList<Cargo> cargoArray = this.mDBCargo.queryAll(cargoType);
					cargoHashMap.put(cargoType, cargoArray);
				}
				this.mSettingGroup.setmFirstFeelingArray(firstFeelingArray);
				this.mSettingGroup.setmCharacteristicArray(characteristicArray);
				this.mSettingGroup.setmCargoTypeArray(cargoTypeArray);
				this.mSettingGroup.setmCargoArray(cargoHashMap);
			}
		}
		return this.mSettingGroup;
	}

	public DBSettingGroup getmDBSettingGroup() {
		if (this.mDBSettingGroup==null) {
			this.mDBSettingGroup = new DBSettingGroup(this);
		}
		return mDBSettingGroup;
	}
	public DBCargo getmDBCargo() {
		if (this.mDBCargo==null) {
			this.mDBCargo = new DBCargo(this);
		}
		return mDBCargo;
	}
	public DBCargoInList getmDBCargoInList() {
		if (this.mDBCargoInList==null) {
			this.mDBCargoInList = new DBCargoInList(this);
		}
		return mDBCargoInList;
	}
	public DBCargoType getmDBCargoType() {
		if (this.mDBCargoType==null) {
			this.mDBCargoType = new DBCargoType(this);
		}
		return mDBCargoType;
	}
	public DBCharacteristic getmDBCharacteristic() {
		if (this.mDBCharacteristic==null) {
			this.mDBCharacteristic = new DBCharacteristic(this);
		}
		return mDBCharacteristic;
	}
	public DBCharacteristicItem getmDBCharacteristicItem() {
		if (this.mDBCharacteristicItem==null) {
			this.mDBCharacteristicItem = new DBCharacteristicItem(this);
		}
		return mDBCharacteristicItem;
	}
	public DBCharacteristicItemInList getmDBCharacteristicItemInList() {
		if (this.mDBCharacteristicItemInList==null) {
			this.mDBCharacteristicItemInList = new DBCharacteristicItemInList(this);
		}
		return mDBCharacteristicItemInList;
	}
	public DBFirstFeeling getmDBFirstFeeling() {
		if (this.mDBFirstFeeling==null) {
			this.mDBFirstFeeling = new DBFirstFeeling(this);
		}
		return mDBFirstFeeling;
	}
	public DBShoppingList getmDBShoppingList() {
		if (this.mDBShoppingList==null) {
			this.mDBShoppingList = new DBShoppingList(this);
		}
		return mDBShoppingList;
	}

	public SettingGroup getmEditingSettingGroup() {
		return mEditingSettingGroup;
	}

	public void setmEditingSettingGroup(SettingGroup mEditingSettingGroup) {
		this.mEditingSettingGroup = mEditingSettingGroup;
	}

	public int addFirstFeeling(FirstFeeling firstFeeling) {
		if (this.getmEditingSettingGroup()==null) {
			return SFGlobal.DB_MSG_ERROR;
		}
		return this.getmDBFirstFeeling().upsert(firstFeeling);
	}
	public int removeFirstFeeling(FirstFeeling firstFeeling) {
		if (this.getmEditingSettingGroup()==null) {
			return SFGlobal.DB_MSG_ERROR;
		}
		boolean dbRs = this.getmDBFirstFeeling().delete(firstFeeling);
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}
	public int addCharacteristic(Characteristic characteristic) {
		if (this.getmEditingSettingGroup()==null) {
			return SFGlobal.DB_MSG_ERROR;
		}
		return this.getmDBCharacteristic().upsert(characteristic);
	}
}
