package sf.tools.peddlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import sf.tools.peddlers.adapter.AdapterSettingGroupCargo;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.viewholder.activity.VHACargoType;
import sf.tools.peddlers.viewholder.activity.VHACargoType.OnCargoTypeChangedListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ActivitySettingGroupCargoList extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCargoList";

	private Button btnBack = null;
	private Button btnAddCargo = null;
	private ListView lvCargoList = null;
	private VHACargoType mVHACargoType = null;

	private AdapterSettingGroupCargo mAdapterSettingGroupCargo = null;

	private HashMap<CargoType, ArrayList<Cargo>> mCargoHashMap = null;
	private CargoType mSelectedCargoType = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_setting_group_cargo_list);

	    super.onCreate(savedInstanceState);
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
		this.btnBack = (Button) this.findViewById(R.id.btnBack);
		this.btnAddCargo = (Button) this.findViewById(R.id.btnAddCargo);
		this.lvCargoList = (ListView) this.findViewById(R.id.lvCargoList);
		this.mVHACargoType = new VHACargoType(this, this.getCargoTypeArray());
		super.initView();
	}
	@Override
	protected void setListener() {
		this.btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySettingGroupCargoList.this.finish();
			}
		});
		this.btnAddCargo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivitySettingGroupCargoList.this, ActivitySettingGroupAddCargo.class);
				ActivitySettingGroupCargoList.this.startActivity(intent);
			}
		});
		this.mVHACargoType.setmOnCargoTypeChangedListener(new OnCargoTypeChangedListener() {
			@Override
			public void onCargoTypeChanged(CargoType cargoType) {
				setmSelectedCargoType(cargoType);
			}
		});
		super.setListener();
	}

	private void refreshCargo() {
		ArrayList<Cargo> cargoList = this.mCargoHashMap.get(this.getmSelectedCargoType());
		if (this.mAdapterSettingGroupCargo==null) {
			this.mAdapterSettingGroupCargo = new AdapterSettingGroupCargo(this, cargoList);
			this.lvCargoList.setAdapter(mAdapterSettingGroupCargo);
		} else {
			this.mAdapterSettingGroupCargo.setmCargoArray(cargoList);
			this.mAdapterSettingGroupCargo.notifyDataSetChanged();
		}
	}

	public CargoType getmSelectedCargoType() {
		return mSelectedCargoType;
	}

	public void setmSelectedCargoType(CargoType mSelectedCargoType) {
		this.mSelectedCargoType = mSelectedCargoType;
		this.refreshCargo();
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

	public ArrayList<CargoType> getCargoTypeArray() {
		ArrayList<CargoType> cargoTypeArray = new ArrayList<CargoType>();

		Set<CargoType> cargoTypeSet = this.mCargoHashMap.keySet();
		for (CargoType cargoType : cargoTypeSet) {
			cargoTypeArray.add(cargoType);
		}

		return cargoTypeArray;
	}
}
