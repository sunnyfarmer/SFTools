package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.adapter.VHSettingGroupCargoType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AdapterSettingGroupCargoType extends BaseAdapter implements
		OnItemClickListener {
	public static final String TAG = "AdapterSettingGroupCargoType";

	private Context mContext = null;
	private ArrayList<CargoType> mCargoTypeArray = null;

	public AdapterSettingGroupCargoType(Context context, ArrayList<CargoType> cargoTypeArray) {
		this.mContext = context;
		this.mCargoTypeArray = cargoTypeArray;
	}

	@Override
	public int getCount() {
		return this.mCargoTypeArray.size();
	}

	@Override
	public CargoType getItem(int position) {
		return this.mCargoTypeArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHSettingGroupCargoType vhSettingGroupCargoType = null;
		if (convertView==null) {
			vhSettingGroupCargoType = new VHSettingGroupCargoType();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.adapter_setting_group_cargo_type, null);
			vhSettingGroupCargoType.tvCargoType = (TextView) convertView.findViewById(R.id.tvCargoType);
			vhSettingGroupCargoType.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

			convertView.setTag(vhSettingGroupCargoType);
		} else {
			vhSettingGroupCargoType = (VHSettingGroupCargoType) convertView.getTag();
		}

		final CargoType cargoType = this.getItem(position);
		vhSettingGroupCargoType.tvCargoType.setText(cargoType.getmCargoTypeName());
		vhSettingGroupCargoType.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int dbRs = ((TopActivity)mContext).getmApp().getmDbManager().removeCargoType(cargoType);
				if (dbRs==SFGlobal.DB_MSG_OK) {
					mCargoTypeArray.remove(cargoType);
					AdapterSettingGroupCargoType.this.notifyDataSetChanged();
				} else {
					((TopActivity)mContext).showToast(R.string.system_error);
				}
			}
		});

		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

}
