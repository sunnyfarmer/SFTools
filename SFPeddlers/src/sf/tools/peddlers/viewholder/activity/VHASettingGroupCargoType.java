package sf.tools.peddlers.viewholder.activity;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import sf.tools.peddlers.BaseActivity.OnInputConfirmedListener;
import sf.tools.peddlers.ActivitySettingGroup;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.adapter.AdapterSettingGroupCargoType;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;

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
		ArrayList<CargoType> cargoTypeArray = this.mActivity.getmApp().getmDbManager().getmDBCargoType().queryAll(mSettingGroup);
		this.mSettingGroup.setmCargoTypeArray(cargoTypeArray);
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
				mActivity.showInputDialog(
						mActivity.getText(R.string.please_input_new_cargo_type_name).toString(),
						"",
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								if (inputMsg.trim().equals("")) {
									mActivity.showToast(R.string.must_be_filled);
									return;
								}
								CargoType cargoType = new CargoType(inputMsg, mSettingGroup);
								int dbRs = mActivity.getmApp().getmDbManager().upsertCargoType(cargoType);
								if (dbRs==SFGlobal.DB_MSG_OK) {
									mSettingGroup.getmCargoTypeArray().add(cargoType);
									mAdapterSettingGroupCargoType.notifyDataSetChanged();
									VHASettingGroupCargoType.this.lvCargoType.setVisibility(View.VISIBLE);
								} else if (dbRs==SFGlobal.DB_MSG_SAME_COLUMN) {
									mActivity.showToast(R.string.same_cargo_type);
								} else {
									mActivity.showToast(R.string.system_error);
								}
							}
						});
			}
		});
	}

}
