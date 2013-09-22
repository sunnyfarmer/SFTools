package sf.tools.peddlers.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import sf.log.SFLog;
import sf.tools.peddlers.ActivityStatisticShoppingListDetail;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.ShoppingList;
import sf.tools.peddlers.utils.SFBitmapManager;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.adapter.VHStatisticsShoppingList;

public class AdapterStatisticsShoppingList extends SFBaseAdapter implements OnItemClickListener{
	public static final String TAG = "AdapterStatisticsShoppingList";

	private ArrayList<ShoppingList> mShoppingListArray = null;

	public AdapterStatisticsShoppingList(TopActivity activity, ArrayList<ShoppingList> shoppingListArray) {
		super(activity);
		this.mShoppingListArray = shoppingListArray;
	}

	public ArrayList<ShoppingList> getmShoppingListArray() {
		return mShoppingListArray;
	}

	public void setmShoppingListArray(ArrayList<ShoppingList> mShoppingListArray) {
		this.mShoppingListArray = mShoppingListArray;
	}

	@Override
	public int getCount() {
		if (this.mShoppingListArray!=null) {
			return this.mShoppingListArray.size();
		}
		return 0;
	}

	@Override
	public ShoppingList getItem(int position) {
		if (this.mShoppingListArray!=null) {
			return this.mShoppingListArray.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHStatisticsShoppingList vhStatisticsShoppingList = null;
		if (convertView==null) {
			vhStatisticsShoppingList = new VHStatisticsShoppingList();
			convertView = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_statistics_shopping_list, null);
			vhStatisticsShoppingList.tvFirstFeeling = (TextView) convertView.findViewById(R.id.tvFirstFeeling);
			vhStatisticsShoppingList.tvCharacteristic = (TextView) convertView.findViewById(R.id.tvCharacteristic);
			vhStatisticsShoppingList.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			vhStatisticsShoppingList.llCargoList = (LinearLayout) convertView.findViewById(R.id.llCargoList);
			convertView.setTag(vhStatisticsShoppingList);
		} else {
			vhStatisticsShoppingList = (VHStatisticsShoppingList) convertView.getTag();
		}
		final ShoppingList shoppingList = this.getItem(position);
		vhStatisticsShoppingList.tvFirstFeeling.setText(shoppingList.getmFirstFeeling().getmFirstFeelingName());
		vhStatisticsShoppingList.tvCharacteristic.setText(getCharacteristicItemString(shoppingList.getmCharacteristic()));
		vhStatisticsShoppingList.tvTime.setText(formatTime(shoppingList.getmTimestamp()));

		ArrayList<Cargo> cargoList = shoppingList.getmRelatedCargo();
		int cot = 0;
		int MAX_IV = 3;
		vhStatisticsShoppingList.llCargoList.removeAllViews();
		for (Cargo cargo : cargoList) {
			ImageView iv = new ImageView(this.mActivity);
			LayoutParams lp = new LayoutParams(
					this.mActivity.getResources().getDimensionPixelOffset(R.dimen.activity_statistics_cargo_width),
					this.mActivity.getResources().getDimensionPixelOffset(R.dimen.activity_statistics_cargo_height)
					);
			iv.setLayoutParams(lp);
			Bitmap bm = SFBitmapManager.getBitmap(cargo.getmCargoId(), mApp);
			iv.setImageBitmap(bm);
			vhStatisticsShoppingList.llCargoList.addView(iv);
			cot++;
			if (cot >= MAX_IV) {
				break;
			}
		}
		return convertView;
	}

	protected String getCharacteristicItemString(ArrayList<Characteristic> characteristicArray) {
		String text = "";
		for (Characteristic characteristic : characteristicArray) {
			if (characteristic.getmSelectedCharacteristicItem()!=null) {
				if (text!="") {
					text+=",";
				}
				text += characteristic.getmSelectedCharacteristicItem().getmCharacteristicItemName();
			}
		}
		return text.trim();
	}

	protected String formatTime(long time) {
		String timeText = null;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		timeText = String.format(
				Locale.US,
				"%s/%02d/%02d\n%02d:%02d",
				c.get(Calendar.YEAR),
				c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH),
				c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE)
				);
		return timeText;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		SFLog.d(TAG, String.valueOf(this.getItem(position).getmTimestamp()));
		Intent intent = new Intent(this.mActivity, ActivityStatisticShoppingListDetail.class);
		intent.putExtra(SFGlobal.EXTRA_SHOPPINGLIST_ID, this.getItem(position).getmShoppingListId());
		this.mActivity.startActivity(intent);
	}
}
