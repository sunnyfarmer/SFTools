package sf.tools.peddlers.model;

import java.io.Serializable;
import java.util.ArrayList;

import sf.log.SFLog;
import sf.tools.peddlers.db.DataStructure.DSShoppingList;

import android.content.ContentValues;

public class ShoppingList implements Serializable, Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1977230604481916816L;

	public static final String TAG = "ShoppingList";

	private String mShoppingListId = null;
	private FirstFeeling mFirstFeeling = null;
	private SettingGroup mSettingGroup = null;
	private ArrayList<Characteristic> mCharacteristic = null;
	private ArrayList<Cargo> mLookCargo = null;
	private ArrayList<Cargo> mBuyCargo = null;
	private ArrayList<Cargo> mRelatedCargo = null;

	public ShoppingList(FirstFeeling firstFeeling, SettingGroup settingGroup, ArrayList<Characteristic> characteristicArray) {
		this.setmFirstFeeling(firstFeeling);
		this.setmSettingGroup(settingGroup);
		this.setmCharacteristic(characteristicArray);
	}
	
	public String getmShoppingListId() {
		return mShoppingListId;
	}
	public void setmShoppingListId(String mShoppingListId) {
		this.mShoppingListId = mShoppingListId;
	}
	public FirstFeeling getmFirstFeeling() {
		return mFirstFeeling;
	}
	public void setmFirstFeeling(FirstFeeling mFirstFeeling) {
		this.mFirstFeeling = mFirstFeeling;
	}
	public SettingGroup getmSettingGroup() {
		return mSettingGroup;
	}
	public void setmSettingGroup(SettingGroup mSettingGroup) {
		this.mSettingGroup = mSettingGroup;
	}
	public ArrayList<CharacteristicItem> getmSelectedCharacteristicItem() {
		ArrayList<CharacteristicItem> itemArray = new ArrayList<CharacteristicItem>();
		for (Characteristic characteristic : this.getmCharacteristic()) {
			if (characteristic.getmSelectedCharacteristicItem()!=null) {
				itemArray.add(characteristic.getmSelectedCharacteristicItem());
			} else {
				SFLog.e(TAG, "有特征选项未填");
			}
		}
		return itemArray;
	}
	public ArrayList<Characteristic> getmCharacteristic() {
		return mCharacteristic;
	}
	public void setmCharacteristic(ArrayList<Characteristic> mCharacteristic) {
		this.mCharacteristic = mCharacteristic;
	}
	public ArrayList<Cargo> getmLookCargo() {
		return mLookCargo;
	}
	public void setmLookCargo(ArrayList<Cargo> mLookCargo) {
		this.mLookCargo = mLookCargo;
	}
	public ArrayList<Cargo> getmBuyCargo() {
		return mBuyCargo;
	}
	public void setmBuyCargo(ArrayList<Cargo> mBuyCargo) {
		this.mBuyCargo = mBuyCargo;
	}
	public ArrayList<Cargo> getmRelatedCargo() {
		return mRelatedCargo;
	}
	public void setmRelatedCargo(ArrayList<Cargo> mRelatedCargo) {
		this.mRelatedCargo = mRelatedCargo;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mShoppingListId != null) {
			values.put(DSShoppingList.COL_SHOPPING_LIST_ID, this.mShoppingListId);
		}
		values.put(DSShoppingList.COL_FIRST_FEELING_ID, this.mFirstFeeling.getmFirstFeelingId());
		values.put(DSShoppingList.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		return values;
	}
}