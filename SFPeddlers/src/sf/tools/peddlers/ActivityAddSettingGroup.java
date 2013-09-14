package sf.tools.peddlers;

import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.activity.VHASettingGroupCargoList;
import sf.tools.peddlers.viewholder.activity.VHASettingGroupCargoType;
import sf.tools.peddlers.viewholder.activity.VHASettingGroupCharacteristic;
import sf.tools.peddlers.viewholder.activity.VHASettingGroupFirstFeeling;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityAddSettingGroup extends TopActivity {
	public static final String TAG = "ActivityAddSettingGroup";

	protected VHASettingGroupFirstFeeling mVHASettingGroupFirstFeeling = null;
	protected VHASettingGroupCargoType mVHASettingGroupCargoType = null;
	protected VHASettingGroupCharacteristic mVHASettingGroupCharacteristic = null;
	protected VHASettingGroupCargoList mVHASettingGroupCargoList = null;

	private SettingGroup mSettingGroup = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_add_setting_group);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		super.initData();
		if (this.mApp.getmEditingSettingGroup()==null) {
			this.mApp.setmEditingSettingGroup(new SettingGroup(this.getText(R.string.click_to_chage_title).toString()));
		}
		this.mSettingGroup = this.mApp.getmEditingSettingGroup();
	}

	@Override
	protected void initView() {
		super.initView();
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnOrganizing());
		this.mVHAHeader.setLeftText(R.string.setting_group);
		this.mVHAHeader.hideRight();
		this.setSettingGroupName(this.mSettingGroup.getmSettingGroupName());

		this.mVHASettingGroupFirstFeeling = new VHASettingGroupFirstFeeling(this, this.mSettingGroup);
		this.mVHASettingGroupCargoType = new VHASettingGroupCargoType(this, this.mSettingGroup);
		this.mVHASettingGroupCharacteristic = new VHASettingGroupCharacteristic(this, mSettingGroup);
		this.mVHASettingGroupCargoList = new VHASettingGroupCargoList(this, mSettingGroup);
	}

	private void setSettingGroupName(String settingGroupName) {
		this.mSettingGroup.setmSettingGroupName(settingGroupName);
		this.mVHAHeader.setTitleText(settingGroupName);
	}

	@Override
	protected void setListener() {
		super.setListener();
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityAddSettingGroup.this.finish();
			}
		});
		this.mVHAHeader.getTvTitle().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityAddSettingGroup.this.showInputDialog(
						ActivityAddSettingGroup.this.getString(R.string.please_input_setting_group_name),
						ActivityAddSettingGroup.this.mSettingGroup.getmSettingGroupName(),
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								if (inputMsg.trim().equals("")) {
									ActivityAddSettingGroup.this.showToast(R.string.must_be_filled);
									return;
								}
								if (inputMsg.equals(mSettingGroup.getmSettingGroupName())) {
									return;
								}
								SettingGroup sg = new SettingGroup(inputMsg);
								sg.setmSettingGroupId(mSettingGroup.getmSettingGroupId());
								int dbRs = ActivityAddSettingGroup.this.getmApp().getmDbManager().upsertSettingGroup(sg);
								if (dbRs==SFGlobal.DB_MSG_OK) {
									ActivityAddSettingGroup.this.setSettingGroupName(inputMsg);
								} else if (dbRs==SFGlobal.DB_MSG_SAME_COLUMN) {
									ActivityAddSettingGroup.this.showToast(R.string.same_setting_group_name);
								} else {
									ActivityAddSettingGroup.this.showToast(R.string.system_error);
								}

							}
						});
			}
		});
	}
}
