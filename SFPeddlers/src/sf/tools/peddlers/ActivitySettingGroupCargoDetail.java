package sf.tools.peddlers;

import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.utils.SFGlobal;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySettingGroupCargoDetail extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCargoDetail";

	private Cargo mCargo = null;

	private Button btnBack = null;
	private TextView tvCargoName = null;
	private ImageView ivCargo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		this.mCargo = (Cargo) this.getIntent().getSerializableExtra(SFGlobal.EXTRA_CARGO);
		super.initData();
	}
	@Override
	protected void initView() {
		this.setContentView(R.layout.activity_setting_group_cargo_detail);

		this.btnBack = (Button) this.findViewById(R.id.btnBack);
		this.tvCargoName = (TextView) this.findViewById(R.id.tvCargoName);
		this.ivCargo = (ImageView) this.findViewById(R.id.ivCargo);

		this.tvCargoName.setText(this.mCargo.getmCargoName());
		super.initView();
	}
	@Override
	protected void setListener() {
		this.btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

		super.setListener();
	}
}
