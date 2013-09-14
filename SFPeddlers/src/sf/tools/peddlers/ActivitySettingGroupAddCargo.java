package sf.tools.peddlers;

import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.utils.SFBitmapManager;
import sf.tools.peddlers.utils.SFGlobal;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class ActivitySettingGroupAddCargo extends TopActivity {
	public static final String TAG = "ActivitySettingGroupAddCargo";

	private CargoType mCargoType = null;

	private EditText etCargoName = null;
	private ImageView ivCargo = null;

	private Bitmap mBitmap = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_setting_group_add_cargo);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		Intent intent = this.getIntent();
		this.mCargoType = (CargoType) intent
				.getSerializableExtra(SFGlobal.EXTRA_CARGOTYPE);
		super.initData();
	}

	@Override
	protected void initView() {
		super.initView();
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.finish);
		this.mVHAHeader.setTitleText(this.mCargoType.getmCargoTypeName());
		this.etCargoName = (EditText) this.findViewById(R.id.etCargoName);
		this.ivCargo = (ImageView) this.findViewById(R.id.ivCargo);
	}

	@Override
	protected void setListener() {
		super.setListener();
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		this.mVHAHeader.setRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Cargo cargo = produceCargo();
				int dbRs = mApp.getmDbManager().upsertCargo(cargo);
				if (dbRs==SFGlobal.DB_MSG_OK) {
					saveImage(cargo.getmCargoId());

					Intent data = new Intent();
					data.putExtra(SFGlobal.EXTRA_CARGO, cargo);
					ActivitySettingGroupAddCargo.this.setResult(RESULT_OK, data);
					finish();
				} else if (dbRs==SFGlobal.DB_MSG_SAME_COLUMN) {
					showToast(R.string.same_cargo_name);
				} else {
					showToast(R.string.system_error);
				}
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
		super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SFGlobal.RS_CODE_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
        	//获得选中图片路径
        	Uri selectedImage = data.getData();
        	String[] filePathColumn = { MediaStore.Images.Media.DATA };
        	Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        	cursor.moveToFirst();
        	int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        	String picturePath = cursor.getString(columnIndex);
        	cursor.close();

        	if (mBitmap!=null && !mBitmap.isRecycled()) {
        		mBitmap.recycle();
        	}
        	mBitmap = BitmapFactory.decodeFile(picturePath);
        	ivCargo.setImageBitmap(mBitmap);
        }
	}

	protected void saveImage(int cargoId) {
		SFBitmapManager.saveBitmap(mApp, mBitmap, cargoId);
	}
	protected Cargo produceCargo() {
		String cargoName = this.etCargoName.getText().toString().trim();
		if (cargoName.equals("")) {
			this.showToast(R.string.cargo_name_must_be_filled);
			return null;
		}
		Cargo cargo = new Cargo(cargoName, mCargoType);
		return cargo;
	}
}
