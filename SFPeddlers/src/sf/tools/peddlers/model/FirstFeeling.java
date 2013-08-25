package sf.tools.peddlers.model;

import java.io.Serializable;

public class FirstFeeling implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6370100111874428210L;

	public static final String TAG = "FirstFeeling";

	private String mFirstFeeling = null;

	public FirstFeeling(String firstFeeling) {
		this.setmFirstFeeling(firstFeeling);
	}

	public String getmFirstFeeling() {
		return mFirstFeeling;
	}

	public void setmFirstFeeling(String mFirstFeeling) {
		this.mFirstFeeling = mFirstFeeling;
	}
}
