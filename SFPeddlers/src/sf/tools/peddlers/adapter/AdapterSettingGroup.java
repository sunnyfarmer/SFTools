package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.log.SFLog;
import sf.tools.peddlers.ActivityAddSettingGroup;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.utils.SFGlobal;
import sf.tools.peddlers.viewholder.adapter.VHSettingGroup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AdapterSettingGroup extends SFBaseAdapter implements OnItemClickListener{
	public static final String TAG = "AdapterSettingGroup";

	private ArrayList<SettingGroup> mSettingGroupArray = null;

	public AdapterSettingGroup(TopActivity activity, ArrayList<SettingGroup> settingGroupArray) {
		super(activity);
		this.mSettingGroupArray = settingGroupArray;
	}

	public void setmSettingGroupArray(ArrayList<SettingGroup> settingGroupArray) {
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
			convertView = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_setting_group, null);

			vhSettingGroup = new VHSettingGroup();
			vhSettingGroup.tvSettingGroupName = (TextView) convertView.findViewById(R.id.tvSettingGroupName);
			vhSettingGroup.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
			vhSettingGroup.btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
			convertView.setTag(vhSettingGroup);
		} else {
			vhSettingGroup = (VHSettingGroup) convertView.getTag();
		}

		final SettingGroup settingGroup = this.getItem(position);

		//获得选中的id
		convertView.setFocusable(true);
		String selectedSettingGroupId = this.mApp.getSettingGroupId();
		SFLog.d(TAG,
				String.format("%s \n %s", selectedSettingGroupId, settingGroup.getmSettingGroupId()));
		if (selectedSettingGroupId!=null && selectedSettingGroupId.equals(settingGroup.getmSettingGroupId())) {
			convertView.setBackgroundResource(R.drawable.selector_list_checked);
		} else {
			convertView.setBackgroundResource(R.drawable.selector_list);
		}

		vhSettingGroup.tvSettingGroupName.setText(settingGroup.getmSettingGroupName());
		vhSettingGroup.tvSettingGroupName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//选中SettingGroup
				mApp.setSettingGroupId(settingGroup.getmSettingGroupId());
				AdapterSettingGroup.this.notifyDataSetChanged();
			}
		});
		vhSettingGroup.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				delete(settingGroup);
			}
		});
		vhSettingGroup.btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, ActivityAddSettingGroup.class);
				mApp.setmEditingSettingGroup(settingGroup);
				mActivity.startActivity(intent);
			}
		});

		return convertView;
	}
	
	private void delete(final SettingGroup settigGroup) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
		builder.setTitle(R.string.warning)
		.setMessage(R.string.whether_delete_setting_group)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//删除购物单
				int dbRs = mApp.getmDbManager().removeSettingGroup(settigGroup);
				if (dbRs==SFGlobal.DB_MSG_OK) {
					mSettingGroupArray = mApp.getmDbManager().getmDBSettingGroup().queryAll();
					AdapterSettingGroup.this.notifyDataSetChanged();
				} else {
					mActivity.showToast(R.string.system_error);
				}
			}
		})
		.setNegativeButton(R.string.no,null)
		.create().show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

}
