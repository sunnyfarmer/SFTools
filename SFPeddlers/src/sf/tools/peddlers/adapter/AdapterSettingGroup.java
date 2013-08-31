package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.R;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.viewholder.VHSettingGroup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AdapterSettingGroup extends BaseAdapter implements OnItemClickListener{
	public static final String TAG = "AdapterSettingGroup";

	private Context mContext = null;
	private ArrayList<SettingGroup> mSettingGroupArray = null;

	public AdapterSettingGroup(Context context, ArrayList<SettingGroup> settingGroupArray) {
		this.mContext = context;
		this.mSettingGroupArray = settingGroupArray;
	}

	@Override
	public int getCount() {
		return this.mSettingGroupArray.size();
	}

	@Override
	public SettingGroup getItem(int position) {
		return this.mSettingGroupArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHSettingGroup vhSettingGroup = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.adapter_setting_group, null);

			vhSettingGroup = new VHSettingGroup();
			vhSettingGroup.tvSettingGroupName = (TextView) convertView.findViewById(R.id.tvSettingGroupName);
			vhSettingGroup.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

			convertView.setTag(vhSettingGroup);
		} else {
			vhSettingGroup = (VHSettingGroup) convertView.getTag();
		}

		final SettingGroup settingGroup = this.getItem(position);
		vhSettingGroup.tvSettingGroupName.setText(settingGroup.getmSettingGroupName());
		vhSettingGroup.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				delete(settingGroup);
			}
		});

		return convertView;
	}

	private void delete(SettingGroup settigGroup) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.warning)
		.setMessage(R.string.whether_delete_setting_group)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//删除购物单
			}
		})
		.setNegativeButton(R.string.no,null)
		.create().show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
