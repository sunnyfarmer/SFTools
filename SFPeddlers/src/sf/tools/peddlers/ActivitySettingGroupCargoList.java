package sf.tools.peddlers;

import sf.tools.peddlers.viewholder.activity.VHASettingGroupCargoType;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class ActivitySettingGroupCargoList extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCargoList";

	private Button btnBack = null;
	private Button btnAddCargo = null;
	private ListView lvCargoList = null;

	private VHASettingGroupCargoType mVHASettingGroupCargoType = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
	}
	@Override
	protected void initView() {
		super.initView();
		this.setContentView(R.layout.activity_setting_group_cargo_list);
	}
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		super.setListener();
	}
}
