package sf.tools.peddlers.model;

import java.io.Serializable;
import java.util.ArrayList;

import sf.tools.peddlers.db.DataStructure.DSCharacteristic;

import android.content.ContentValues;

public class Characteristic implements Serializable,Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4749038793940437454L;

	public static final String TAG = "Characteristic";

	private int mCharacteristicId = ID_UNDEFINED;
	private String mCharacteristicName = null;
	private SettingGroup mSettingGroup = null;
	private ArrayList<CharacteristicItem> mCharacteristicItemArray = null;

	private CharacteristicItem mSelectedCharacteristicItem = null;

	public Characteristic(String title) {
		this.setmCharacteristicName(title);
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
	public void setmSelectedCharacteristicItem(String characteristicItemString) {
		for (CharacteristicItem characteristicItem : this.mCharacteristicItemArray) {
			if (characteristicItemString.equals(characteristicItem.getmCharacteristicItemName())) {
				this.mSelectedCharacteristicItem = characteristicItem;
			}
		}
	}
	@Override
	public String toString() {
		return String.format("%s:%s", this.mCharacteristicName, this.mSelectedCharacteristicItem.getmCharacteristicItemName());
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(DSCharacteristic.COL_CHARACTERISTIC_ID, this.mCharacteristicId);
		values.put(DSCharacteristic.COL_CHARACTERISTIC_NAME, this.mCharacteristicName);
		values.put(DSCharacteristic.COL_SETTING_GROUP_ID, this.mSettingGroup.getmSettingGroupId());
		return values;
	}
}
