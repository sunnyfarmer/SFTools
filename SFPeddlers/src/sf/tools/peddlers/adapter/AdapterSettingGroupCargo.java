package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.log.SFLog;
import sf.tools.peddlers.ActivitySettingGroupCargoDetail;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.utils.SFBitmapManager;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.adapter.VHSettingGroupCargo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

			convertView.setTag(vhSettingGroupCargo);
		} else {
			vhSettingGroupCargo = (VHSettingGroupCargo) convertView.getTag();
		}

		final Cargo cargo = this.getItem(position);
		vhSettingGroupCargo.tvCargo.setText(cargo.getmCargoName());
		Bitmap bitmap = SFBitmapManager.getBitmap(cargo.getmCargoId(), ((TopActivity)mContext).getmApp());
		if (bitmap!=null) {
			vhSettingGroupCargo.ivCargo.setImageBitmap(bitmap);
		}
		vhSettingGroupCargo.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int dbRs = ((TopActivity)mContext).getmApp().getmDbManager().removeCargo(cargo);
				if (dbRs==SFGlobal.DB_MSG_OK) {
					SFBitmapManager.removeBitmap(cargo.getmCargoId(), ((TopActivity)mContext).getmApp());
					mCargoArray.remove(cargo);
					AdapterSettingGroupCargo.this.notifyDataSetChanged();
				} else {
					((TopActivity)mContext).showToast(R.string.system_error);
				}
			}
		});
		vhSettingGroupCargo.ivCargo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoCargoDetailView(cargo);
			}
		});
		vhSettingGroupCargo.tvCargo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoCargoDetailView(cargo);
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

	private void gotoCargoDetailView(Cargo cargo) {
		Intent intent = new Intent(this.mContext, ActivitySettingGroupCargoDetail.class);
		intent.putExtra(SFGlobal.EXTRA_CARGO, cargo);
		((TopActivity)this.mContext).startActivityForResult(intent, SFGlobal.RS_CODE_EDIT_CARGO);
	}
}
