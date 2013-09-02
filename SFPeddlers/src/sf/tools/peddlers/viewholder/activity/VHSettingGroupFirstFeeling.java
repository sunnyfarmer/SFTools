package sf.tools.peddlers.viewholder.activity;

import sf.tools.peddlers.R;
import sf.tools.peddlers.adapter.AdapterSettingGroupFirstFeeling;
import sf.tools.peddlers.model.SettingGroup;
import android.app.Activity;
import android.widget.Button;
import android.widget.ListView;

public class VHSettingGroupFirstFeeling {
	public static final String TAG = "VHSettingGroupFirstFeeling";

	private Button btnAddFirstFeeling = null;
	private ListView lvFirstFeelingConfig = null;

	private Activity mActivity = null;
	private AdapterSettingGroupFirstFeeling mAdapterSettingGroupFirstFeeling= null;

	private SettingGroup mSettingGroup = null;

	public VHSettingGroupFirstFeeling(Activity activity, SettingGroup settingGroup) {
		this.mActivity = activity;
		this.mSettingGroup = settingGroup;

		this.initData();
	}

	private void initData() {
		
	}

	private void initView() {
		this.btnAddFirstFeeling = (Button) this.mActivity.findViewById(R.id.btnAddFirstFeeling);
		this.lvFirstFeelingConfig = (ListView) this.mActivity.findViewById(R.id.lvFirstFeelingConfig);		
	}

	private void setListener() {
		
	}
}
