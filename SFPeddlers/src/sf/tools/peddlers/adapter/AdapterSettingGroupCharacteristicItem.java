package sf.tools.peddlers.adapter;

import java.util.ArrayList;

import sf.tools.peddlers.model.CharacteristicItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
