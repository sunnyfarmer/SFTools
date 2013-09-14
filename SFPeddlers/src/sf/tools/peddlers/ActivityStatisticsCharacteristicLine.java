package sf.tools.peddlers;

import android.os.Bundle;

public class ActivityStatisticsCharacteristicLine extends TopActivity {
	public static final String TAG = "ActivityStatisticsCharacteristicLine";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_characteristic_line);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
	}
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnStatistics());
	}
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		super.setListener();
	}
}
