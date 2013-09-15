package sf.tools.peddlers.model;

import sf.tools.peddlers.db.DataStructure.DSCharacteristicItem;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class CharacteristicItem extends Model {
	public static final String TAG = "CharacteristicItem";

	private int mCharacteristicItemId = ID_UNDEFINED;
	private String mCharacteristicItemName = null;
	private Characteristic mCharacteristic = null;

    public static final Parcelable.Creator<CharacteristicItem> CREATOR = new Parcelable.Creator<CharacteristicItem>() {
		public CharacteristicItem createFromParcel(Parcel in) {
		    return new CharacteristicItem(in);
		}
		
		public CharacteristicItem[] newArray(int size) {
		    return new CharacteristicItem[size];
		}
	};
	public CharacteristicItem(Parcel in) {
		this.mCharacteristicItemId = in.readInt();
		this.mCharacteristicItemName = in.readString();
//		this.mCharacteristic = in.readParcelable(null);
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeInt(this.mCharacteristicItemId);
		out.writeString(this.mCharacteristicItemName);
//		out.writeParcelable(mCharacteristic, flags);
	}
	public CharacteristicItem(int id, String name, Characteristic characteristic) {
		this.setmCharacteristicItemId(id);
		this.setmCharacteristicItemName(name);
		this.setmCharacteristic(characteristic);
	}
	public CharacteristicItem(String name, Characteristic characteristic) {
		this(ID_UNDEFINED, name, characteristic);
	}
	
	public int getmCharacteristicItemId() {
		return mCharacteristicItemId;
	}
	public void setmCharacteristicItemId(int mCharacteristicItemId) {
		this.mCharacteristicItemId = mCharacteristicItemId;
	}
	public String getmCharacteristicItemName() {
		return mCharacteristicItemName;
	}
	public void setmCharacteristicItemName(String mCharacteristicItemName) {
		this.mCharacteristicItemName = mCharacteristicItemName;
	}
	public Characteristic getmCharacteristic() {
		return mCharacteristic;
	}
	public void setmCharacteristic(Characteristic mCharacteristic) {
		this.mCharacteristic = mCharacteristic;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mCharacteristicItemId != Model.ID_UNDEFINED) {
			values.put(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_ID, this.mCharacteristicItemId);
		}
		values.put(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_NAME, this.mCharacteristicItemName);
		values.put(DSCharacteristicItem.COL_CHARACTERISTIC_ID, this.mCharacteristic.getmCharacteristicId());
		return values;
	}

}
