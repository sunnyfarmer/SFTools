package sf.tools.peddlers.model;

import java.util.ArrayList;
import sf.tools.peddlers.db.DataStructure.DSSettingGroup;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class SettingGroup extends Model{

	public static final String TAG = "SettingGroup";

	private String mSettingGroupId = null;
	private String mSettingGroupName = null;

	//init null
	private ArrayList<FirstFeeling> mFirstFeelingArray = new ArrayList<FirstFeeling>();
	private ArrayList<Characteristic> mCharacteristicArray = new ArrayList<Characteristic>();
	private ArrayList<CargoType> mCargoTypeArray = new ArrayList<CargoType>();
	private SparseArray<ArrayList<Cargo>> mCargoArray = new SparseArray<ArrayList<Cargo>>();

    public static final Parcelable.Creator<SettingGroup> CREATOR = new Parcelable.Creator<SettingGroup>() {
		public SettingGroup createFromParcel(Parcel in) {
		    return new SettingGroup(in);
		}
		
		public SettingGroup[] newArray(int size) {
		    return new SettingGroup[size];
		}
	};
	public SettingGroup(Parcel in) {
		super(in);
		mFirstFeelingArray = new ArrayList<FirstFeeling>();
		mCharacteristicArray = new ArrayList<Characteristic>();
		mCargoTypeArray = new ArrayList<CargoType>();
		mCargoArray = new SparseArray<ArrayList<Cargo>>();

		this.mSettingGroupId = in.readString();
		this.mSettingGroupName = in.readString();
//		int firstFeelingArraySize = in.readInt();
//		for(int cot = 0; cot < firstFeelingArraySize; cot++) {
//			FirstFeeling firstFeeling = in.readParcelable(null);
//			this.mFirstFeelingArray.add(firstFeeling);
//		}
//		int characteristicArraySize = in.readInt();
//		for(int cot = 0; cot < characteristicArraySize; cot++) {
//			Characteristic characteristic = in.readParcelable(null);
//			this.mCharacteristicArray.add(characteristic);
//		}
//		int cargoTypeArraySize = in.readInt();
//		for(int cot = 0; cot < cargoTypeArraySize; cot++) {
//			CargoType cargoType = in.readParcelable(null);
//			this.mCargoTypeArray.add(cargoType);
//		}
//		int sizeOfCargoListArraySize = in.readInt();
//		for(int cot = 0; cot < sizeOfCargoListArraySize; cot++) {
//			int key = in.readInt();
//			int sizeOfCargoList = in.readInt();
//			ArrayList<Cargo> cargoList = new ArrayList<Cargo>();
//			for (int cotOfCargo = 0; cotOfCargo < sizeOfCargoList; cotOfCargo++) {
//				Cargo cargo = in.readParcelable(null);
//				cargoList.add(cargo);
//			}
//			this.mCargoArray.put(key, cargoList);
//		}
//
//		int sizeOfCargoSparseArray = in.readInt();
//		this.mCargoArray = new SparseArray<ArrayList<Cargo>>();
//		for(int cot = 0; cot < sizeOfCargoSparseArray; cot++) {
//			int key = in.readInt();
//			ArrayList<Cargo> cargoList = (ArrayList<Cargo>) in.readSerializable();
//			this.mCargoArray.put(key, cargoList);
//		}
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeString(this.mSettingGroupId);
		out.writeString(this.mSettingGroupName);
//		if (this.mFirstFeelingArray!=null) {
//			out.writeInt(this.mFirstFeelingArray.size());
//			for (FirstFeeling firstFeeling : this.mFirstFeelingArray) {
//				out.writeParcelable(firstFeeling, flags);
//			}
//		} else {
//			out.writeInt(0);
//		}
//		if (this.mCharacteristicArray!=null) {
//			out.writeInt(this.mCharacteristicArray.size());
//			for (Characteristic characteristic : this.mCharacteristicArray) {
//				out.writeParcelable(characteristic, flags);
//			}
//		} else {
//			out.writeInt(0);
//		}
//		if (this.mCargoTypeArray!=null) {
//			out.writeInt(this.mCargoTypeArray.size());
//			for (CargoType cargoType : this.mCargoTypeArray) {
//				out.writeParcelable(cargoType, flags);
//			}
//		} else {
//			out.writeInt(0);
//		}
//		if (this.mCargoArray!=null) {
//			out.writeInt(this.mCargoArray.size());
//			for(int cot = 0; cot < mCargoArray.size(); cot++) {
//				int key = mCargoArray.keyAt(cot);
//				out.writeInt(key);
//				ArrayList<Cargo> cargoList = mCargoArray.get(key);
//				out.writeInt(cargoList.size());
//				for (Cargo cargo : cargoList) {
//					out.writeParcelable(cargo, flags);
//				}
//			}
//		} else {
//			out.writeInt(0);
//		}
	}

	public SettingGroup(String settingGroupName) {
		this.setmSettingGroupName(settingGroupName);
	}

	public String getmSettingGroupId() {
		return mSettingGroupId;
	}
	public void setmSettingGroupId(String mSettingGroupId) {
		this.mSettingGroupId = mSettingGroupId;
	}
	public String getmSettingGroupName() {
		return mSettingGroupName;
	}
	public void setmSettingGroupName(String mSettingGroupName) {
		this.mSettingGroupName = mSettingGroupName;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mSettingGroupId != null) {
			values.put(DSSettingGroup.COL_SETTING_GROUP_ID, this.mSettingGroupId);
		}
		values.put(DSSettingGroup.COL_SETTING_GROUP_NAME, this.mSettingGroupName);
		return values;
	}

	public ArrayList<FirstFeeling> getmFirstFeelingArray() {
		return mFirstFeelingArray;
	}

	public void setmFirstFeelingArray(ArrayList<FirstFeeling> mFirstFeelingArray) {
		this.mFirstFeelingArray = mFirstFeelingArray;
	}

	public ArrayList<Characteristic> getmCharacteristicArray() {
		return mCharacteristicArray;
	}

	public void setmCharacteristicArray(
			ArrayList<Characteristic> mCharacteristicArray) {
		this.mCharacteristicArray = mCharacteristicArray;
	}

	public ArrayList<CargoType> getmCargoTypeArray() {
		return mCargoTypeArray;
	}

	public void setmCargoTypeArray(ArrayList<CargoType> mCargoTypeArray) {
		this.mCargoTypeArray = mCargoTypeArray;
	}

	public SparseArray<ArrayList<Cargo>> getmCargoArray() {
		return mCargoArray;
	}

	public void setmCargoArray(SparseArray<ArrayList<Cargo>> mCargoArray) {
		this.mCargoArray = mCargoArray;
	}
	/**
	 * 插入商品
	 * @param cargo
	 */
	public void putCargo(Cargo cargo) {
		if (this.mCargoArray==null) {
			this.mCargoArray = new SparseArray<ArrayList<Cargo>>();
		}
		if (null == this.mCargoArray.get(cargo.getmCargoType().getmCargoTypeId())) {
			this.mCargoArray.put(cargo.getmCargoType().getmCargoTypeId(), new ArrayList<Cargo>());
		}
		this.mCargoArray.get(cargo.getmCargoType().getmCargoTypeId()).add(cargo);
	}
}
