package sf.tools.peddlers;

import sf.log.SFLog;
import sf.tools.peddlers.viewholder.activity.VHACharacteristicItem;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityStatisticsCargoRankList extends TopActivity {
	public static final String TAG = "ActivityStatisticsCargoRankList";

	private Button btnLook = null;
	private Button btnBuy = null;
	private Button btnVisibility = null;
	private VHACharacteristicItem mVHACharacteristicItem = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_cargo_rank_list);
	    super.onCreate(savedInstanceState);
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
		this.mVHAHeader.setTitleText(R.string.rank_list);

		this.mVHACharacteristicItem = new VHACharacteristicItem(this);

		this.btnLook = (Button) this.findViewById(R.id.btnLook);
		this.btnBuy = (Button) this.findViewById(R.id.btnBuy);
		this.btnVisibility = (Button) this.findViewById(R.id.btnVisibility);
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
				SFLog.d(TAG, mVHACharacteristicItem.getmSelectedCharacteristicItem().getmCharacteristicItemName());
			}
		});
		this.btnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
		this.mVHAFooter.showFooter();
		this.btnLook.setVisibility(View.VISIBLE);
		this.btnBuy.setVisibility(View.VISIBLE);
		this.btnVisibility.setVisibility(View.VISIBLE);
		this.btnVisibility.setText(R.string.hide);
		this.mVHACharacteristicItem.show();
	}
	protected void hideTools() {
		this.mVHAFooter.hideFooter();
		this.mVHAHeader.hideHeader();
		this.btnLook.setVisibility(View.INVISIBLE);
		this.btnBuy.setVisibility(View.INVISIBLE);
		this.btnVisibility.setVisibility(View.VISIBLE);
		this.btnVisibility.setText(R.string.show);
		this.mVHACharacteristicItem.hide();
	}
}
