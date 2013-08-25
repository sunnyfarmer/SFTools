package sf.tools.peddlers.model;

import java.io.Serializable;

public class Cargo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2253625273503187217L;
	public static final String TAG = "Cargo";

	public static enum CUSTOMER_BEHAVIOR {
		CB_NONE,
		CB_LOOK,
		CB_BUY
	};

	private String mCargoId = null;
	private String mCargoName = null;
	private CargoType mCargoType = null;

	private CUSTOMER_BEHAVIOR mBehavior = CUSTOMER_BEHAVIOR.CB_NONE;

	public Cargo(String cargoName, CargoType cargoType) {
		this.setmCargoName(cargoName);
		this.setmCargoType(cargoType);
	}

	public String getmCargoId() {
		return mCargoId;
	}

	public void setmCargoId(String mCargoId) {
		this.mCargoId = mCargoId;
	}

	public String getmCargoName() {
		return mCargoName;
	}
	public void setmCargoName(String mCargoName) {
		this.mCargoName = mCargoName;
	}
	public CargoType getmCargoType() {
		return mCargoType;
	}
	public void setmCargoType(CargoType mCargoType) {
		this.mCargoType = mCargoType;
	}

	public CUSTOMER_BEHAVIOR getmBehavior() {
		return mBehavior;
	}

	public void setmBehavior(CUSTOMER_BEHAVIOR mBehavior) {
		this.mBehavior = mBehavior;
	}
}
