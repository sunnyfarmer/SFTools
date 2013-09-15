package sf.tools.peddlers.viewholder.activity;

import java.util.ArrayList;

import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.adapter.AdapterStatisticsCargo;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Characteristic;

public class VHACargoNCharacteristic {
	public static final String TAG = "VHACargoNCharacteristic";

	private TopActivity mActivity = null;

	private Spinner spCargoType = null;
	private Spinner spCargo = null;
	private Spinner spCharacteristic = null;

	private ArrayAdapter<String> mAdapterCargoType = null;
	private AdapterStatisticsCargo mAdapterStatisticsCargo = null;
	private ArrayAdapter<String> mAdapterCharacteristic = null;

	private ArrayList<CargoType> mCargoTypeArray = null;
	private SparseArray<ArrayList<Cargo>> mCargoArray = null;
	private ArrayList<Characteristic> mCharacteristicArray = null;

	private CargoType mSelectedCargoType = null;
	private Cargo mSelectedCargo = null;
	private Characteristic mSelectedCharacteristic = null;

	public VHACargoNCharacteristic(TopActivity activity) {
		this.mActivity = activity;
		this.initData();
		this.initView();
		this.setListener();
	}
	private void initData() {
		this.mCharacteristicArray = this.mActivity.getmApp().getSettingGroup().getmCharacteristicArray();
		this.mCargoTypeArray = this.mActivity.getmApp().getSettingGroup().getmCargoTypeArray();
		this.mCargoArray = this.mActivity.getmApp().getSettingGroup().getmCargoArray();

		this.mAdapterCargoType = new ArrayAdapter<String>(this.mActivity, android.R.layout.simple_spinner_item);
		this.mAdapterCargoType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.mAdapterCargoType.clear();
		for(String string : this.getCargoTypeName()) {
			this.mAdapterCargoType.add(string);
		}

		this.mAdapterStatisticsCargo = new AdapterStatisticsCargo(this.mActivity, null);

		this.mAdapterCharacteristic = new ArrayAdapter<String>(this.mActivity, android.R.layout.simple_spinner_item);
		this.mAdapterCharacteristic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.mAdapterCharacteristic.clear();
		for(String string : this.getCharacteristicName()) {
			this.mAdapterCharacteristic.add(string);
		}
	}
	private void initView() {
		this.spCargoType = (Spinner) this.mActivity.findViewById(R.id.spCargoType);
		this.spCargo = (Spinner) this.mActivity.findViewById(R.id.spCargo);
		this.spCharacteristic = (Spinner) this.mActivity.findViewById(R.id.spCharacteristic);

		this.spCargoType.setAdapter(mAdapterCargoType);
		this.spCargo.setAdapter(this.mAdapterStatisticsCargo);
		this.spCharacteristic.setAdapter(mAdapterCharacteristic);
	}
	private void setListener() {
		this.spCargoType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CargoType cargoType = mCargoTypeArray.get(position);
				mSelectedCargoType = cargoType;
				ArrayList<Cargo> cargoList = mCargoArray.get(cargoType.getmCargoTypeId());
				mAdapterStatisticsCargo.setmCargoArray(cargoList);
				mAdapterStatisticsCargo.notifyDataSetChanged();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		this.spCargo.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				ArrayList<Cargo> cargoList = mCargoArray.get(mSelectedCargoType.getmCargoTypeId());
				Cargo cargo = cargoList.get(position);
				mSelectedCargo = cargo;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		this.spCharacteristic.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectedCharacteristic = mCharacteristicArray.get(position);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	public void show() {
		this.spCargoType.setVisibility(View.VISIBLE);
		this.spCargo.setVisibility(View.VISIBLE);
		this.spCharacteristic.setVisibility(View.VISIBLE);
	}
	public void hide() {
		this.spCargoType.setVisibility(View.GONE);
		this.spCargo.setVisibility(View.GONE);
		this.spCharacteristic.setVisibility(View.GONE);		
	}
	private ArrayList<String> getCharacteristicName() {
		ArrayList<String> array = new ArrayList<String>();
		for (Characteristic characteristic : this.mCharacteristicArray) {
			array.add(characteristic.getmCharacteristicName());
		}
		return array;
	}
	private ArrayList<String> getCargoTypeName() {
		ArrayList<String> array = new ArrayList<String>();
		for (CargoType cargoType : this.mCargoTypeArray) {
			array.add(cargoType.getmCargoTypeName());
		}
		return array;
	}
	public CargoType getmSelectedCargoType() {
		return mSelectedCargoType;
	}
	public Cargo getmSelectedCargo() {
		return mSelectedCargo;
	}
	public Characteristic getmSelectedCharacteristic() {
		return mSelectedCharacteristic;
	}
}
