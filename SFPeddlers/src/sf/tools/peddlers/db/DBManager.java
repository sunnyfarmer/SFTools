package sf.tools.peddlers.db;

import sf.tools.peddlers.SFPeddlersApp;
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
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;

public class DBManager {
	public static final String TAG = "DBManager";

	private DBSettingGroup mDBSettingGroup = null;
	private DBCargo mDBCargo = null;
	private DBCargoInList mDBCargoInList = null;
	private DBCargoType mDBCargoType = null;
	private DBCharacteristic mDBCharacteristic = null;
	private DBCharacteristicItem mDBCharacteristicItem = null;
	private DBCharacteristicItemInList mDBCharacteristicItemInList = null;
	private DBFirstFeeling mDBFirstFeeling = null;
	private DBShoppingList mDBShoppingList = null;

	private SFPeddlersApp mApp = null;

	public DBManager(SFPeddlersApp app) {
		this.mApp = app;
	}

	public DBSettingGroup getmDBSettingGroup() {
		if (this.mDBSettingGroup == null) {
			this.mDBSettingGroup = new DBSettingGroup(this.mApp);
		}
		return mDBSettingGroup;
	}

	public DBCargo getmDBCargo() {
		if (this.mDBCargo == null) {
			this.mDBCargo = new DBCargo(this.mApp);
		}
		return mDBCargo;
	}

	public DBCargoInList getmDBCargoInList() {
		if (this.mDBCargoInList == null) {
			this.mDBCargoInList = new DBCargoInList(this.mApp);
		}
		return mDBCargoInList;
	}

	public DBCargoType getmDBCargoType() {
		if (this.mDBCargoType == null) {
			this.mDBCargoType = new DBCargoType(this.mApp);
		}
		return mDBCargoType;
	}

	public DBCharacteristic getmDBCharacteristic() {
		if (this.mDBCharacteristic == null) {
			this.mDBCharacteristic = new DBCharacteristic(this.mApp);
		}
		return mDBCharacteristic;
	}

	public DBCharacteristicItem getmDBCharacteristicItem() {
		if (this.mDBCharacteristicItem == null) {
			this.mDBCharacteristicItem = new DBCharacteristicItem(this.mApp);
		}
		return mDBCharacteristicItem;
	}

	public DBCharacteristicItemInList getmDBCharacteristicItemInList() {
		if (this.mDBCharacteristicItemInList == null) {
			this.mDBCharacteristicItemInList = new DBCharacteristicItemInList(
					this.mApp);
		}
		return mDBCharacteristicItemInList;
	}

	public DBFirstFeeling getmDBFirstFeeling() {
		if (this.mDBFirstFeeling == null) {
			this.mDBFirstFeeling = new DBFirstFeeling(this.mApp);
		}
		return mDBFirstFeeling;
	}

	public DBShoppingList getmDBShoppingList() {
		if (this.mDBShoppingList == null) {
			this.mDBShoppingList = new DBShoppingList(this.mApp);
		}
		return mDBShoppingList;
	}

	public int upsertSettingGroup(SettingGroup settingGroup) {
		return this.getmDBSettingGroup().upsert(settingGroup);
	}

	public int removeSettingGroup(SettingGroup settingGroup) {
		boolean dbRs = this.getmDBSettingGroup().delete(settingGroup.getmSettingGroupName());
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}

	public int upsertFirstFeeling(FirstFeeling firstFeeling) {
		return this.getmDBFirstFeeling().upsert(firstFeeling);
	}

	public int removeFirstFeeling(FirstFeeling firstFeeling) {
		boolean dbRs = this.getmDBFirstFeeling().delete(firstFeeling);
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}

	public int upsertCharacteristic(Characteristic characteristic) {
		return this.getmDBCharacteristic().upsert(characteristic);
	}

	public int removeCharacteristic(Characteristic characteristic) {
		boolean dbRs = this.getmDBCharacteristic().delete(characteristic);
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}

	public int upsertCharacteristicItem(CharacteristicItem characteristicItem) {
		return this.getmDBCharacteristicItem().upsert(characteristicItem);
	}

	public int removeCharacteristicItem(CharacteristicItem characteristicItem) {
		boolean dbRs = this.getmDBCharacteristicItem().delete(
				characteristicItem);
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}

	public int upsertCargoType(CargoType cargoType) {
		return this.getmDBCargoType().upsert(cargoType);
	}

	public int removeCargoType(CargoType cargoType) {
		boolean dbRs = this.getmDBCargoType().delete(cargoType);
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}

	public int upsertCargo(Cargo cargo) {
		return this.getmDBCargo().upsert(cargo);
	}

	public int removeCargo(Cargo cargo) {
		boolean dbRs = this.getmDBCargo().delete(cargo);
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}
}
