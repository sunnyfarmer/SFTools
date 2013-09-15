package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.utils.SFBitmapManager;
import sf.tools.peddlers.viewholder.adapter.VHStatisticsCargo;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterStatisticsCargo extends SFBaseAdapter {
	public static final String TAG  = "AdapterStatisticsCargo";

	private ArrayList<Cargo> mCargoArray = null;

	public AdapterStatisticsCargo(TopActivity activity, ArrayList<Cargo> cargoArray) {
		super(activity);
		this.setmCargoArray(cargoArray);
	}

	public void setmCargoArray(ArrayList<Cargo> cargoArray) {
		this.mCargoArray = cargoArray;
	}

	@Override
	public int getCount() {
		if (this.mCargoArray!=null) {
			return this.mCargoArray.size();
		}
		return 0;
	}

	@Override
	public Cargo getItem(int position) {
		if (this.mCargoArray!=null) {
			return this.mCargoArray.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHStatisticsCargo vhStatisticsCargo = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_statistics_cargo, null);
			vhStatisticsCargo = new VHStatisticsCargo();
			vhStatisticsCargo.ivCargo = (ImageView) convertView.findViewById(R.id.ivCargo);
			vhStatisticsCargo.tvCargo = (TextView) convertView.findViewById(R.id.tvCargo);
			convertView.setTag(vhStatisticsCargo);
		} else {
			vhStatisticsCargo = (VHStatisticsCargo) convertView.getTag();
		}

		final Cargo cargo = this.getItem(position);
		Bitmap bitmap = SFBitmapManager.getBitmap(cargo.getmCargoId(), this.mApp);
		vhStatisticsCargo.ivCargo.setImageBitmap(bitmap);
		vhStatisticsCargo.tvCargo.setText(cargo.getmCargoName());

		return convertView;
	}

}
