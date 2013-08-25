package sf.tools.peddlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import sf.log.SFLog;
import sf.tools.peddlers.adapter.AdapterShoppingCargo;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.ShoppingList;
import sf.tools.peddlers.utils.SFGlobal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ActivityShopping extends TopActivity {
	public static final String TAG = "ActivityShopping";

	private HashMap<CargoType, ArrayList<Cargo>> mCargoHashMap = null;
	private CargoType mSelectedCargo = null;

	private RadioGroup rgCargoType = null;
	private ListView lvCargo = null;

	private AdapterShoppingCargo mAdapterShoppingCargo = null;

	private ShoppingList mShoppingList = null;

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

		CargoType[] cargoTypeArray = {
				new CargoType("连衣裙"),
				new CargoType("短裙"),
				new CargoType("女体恤"),
				new CargoType("牛仔裤"),
				new CargoType("热裤")
		};
		for (CargoType cargoType : cargoTypeArray) {
			for (int cot = 0; cot < 10; cot++) {
				String cargoName = cargoType.getCargoTypeName() + cot;
				Cargo cargo = new Cargo(cargoName, cargoType);
				this.putCargo(cargo);
			}
		}
	}
	@Override
	protected void initView() {
		super.initView();
		this.setContentView(R.layout.activity_shopping);

		this.lvCargo = (ListView) this.findViewById(R.id.lvCargo);

		this.rgCargoType = (RadioGroup) this.findViewById(R.id.rgCargoType);
		this.refreshCargoType();
	}
	@Override
	protected void setListener() {
		super.setListener();
		this.rgCargoType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton)group.findViewById(checkedId);
				CargoType cargoType = (CargoType) rb.getTag();
				ActivityShopping.this.setmSelectedCargo(cargoType);
			}
		});
	}

	public void putCargo(Cargo cargo) {
		if (this.mCargoHashMap==null) {
			this.mCargoHashMap = new HashMap<CargoType, ArrayList<Cargo>>();
		}
		if (!this.mCargoHashMap.containsKey(cargo.getmCargoType())) {
			this.mCargoHashMap.put(cargo.getmCargoType(), new ArrayList<Cargo>());
		}
		this.mCargoHashMap.get(cargo.getmCargoType()).add(cargo);
	}

	private void refreshCargoType() {
		this.rgCargoType.removeAllViews();

		Set<CargoType> cargoTypeSet = this.mCargoHashMap.keySet();
		for (CargoType cargoType : cargoTypeSet) {
			RadioButton rb = new RadioButton(this);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			rb.setLayoutParams(params);
			rb.setGravity(Gravity.CENTER);
			rb.setBackgroundResource(R.drawable.tab_checkbox_selector);
			rb.setButtonDrawable(android.R.color.transparent);
			rb.setText(cargoType.getCargoTypeName());
			rb.setTag(cargoType);

			this.rgCargoType.addView(rb);
		}
	}
	private void refreshCargo() {
		ArrayList<Cargo> cargoList = this.mCargoHashMap.get(this.getmSelectedCargo());

		if (this.mAdapterShoppingCargo==null) {
			this.mAdapterShoppingCargo = new AdapterShoppingCargo(this, cargoList);
			this.lvCargo.setAdapter(this.mAdapterShoppingCargo);
		} else {
			this.mAdapterShoppingCargo.setmCargoList(cargoList);
			this.mAdapterShoppingCargo.notifyDataSetChanged();
		}
	}

	public CargoType getmSelectedCargo() {
		return mSelectedCargo;
	}

	public void setmSelectedCargo(CargoType mSelectedCargo) {
		this.mSelectedCargo = mSelectedCargo;
		this.refreshCargo();
	}

	private void parseShoppingList(ShoppingList shoppingList) {
		if (shoppingList==null || shoppingList.getmFirstFeeling()==null) {
			Intent firstFeelingIntent = new Intent(this, ActivityFirstFeeling.class);
			this.startActivity(firstFeelingIntent);
			this.finish();
		} else if (shoppingList.getmCharacteristic()==null){
			Intent characteristicIntent = new Intent(this, ActivityCustomerCharacteristic.class);
			characteristicIntent.putExtra(SFGlobal.EXTRA_SHOPPINGLIST, shoppingList);
			this.startActivity(characteristicIntent);
			this.finish();
		}

		SFLog.d(TAG, "FirstFeeling: "+shoppingList.getmFirstFeeling().getmFirstFeeling());
		for (Characteristic characteristic : shoppingList.getmCharacteristic()) {
			SFLog.d(TAG, "Chara: " + characteristic.getmTitle()+","+characteristic.getmSelectedCharacteristic());
		}
	}
}
