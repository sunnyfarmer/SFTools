package sf.tools.peddlers.viewholder.activity;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;

public class VHACharacteristicItem {
	public static final String TAG = "VHACharacteristic";

	private TopActivity mActivity = null;

	private Spinner spCharacteristic = null;
	private Spinner spCharacteristicItem = null;

	private ArrayList<Characteristic> mCharacteristicArray = null;

	private ArrayAdapter<String> mAdapterCharacteristic = null;
	private ArrayAdapter<String> mAdapterCharacteristicItem = null;

	private Characteristic mSelectedCharacteristic = null;
	private CharacteristicItem mSelectedCharacteristicItem = null; 

	public VHACharacteristicItem(TopActivity activity) {
		this.mActivity = activity;
		this.initData();
		this.initView();
		this.setListener();
	}

	private void initData() {
		this.mCharacteristicArray = this.mActivity.getmApp().getSettingGroup().getmCharacteristicArray();

		this.mAdapterCharacteristic = new ArrayAdapter<String>(this.mActivity, android.R.layout.simple_spinner_item);
		this.mAdapterCharacteristic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.mAdapterCharacteristic.clear();
		for (String characteristicName : this.getCharacteristicName()) {
			this.mAdapterCharacteristic.add(characteristicName);
		}

		this.mAdapterCharacteristicItem = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item);
		this.mAdapterCharacteristicItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	private void initView() {
		this.spCharacteristic = (Spinner) this.mActivity.findViewById(R.id.spCharacteristic);
		this.spCharacteristicItem = (Spinner) this.mActivity.findViewById(R.id.spCharacteristicItem);

		this.spCharacteristic.setAdapter(mAdapterCharacteristic);
		this.spCharacteristicItem.setAdapter(mAdapterCharacteristicItem);
	}
	private void setListener() {
		this.spCharacteristic.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Characteristic characteristic = mCharacteristicArray.get(position);
				mSelectedCharacteristic = characteristic;
				mAdapterCharacteristicItem.clear();
				ArrayList<String> itemStringArray = getCharacteristicItemName(characteristic);
				for (String string : itemStringArray) {
					mAdapterCharacteristicItem.add(string);
				}
				mAdapterCharacteristicItem.notifyDataSetChanged();
			}
		});
		this.spCharacteristicItem.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectedCharacteristicItem = mSelectedCharacteristic.getmCharacteristicItemArray().get(position);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	public void show() {
		this.spCharacteristic.setVisibility(View.VISIBLE);
		this.spCharacteristicItem.setVisibility(View.VISIBLE);
	}
	public void hide() {
		this.spCharacteristic.setVisibility(View.GONE);
		this.spCharacteristicItem.setVisibility(View.GONE);
	}
	private ArrayList<String> getCharacteristicName() {
		ArrayList<String> array = new ArrayList<String>();
		for (Characteristic characteristic : this.mCharacteristicArray) {
			array.add(characteristic.getmCharacteristicName());
		}
		return array;
	}
	private ArrayList<String> getCharacteristicItemName(Characteristic characteristic) {
		return characteristic.getmCharacteristicItemStringArray();
	}
}
