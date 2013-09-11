package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterSettingGroup;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;

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
	protected void onStart() {
		super.onStart();

		this.refreshSettingGroupArray();
	}

	@Override
	protected void initData() {
		super.initData();
		this.refreshSettingGroupArray();
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
				ActivitySettingGroup.this.showInputDialog(
						getText(R.string.please_input_setting_group_name).toString(),
						"",
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								if (inputMsg.trim().equals("")) {
									ActivitySettingGroup.this.showToast(R.string.must_be_filled);
									return;
								}
								SettingGroup sg = new SettingGroup(inputMsg);
								int msg = mApp.getmDBSettingGroup().upsert(sg);
								if (msg==SFGlobal.DB_MSG_OK) {
									Intent intent = new Intent(ActivitySettingGroup.this, ActivityAddSettingGroup.class);
									mApp.setmEditingSettingGroup(sg);
									ActivitySettingGroup.this.startActivity(intent);
								} else {
									ActivitySettingGroup.this.showToast(R.string.same_setting_group_name);
								}
							}
						});
			}
		});
	}

	public void refreshSettingGroupArray() {
		this.mSettingGroupArray = this.mApp.getmDBSettingGroup().queryAll();
		if (this.mAdapterSettingGroup==null) {
			this.mAdapterSettingGroup = new AdapterSettingGroup(this, mSettingGroupArray);
		} else {
			this.mAdapterSettingGroup.setmSettingGroupArray(mSettingGroupArray);
			this.mAdapterSettingGroup.notifyDataSetChanged();
		}
	}
}
