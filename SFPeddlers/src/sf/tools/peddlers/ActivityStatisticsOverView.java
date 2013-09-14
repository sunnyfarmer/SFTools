package sf.tools.peddlers;

import android.os.Bundle;

public class ActivityStatisticsOverView extends TopActivity {
	public static final String TAG = "ActivityStatisticsOverView";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_overview);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnStatistics());
	}
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		super.setListener();
	}
}
