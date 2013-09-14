package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.BaseActivity;
import sf.tools.peddlers.R;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.adapter.VHSettingGroupFirstFeeling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AdapterSettingGroupFirstFeeling extends BaseAdapter implements
		OnItemClickListener {
	public static final String TAG = "AdapterSettingGroupFirstFeeling";

	private Context mContext = null;
	private ArrayList<FirstFeeling> mFirstFeelingArray = null;

	public AdapterSettingGroupFirstFeeling(Context context, ArrayList<FirstFeeling> firstFeelingArray) {
		this.mContext = context;
		this.mFirstFeelingArray = firstFeelingArray;
	}

	@Override
	public int getCount() {
		return this.mFirstFeelingArray.size();
	}

	@Override
	public FirstFeeling getItem(int position) {
		return this.mFirstFeelingArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHSettingGroupFirstFeeling vhSettingGroupFirstFeeling = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.adapter_setting_group_first_feeling, null);
			vhSettingGroupFirstFeeling = new VHSettingGroupFirstFeeling();

			vhSettingGroupFirstFeeling.tvFirstFeeling = (TextView) convertView.findViewById(R.id.tvFirstFeeling);
			vhSettingGroupFirstFeeling.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
			convertView.setTag(vhSettingGroupFirstFeeling);
		} else {
			vhSettingGroupFirstFeeling = (VHSettingGroupFirstFeeling) convertView.getTag();
		}

		final FirstFeeling firstFeeling = this.getItem(position);
		vhSettingGroupFirstFeeling.tvFirstFeeling.setText(firstFeeling.getmFirstFeelingName());

		vhSettingGroupFirstFeeling.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int dbRs = ((BaseActivity)mContext).getmApp().getmDbManager().removeFirstFeeling(firstFeeling);
				if (dbRs==SFGlobal.DB_MSG_OK) {
					AdapterSettingGroupFirstFeeling.this.mFirstFeelingArray.remove(firstFeeling);
					AdapterSettingGroupFirstFeeling.this.notifyDataSetChanged();
				} else {
					((BaseActivity)mContext).showToast(R.string.system_error);
				}
			}
		});

		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {}
}
