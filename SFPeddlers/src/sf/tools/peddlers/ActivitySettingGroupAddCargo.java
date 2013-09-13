package sf.tools.peddlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import sf.log.SFLog;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
				produceCargo();
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

	protected void saveImage() {
		
	}
	protected Cargo produceCargo() {
		String cargoName = this.etCargoName.getText().toString().trim();
		if (cargoName.equals("")) {
			this.showToast(R.string.cargo_name_must_be_filled);
			return null;
		}
		if (this.mBitmap!=null) {
			try {
				File file = new File(this.getFilesDir().getAbsolutePath()+"/bitmap.png");
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				this.mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			} catch (FileNotFoundException e) {
				SFLog.e(TAG, "new FileOutputStream File error");
			}

		}
		Cargo cargo = new Cargo(cargoName, mCargoType);
		return cargo;
	}
}
