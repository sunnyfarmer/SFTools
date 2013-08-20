package sf.tools.peddlers.model;

public class CargoType {
	public static final String TAG = "CargoType";

	private String mCargoTypeName = null;

	public CargoType(String cargoTypeName) {
		this.setCargoTypeName(cargoTypeName);
	}

	public String getCargoTypeName() {
		return mCargoTypeName;
	}

	public void setCargoTypeName(String cargoTypeName) {
		this.mCargoTypeName = cargoTypeName;
	}

	@Override
	public boolean equals(Object o) {
		//名字相同视为相同
		if (o instanceof CargoType) {
			CargoType type = (CargoType) o;
			if (type.getCargoTypeName()!=null && this.getCargoTypeName()!=null) {
				return type.getCargoTypeName().equals(this.getCargoTypeName());
			}
		}
		return false;
	}
}