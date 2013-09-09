package sf.tools.peddlers;

import sf.tools.peddlers.adapter.AdapterSettingGroupCharacteristicItem;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.utils.SFGlobal;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActivitySettingGroupCharacteristicItem extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCharacteristicItem";

	private Characteristic mCharacteristic = null;

	private Button btnBack = null;
	private TextView tvCharacteristicName = null;
	private Button btnAdd = null;
	private ListView lvCharacteristicItem = null;

	private AdapterSettingGroupCharacteristicItem mAdapterSettingGroupCharacteristicItem = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
	    Intent intent = this.getIntent();
	    this.mCharacteristic = (Characteristic) intent.getSerializableExtra(SFGlobal.EXTRA_CHARACTERISTIC);
		this.mAdapterSettingGroupCharacteristicItem = new AdapterSettingGroupCharacteristicItem(this, this.mCharacteristic.getmCharacteristicItemArray());
		super.initData();
	}

	@Override
	protected void initView() {
		this.setContentView(R.layout.activity_setting_group_characteristic_item);

		this.btnBack = (Button) this.findViewById(R.id.btnBack);
		this.tvCharacteristicName = (TextView) this.findViewById(R.id.tvCharaceristicName);
		this.btnAdd = (Button) this.findViewById(R.id.btnAdd);
		this.lvCharacteristicItem = (ListView) this.findViewById(R.id.lvCharacteristicItem);

		this.setCharacteristicName();
		this.lvCharacteristicItem.setAdapter(mAdapterSettingGroupCharacteristicItem);
		super.initView();
	}

	@Override
	protected void setListener() {
		this.btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySettingGroupCharacteristicItem.this.finish();
			}
		});
		this.btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySettingGroupCharacteristicItem.this.showInputDialog(
						ActivitySettingGroupCharacteristicItem.this.getText(R.string.add_characteristic_item).toString(),
						"",
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								ActivitySettingGroupCharacteristicItem.this.mCharacteristic.getmCharacteristicItemArray().add(
										new CharacteristicItem(inputMsg, mCharacteristic)
										);
							}
						});
			}
		});
		super.setListener();
	}

	private void setCharacteristicName () {
		this.tvCharacteristicName.setText(this.mCharacteristic.getmCharacteristicName());
	}
}
