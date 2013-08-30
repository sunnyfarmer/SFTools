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

public class ActivityCustomerCharacteristic extends TopActivity {
	public static final String TAG = "ActivityCustomerCharacteristic";

	private ArrayList<Characteristic> mCharacteristicArray = new ArrayList<Characteristic>();
	private AdapterCustomerCharacteristic mAdapterCustomerCharacteristic = null;

	private Button btnBack = null;
	private Button btnBeginShopping = null;
	private Button btnInSelling = null;
	private Button btnStatistics = null;
	private Button btnOrganizing = null;
	private ListView lvCustomerCharacteristic = null;

	private ShoppingList mShoppingList = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = this.getIntent();
		this.mShoppingList = (ShoppingList) intent.getSerializableExtra(SFGlobal.EXTRA_SHOPPINGLIST);

		this.parseShoppingList(this.mShoppingList);
	}

	@Override
	protected void initData() {
		super.initData();
		this.mCharacteristicArray.clear();
		Characteristic sizeCharacter = new Characteristic("鞋码", null);
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("35", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("36", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("37", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("38", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("39", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("40", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("41", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("42", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("43", sizeCharacter));
		sizeCharacter.addCharacteristicItem(new CharacteristicItem("44", sizeCharacter));
		Characteristic voiceCharacter = new Characteristic("口音", null);
		voiceCharacter.addCharacteristicItem(new CharacteristicItem("粤语", voiceCharacter));
		voiceCharacter.addCharacteristicItem(new CharacteristicItem("普通话", voiceCharacter));
		this.mCharacteristicArray.add(sizeCharacter);
		this.mCharacteristicArray.add(voiceCharacter);

		this.mAdapterCustomerCharacteristic = new AdapterCustomerCharacteristic(this, this.mCharacteristicArray);
	}
	@Override
	protected void initView() {
		super.initView();
		this.setContentView(R.layout.activity_customer_characteristic);

		this.lvCustomerCharacteristic = (ListView) this.findViewById(R.id.lvCustomerCharacteristic);
		this.lvCustomerCharacteristic.setAdapter(this.mAdapterCustomerCharacteristic);

		this.btnBack = (Button) this.findViewById(R.id.btnBack);
		this.btnBeginShopping = (Button) this.findViewById(R.id.btnBeginShopping);
		this.btnInSelling = (Button) this.findViewById(R.id.btnInSelling);
		this.btnStatistics = (Button) this.findViewById(R.id.btnStatistics);
		this.btnOrganizing = (Button) this.findViewById(R.id.btnOrganizing);
	}
	@Override
	protected void setListener() {
		super.setListener();

		this.btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityCustomerCharacteristic.this.finish();
			}
		});
		this.btnBeginShopping.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (Characteristic chara : ActivityCustomerCharacteristic.this.mCharacteristicArray) {
					SFLog.d(TAG, chara.toString());
				}
				Intent intent = new Intent(ActivityCustomerCharacteristic.this, ActivityShopping.class);
				ActivityCustomerCharacteristic.this.mShoppingList.setmCharacteristic(mCharacteristicArray);
				intent.putExtra(SFGlobal.EXTRA_SHOPPINGLIST, ActivityCustomerCharacteristic.this.mShoppingList);
				ActivityCustomerCharacteristic.this.startActivity(intent);
			}
		});
		this.btnInSelling.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		this.btnStatistics.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		this.btnOrganizing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void parseShoppingList(ShoppingList shoppingList) {
		if (this.mShoppingList==null) {
			Intent backIntent = new Intent(this, ActivityFirstFeeling.class);
			this.startActivity(backIntent);
			this.finish();
		} else {
			if (shoppingList.getmCharacteristic()!=null) {
				this.mCharacteristicArray = shoppingList.getmCharacteristic();
			}
			Toast.makeText(this, "FirstFeeling: " + this.mShoppingList.getmFirstFeeling().getmFirstFeelingName(), Toast.LENGTH_SHORT)
			.show();
			SFLog.d(TAG, "FirstFeeling: " + this.mShoppingList.getmFirstFeeling().getmFirstFeelingName());
		}
	}
}
