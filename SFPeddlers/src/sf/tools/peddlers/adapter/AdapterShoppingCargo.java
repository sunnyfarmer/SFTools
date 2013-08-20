package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.R;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.viewholder.VHShoppingCargo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterShoppingCargo extends BaseAdapter{
	public static final String TAG = "AdapterShoppingCargo";

	private Context mContext = null;
	private ArrayList<Cargo> mCargoList = null;

	public AdapterShoppingCargo(Context context, ArrayList<Cargo> cargoList) {
		this.mContext = context;
		this.mCargoList = cargoList;
	}

	public ArrayList<Cargo> getmCargoList() {
		return mCargoList;
	}

	public void setmCargoList(ArrayList<Cargo> mCargoList) {
		this.mCargoList = mCargoList;
	}

	@Override
	public int getCount() {
		if (this.mCargoList!=null) {
			return this.mCargoList.size();
		}
		return 0;
	}

	@Override
	public Cargo getItem(int position) {
		if (this.mCargoList!=null) {
			return this.mCargoList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHShoppingCargo vhShoppingCargo = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.adapter_shopping_cargo, null);
			vhShoppingCargo = new VHShoppingCargo();
			vhShoppingCargo.ivCargo = (ImageView) convertView.findViewById(R.id.ivCargo);
			vhShoppingCargo.tvCargo = (TextView) convertView.findViewById(R.id.tvCargo);
			vhShoppingCargo.btnLook = (Button) convertView.findViewById(R.id.btnLook);
			vhShoppingCargo.btnBuy = (Button) convertView.findViewById(R.id.btnBuy);

			convertView.setTag(vhShoppingCargo);
		} else {
			vhShoppingCargo = (VHShoppingCargo) convertView.getTag();
		}

		Cargo cargo = this.getItem(position);
		vhShoppingCargo.tvCargo.setText(cargo.getmCargoName());

		return convertView;
	}

	
}
