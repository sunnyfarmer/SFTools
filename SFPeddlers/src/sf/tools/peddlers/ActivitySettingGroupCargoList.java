package sf.tools.peddlers;

import java.util.ArrayList;
import sf.tools.peddlers.adapter.AdapterSettingGroupCargo;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.activity.VHACargoType;
import sf.tools.peddlers.viewholder.activity.VHACargoType.OnCargoTypeChangedListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ActivitySettingGroupCargoList extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCargoList";

	private ListView lvCargoList = null;
	private VHACargoType mVHACargoType = null;

	private AdapterSettingGroupCargo mAdapterSettingGroupCargo = null;

	private SparseArray<ArrayList<Cargo>> mCargoHashMap = new SparseArray<ArrayList<Cargo>>();
	private ArrayList<CargoType> mCargoTypeArray = null;
	private CargoType mSelectedCargoType = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_setting_group_cargo_list);

	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==SFGlobal.RS_CODE_ADD_CARGO && resultCode==RESULT_OK) {
			Cargo cargo = this.mApp.getmEdittingCargo();
			this.putCargo(cargo);
			this.refreshCargo();
		} else if (requestCode==SFGlobal.RS_CODE_EDIT_CARGO && resultCode==RESULT_OK) {
			Cargo cargo = this.mApp.getmEdittingCargo();
			this.replaceCargo(cargo);
			this.refreshCargo();
		}
	}

	@Override
	protected void initData() {
		super.initData();
		this.mCargoTypeArray = this.mApp.getmEditingSettingGroup().getmCargoTypeArray();
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnOrganizing());
		this.mVHAHeader.setTitleText(R.string.cargo_list);
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.add_cargo);
		this.lvCargoList = (ListView) this.findViewById(R.id.lvCargoList);
		this.mVHACargoType = new VHACargoType(this, this.getCargoTypeArray());

		if (this.mCargoTypeArray!=null && this.mCargoTypeArray.size()>0) {
			this.setmSelectedCargoType(this.mCargoTypeArray.get(0));
			this.mVHACargoType.checkCargoType(this.getmSelectedCargoType());
		}
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
				intent.putExtra(SFGlobal.EXTRA_CARGOTYPE_ID, ActivitySettingGroupCargoList.this.getmSelectedCargoType().getmCargoTypeId());
				ActivitySettingGroupCargoList.this.startActivityForResult(intent, SFGlobal.RS_CODE_ADD_CARGO);
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
		ArrayList<Cargo> cargoList = this.mCargoHashMap.get(this.getmSelectedCargoType().getmCargoTypeId());
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
		this.initCargoList(this.mSelectedCargoType, false);
		this.refreshCargo();
	}

	public void initCargoList(CargoType cargoType, boolean forceReadFromDB) {
		ArrayList<Cargo> cargoList = this.mCargoHashMap.get(cargoType.getmCargoTypeId());
		if (forceReadFromDB || cargoList==null) {
			cargoList = this.mApp.getmDbManager().getmDBCargo().queryAll(cargoType);
			this.mCargoHashMap.put(cargoType.getmCargoTypeId(), cargoList);
		}
	}
	public void putCargo(Cargo cargo) {
		if (this.mCargoHashMap==null) {
			this.mCargoHashMap = new SparseArray<ArrayList<Cargo>>();
		}
		if (null == this.mCargoHashMap.get(cargo.getmCargoType().getmCargoTypeId())) {
			this.mCargoHashMap.put(cargo.getmCargoType().getmCargoTypeId(), new ArrayList<Cargo>());
		}
		this.mCargoHashMap.get(cargo.getmCargoType().getmCargoTypeId()).add(cargo);
	}

	public void replaceCargo(Cargo cargo) {
		if (this.mCargoHashMap==null) {
			this.mCargoHashMap = new SparseArray<ArrayList<Cargo>>();
		}
		if (null == this.mCargoHashMap.get(cargo.getmCargoType().getmCargoTypeId())) {
			this.mCargoHashMap.put(cargo.getmCargoType().getmCargoTypeId(), new ArrayList<Cargo>());
		}
		ArrayList<Cargo> cargoList = this.mCargoHashMap.get(cargo.getmCargoType().getmCargoTypeId());
		for (Cargo cargoInList : cargoList) {
			if (cargoInList.getmCargoId()==cargo.getmCargoId()) {
				cargoInList.setmCargoName(cargo.getmCargoName());
				break;
			}
		}
	}

	public ArrayList<CargoType> getCargoTypeArray() {
		return this.mCargoTypeArray;
	}
}
