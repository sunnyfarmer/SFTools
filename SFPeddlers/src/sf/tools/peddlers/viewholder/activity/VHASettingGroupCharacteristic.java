package sf.tools.peddlers.viewholder.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.TopActivity.OnInputConfirmedListener;
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
		Characteristic characteristic1 = new Characteristic("鞋码", this.mSettingGroup);
		characteristic1.addCharacteristicItem(new CharacteristicItem("35", characteristic1));
		characteristic1.addCharacteristicItem(new CharacteristicItem("36", characteristic1));
		characteristic1.addCharacteristicItem(new CharacteristicItem("37", characteristic1));
		characteristic1.addCharacteristicItem(new CharacteristicItem("38", characteristic1));
		characteristic1.addCharacteristicItem(new CharacteristicItem("39", characteristic1));
		characteristic1.addCharacteristicItem(new CharacteristicItem("40", characteristic1));
		characteristic1.addCharacteristicItem(new CharacteristicItem("41", characteristic1));
		Characteristic characteristic2 = new Characteristic("性别", this.mSettingGroup);
		characteristic2.addCharacteristicItem(new CharacteristicItem("男", characteristic2));
		characteristic2.addCharacteristicItem(new CharacteristicItem("女", characteristic2));
		Characteristic characteristic3 = new Characteristic("口音", this.mSettingGroup);
		characteristic3.addCharacteristicItem(new CharacteristicItem("粤语", characteristic3));
		characteristic3.addCharacteristicItem(new CharacteristicItem("普通话", characteristic3));

		this.mSettingGroup.getmCharacteristicArray().add(characteristic1);
		this.mSettingGroup.getmCharacteristicArray().add(characteristic2);
		this.mSettingGroup.getmCharacteristicArray().add(characteristic3);

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
								mSettingGroup.getmCharacteristicArray().add(new Characteristic(inputMsg, mSettingGroup));
								mAdapterSettingGroupCharacteristic.notifyDataSetChanged();
							}
						});
			}
		});
	}
}
