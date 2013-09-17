package sf.tools.peddlers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityStatisticsOverView extends TopActivity {
	public static final String TAG = "ActivityStatisticsOverView";

	private Button btnCargoRankList = null;
	private Button btnCharacteristicLine = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_overview);
	    super.onCreate(savedInstanceState);

	    if (this.mApp.getSettingGroup()==null) {
			Intent intent = new Intent(this, ActivitySettingGroup.class);
			this.startActivity(intent);
			this.finish();
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAHeader.hideLeft();
		this.mVHAHeader.hideRight();
		if (this.mApp.getSettingGroup()!=null) {
			this.mVHAHeader.setTitleText(this.mApp.getSettingGroup().getmSettingGroupName());
		}
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnStatistics());

		this.btnCargoRankList = (Button) this.findViewById(R.id.btnCargoRankList);
		this.btnCharacteristicLine = (Button) this.findViewById(R.id.btnCharacteristicLine);
	}
	@Override
	protected void setListener() {
		super.setListener();

		this.btnCargoRankList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toActivity(ActivityStatisticsCargoRankList.class);
			}
		});
		this.btnCharacteristicLine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toActivity(ActivityStatisticsCharacteristicLine.class);
			}
		});
	}
}
