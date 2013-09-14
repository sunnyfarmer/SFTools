package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterFirstFeeling;
import sf.tools.peddlers.model.FirstFeeling;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class ActivityFirstFeeling extends TopActivity {
	public static final String TAG = "FirstFeelingActivity";

	private ListView lvFirstFeeling = null;

	private AdapterFirstFeeling mFirstFeelingAdapter = null;
	private ArrayList<FirstFeeling> mFirstFeelingArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_first_feeling);
		super.onCreate(savedInstanceState);

		if (this.mApp.getSettingGroup()==null) {
			Intent intent = new Intent(this, ActivitySettingGroup.class);
			this.startActivity(intent);
			this.finish();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		this.refreshFirstFeeling();
		this.mVHAHeader.setTitleText(this.mApp.getSettingGroup().getmSettingGroupName());
	}

	@Override
	protected void initData() {
		super.initData();
	}

	@Override
	protected void initView() {
		super.initView();

		this.lvFirstFeeling = (ListView) this.findViewById(R.id.lvFirstFeeling);

		if (this.mApp.getSettingGroup()!=null) {
			this.mVHAHeader.setTitleText(this.mApp.getSettingGroup().getmSettingGroupName());
		}
		this.mVHAHeader.hideLeft();
		this.mVHAHeader.hideRight();
	}

	@Override
	protected void setListener() {
		super.setListener();
		this.lvFirstFeeling.setOnItemClickListener(this.mFirstFeelingAdapter);
	}

	protected void refreshFirstFeeling() {
		this.mFirstFeelingArray = this.mApp.getSettingGroup().getmFirstFeelingArray();
		if (this.mFirstFeelingAdapter==null) {
			this.mFirstFeelingAdapter = new AdapterFirstFeeling(this, this.mFirstFeelingArray);
		} else {
			this.mFirstFeelingAdapter.setmFirstFeelingArray(mFirstFeelingArray);
			this.mFirstFeelingAdapter.notifyDataSetChanged();
		}
		if (this.lvFirstFeeling.getAdapter()==null || !this.lvFirstFeeling.getAdapter().equals(this.mFirstFeelingAdapter)) {
			this.lvFirstFeeling.setAdapter(mFirstFeelingAdapter);
		}
	}
}
