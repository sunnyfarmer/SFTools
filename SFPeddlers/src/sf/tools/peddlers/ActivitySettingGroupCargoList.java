package sf.tools.peddlers;

import java.util.ArrayList;
import java.util.HashMap;

import sf.tools.peddlers.adapter.AdapterSettingGroupCargo;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.activity.VHACargoType;
import sf.tools.peddlers.viewholder.activity.VHACargoType.OnCargoTypeChangedListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ActivitySettingGroupCargoList extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCargoList";

	private ListView lvCargoList = null;
	private VHACargoType mVHACargoType = null;

	private AdapterSettingGroupCargo mAdapterSettingGroupCargo = null;

	private HashMap<CargoType, ArrayList<Cargo>> mCargoHashMap = null;
	private ArrayList<CargoType> mCargoTypeArray = null;
	private CargoType mSelectedCargoType = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_setting_group_cargo_list);

	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		super.initData();
		this.mCargoTypeArray = this.mApp.getmEditingSettingGroup().getmCargoTypeArray();
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAHeader.setTitleText(R.string.cargo_list);
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.add_cargo);
		this.lvCargoList = (ListView) this.findViewById(R.id.lvCargoList);
		this.mVHACargoType = new VHACargoType(this, this.getCargoTypeArray());
	}
	@Override
	protected void setListener() {
		super.setListener();
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySettingGroupCargoList.this.finish();
			}
		});
		this.mVHAHeader.setRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivitySettingGroupCargoList.this, ActivitySettingGroupAddCargo.class);
				ActivitySettingGroupCargoList.this.startActivityForResult(intent, SFGlobal.RS_CODE_ADD_CARGO);
				ActivitySettingGroupCargoList.this.startActivity(intent);
			}
		});
		this.mVHACargoType.setmOnCargoTypeChangedListener(new OnCargoTypeChangedListener() {
			@Override
			public void onCargoTypeChanged(CargoType cargoType) {
				setmSelectedCargoType(cargoType);
			}
		});
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
		return this.mCargoTypeArray;
	}
}
