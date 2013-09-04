package sf.tools.peddlers.viewholder.activity;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.SettingGroup;

public class VHASettingGroupCargoType {
	public static final String TAG = "VHASettingGroupCargoType";

	private TopActivity mActivity = null;
	private SettingGroup mSettingGroup = null;

	private LinearLayout llCargoType = null;
	private Button btnAddCargoType = null;
	private ListView lvCargoType = null;

	public VHASettingGroupCargoType(TopActivity activity, SettingGroup settingGroup) {
		this.mActivity = activity;
		this.mSettingGroup = settingGroup;

		this.initData();
		this.initView();
		this.setListener();
	}

	private void initData() {
		this.mSettingGroup.setmCargoTypeArray(new ArrayList<CargoType>());
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋1", this.mSettingGroup));
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋2", this.mSettingGroup));
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋3", this.mSettingGroup));
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋4", this.mSettingGroup));
		this.mSettingGroup.getmCargoTypeArray().add(new CargoType("拖鞋5", this.mSettingGroup));
	}
	private void initView() {
		this.llCargoType = (LinearLayout) this.mActivity.findViewById(R.id.llCargoType);
		this.btnAddCargoType = (Button) this.mActivity.findViewById(R.id.btnAddCargoType);
		this.lvCargoType = (ListView) this.mActivity.findViewById(R.id.lvCargoType);
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
	}

}
