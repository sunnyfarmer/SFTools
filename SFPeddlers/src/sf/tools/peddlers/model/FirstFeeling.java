package sf.tools.peddlers.model;

import sf.tools.peddlers.db.DataStructure.DSFirstFeeling;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class FirstFeeling extends Model{
	public static final String TAG = "FirstFeeling";

	private SettingGroup mSettingGroup = null;
	private String mFirstFeelingName = null;
	private int mFirstFeelingId = ID_UNDEFINED;

    public static final Parcelable.Creator<FirstFeeling> CREATOR = new Parcelable.Creator<FirstFeeling>() {
		public FirstFeeling createFromParcel(Parcel in) {
		    return new FirstFeeling(in);
		}
		
		public FirstFeeling[] newArray(int size) {
		    return new FirstFeeling[size];
		}
	};
	public FirstFeeling(Parcel in) {
		super(in);
		this.mFirstFeelingId = in.readInt();
		this.mFirstFeelingName = in.readString();
		this.mSettingGroup = in.readParcelable(null);
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeInt(this.mFirstFeelingId);
		out.writeString(this.mFirstFeelingName);
		out.writeParcelable(mSettingGroup, flags);
	}
	public FirstFeeling(String firstFeeling, SettingGroup settingGroup) {
		this.setmFirstFeelingName(firstFeeling);
		this.setmSettingGroup(settingGroup);
	}

	public SettingGroup getmSettingGroup() {
		return mSettingGroup;
	}

	public void setmSettingGroup(SettingGroup mSettingGroup) {
		this.mSettingGroup = mSettingGroup;
	}

	public int getmFirstFeelingId() {
		return mFirstFeelingId;
	}

	public void setmFirstFeelingId(int mFirstFeelingId) {
		this.mFirstFeelingId = mFirstFeelingId;
	}

	public String getmFirstFeelingName() {
		return mFirstFeelingName;
	}

	public void setmFirstFeelingName(String mFirstFeeling) {
		this.mFirstFeelingName = mFirstFeeling;
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mFirstFeelingId != Model.ID_UNDEFINED) {
			values.put(DSFirstFeeling.COL_FIRST_FEELING_ID, this.mFirstFeelingId);
		}
		values.put(DSFirstFeeling.COL_FIRST_FEELING_NAME, this.mFirstFeelingName);
		values.put(DSFirstFeeling.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		return values;
	}
}
