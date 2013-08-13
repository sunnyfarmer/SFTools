package sf.tools.peddlers.model;

import java.util.ArrayList;

public class Characteristic {
	public static final String TAG = "Characteristic";

	private String mTitle = null;
	private ArrayList<String> mValuesArray = null;

	private String mSelectedCharacteristic = null;

	public Characteristic(String title) {
		this.setmTitle(title);
	}

	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public ArrayList<String> getmValuesArray() {
		if (this.mValuesArray == null) {
			this.mValuesArray = new ArrayList<String>();
		}
		return mValuesArray;
	}
	public void setmValuesArray(ArrayList<String> mValuesArray) {
		this.mValuesArray = mValuesArray;
	}
	public void addValue(String value) {
		this.getmValuesArray().add(value);
	}
	public String getmSelectedCharacteristic() {
		return mSelectedCharacteristic;
	}
	public void setmSelectedCharacteristic(String mSelectedCharacteristic) {
		if (-1 != this.mValuesArray.indexOf(mSelectedCharacteristic)) {
			this.mSelectedCharacteristic = mSelectedCharacteristic;
		}
	}
}
