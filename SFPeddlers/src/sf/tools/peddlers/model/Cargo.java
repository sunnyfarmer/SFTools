package sf.tools.peddlers.model;

public class Cargo {
	public static final String TAG = "Cargo";

	public static enum CUSTOMER_BEHAVIOR {
		CB_NONE,
		CB_LOOK,
		CB_BUY
	};

	private String mCargoName = null;
	private CargoType mCargoType = null;

	private CUSTOMER_BEHAVIOR mBehavior = CUSTOMER_BEHAVIOR.CB_NONE;

	public Cargo(String cargoName, CargoType cargoType) {
		this.setmCargoName(cargoName);
		this.setmCargoType(cargoType);
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
