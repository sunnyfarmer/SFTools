package sf.tools.peddlers.model;

import sf.tools.peddlers.db.DataStructure.DSCargoType;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class CargoType extends Model{
	public static final String TAG = "CargoType";

	private int mCargoTypeId = ID_UNDEFINED;
	private String mCargoTypeName = null;
	private SettingGroup mSettingGroup = null;

    public static final Parcelable.Creator<CargoType> CREATOR = new Parcelable.Creator<CargoType>() {
		public CargoType createFromParcel(Parcel in) {
		    return new CargoType(in);
		}
		
		public CargoType[] newArray(int size) {
		    return new CargoType[size];
		}
	};
	public CargoType(Parcel in) {
		super(in);
		this.mCargoTypeId = in.readInt();
		this.mCargoTypeName = in.readString();
		this.mSettingGroup = in.readParcelable(null);
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeInt(this.mCargoTypeId);
		out.writeString(mCargoTypeName);
		out.writeParcelable(mSettingGroup, flags);
	}
	public CargoType(String cargoTypeName, SettingGroup settingGroup) {
		this.setmCargoTypeName(cargoTypeName);
		this.setmSettingGroup(settingGroup);
	}

	public int getmCargoTypeId() {
		return mCargoTypeId;
	}

	public void setmCargoTypeId(int mCargoTypeId) {
		this.mCargoTypeId = mCargoTypeId;
	}

	public String getmCargoTypeName() {
		return mCargoTypeName;
	}

	public void setmCargoTypeName(String mCargoTypeName) {
		this.mCargoTypeName = mCargoTypeName;
	}

	public SettingGroup getmSettingGroup() {
		return mSettingGroup;
	}

	public void setmSettingGroup(SettingGroup mSettingGroup) {
		this.mSettingGroup = mSettingGroup;
	}

	@Override
	public boolean equals(Object o) {
		//名字相同视为相同
		if (o instanceof CargoType) {
			CargoType type = (CargoType) o;
			if (type.getmCargoTypeName()!=null && this.getmCargoTypeName()!=null) {
				return type.getmCargoTypeName().equals(this.getmCargoTypeName());
			}
		}
		return false;
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mCargoTypeId != Model.ID_UNDEFINED) {
			values.put(DSCargoType.COL_CARGO_TYPE_ID, this.mCargoTypeId);
		}
		values.put(DSCargoType.COL_CARGO_TYPE_NAME, this.mCargoTypeName);
		values.put(DSCargoType.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		return values;
	}
}
