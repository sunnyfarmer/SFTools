package sf.tools.peddlers;

import sf.tools.peddlers.model.Cargo;
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
import android.widget.ImageView;

public class ActivitySettingGroupCargoDetail extends TopActivity {
	public static final String TAG = "ActivitySettingGroupCargoDetail";

	private Cargo mCargo = null;

	private ImageView ivCargo = null;
	private Bitmap mBitmap = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_setting_group_cargo_detail);
	    super.onCreate(savedInstanceState);
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

	@Override
	protected void initData() {
		this.mCargo = this.mApp.getmEdittingCargo();
		super.initData();
	}
	@Override
	protected void initView() {
		super.initView();
		this.mVHAFooter.setCheckedButton(this.mVHAFooter.getBtnOrganizing());
		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setRightText(R.string.back);
		this.mVHAHeader.hideRight();
		this.mVHAHeader.setTitleText(this.mCargo.getmCargoName());
		this.ivCargo = (ImageView) this.findViewById(R.id.ivCargo);

		this.mBitmap = SFBitmapManager.getBitmap(this.mCargo.getmCargoId(), mApp);
		this.ivCargo.setImageBitmap(this.mBitmap);
	}
	@Override
	protected void setListener() {
		super.setListener();
		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mApp.getmDbManager().upsertCargo(mCargo);
				SFBitmapManager.saveBitmap(mApp, mBitmap, mCargo.getmCargoId());

				Intent data = new Intent();
				mApp.setmEdittingCargo(mCargo);
				setResult(RESULT_OK, data);
				finish();
			}
		});
		this.mVHAHeader.getTvTitle().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showInputDialog(
						getText(R.string.please_input_new_cargo_name).toString(),
						"",
						new OnInputConfirmedListener() {
							@Override
							public void onInputConfirmed(String inputMsg) {
								if (inputMsg.equals("")) {
									showToast(R.string.must_be_filled);
									return;
								}
								Cargo cargo = mApp.getmDbManager().getmDBCargo().query(mCargo.getmCargoType(), inputMsg);
								if (cargo!=null) {
									showToast(R.string.same_cargo_name);
									return;
								}
								mCargo.setmCargoName(inputMsg);
								mVHAHeader.setTitleText(mCargo.getmCargoName());
							}
						});
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
	public void onBackPressed() {
		this.mVHAHeader.getBtnLeft().performClick();
	}
}
