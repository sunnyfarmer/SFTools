package sf.tools.peddlers.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import sf.tools.peddlers.ActivitySettingGroupCharacteristicItem;
import sf.tools.peddlers.R;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.adapter.VHSettingGroupCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AdapterSettingGroupCharacteristic extends BaseAdapter implements
		OnItemClickListener {
	public static final String TAG = "AdapterSettingGroupCharacteristic";

	private Context mContext = null;
	private ArrayList<Characteristic> mCharacteristicArray = null;

	public AdapterSettingGroupCharacteristic(Context context, ArrayList<Characteristic> characteristicArray) {
		this.mContext = context;
		this.mCharacteristicArray = characteristicArray;
	}
	
	@Override
	public int getCount() {
		return this.mCharacteristicArray.size();
	}

	@Override
	public Characteristic getItem(int position) {
		return this.mCharacteristicArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHSettingGroupCharacteristic vhSettingGroupCharacteristic = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_setting_group_characteristic, null);
			vhSettingGroupCharacteristic = new VHSettingGroupCharacteristic();
			vhSettingGroupCharacteristic.tvCharacteristicName = (TextView) convertView.findViewById(R.id.tvCharacteristicName);
			vhSettingGroupCharacteristic.btnUnfold = (Button) convertView.findViewById(R.id.btnUnFold);
			vhSettingGroupCharacteristic.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
			convertView.setTag(vhSettingGroupCharacteristic);
		} else {
			vhSettingGroupCharacteristic = (VHSettingGroupCharacteristic) convertView.getTag();
		}
		final Characteristic characteristic = this.getItem(position);
		vhSettingGroupCharacteristic.tvCharacteristicName.setText(characteristic.getmCharacteristicName());
		vhSettingGroupCharacteristic.btnUnfold.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toCharacteristicItemView(characteristic);
			}
		});
		vhSettingGroupCharacteristic.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCharacteristicArray.remove(characteristic);
				AdapterSettingGroupCharacteristic.this.notifyDataSetChanged();
			}
		});
		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Characteristic characteristic = this.getItem(position);
		this.toCharacteristicItemView(characteristic);
	}

	private void toCharacteristicItemView(Characteristic characteristic) {
		Intent intent = new Intent(this.mContext, ActivitySettingGroupCharacteristicItem.class);
		intent.putExtra(SFGlobal.EXTRA_CHARACTERISTIC, characteristic);
		this.mContext.startActivity(intent);
	}
}
