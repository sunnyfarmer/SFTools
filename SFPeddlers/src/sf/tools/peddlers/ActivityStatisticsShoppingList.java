package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterStatisticsShoppingList;
import sf.tools.peddlers.model.ShoppingList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ActivityStatisticsShoppingList extends TopActivity {
	public static final String TAG = "ActivityStatisticsShoppingList";

	private ArrayList<ShoppingList> mShoppingListArray = null;

	private ListView lvShoppingList = null;
	private AdapterStatisticsShoppingList mAdapterStatisticsShoppingList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_shopping_list);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.refreshList();
	}

	@Override
	protected void initData() {
		super.initData();
		this.mShoppingListArray = this.mApp.getmDbManager().getmDBShoppingList().queryAll(this.mApp.getSettingGroup());
	}
	@Override
	protected void initView() {
		super.initView();
		this.lvShoppingList = (ListView) this.findViewById(R.id.lvShoppingList);

		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setTitleText(R.string.shopping_list);
		this.mVHAHeader.setRightText(R.string.setting);
	}
	@Override
	protected void setListener() {
		super.setListener();

		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		this.mVHAHeader.setRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO: setting
			}
		});
	}

	protected void refreshList() {
		if (this.mAdapterStatisticsShoppingList==null) {
			this.mAdapterStatisticsShoppingList = new AdapterStatisticsShoppingList(this, this.mShoppingListArray);
			this.lvShoppingList.setAdapter(mAdapterStatisticsShoppingList);
			this.lvShoppingList.setOnItemClickListener(mAdapterStatisticsShoppingList);
		} else {
			this.mAdapterStatisticsShoppingList.setmShoppingListArray(mShoppingListArray);
			this.mAdapterStatisticsShoppingList.notifyDataSetChanged();
		}
	}
}
