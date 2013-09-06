package sf.tools.peddlers.viewholder.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import sf.tools.peddlers.ActivitySettingGroupCargoList;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.SettingGroup;

public class VHASettingGroupCargoList {
	public static final String TAG = "VHASettingGroupCargoList";

	private TopActivity mActivity = null;
	private SettingGroup mSettingGroup = null;

	private LinearLayout llCargoList = null;

	public VHASettingGroupCargoList(TopActivity activity, SettingGroup settingGroup) {
		this.mActivity = activity;
		this.mSettingGroup = settingGroup;

		this.initData();
		this.initView();
		this.setListener();
	}

	private void initData() {
		
	}
	private void initView() {
		this.llCargoList = (LinearLayout) this.mActivity.findViewById(R.id.llCargoList);
	}
	private void setListener() {
		this.llCargoList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, ActivitySettingGroupCargoList.class);
				mActivity.startActivity(intent);
			}
		});
	}
}
