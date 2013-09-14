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
import sf.tools.peddlers.viewholder.activity.VHACargoType;
import sf.tools.peddlers.viewholder.activity.VHACargoType.OnCargoTypeChangedListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ActivityShopping extends TopActivity {
	public static final String TAG = "ActivityShopping";

	private HashMap<CargoType, ArrayList<Cargo>> mCargoHashMap = null;
	private CargoType mSelectedCargoType = null;

	private ListView lvCargo = null;
	private Button btnFinishShopping = null;
	private VHACargoType mVHACargoType = null;

	private AdapterShoppingCargo mAdapterShoppingCargo = null;

	private ShoppingList mShoppingList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_shopping);

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
		CargoType[] cargoTypeArray = {
				new CargoType("连衣裙", null),
				new CargoType("短裙", null),
				new CargoType("女体恤", null),
				new CargoType("牛仔裤", null),
				new CargoType("热裤", null)
		};
		for (CargoType cargoType : cargoTypeArray) {
			for (int cot = 0; cot < 10; cot++) {
				String cargoName = cargoType.getmCargoTypeName() + cot;
				Cargo cargo = new Cargo(cargoName, cargoType);
				this.putCargo(cargo);
			}
		}
		super.initData();
	}
	@Override
	protected void initView() {
		this.lvCargo = (ListView) this.findViewById(R.id.lvCargo);

		this.mVHACargoType = new VHACargoType(this, this.getCargoTypeArray());

		this.btnFinishShopping = (Button) this.findViewById(R.id.btnFinishShopping);
		super.initView();
	}
	@Override
	protected void setListener() {
		this.mVHACargoType.setmOnCargoTypeChangedListener(new OnCargoTypeChangedListener() {
			@Override
			public void onCargoTypeChanged(CargoType cargoType) {
				ActivityShopping.this.setmSelectedCargoType(cargoType);
			}
		});
		this.btnFinishShopping.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityShopping.this.finishShoppingList();
				//TODO: 入database
			}
		});
		super.setListener();
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

	private void refreshCargo() {
		ArrayList<Cargo> cargoList = this.mCargoHashMap.get(this.getmSelectedCargoType());

		if (this.mAdapterShoppingCargo==null) {
			this.mAdapterShoppingCargo = new AdapterShoppingCargo(this, cargoList);
			this.lvCargo.setAdapter(this.mAdapterShoppingCargo);
		} else {
			this.mAdapterShoppingCargo.setmCargoList(cargoList);
			this.mAdapterShoppingCargo.notifyDataSetChanged();
		}
	}

	public ArrayList<CargoType> getCargoTypeArray() {
		ArrayList<CargoType> cargoTypeArray = new ArrayList<CargoType>();

		Set<CargoType> cargoTypeSet = this.mCargoHashMap.keySet();
		for (CargoType cargoType : cargoTypeSet) {
			cargoTypeArray.add(cargoType);
		}

		return cargoTypeArray;
	}

	public CargoType getmSelectedCargoType() {
		return mSelectedCargoType;
	}

	public void setmSelectedCargoType(CargoType mSelectedCargoType) {
		this.mSelectedCargoType = mSelectedCargoType;
		this.refreshCargo();
	}

	private void parseShoppingList(ShoppingList shoppingList) {
		if (shoppingList==null || shoppingList.getmFirstFeeling()==null) {
			Intent firstFeelingIntent = new Intent(this, ActivityFirstFeeling.class);
			this.startActivity(firstFeelingIntent);
			this.finish();
		} else if (shoppingList.getmCharacteristic()==null){
			Intent characteristicIntent = new Intent(this, ActivityCharacteristic.class);
			characteristicIntent.putExtra(SFGlobal.EXTRA_SHOPPINGLIST, shoppingList);
			this.startActivity(characteristicIntent);
			this.finish();
		}

		SFLog.d(TAG, "FirstFeeling: "+shoppingList.getmFirstFeeling().getmFirstFeelingName());
		for (Characteristic characteristic : shoppingList.getmCharacteristic()) {
			SFLog.d(TAG,
					"Chara: " +
					characteristic.getmCharacteristicName()+","+
					characteristic.getmSelectedCharacteristicItem());
		}
	}

	private void finishShoppingList() {
		ArrayList<Cargo> lookCargoArray = new ArrayList<Cargo>();
		ArrayList<Cargo> buyCargoArray = new ArrayList<Cargo>();
		ArrayList<Cargo> relatedCargoArray = new ArrayList<Cargo>();

		for (CargoType cargoType : this.mCargoHashMap.keySet()) {
			ArrayList<Cargo> cargoArray = this.mCargoHashMap.get(cargoType);
			for (Cargo cargo : cargoArray) {
				switch (cargo.getmBehavior()) {
				case CB_NONE:
					break;
				case CB_LOOK:
					lookCargoArray.add(cargo);
					relatedCargoArray.add(cargo);
					break;
				case CB_BUY:
					lookCargoArray.add(cargo);
					buyCargoArray.add(cargo);
					relatedCargoArray.add(cargo);
					break;
				default:
					break;
				}
			}
		}
		this.mShoppingList.setmLookCargo(lookCargoArray);
		this.mShoppingList.setmBuyCargo(buyCargoArray);
		this.mShoppingList.setmRelatedCargo(relatedCargoArray);
	}
}
