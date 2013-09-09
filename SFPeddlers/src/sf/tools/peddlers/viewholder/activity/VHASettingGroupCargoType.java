package sf.tools.peddlers.viewholder.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import sf.tools.peddlers.BaseActivity.OnInputConfirmedListener;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.adapter.AdapterSettingGroupCargoType;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.SettingGroup;

public class VHASettingGroupCargoType {
	public static final String TAG = "VHASettingGroupCargoType";

	private TopActivity mActivity = null;
	private SettingGroup mSettingGroup = null;

	private LinearLayout llCargoType = null;
	private Button btnAddCargoType = null;
	private ListView lvCargoType = null;

	private AdapterSettingGroupCargoType mAdapterSettingGroupCargoType = null;

	public VHASettingGroupCargoType(TopActivity activity, SettingGroup settingGroup) {
		this.mActivity = activity;
		this.mSettingGroup = settingGroup;

		this.initData();
		this.initView();
		this.setListener();
	}

	private void initData() {
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋1", this.mSettingGroup));
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋2", this.mSettingGroup));
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋3", this.mSettingGroup));
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋4", this.mSettingGroup));
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋5", this.mSettingGroup));

		this.mAdapterSettingGroupCargoType = new AdapterSettingGroupCargoType(this.mActivity, this.mSettingGroup.getmCargoTypeArray());
	}
	private void initView() {
		this.llCargoType = (LinearLayout) this.mActivity.findViewById(R.id.llCargoType);
		this.btnAddCargoType = (Button) this.mActivity.findViewById(R.id.btnAddCargoType);
		this.lvCargoType = (ListView) this.mActivity.findViewById(R.id.lvCargoType);
		this.lvCargoType.setAdapter(this.mAdapterSettingGroupCargoType);
	}
	private void setListener() {
		this.llCargoType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int visibility = VHASettingGroupCargoType.this.lvCargoType.getVisibility();
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
				VHASettingGroupCargoType.this.lvCargoType.setVisibility(visibility);
			}
		});
		this.btnAddCargoType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.showInputDialog(
						mActivity.getText(R.string.please_input_new_cargo_type_name).toString(),
						"",
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								// TODO Auto-generated method stub
								mSettingGroup.getmCargoTypeArray().add(new CargoType(inputMsg, mSettingGroup));
								mAdapterSettingGroupCargoType.notifyDataSetChanged();
							}
						});
			}
		});
	}

}
