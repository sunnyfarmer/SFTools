package sf.tools.peddlers;

import java.util.ArrayList;
import sf.log.SFLog;
import sf.tools.peddlers.adapter.AdapterStatisticsRankList;
import sf.tools.peddlers.adapter.AdapterStatisticsRankList.RANK_LIST_TYPE;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.RankListItem;
import sf.tools.peddlers.viewholder.activity.VHACharacteristicItem;
import sf.utils.SFAdvertisement;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityStatisticsCargoRankList extends TopActivity {
	public static final String TAG = "ActivityStatisticsCargoRankList";

	private TextView tvMsg = null;
	private Button btnLook = null;
	private Button btnBuy = null;
	private Button btnVisibility = null;
	private VHACharacteristicItem mVHACharacteristicItem = null;
	private ListView lvRankList = null;
	private AdapterStatisticsRankList mAdapterStatisticsRankList = null;

	private ArrayList<RankListItem> mRankListItemArray = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_cargo_rank_list);
	    super.onCreate(savedInstanceState);

	    SFAdvertisement.showSpotAd(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		CharacteristicItem item = this.mVHACharacteristicItem.getmSelectedCharacteristicItem();
		if (item!=null) {
			String msg = this.getString(R.string.please_click_to_check_rank_list,
					item.getmCharacteristic().getmCharacteristicName(),
					item.getmCharacteristicItemName(),
					item.getmCharacteristic().getmCharacteristicName(),
					item.getmCharacteristicItemName());
			this.tvMsg.setText(msg);
			this.tvMsg.setVisibility(View.VISIBLE);
		}
	}
	@Override
	protected void initData() {
		super.initData();
		this.mAdapterStatisticsRankList = new AdapterStatisticsRankList(this, null, null);
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnStatistics());
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.back);
		this.mVHAHeader.hideRight();
		this.mVHAHeader.setTitleText(R.string.rank_list);

		this.mVHACharacteristicItem = new VHACharacteristicItem(this);

		this.tvMsg = (TextView) this.findViewById(R.id.tvMsg);
		this.btnLook = (Button) this.findViewById(R.id.btnLook);
		this.btnBuy = (Button) this.findViewById(R.id.btnBuy);
		this.btnVisibility = (Button) this.findViewById(R.id.btnVisibility);
		this.lvRankList = (ListView) this.findViewById(R.id.lvRankList);

		this.lvRankList.setAdapter(this.mAdapterStatisticsRankList);
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
				SFLog.d(TAG, mVHACharacteristicItem.getmSelectedCharacteristicItem().getmCharacteristicItemName());
				CharacteristicItem characteristicItem = mVHACharacteristicItem.getmSelectedCharacteristicItem();
				mRankListItemArray = mApp.getmDbManager().getmDBRankList().queryLookRank(characteristicItem);
				mAdapterStatisticsRankList.setmRankListItemArray(mRankListItemArray);
				mAdapterStatisticsRankList.setmType(RANK_LIST_TYPE.RANK_LIST_TYPE_LOOK);
				mAdapterStatisticsRankList.notifyDataSetChanged();
				if (mRankListItemArray==null || mRankListItemArray.size()==0) {
					tvMsg.setText(R.string.rank_list_no_data);
					tvMsg.setVisibility(View.VISIBLE);
				} else {
					tvMsg.setVisibility(View.GONE);
				}
			}
		});
		this.btnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SFLog.d(TAG, mVHACharacteristicItem.getmSelectedCharacteristicItem().getmCharacteristicItemName());
				CharacteristicItem characteristicItem = mVHACharacteristicItem.getmSelectedCharacteristicItem();
				mRankListItemArray = mApp.getmDbManager().getmDBRankList().queryBuyRank(characteristicItem);
				mAdapterStatisticsRankList.setmRankListItemArray(mRankListItemArray);
				mAdapterStatisticsRankList.setmType(RANK_LIST_TYPE.RANK_LIST_TYPE_BUY);
				mAdapterStatisticsRankList.notifyDataSetChanged();
				if (mRankListItemArray==null || mRankListItemArray.size()==0) {
					tvMsg.setText(R.string.rank_list_no_data);
					tvMsg.setVisibility(View.VISIBLE);
				} else {
					tvMsg.setVisibility(View.GONE);
				}
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
		this.mVHACharacteristicItem.show();
	}
	protected void hideTools() {
//		this.mVHAFooter.hideFooter();
		this.mVHAHeader.hideHeader();
		this.btnLook.setVisibility(View.INVISIBLE);
		this.btnBuy.setVisibility(View.INVISIBLE);
		this.btnVisibility.setVisibility(View.VISIBLE);
		this.btnVisibility.setText(R.string.show);
		this.mVHACharacteristicItem.hide();
	}
}
