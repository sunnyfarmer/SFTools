package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.viewholder.adapter.VHCustomerCharacteristic;
import sf.view.RadioButtonGroup;
import sf.view.RadioButtonGroup.OnCheckedChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterCustomerCharacteristic extends SFBaseAdapter {
	public static final String TAG = "AdapterCustomerCharacteristic";

	private ArrayList<Characteristic> mCharacteristicArray = null;

	public AdapterCustomerCharacteristic(TopActivity activity, ArrayList<Characteristic> characteristicArray) {
		super(activity);
		this.mCharacteristicArray = characteristicArray;
	}

	@Override
	public int getCount() {
		if (this.mCharacteristicArray!=null) {
			return this.mCharacteristicArray.size();
		}
		return 0;
	}

	@Override
	public Characteristic getItem(int position) {
		if (this.mCharacteristicArray!=null) {
			return this.mCharacteristicArray.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHCustomerCharacteristic vhCustomerCharacteristic = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_customer_characteristic, null);
			vhCustomerCharacteristic = new VHCustomerCharacteristic();
			vhCustomerCharacteristic.tvCharacteristic = (TextView) convertView.findViewById(R.id.tvCharacteristic);
			vhCustomerCharacteristic.rbgCharacteristic = (RadioButtonGroup) convertView.findViewById(R.id.rbgCharacteristic);

			convertView.setTag(vhCustomerCharacteristic);
		} else {
			vhCustomerCharacteristic = (VHCustomerCharacteristic) convertView.getTag();
		}

		final Characteristic characteristic = this.getItem(position);
		vhCustomerCharacteristic.tvCharacteristic.setText(characteristic.getmCharacteristicName());
		vhCustomerCharacteristic.rbgCharacteristic.setValues(characteristic.getmCharacteristicItemStringArray());
		vhCustomerCharacteristic.rbgCharacteristic.setCheckedValue(
				characteristic.getmSelectedCharacteristicItem()!=null ?
				characteristic.getmSelectedCharacteristicItem().getmCharacteristicItemName():
				null);
		vhCustomerCharacteristic.rbgCharacteristic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChange(String value) {
				characteristic.setmSelectedCharacteristicItem(value);
			}
		});
		return convertView;
	}

}
