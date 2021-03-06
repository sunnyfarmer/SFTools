package sf.tools.peddlers.adapter;

import java.util.ArrayList;
import sf.tools.peddlers.ActivitySettingGroupCharacteristicItem;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.adapter.VHSettingGroupCharacteristic;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AdapterSettingGroupCharacteristic extends SFBaseAdapter implements
		OnItemClickListener {
	public static final String TAG = "AdapterSettingGroupCharacteristic";

	private ArrayList<Characteristic> mCharacteristicArray = null;

	public AdapterSettingGroupCharacteristic(TopActivity activity, ArrayList<Characteristic> characteristicArray) {
		super(activity);
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
			convertView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_setting_group_characteristic, null);
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
		vhSettingGroupCharacteristic.tvCharacteristicName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toCharacteristicItemView(characteristic);
			}
		});
		vhSettingGroupCharacteristic.btnUnfold.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toCharacteristicItemView(characteristic);
			}
		});
		vhSettingGroupCharacteristic.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int dbRs = mApp.getmDbManager().removeCharacteristic(characteristic);
				if (dbRs==SFGlobal.DB_MSG_OK) {
					mCharacteristicArray.remove(characteristic);
					AdapterSettingGroupCharacteristic.this.notifyDataSetChanged();
				} else {
					mActivity.showToast(R.string.system_error);
				}
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
		Intent intent = new Intent(this.mActivity, ActivitySettingGroupCharacteristicItem.class);
		intent.putExtra(SFGlobal.EXTRA_CHARACTERISTIC_ID, characteristic.getmCharacteristicId());
		this.mActivity.startActivity(intent);
	}
}
