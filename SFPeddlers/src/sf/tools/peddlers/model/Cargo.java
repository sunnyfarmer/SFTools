package sf.tools.peddlers.model;

import java.io.Serializable;

import sf.tools.peddlers.db.DataStructure.DSCargo;

import android.content.ContentValues;

public class Cargo implements Serializable,Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2253625273503187217L;
	public static final String TAG = "Cargo";

	public static enum CUSTOMER_BEHAVIOR {
		CB_NONE,
		CB_LOOK,
		CB_BUY
	};

	private int mCargoId = ID_UNDEFINED;
	private String mCargoName = null;
	private CargoType mCargoType = null;
	private SettingGroup mSettingGroup = null;

	private CUSTOMER_BEHAVIOR mBehavior = CUSTOMER_BEHAVIOR.CB_NONE;

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(DSCargo.COL_CARGO_ID, this.mCargoId);
		values.put(DSCargo.COL_CARGO_NAME, this.mCargoName);
		values.put(DSCargo.COL_CARGO_TYPE_ID, this.mCargoType.getmCargoTypeId());
		values.put(DSCargo.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		return values;
	}

	public Cargo(String cargoName, CargoType cargoType) {
		this.setmCargoName(cargoName);
		this.setmCargoType(cargoType);
	}

	public int getmCargoId() {
		return mCargoId;
	}

	public void setmCargoId(int mCargoId) {
		this.mCargoId = mCargoId;
	}

	public String getmCargoName() {
		return mCargoName;
	}
	public void setmCargoName(String mCargoName) {
		this.mCargoName = mCargoName;
	}
	public CargoType getmCargoType() {
		return mCargoType;
	}
	public void setmCargoType(CargoType mCargoType) {
		this.mCargoType = mCargoType;
	}

	public CUSTOMER_BEHAVIOR getmBehavior() {
		return mBehavior;
	}

	public void setmBehavior(CUSTOMER_BEHAVIOR mBehavior) {
		this.mBehavior = mBehavior;
	}

	public SettingGroup getmSettingGroup() {
		return mSettingGroup;
	}

	public void setmSettingGroup(SettingGroup mSettingGroup) {
		this.mSettingGroup = mSettingGroup;
	}
}
