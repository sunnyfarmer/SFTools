package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.log.SFLog;
import sf.tools.peddlers.ActivitySettingGroupCargoDetail;
import sf.tools.peddlers.R;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.adapter.VHSettingGroupCargo;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterSettingGroupCargo extends BaseAdapter implements
		OnItemClickListener {
	public static final String TAG = "AdapterSettingGroupCargo";

	private Context mContext = null;
	private ArrayList<Cargo> mCargoArray = null;

	public AdapterSettingGroupCargo(Context context, ArrayList<Cargo> cargoArray) {
		this.mContext = context;
		this.mCargoArray = cargoArray;
	}
	@Override
	public int getCount() {
		return this.mCargoArray.size();
	}

	@Override
	public Cargo getItem(int position) {
		return this.mCargoArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHSettingGroupCargo vhSettingGroupCargo = null;
		if (convertView==null) {
			vhSettingGroupCargo = new VHSettingGroupCargo();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.adapter_setting_group_cargo, null);
			vhSettingGroupCargo.ivCargo = (ImageView) convertView.findViewById(R.id.ivCargo);
			vhSettingGroupCargo.tvCargo = (TextView) convertView.findViewById(R.id.tvCargo);
			vhSettingGroupCargo.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
		} else {
			vhSettingGroupCargo = (VHSettingGroupCargo) convertView.getTag();
		}

		final Cargo cargo = this.getItem(position);
		SFLog.d(TAG, cargo.getmCargoName());
		vhSettingGroupCargo.tvCargo.setText(cargo.getmCargoName());
		vhSettingGroupCargo.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCargoArray.remove(cargo);
				AdapterSettingGroupCargo.this.notifyDataSetChanged();
			}
		});

		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Cargo cargo = this.getItem(position);
		Intent intent = new Intent(this.mContext, ActivitySettingGroupCargoDetail.class);
		intent.putExtra(SFGlobal.EXTRA_CARGO, cargo);
		this.mContext.startActivity(intent);
	}
	public ArrayList<Cargo> getmCargoArray() {
		return mCargoArray;
	}
	public void setmCargoArray(ArrayList<Cargo> mCargoArray) {
		this.mCargoArray = mCargoArray;
	}

}
