package sf.tools.peddlers;

import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.viewholder.activity.VHASettingGroupCargoType;
import sf.tools.peddlers.viewholder.activity.VHASettingGroupFirstFeeling;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityAddSettingGroup extends TopActivity {
	public static final String TAG = "ActivityAddSettingGroup";

	private TextView tvSettingGroupName = null;
	private Button btnBack = null;
	private VHASettingGroupFirstFeeling mVHASettingGroupFirstFeeling = null;
	private VHASettingGroupCargoType mVHASettingGroupCargoType = null;

	private SettingGroup mSettingGroup = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		this.mSettingGroup = new SettingGroup(this.getText(R.string.click_to_chage_title).toString());
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		this.setContentView(R.layout.activity_add_setting_group);

		this.tvSettingGroupName = (TextView) this.findViewById(R.id.tvSettingGroupName);
		this.setSettingGroupName(this.mSettingGroup.getmSettingGroupName());

		this.btnBack = (Button) this.findViewById(R.id.btnBack);

		this.mVHASettingGroupFirstFeeling = new VHASettingGroupFirstFeeling(this, this.mSettingGroup);
		this.mVHASettingGroupCargoType = new VHASettingGroupCargoType(this, this.mSettingGroup);
	}

	private void setSettingGroupName(String settingGroupName) {
		this.mSettingGroup.setmSettingGroupName(settingGroupName);
		this.tvSettingGroupName.setText(settingGroupName);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		super.setListener();

		this.btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityAddSettingGroup.this.finish();
			}
		});
		this.tvSettingGroupName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityAddSettingGroup.this.showInputDialog(
						ActivityAddSettingGroup.this.getString(R.string.please_input_setting_group_name),
						ActivityAddSettingGroup.this.mSettingGroup.getmSettingGroupName(),
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								ActivityAddSettingGroup.this.setSettingGroupName(inputMsg);
							}
						});
			}
		});
	}

//	private void showChangeSettingGroupNameDialog() {
//		if (this.mAlertDialogToChageName == null) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.please_input_setting_group_name);
//			if (this.getmEditText()==null) {
//				this.getmEditText() = new EditText(this);
//			}
//			this.mEditTextOfSettingGroupName.setId(Integer.MAX_VALUE);
//			builder.setView(this.mEditTextOfSettingGroupName);
//			builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					String settingGroupName = ActivityAddSettingGroup.this.mEditTextOfSettingGroupName.getText().toString();
//					ActivityAddSettingGroup.this.setSettingGroupName(settingGroupName);
//				}
//			});
//			this.mAlertDialogToChageName = builder.create();
//		}
//		this.mEditTextOfSettingGroupName.setText(this.mSettingGroup.getmSettingGroupName());
//		if (!this.mAlertDialogToChageName.isShowing()) {
//			this.mAlertDialogToChageName.show();
//		}
//	}
}
