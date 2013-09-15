package sf.tools.peddlers.model;

import java.util.ArrayList;

import sf.tools.peddlers.db.DataStructure.DSCharacteristic;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class Characteristic extends Model{
	public static final String TAG = "Characteristic";

	private int mCharacteristicId = ID_UNDEFINED;
	private String mCharacteristicName = null;
	private SettingGroup mSettingGroup = null;
	private ArrayList<CharacteristicItem> mCharacteristicItemArray = new ArrayList<CharacteristicItem>();

	private CharacteristicItem mSelectedCharacteristicItem = null;

    public static final Parcelable.Creator<Characteristic> CREATOR = new Parcelable.Creator<Characteristic>() {
		public Characteristic createFromParcel(Parcel in) {
		    return new Characteristic(in);
		}
		
		public Characteristic[] newArray(int size) {
		    return new Characteristic[size];
		}
	};
	public Characteristic(Parcel in) {
		this.mCharacteristicId = in.readInt();
		this.mCharacteristicName = in.readString();
		this.mSettingGroup = in.readParcelable(null);
		int sizeOfItem = in.readInt();
		mCharacteristicItemArray = new ArrayList<CharacteristicItem>();
		for(int cot = 0; cot < sizeOfItem; cot++) {
			CharacteristicItem item = in.readParcelable(null);
			this.mCharacteristicItemArray.add(item);
		}
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeInt(this.mCharacteristicId);
		out.writeString(this.mCharacteristicName);
		out.writeParcelable(mSettingGroup, flags);
		if (this.mCharacteristicItemArray!=null) {
			out.writeInt(this.mCharacteristicItemArray.size());
			for (CharacteristicItem characteristicItem : this.mCharacteristicItemArray) {
				out.writeParcelable(characteristicItem, flags);
			}
		} else {
			out.writeInt(0);
		}
	}

	public Characteristic(String title, SettingGroup settingGroup) {
		this.setmCharacteristicName(title);
		this.setmSettingGroup(settingGroup);
	}

	public int getmCharacteristicId() {
		return mCharacteristicId;
	}

	public void setmCharacteristicId(int mCharacteristicId) {
		this.mCharacteristicId = mCharacteristicId;
	}

	public String getmCharacteristicName() {
		return mCharacteristicName;
	}
	public void setmCharacteristicName(String mTitle) {
		this.mCharacteristicName = mTitle;
	}
	public ArrayList<CharacteristicItem> getmCharacteristicItemArray() {
		if (this.mCharacteristicItemArray == null) {
			this.mCharacteristicItemArray = new ArrayList<CharacteristicItem>();
		}
		return mCharacteristicItemArray;
	}
	public ArrayList<String> getmCharacteristicItemStringArray() {
		ArrayList<String> stringArray = new ArrayList<String>();
		for (CharacteristicItem characteristicItem : this.getmCharacteristicItemArray()) {
			stringArray.add(characteristicItem.getmCharacteristicItemName());
		}
		return stringArray;
	}
	public void setmCharacteristicItemArray(ArrayList<CharacteristicItem> mValuesArray) {
		this.mCharacteristicItemArray = mValuesArray;
	}
	public void addCharacteristicItem(CharacteristicItem value) {
		this.getmCharacteristicItemArray().add(value);
	}
	public CharacteristicItem getmSelectedCharacteristicItem() {
		return mSelectedCharacteristicItem;
	}
	public void setmSelectedCharacteristicItem(CharacteristicItem characteristicItem) {
		this.mSelectedCharacteristicItem = characteristicItem;
	}
	public void setmSelectedCharacteristicItemString(String characteristicItemString) {
		if (characteristicItemString!=null) {
			for (CharacteristicItem characteristicItem : this.mCharacteristicItemArray) {
				if (characteristicItemString.equals(characteristicItem.getmCharacteristicItemName())) {
					this.mSelectedCharacteristicItem = characteristicItem;
				}
			}
		}
	}
	public SettingGroup getmSettingGroup() {
		return mSettingGroup;
	}

	public void setmSettingGroup(SettingGroup mSettingGroup) {
		this.mSettingGroup = mSettingGroup;
	}

	@Override
	public String toString() {
		String toString = null;
		if (this.getmSelectedCharacteristicItem()==null) {
			toString = String.format("%s:null", this.mCharacteristicName);
		} else {
			toString = String.format("%s:%s", this.mCharacteristicName, this.mSelectedCharacteristicItem.getmCharacteristicItemName());
		}
		return toString;
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mCharacteristicId != Model.ID_UNDEFINED) {
			values.put(DSCharacteristic.COL_CHARACTERISTIC_ID, this.mCharacteristicId);
		}
		values.put(DSCharacteristic.COL_CHARACTERISTIC_NAME, this.mCharacteristicName);
		values.put(DSCharacteristic.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		return values;
	}
}
