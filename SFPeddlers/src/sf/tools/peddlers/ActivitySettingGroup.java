package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterSettingGroup;
import sf.tools.peddlers.model.SettingGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ActivitySettingGroup extends TopActivity {
	public static final String TAG = "ActivitySettingGroup";

	private ListView lvSettingGroup = null;

	private ArrayList<SettingGroup> mSettingGroupArray = null;
	private AdapterSettingGroup mAdapterSettingGroup = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_setting_group);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		super.initData();
		this.mSettingGroupArray = new ArrayList<SettingGroup>();
		this.mSettingGroupArray.addAll(this.mApp.getmDBSettingGroup().queryAll());

		this.mAdapterSettingGroup = new AdapterSettingGroup(this, mSettingGroupArray);
	}

	@Override
	protected void initView() {
		super.initView();
		this.lvSettingGroup = (ListView) this.findViewById(R.id.lvSettingGroup);
		this.lvSettingGroup.setAdapter(mAdapterSettingGroup);

		this.mVHAHeader.setLeftText(R.string.add_setting_group);
		this.mVHAHeader.hideRight();
		this.mVHAHeader.setTitleText(R.string.setting_group);
	}

	@Override
	protected void setListener() {
		super.setListener();

		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivitySettingGroup.this, ActivityAddSettingGroup.class);
				ActivitySettingGroup.this.startActivity(intent);
			}
		});
	}
}
