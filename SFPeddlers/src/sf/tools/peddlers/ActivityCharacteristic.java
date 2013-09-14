package sf.tools.peddlers;

import java.util.ArrayList;

import sf.log.SFLog;
import sf.tools.peddlers.adapter.AdapterCustomerCharacteristic;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.ShoppingList;
import sf.tools.peddlers.utils.SFGlobal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityCharacteristic extends TopActivity {
	public static final String TAG = "ActivityCustomerCharacteristic";

	private ArrayList<Characteristic> mCharacteristicArray = new ArrayList<Characteristic>();
	private AdapterCustomerCharacteristic mAdapterCustomerCharacteristic = null;
	private ListView lvCharacteristic = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_customer_characteristic);
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
		this.refreshCharacteristic();
		showToast(this.mApp.getmShoppingList().getmFirstFeeling().getmFirstFeelingName());
	}

	@Override
	protected void initData() {
		super.initData();
	}
	@Override
	protected void initView() {
		super.initView();
		this.lvCharacteristic = (ListView) this.findViewById(R.id.lvCharacteristic);

		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.shopping_begin);
		this.mVHAHeader.setTitleText(this.mApp.getSettingGroup().getmSettingGroupName());
	}
	@Override
	protected void setListener() {
		super.setListener();
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityCharacteristic.this.finish();
			}
		});
		this.mVHAHeader.setRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (Characteristic chara : ActivityCharacteristic.this.mCharacteristicArray) {
					SFLog.d(TAG, chara.toString());
				}
				ActivityCharacteristic.this.mApp.getmShoppingList().setmCharacteristic(mCharacteristicArray);
				Intent intent = new Intent(ActivityCharacteristic.this, ActivityShopping.class);
				ActivityCharacteristic.this.startActivity(intent);
			}
		});
	}
	protected void refreshCharacteristic() {
		this.mCharacteristicArray = this.mApp.getSettingGroup().getmCharacteristicArray();
		if (this.mAdapterCustomerCharacteristic==null) {
			this.mAdapterCustomerCharacteristic = new AdapterCustomerCharacteristic(this, mCharacteristicArray);
		} else {
			this.mAdapterCustomerCharacteristic.setmCharacteristic(mCharacteristicArray);
			this.mAdapterCustomerCharacteristic.notifyDataSetChanged();
		}
		if (this.lvCharacteristic.getAdapter()==null ||
				!this.lvCharacteristic.getAdapter().equals(this.mAdapterCustomerCharacteristic)) {
			this.lvCharacteristic.setAdapter(mAdapterCustomerCharacteristic);
		}
	}
}
