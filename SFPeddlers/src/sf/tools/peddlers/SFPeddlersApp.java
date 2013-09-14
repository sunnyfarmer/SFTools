package sf.tools.peddlers;

import java.util.ArrayList;
import sf.tools.peddlers.db.DBManager;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.model.ShoppingList;
import sf.tools.peddlers.utils.SFGlobal;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.SparseArray;

public class SFPeddlersApp extends Application {
	public static final String TAG = "SFPeddlersApp";

	public DBManager mDBManager = null;

	/**
	 * 正在使用的商铺ID
	 */
	private String mSettingGroupId = null;
	/**
	 * 正在使用的商铺设置
	 */
	private SettingGroup mSettingGroup = null;
	/**
	 * 商铺设置是否需要重新加载
	 */
	private boolean mIsSettingGroupDirty = false;

	private SettingGroup mEditingSettingGroup = null;
	private Cargo mEdittingCargo = null;

	private ShoppingList mShoppingList = null;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public DBManager getmDbManager() {
		if (this.mDBManager==null) {
			this.mDBManager = new DBManager(this);
		}
		return this.mDBManager;
	}

	public SharedPreferences getSharedPreferences() {
		return this.getSharedPreferences(SFGlobal.SP_FILE_NAME, Context.MODE_PRIVATE);
	}

	public void setSettingGroupId(String settingGroupId) {
		if (settingGroupId!=null) {
			SettingGroup settingGroup = this.getmDbManager().getmDBSettingGroup().queryById(settingGroupId);
			if (settingGroup!=null) {
				SharedPreferences sp = this.getSharedPreferences();
				Editor editor = sp.edit();
				editor.putString(SFGlobal.SP_SETTING_GROUP_ID, settingGroupId);
				editor.commit();

				this.mSettingGroupId = settingGroupId;
			}
		}
	}
	public String getSettingGroupId() {
		if (this.mSettingGroupId==null) {
			SharedPreferences sp = this.getSharedPreferences();
			this.mSettingGroupId = sp.getString(SFGlobal.SP_SETTING_GROUP_ID, null);
		}

		return this.mSettingGroupId;
	}

	public SettingGroup getSettingGroup() {
		if (this.mIsSettingGroupDirty || this.mSettingGroup==null || !this.mSettingGroup.getmSettingGroupId().equals(this.getSettingGroupId())) {
			this.mSettingGroup = null;
			String settingGroupId = this.getSettingGroupId();
			if (settingGroupId!=null) {
				this.mSettingGroup = this.getmDbManager().getmDBSettingGroup().queryById(settingGroupId);

				ArrayList<FirstFeeling> firstFeelingArray = this.getmDbManager().getmDBFirstFeeling().queryAll(this.mSettingGroup);
				ArrayList<Characteristic> characteristicArray = this.getmDbManager().getmDBCharacteristic().queryAll(this.mSettingGroup);
				ArrayList<CargoType> cargoTypeArray = this.getmDbManager().getmDBCargoType().queryAll(this.mSettingGroup);
				SparseArray<ArrayList<Cargo>> cargoArray = new SparseArray<ArrayList<Cargo>>();
				for (CargoType cargoType : cargoTypeArray) {
					ArrayList<Cargo> cargoArrayOfType = this.getmDbManager().getmDBCargo().queryAll(cargoType);
					cargoArray.put(cargoType.getmCargoTypeId(), cargoArrayOfType);
				}
				this.mSettingGroup.setmFirstFeelingArray(firstFeelingArray);
				this.mSettingGroup.setmCharacteristicArray(characteristicArray);
				this.mSettingGroup.setmCargoTypeArray(cargoTypeArray);
				this.mSettingGroup.setmCargoArray(cargoArray);
			}
			this.mIsSettingGroupDirty = false;
		}
		return this.mSettingGroup;
	}

	public void setmIsSettingGroupDirty(boolean mIsSettingGroupDirty) {
		this.mIsSettingGroupDirty = mIsSettingGroupDirty;
	}

	public ShoppingList getmShoppingList() {
		return mShoppingList;
	}

	public void setmShoppingList(ShoppingList mShoppingList) {
		this.mShoppingList = mShoppingList;
	}

	public SettingGroup getmEditingSettingGroup() {
		return mEditingSettingGroup;
	}

	public void setmEditingSettingGroup(SettingGroup mEditingSettingGroup) {
		this.mEditingSettingGroup = mEditingSettingGroup;
	}

	public Cargo getmEdittingCargo() {
		return mEdittingCargo;
	}

	public void setmEdittingCargo(Cargo mEdittingCargo) {
		this.mEdittingCargo = mEdittingCargo;
	}
}
