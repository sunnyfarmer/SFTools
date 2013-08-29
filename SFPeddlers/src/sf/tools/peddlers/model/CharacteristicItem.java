package sf.tools.peddlers.model;

import java.io.Serializable;

import sf.tools.peddlers.db.DataStructure.DSCharacteristicItem;

import android.content.ContentValues;

public class CharacteristicItem implements Serializable, Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9150668579559277416L;
	public static final String TAG = "CharacteristicItem";

	private int mCharacteristicItemId = ID_UNDEFINED;
	private String mCharacteristicItemName = null;
	private Characteristic mCharacteristic = null;

	public CharacteristicItem(int id, String name, Characteristic characteristic) {
		this.setmCharacteristicItemId(id);
		this.setmCharacteristicItemName(name);
		this.setmCharacteristic(characteristic);
	}
	public CharacteristicItem(String name, Characteristic characteristic) {
		this(0, name, characteristic);
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
		values.put(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_ID, this.mCharacteristicItemId);
		values.put(DSCharacteristicItem.COL_CHARACTERISTIC_ITEM_NAME, this.mCharacteristicItemName);
		values.put(DSCharacteristicItem.COL_CHARACTERISTIC_ID, this.mCharacteristic.getmCharacteristicId());
		return values;
	}

}
