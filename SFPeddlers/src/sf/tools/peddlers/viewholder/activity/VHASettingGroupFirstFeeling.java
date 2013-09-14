package sf.tools.peddlers.viewholder.activity;

import java.util.ArrayList;

import sf.tools.peddlers.BaseActivity.OnInputConfirmedListener;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.adapter.AdapterSettingGroupFirstFeeling;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class VHASettingGroupFirstFeeling {
	public static final String TAG = "VHSettingGroupFirstFeeling";

	private Button btnAddFirstFeeling = null;
	private ListView lvFirstFeelingConfig = null;
	private LinearLayout llFirstFeeling = null;

	private TopActivity mActivity = null;
	private AdapterSettingGroupFirstFeeling mAdapterSettingGroupFirstFeeling= null;

	private SettingGroup mSettingGroup = null;

	public VHASettingGroupFirstFeeling(TopActivity activity, SettingGroup settingGroup) {
		this.mActivity = activity;
		this.mSettingGroup = settingGroup;

		this.initData();
		this.initView();
		this.setListener();
	}

	private void initData() {
		ArrayList<FirstFeeling> firstFeelingArray = this.mActivity.getmApp().getmDbManager().getmDBFirstFeeling().queryAll(mSettingGroup);
		this.mSettingGroup.setmFirstFeelingArray(firstFeelingArray);
		this.mAdapterSettingGroupFirstFeeling = new AdapterSettingGroupFirstFeeling(this.mActivity, this.mSettingGroup.getmFirstFeelingArray());
	}

	private void initView() {
		this.llFirstFeeling = (LinearLayout) this.mActivity.findViewById(R.id.llFirstFeeling);
		this.btnAddFirstFeeling = (Button) this.mActivity.findViewById(R.id.btnAddFirstFeeling);
		this.lvFirstFeelingConfig = (ListView) this.mActivity.findViewById(R.id.lvFirstFeelingConfig);
		this.lvFirstFeelingConfig.setAdapter(mAdapterSettingGroupFirstFeeling);
	}

	private void setListener() {
		this.btnAddFirstFeeling.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				VHASettingGroupFirstFeeling.this.mActivity.showInputDialog(
						mActivity.getString(R.string.please_input_new_first_feeling),
						"",
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								if (inputMsg==null || inputMsg.equals("")) {
									mActivity.showToast(R.string.must_be_filled);
									return;
								}
								FirstFeeling firstFeeling = new FirstFeeling(inputMsg, mSettingGroup);
								int dbRs = mActivity.getmApp().getmDbManager().upsertFirstFeeling(firstFeeling);
								if (dbRs==SFGlobal.DB_MSG_OK) {
									mSettingGroup.getmFirstFeelingArray().add(firstFeeling);
									mAdapterSettingGroupFirstFeeling.notifyDataSetChanged();
									VHASettingGroupFirstFeeling.this.lvFirstFeelingConfig.setVisibility(View.VISIBLE);
								} else if (dbRs==SFGlobal.DB_MSG_SAME_COLUMN) {
									mActivity.showToast(R.string.same_first_feeling);
								} else {
									mActivity.showToast(R.string.system_error);
								}
							}
						});
			}
		});
		this.llFirstFeeling.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int visibility = VHASettingGroupFirstFeeling.this.lvFirstFeelingConfig.getVisibility();
				switch (visibility) {
				case View.VISIBLE:
					visibility = View.GONE;
					break;
				case View.INVISIBLE:
				case View.GONE:
				default:
					visibility = View.VISIBLE;
					break;
				}
				VHASettingGroupFirstFeeling.this.lvFirstFeelingConfig.setVisibility(visibility);
			}
		});
	}
}
