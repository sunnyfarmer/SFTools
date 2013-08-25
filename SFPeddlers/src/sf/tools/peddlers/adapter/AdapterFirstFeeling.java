package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.ActivityCustomerCharacteristic;
import sf.tools.peddlers.R;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.ShoppingList;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.VHFirstFeeling;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterFirstFeeling extends BaseAdapter implements OnItemClickListener{
	public static final String TAG = "FirstFeelingAdapter";

	private Context mContext = null;
	private ArrayList<FirstFeeling> mFirstFeelingArray = null;

	public AdapterFirstFeeling(Context context, ArrayList<FirstFeeling> firstFeelingArray) {
		this.mFirstFeelingArray = firstFeelingArray;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		if (this.mFirstFeelingArray!=null) {
			return this.mFirstFeelingArray.size();
		}
		return 0;
	}

	@Override
	public FirstFeeling getItem(int position) {
		if (this.mFirstFeelingArray!=null) {
			return this.mFirstFeelingArray.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHFirstFeeling firstFeelingViewHolder = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(this.mContext)
					.inflate(R.layout.adapter_first_feeling, null);
			firstFeelingViewHolder = new VHFirstFeeling();
			firstFeelingViewHolder.tvFirstFeeling = (TextView) convertView.findViewById(R.id.tvFirstFeeling);
			convertView.setTag(firstFeelingViewHolder);
		} else {
			firstFeelingViewHolder = (VHFirstFeeling) convertView.getTag();
		}

		firstFeelingViewHolder.tvFirstFeeling.setText(this.mFirstFeelingArray.get(position).getmFirstFeeling());

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 110);
		convertView.setLayoutParams(params);

		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this.mContext, ActivityCustomerCharacteristic.class);

		FirstFeeling firstFeeling = this.getItem(arg2);
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setmFirstFeeling(firstFeeling);

		intent.putExtra(SFGlobal.EXTRA_SHOPPINGLIST, shoppingList);

		this.mContext.startActivity(intent);
	}
}
