package sf.tools.peddlers.model;

public class Cargo {
	public static final String TAG = "Cargo";

	private String mCargoName = null;
	private CargoType mCargoType = null;

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
}
