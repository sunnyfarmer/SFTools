package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterSettingGroupCharacteristicItem;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.utils.SFGlobal;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ActivitySettingGroupCharacteristicItem extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCharacteristicItem";

	private Characteristic mCharacteristic = null;

	private ListView lvCharacteristicItem = null;

	private AdapterSettingGroupCharacteristicItem mAdapterSettingGroupCharacteristicItem = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_setting_group_characteristic_item);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		super.initData();
	    Intent intent = this.getIntent();
	    Characteristic characteristicFromIntent = (Characteristic) intent.getSerializableExtra(SFGlobal.EXTRA_CHARACTERISTIC);
	    ArrayList<Characteristic> characteristicArray = this.mApp.getmEditingSettingGroup().getmCharacteristicArray();
	    for (Characteristic characteristic : characteristicArray) {
			if (characteristic.getmCharacteristicId() == characteristicFromIntent.getmCharacteristicId()) {
				this.mCharacteristic = characteristic;
				break;
			}
		}

	    if (this.mCharacteristic==null) {
	    	this.finish();
	    }
		this.mAdapterSettingGroupCharacteristicItem = new AdapterSettingGroupCharacteristicItem(this, this.mCharacteristic.getmCharacteristicItemArray());
	}

	@Override
	protected void initView() {
		super.initView();
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.add_characteristic_item);
		this.lvCharacteristicItem = (ListView) this.findViewById(R.id.lvCharacteristicItem);

		this.setCharacteristicName();
		this.lvCharacteristicItem.setAdapter(mAdapterSettingGroupCharacteristicItem);
	}

	@Override
	protected void setListener() {
		super.setListener();
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySettingGroupCharacteristicItem.this.finish();
			}
		});
		this.mVHAHeader.setRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySettingGroupCharacteristicItem.this.showInputDialog(
						ActivitySettingGroupCharacteristicItem.this.getText(R.string.add_characteristic_item).toString(),
						"",
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								CharacteristicItem characteristicItem = new CharacteristicItem(inputMsg, mCharacteristic);
								int dbRs = ActivitySettingGroupCharacteristicItem.this.getmApp().getmDbManager().upsertCharacteristicItem(characteristicItem);
								if (dbRs==SFGlobal.DB_MSG_OK) {
									ActivitySettingGroupCharacteristicItem.this.mCharacteristic.getmCharacteristicItemArray().add(
											new CharacteristicItem(inputMsg, mCharacteristic)
											);
									mAdapterSettingGroupCharacteristicItem.notifyDataSetChanged();
								} else if (dbRs == SFGlobal.DB_MSG_SAME_COLUMN) {
									ActivitySettingGroupCharacteristicItem.this.showToast(R.string.same_characteristic_item);
								} else {
									ActivitySettingGroupCharacteristicItem.this.showToast(R.string.system_error);
								}
							}
						});
			}
		});
	}

	private void setCharacteristicName () {
		this.mVHAHeader.setTitleText(this.mCharacteristic.getmCharacteristicName());
	}
}
