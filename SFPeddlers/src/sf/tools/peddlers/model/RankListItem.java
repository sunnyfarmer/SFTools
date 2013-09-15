package sf.tools.peddlers.model;

public class RankListItem {
	public static final String TAG = "RankListItem";

	private Cargo mCargo = null;
	private int quantity = 0;

	public Cargo getmCargo() {
		return mCargo;
	}
	public void setmCargo(Cargo mCargo) {
		this.mCargo = mCargo;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
