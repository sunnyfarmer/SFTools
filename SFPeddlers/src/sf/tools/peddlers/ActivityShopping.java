package sf.tools.peddlers;

import java.util.ArrayList;
import sf.log.SFLog;
import sf.tools.peddlers.adapter.AdapterShoppingCargo;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.activity.VHACargoType;
import sf.tools.peddlers.viewholder.activity.VHACargoType.OnCargoTypeChangedListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ActivityShopping extends TopActivity {
	public static final String TAG = "ActivityShopping";

	private SparseArray<ArrayList<Cargo>> mCargoSparseArray = new SparseArray<ArrayList<Cargo>>();
	private ArrayList<CargoType> mCargoTypeArray = null;
	private CargoType mSelectedCargoType = null;

	private ListView lvCargo = null;
	private VHACargoType mVHACargoType = null;

	private AdapterShoppingCargo mAdapterShoppingCargo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_shopping);
	    super.onCreate(savedInstanceState);

	    if (this.mApp.getSettingGroup()==null) {
			Intent intent = new Intent(this, ActivitySettingGroup.class);
			this.startActivity(intent);
			this.finish();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		ArrayList<Characteristic> characteristicArray = this.mApp.getmShoppingList().getmCharacteristic();
		for (Characteristic characteristic : characteristicArray) {
			SFLog.d(TAG, String.format("%s : %s", characteristic.getmCharacteristicName(), characteristic.getmSelectedCharacteristicItem().getmCharacteristicItemName()));
		}
		this.refreshCargoArray();
		
		if (this.mCargoTypeArray!=null && this.mCargoTypeArray.size()>0) {
			this.setmSelectedCargoType(this.mCargoTypeArray.get(0));
			this.mVHACargoType.checkCargoType(this.getmSelectedCargoType());
		}
	}

	@Override
	protected void initData() {
		super.initData();
		this.mCargoTypeArray = this.mApp.getSettingGroup().getmCargoTypeArray();
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnInSelling());
		this.lvCargo = (ListView) this.findViewById(R.id.lvCargo);
		this.mVHACargoType = new VHACargoType(this, this.getCargoTypeArray());

		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.shopping_end);
	}
	@Override
	protected void setListener() {
		super.setListener();
		this.mVHACargoType.setmOnCargoTypeChangedListener(new OnCargoTypeChangedListener() {
			@Override
			public void onCargoTypeChanged(CargoType cargoType) {
				ActivityShopping.this.setmSelectedCargoType(cargoType);
			}
		});
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		this.mVHAHeader.setRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityShopping.this.finishShoppingList();
				int dbRs = mApp.getmDbManager().addShoppingList(mApp.getmShoppingList());
				if (dbRs==SFGlobal.DB_MSG_OK) {
					showToast(R.string.shopping_end);
					Intent intent = new Intent(ActivityShopping.this, ActivityFirstFeeling.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ActivityShopping.this.startActivity(intent);
					finish();
				} else {
					showToast(R.string.system_error);
				}
			}
		});
	}
	/**
	 * 从数据层获得数据
	 */
	public void refreshCargoArray() {
		this.mCargoSparseArray = this.mApp.getSettingGroup().getmCargoArray();
	}
	/**
	 * 刷新商品列表
	 */
	private void refreshCargo() {
		ArrayList<Cargo> cargoList = this.mCargoSparseArray.get(this.getmSelectedCargoType().getmCargoTypeId());

		if (this.mAdapterShoppingCargo==null) {
			this.mAdapterShoppingCargo = new AdapterShoppingCargo(this, cargoList);
			this.lvCargo.setAdapter(this.mAdapterShoppingCargo);
		} else {
			this.mAdapterShoppingCargo.setmCargoList(cargoList);
			this.mAdapterShoppingCargo.notifyDataSetChanged();
		}
	}

	public ArrayList<CargoType> getCargoTypeArray() {
		return this.mCargoTypeArray;
	}

	public CargoType getmSelectedCargoType() {
		return mSelectedCargoType;
	}

	public void setmSelectedCargoType(CargoType mSelectedCargoType) {
		this.mSelectedCargoType = mSelectedCargoType;
		this.refreshCargo();
	}

	private void finishShoppingList() {
		ArrayList<Cargo> lookCargoArray = new ArrayList<Cargo>();
		ArrayList<Cargo> buyCargoArray = new ArrayList<Cargo>();
		ArrayList<Cargo> relatedCargoArray = new ArrayList<Cargo>();

		for (CargoType cargoType : this.getCargoTypeArray()) {
			ArrayList<Cargo> cargoArray = this.mCargoSparseArray.get(cargoType.getmCargoTypeId());
			for (Cargo cargo : cargoArray) {
				switch (cargo.getmBehavior()) {
				case CB_NONE:
					break;
				case CB_LOOK:
					lookCargoArray.add(cargo);
					relatedCargoArray.add(cargo);
					break;
				case CB_BUY:
					lookCargoArray.add(cargo);
					buyCargoArray.add(cargo);
					relatedCargoArray.add(cargo);
					break;
				default:
					break;
				}
			}
		}
		this.mApp.getmShoppingList().setmLookCargo(lookCargoArray);
		this.mApp.getmShoppingList().setmBuyCargo(buyCargoArray);
		this.mApp.getmShoppingList().setmRelatedCargo(relatedCargoArray);
	}
}
