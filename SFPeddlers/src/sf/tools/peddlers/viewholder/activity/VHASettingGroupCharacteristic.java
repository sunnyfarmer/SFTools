package sf.tools.peddlers.viewholder.activity;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import sf.tools.peddlers.BaseActivity.OnInputConfirmedListener;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.adapter.AdapterSettingGroupCharacteristic;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;

public class VHASettingGroupCharacteristic {
	public static final String TAG = "VHASettingGroupCharacteristic";

	private TopActivity mActivity = null;
	private SettingGroup mSettingGroup = null;

	private LinearLayout llCharacteristic = null;
	private Button btnAddCharacteristic = null;
	private ListView lvCharacteristic = null;

	private AdapterSettingGroupCharacteristic mAdapterSettingGroupCharacteristic = null;

	public VHASettingGroupCharacteristic(TopActivity activity, SettingGroup settingGroup) {
		this.mActivity = activity;
		this.mSettingGroup = settingGroup;

		this.initData();
		this.initView();
		this.setListener();
	}

	private void initData() {
		ArrayList<Characteristic> characteristicArray = this.mActivity.getmApp().getmDBCharacteristic().queryAll(mSettingGroup);
		this.mSettingGroup.setmCharacteristicArray(characteristicArray);
		this.mAdapterSettingGroupCharacteristic = new AdapterSettingGroupCharacteristic(mActivity, this.mSettingGroup.getmCharacteristicArray());
	}
	private void initView() {
		this.llCharacteristic = (LinearLayout) this.mActivity.findViewById(R.id.llCharacteristic);
		this.btnAddCharacteristic = (Button) this.mActivity.findViewById(R.id.btnAddCharacteristic);
		this.lvCharacteristic = (ListView) this.mActivity.findViewById(R.id.lvCharacteristic);

		this.lvCharacteristic.setAdapter(this.mAdapterSettingGroupCharacteristic);
	}
	private void setListener() {
		this.llCharacteristic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int visibility = VHASettingGroupCharacteristic.this.lvCharacteristic.getVisibility();
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
				VHASettingGroupCharacteristic.this.lvCharacteristic.setVisibility(visibility);
			}
		});
		this.btnAddCharacteristic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.showInputDialog(
						mActivity.getText(R.string.please_input_new_characteristic).toString(),
						"",
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								Characteristic characteristic = new Characteristic(inputMsg, mSettingGroup);
								int dbRs = mActivity.getmApp().addCharacteristic(characteristic);
								if (dbRs==SFGlobal.DB_MSG_OK) {
									mSettingGroup.getmCharacteristicArray().add(characteristic);
									mAdapterSettingGroupCharacteristic.notifyDataSetChanged();
									VHASettingGroupCharacteristic.this.llCharacteristic.setVisibility(View.VISIBLE);
								} else if (dbRs==SFGlobal.DB_MSG_SAME_COLUMN) {
									mActivity.showToast(R.string.same_characteristic);
								} else {
									mActivity.showToast(R.string.system_error);
								}
							}
						});
			}
		});
		this.lvCharacteristic.setOnItemClickListener(mAdapterSettingGroupCharacteristic);
	}
}
