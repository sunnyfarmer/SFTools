package sf.tools.peddlers;

import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.utils.SFGlobal;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySettingGroupCargoDetail extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCargoDetail";

	private Cargo mCargo = null;

	private ImageView ivCargo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_setting_group_cargo_detail);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		this.mCargo = (Cargo) this.getIntent().getSerializableExtra(SFGlobal.EXTRA_CARGO);
		super.initData();
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.back);
		this.mVHAHeader.hideRight();
		this.mVHAHeader.setTitleText(this.mCargo.getmCargoName());
		this.ivCargo = (ImageView) this.findViewById(R.id.ivCargo);
	}
	@Override
	protected void setListener() {
		super.setListener();
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		this.ivCargo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, SFGlobal.RS_CODE_LOAD_IMAGE);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
