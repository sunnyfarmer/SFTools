package sf.tools.peddlers.viewholder.activity;

import sf.tools.peddlers.BaseActivity.OnInputConfirmedListener;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.adapter.AdapterSettingGroupFirstFeeling;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.SettingGroup;
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
		this.mSettingGroup.getmFirstFeelingArray().add(new FirstFeeling("年轻女性1", this.mSettingGroup));
		this.mSettingGroup.getmFirstFeelingArray().add(new FirstFeeling("年轻女性2", this.mSettingGroup));
		this.mSettingGroup.getmFirstFeelingArray().add(new FirstFeeling("年轻女性3", this.mSettingGroup));
		this.mSettingGroup.getmFirstFeelingArray().add(new FirstFeeling("年轻女性4", this.mSettingGroup));
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
								mSettingGroup.getmFirstFeelingArray().add(new FirstFeeling(inputMsg, mSettingGroup));
								mAdapterSettingGroupFirstFeeling.notifyDataSetChanged();
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
