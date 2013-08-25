package sf.tools.peddlers.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1977230604481916816L;

	public static final String TAG = "ShoppingList";

	private FirstFeeling mFirstFeeling = null;
	private ArrayList<Characteristic> mCharacteristic = null;
	private ArrayList<Cargo> mLookCargo = null;
	private ArrayList<Cargo> mBuyCargo = null;
	private ArrayList<Cargo> mRelatedCargo = null;

	public FirstFeeling getmFirstFeeling() {
		return mFirstFeeling;
	}
	public void setmFirstFeeling(FirstFeeling mFirstFeeling) {
		this.mFirstFeeling = mFirstFeeling;
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
}