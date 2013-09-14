package sf.tools.peddlers.model;

import java.util.ArrayList;

import sf.log.SFLog;
import sf.tools.peddlers.db.DataStructure.DSShoppingList;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class ShoppingList extends Model{
	public static final String TAG = "ShoppingList";

	private String mShoppingListId = null;
	private long mTimestamp = 0;
	private FirstFeeling mFirstFeeling = null;
	private SettingGroup mSettingGroup = null;
	private ArrayList<Characteristic> mCharacteristic = null;
	private ArrayList<Cargo> mLookCargo = null;
	private ArrayList<Cargo> mBuyCargo = null;
	private ArrayList<Cargo> mRelatedCargo = null;

    public static final Parcelable.Creator<ShoppingList> CREATOR = new Parcelable.Creator<ShoppingList>() {
		public ShoppingList createFromParcel(Parcel in) {
		    return new ShoppingList(in);
		}
		
		public ShoppingList[] newArray(int size) {
		    return new ShoppingList[size];
		}
	};
	public ShoppingList(Parcel in) {
		this.mShoppingListId = in.readString();
		this.mTimestamp = in.readLong();
		this.mFirstFeeling = in.readParcelable(null);
		this.mSettingGroup = in.readParcelable(null);
		int sizeOfCaracteristic = in.readInt();
		this.mCharacteristic = new ArrayList<Characteristic>(); 
		for (int cot = 0; cot < sizeOfCaracteristic; cot++) {
			Characteristic characteristic = in.readParcelable(null);
			this.mCharacteristic.add(characteristic);
		}
		int sizeOfLookCargo = in.readInt();
		this.mLookCargo = new ArrayList<Cargo>();
		for (int cot = 0; cot < sizeOfLookCargo; cot++) {
			Cargo cargo = in.readParcelable(null);
			this.mLookCargo.add(cargo);
		}
		int sizeOfBuyCargo = in.readInt();
		this.mBuyCargo = new ArrayList<Cargo>();
		for (int cot = 0; cot < sizeOfBuyCargo; cot++) {
			Cargo cargo = in.readParcelable(null);
			this.mBuyCargo.add(cargo);
		}
		int sizeOfRelatedCargo = in.readInt();
		this.mRelatedCargo = new ArrayList<Cargo>();
		for (int cot = 0; cot < sizeOfRelatedCargo; cot++) {
			Cargo cargo = in.readParcelable(null);
			this.mRelatedCargo.add(cargo);
		}
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeString(this.mShoppingListId);
		out.writeLong(this.mTimestamp);
		out.writeParcelable(this.mFirstFeeling, flags);
		out.writeParcelable(this.mSettingGroup, flags);
		if (this.mCharacteristic!=null) {
			out.writeInt(this.mCharacteristic.size());
			for (Characteristic characteristic : this.mCharacteristic) {
				out.writeParcelable(characteristic, flags);
			}
		} else {
			out.writeInt(0);
		}
		if (this.mLookCargo!=null) {
			out.writeInt(this.mLookCargo.size());
			for (Cargo cargo : this.mLookCargo) {
				out.writeParcelable(cargo, flags);
			}
		} else {
			out.writeInt(0);
		}
		if (this.mBuyCargo!=null) {
			out.writeInt(this.mBuyCargo.size());
			for (Cargo cargo : this.mBuyCargo) {
				out.writeParcelable(cargo, flags);
			}
		} else {
			out.writeInt(0);
		}
		if (this.mRelatedCargo!=null) {
			out.writeInt(this.mRelatedCargo.size());
			for (Cargo cargo : this.mRelatedCargo) {
				out.writeParcelable(cargo, flags);
			}
		} else {
			out.writeInt(0);
		}
	}

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
	public long getmTimestamp() {
		return mTimestamp;
	}
	public void setmTimestamp(long mTimestamp) {
		this.mTimestamp = mTimestamp;
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mShoppingListId != null) {
			values.put(DSShoppingList.COL_SHOPPING_LIST_ID, this.mShoppingListId);
		}
		values.put(DSShoppingList.COL_FIRST_FEELING_ID, this.mFirstFeeling.getmFirstFeelingId());
		values.put(DSShoppingList.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		values.put(DSShoppingList.COL_SHOPPING_TIMESTAMP, this.mTimestamp);
		return values;
	}
}