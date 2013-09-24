package sf.tools.peddlers;

import java.util.ArrayList;
import java.util.HashMap;
import sf.log.SFLog;
import sf.math.SFMath;
import sf.tools.chart.SFLineChart;
import sf.tools.chart.entity.SFLineChartEntity;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.viewholder.activity.VHACargoNCharacteristic;
import sf.utils.SFAdvertisement;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityStatisticsCharacteristicLine extends TopActivity {
	public static final String TAG = "ActivityStatisticsCharacteristicLine";

	private TextView tvMsg = null;
	private Button btnLook = null;
	private Button btnBuy = null;
	private Button btnVisibility = null;
	private VHACargoNCharacteristic mVHACargoNCharacteristic = null;
	private SFLineChart mSFLineChart = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_characteristic_line);
	    super.onCreate(savedInstanceState);
	}
	@Override
	protected void onStart() {
		super.onStart();

		SFAdvertisement.showSpotAd(this);
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Cargo cargo = this.mVHACargoNCharacteristic.getmSelectedCargo();
		Characteristic characteristic = this.mVHACargoNCharacteristic.getmSelectedCharacteristic();
		if (cargo!=null && characteristic!=null) {
			String msg = this.getString(R.string.please_click_to_check_characteristic_line,
					cargo.getmCargoName(),
					characteristic.getmCharacteristicName(),
					cargo.getmCargoName(),
					characteristic.getmCharacteristicName());
			this.tvMsg.setText(msg);
			tvMsg.setVisibility(View.VISIBLE);
		}
	}
	@Override
	protected void initData() {
		super.initData();
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnStatistics());
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.back);
		this.mVHAHeader.hideRight();
		this.mVHAHeader.setTitleText(R.string.characteristic_line);
		this.mVHACargoNCharacteristic = new VHACargoNCharacteristic(this);

		this.tvMsg = (TextView) this.findViewById(R.id.tvMsg);
		this.btnLook = (Button) this.findViewById(R.id.btnLook);
		this.btnBuy = (Button) this.findViewById(R.id.btnBuy);
		this.btnVisibility = (Button) this.findViewById(R.id.btnVisibility);
		this.mSFLineChart = (SFLineChart) this.findViewById(R.id.lcCharacteristic);
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
		this.btnLook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SFLog.d(TAG, mVHACargoNCharacteristic.getmSelectedCargo().getmCargoName());
				SFLog.d(TAG, mVHACargoNCharacteristic.getmSelectedCharacteristic().toString());
				SFLog.d(TAG, mVHACargoNCharacteristic.getmSelectedCargo().getmCargoName());
				SFLog.d(TAG, mVHACargoNCharacteristic.getmSelectedCharacteristic().toString());
				Cargo cargo = mVHACargoNCharacteristic.getmSelectedCargo();
				Characteristic characteristic = mVHACargoNCharacteristic.getmSelectedCharacteristic();
				HashMap<CharacteristicItem, Integer> map = mApp.getmDbManager().getmDBRankList().queryLookQuantity(cargo, characteristic);
				refreshLineChart(map);

				tvMsg.setVisibility(View.GONE);
			}
		});
		this.btnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SFLog.d(TAG, mVHACargoNCharacteristic.getmSelectedCargo().getmCargoName());
				SFLog.d(TAG, mVHACargoNCharacteristic.getmSelectedCharacteristic().toString());
				Cargo cargo = mVHACargoNCharacteristic.getmSelectedCargo();
				Characteristic characteristic = mVHACargoNCharacteristic.getmSelectedCharacteristic();
				HashMap<CharacteristicItem, Integer> map = mApp.getmDbManager().getmDBRankList().queryBuyQuantity(cargo, characteristic);
				refreshLineChart(map);

				tvMsg.setVisibility(View.GONE);
			}
		});
		this.btnVisibility.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = btnVisibility.getText().toString();
				if (text.equals(getString(R.string.show))) {
					showTools();
				} else {
					hideTools();
				}				
			}
		});
	}

	protected void showTools() {
		this.mVHAHeader.showHeader();
//		this.mVHAFooter.showFooter();
		this.btnLook.setVisibility(View.VISIBLE);
		this.btnBuy.setVisibility(View.VISIBLE);
		this.btnVisibility.setVisibility(View.VISIBLE);
		this.btnVisibility.setText(R.string.hide);
		this.mVHACargoNCharacteristic.show();
	}
	protected void hideTools() {
//		this.mVHAFooter.hideFooter();
		this.mVHAHeader.hideHeader();
		this.btnLook.setVisibility(View.INVISIBLE);
		this.btnBuy.setVisibility(View.INVISIBLE);
		this.btnVisibility.setVisibility(View.VISIBLE);
		this.btnVisibility.setText(R.string.show);
		this.mVHACargoNCharacteristic.hide();
	}

	private void refreshLineChart(HashMap<CharacteristicItem, Integer> map) {
		this.mSFLineChart.clearLineEntityList();
		ArrayList<Integer> quantityArray = new ArrayList<Integer>();
		for (CharacteristicItem characteristicItem : map.keySet()) {
			int quantity = map.get(characteristicItem);
			SFLineChartEntity entity = new SFLineChartEntity(characteristicItem.getmCharacteristicItemName(), quantity);
			this.mSFLineChart.addLineChartEntity(entity);

			quantityArray.add(quantity);
		}
		int[] minMax = SFMath.getMinMax(quantityArray);
		this.mSFLineChart.setEntityRange(minMax[0]-10, minMax[1]+10);
		this.mSFLineChart.setmStepsOfValue(10);
		this.mSFLineChart.setmNumOfDisplayingEntity(quantityArray.size());
		this.mSFLineChart.setmIndexOfBeginDisplayingEntity(0);
	}
}
