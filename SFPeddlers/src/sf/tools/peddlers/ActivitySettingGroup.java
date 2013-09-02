package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterSettingGroup;
import sf.tools.peddlers.model.SettingGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ActivitySettingGroup extends TopActivity {
	public static final String TAG = "ActivitySettingGroup";

	private ListView lvSettingGroup = null;
	private Button btnAddSettingGroup = null;

	private ArrayList<SettingGroup> mSettingGroupArray = null;
	private AdapterSettingGroup mAdapterSettingGroup = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		super.initData();
		this.mSettingGroupArray = new ArrayList<SettingGroup>();
		SettingGroup sg1 = new SettingGroup("group 1");
		SettingGroup sg2 = new SettingGroup("group 2");
		SettingGroup sg3 = new SettingGroup("group 3");
		this.mSettingGroupArray.add(sg1);
		this.mSettingGroupArray.add(sg2);
		this.mSettingGroupArray.add(sg3);

		this.mAdapterSettingGroup = new AdapterSettingGroup(this, mSettingGroupArray);
	}

	@Override
	protected void initView() {
		super.initView();
		this.setContentView(R.layout.activity_setting_group);

		this.lvSettingGroup = (ListView) this.findViewById(R.id.lvSettingGroup);
		this.lvSettingGroup.setAdapter(mAdapterSettingGroup);
		this.btnAddSettingGroup = (Button) this.findViewById(R.id.btnAddSettingGroup);
	}

	@Override
	protected void setListener() {
		super.setListener();

		this.btnAddSettingGroup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivitySettingGroup.this, ActivityAddSettingGroup.class);
				ActivitySettingGroup.this.startActivity(intent);
			}
		});
	}
}
