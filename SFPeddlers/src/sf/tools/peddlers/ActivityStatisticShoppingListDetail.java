package sf.tools.peddlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.ShoppingList;
import sf.tools.peddlers.utils.SFBitmapManager;
import sf.tools.peddlers.utils.SFGlobal;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityStatisticShoppingListDetail extends TopActivity {
	public static final String TAG = "ActivityStatisticShoppingListDetail";

	private TextView tvTimestamp = null;
	private TextView tvFirstFeeling = null;
	private TextView tvCharacteristic = null;
	private LinearLayout llLook = null;
	private LinearLayout llBuy = null;

	private ShoppingList mShoppingList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_shopping_list_detail);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.refresh();
	}

	@Override
	protected void initData() {
		super.initData();
		String shoppingListId = this.getIntent().getStringExtra(SFGlobal.EXTRA_SHOPPINGLIST_ID);
		this.mShoppingList = this.mApp.getmDbManager().getmDBShoppingList().query(shoppingListId);
		if (this.mShoppingList==null) {
			finish();
		}
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.hideRight();
		this.mVHAHeader.setTitleText(this.mApp.getSettingGroup().getmSettingGroupName());
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnStatistics());

		this.tvTimestamp = (TextView) this.findViewById(R.id.tvTimestamp);
		this.tvFirstFeeling = (TextView) this.findViewById(R.id.tvFirstFeeling);
		this.tvCharacteristic = (TextView) this.findViewById(R.id.tvCharacteristic);
		this.llLook = (LinearLayout) this.findViewById(R.id.llLook);
		this.llBuy = (LinearLayout) this.findViewById(R.id.llBuy);
	}
	@Override
	protected void setListener() {
		super.setListener();
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void refresh() {
		this.tvTimestamp.setText(this.formatTime(this.mShoppingList.getmTimestamp()));
		this.tvFirstFeeling.setText(this.mShoppingList.getmFirstFeeling().getmFirstFeelingName());
		this.tvCharacteristic.setText(this.getCharacteristicText(this.mShoppingList.getmCharacteristic()));

		this.llLook.removeAllViews();
		for (Cargo cargo : this.mShoppingList.getmLookCargo()) {
			Bitmap bm = SFBitmapManager.getBitmap(cargo.getmCargoId(), this.mApp);
			if (bm!=null) {
				ImageView iv = new ImageView(this);
				LayoutParams params = new LayoutParams(
						(int)this.getResources().getDimension(R.dimen.activity_shopping_cargo_width),
						(int)this.getResources().getDimension(R.dimen.activity_shopping_cargo_height)
						);
				iv.setLayoutParams(params);
				iv.setImageBitmap(bm);
				this.llLook.addView(iv);
			}
		}
		this.llBuy.removeAllViews();
		for (Cargo cargo : this.mShoppingList.getmBuyCargo()) {
			Bitmap bm = SFBitmapManager.getBitmap(cargo.getmCargoId(), this.mApp);
			if (bm!=null) {
				ImageView iv = new ImageView(this);
				LayoutParams params = new LayoutParams(
						(int)this.getResources().getDimension(R.dimen.activity_shopping_cargo_width),
						(int)this.getResources().getDimension(R.dimen.activity_shopping_cargo_height)
						);
				iv.setLayoutParams(params);
				iv.setImageBitmap(bm);
				this.llBuy.addView(iv);
			}
		}
	}
	private CharSequence getCharacteristicText(ArrayList<Characteristic> characteristicArray) {
		String text = "";
		for (Characteristic characteristic : characteristicArray) {
			text += String.format("%s : %s\n",
					characteristic.getmCharacteristicName(),
					characteristic.getmSelectedCharacteristicItem().getmCharacteristicItemName());
		}
		return text;
	}
	protected String formatTime(long time) {
		String timeText = null;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		timeText = String.format(
				Locale.US,
				"%s/%02d/%02d %02d:%02d",
				c.get(Calendar.YEAR),
				c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH),
				c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE)
				);
		return timeText;
	}
}
