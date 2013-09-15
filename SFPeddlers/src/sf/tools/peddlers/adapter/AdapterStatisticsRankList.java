package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.RankListItem;
import sf.tools.peddlers.utils.SFBitmapManager;
import sf.tools.peddlers.viewholder.adapter.VHStatisticsRanklist;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterStatisticsRankList extends SFBaseAdapter {
	public static final String TAG = "AdapterStatisticsRankList";

	public static enum RANK_LIST_TYPE {
		RANK_LIST_TYPE_LOOK,
		RANK_LIST_TYPE_BUY
	};

	private ArrayList<RankListItem> mRankListItemArray = null;
	private RANK_LIST_TYPE mType = null;

	public AdapterStatisticsRankList(TopActivity activity, ArrayList<RankListItem> rankListItemArray, RANK_LIST_TYPE type) {
		super(activity);
		this.setmRankListItemArray(rankListItemArray);
	}

	public void setmType(RANK_LIST_TYPE type) {
		this.mType = type;
	}

	public void setmRankListItemArray(ArrayList<RankListItem> rankListItemArray) {
		this.mRankListItemArray = rankListItemArray;
	}

	@Override
	public int getCount() {
		if (this.mRankListItemArray!=null) {
			return this.mRankListItemArray.size();
		}
		return 0;
	}

	@Override
	public RankListItem getItem(int position) {
		if (this.mRankListItemArray!=null) {
			return this.mRankListItemArray.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHStatisticsRanklist vhStatisticsRanklist = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_statistics_rank_list, null);
			vhStatisticsRanklist = new VHStatisticsRanklist();
			vhStatisticsRanklist.ivCargo = (ImageView) convertView.findViewById(R.id.ivCargo);
			vhStatisticsRanklist.tvCargo = (TextView) convertView.findViewById(R.id.tvCargo);
			vhStatisticsRanklist.tvRank = (TextView) convertView.findViewById(R.id.tvRank);
			vhStatisticsRanklist.tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
			convertView.setTag(vhStatisticsRanklist);
		} else {
			vhStatisticsRanklist = (VHStatisticsRanklist) convertView.getTag();
		}

		final RankListItem rankListItem = this.getItem(position);

		Bitmap bitmap = SFBitmapManager.getBitmap(rankListItem.getmCargo().getmCargoId(), this.mApp);
		vhStatisticsRanklist.ivCargo.setImageBitmap(bitmap);
		vhStatisticsRanklist.tvCargo.setText(rankListItem.getmCargo().getmCargoName());
		vhStatisticsRanklist.tvRank.setText(this.mActivity.getString(R.string.rank_format, position));
		
		vhStatisticsRanklist.tvQuantity.setText(
				this.mActivity.getString(R.string.quantity_format,
						getTitle(),
						rankListItem.getQuantity()));
		return convertView;
	}

	private String getTitle() {
		String title = "";
		switch (this.mType) {
		case RANK_LIST_TYPE_LOOK:
			title = this.mActivity.getString(R.string.look);
			break;
		case RANK_LIST_TYPE_BUY:
			title = this.mActivity.getString(R.string.buy);
			break;
		}
		return title;
	}
	
}
