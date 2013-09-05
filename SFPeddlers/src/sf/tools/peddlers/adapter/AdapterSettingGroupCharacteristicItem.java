package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.R;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.viewholder.adapter.VHSettingGroupCharacteristicItem;
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

public class AdapterSettingGroupCharacteristicItem extends BaseAdapter
		implements OnItemClickListener {
	public static final String TAG = "AdapterSettingGroupCharacteristicItem";

	private Context mContext = null;
	private ArrayList<CharacteristicItem> mCharacteristicItemArray = null;

	public AdapterSettingGroupCharacteristicItem(Context context, ArrayList<CharacteristicItem> characteristicItemArray) {
		this.mContext = context;
		this.mCharacteristicItemArray = characteristicItemArray;
	}
	@Override
	public int getCount() {
		return this.mCharacteristicItemArray.size();
	}

	@Override
	public CharacteristicItem getItem(int position) {
		return this.mCharacteristicItemArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VHSettingGroupCharacteristicItem vhSettingGroupCharacteristicItem = null;
		if (convertView==null) {
			vhSettingGroupCharacteristicItem = new VHSettingGroupCharacteristicItem();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.adapter_setting_group_characteristic_item, null);
			vhSettingGroupCharacteristicItem.tvCharacteristicItemName = (TextView) convertView.findViewById(R.id.tvCharacteristicItemName);
			vhSettingGroupCharacteristicItem.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
			convertView.setTag(vhSettingGroupCharacteristicItem);
		} else {
			vhSettingGroupCharacteristicItem = (VHSettingGroupCharacteristicItem) convertView.getTag();
		}

		final CharacteristicItem characteristicItem = this.getItem(position);
		vhSettingGroupCharacteristicItem.tvCharacteristicItemName.setText(characteristicItem.getmCharacteristicItemName());
		vhSettingGroupCharacteristicItem.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AdapterSettingGroupCharacteristicItem.this.mCharacteristicItemArray.remove(characteristicItem);
				AdapterSettingGroupCharacteristicItem.this.notifyDataSetChanged();
			}
		});

		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

}
