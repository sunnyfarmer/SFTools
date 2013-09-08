package sf.tools.peddlers;

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

	private Button btnBack = null;
	private TextView tvCargoTypeName = null;
	private Button btnFinish = null;
	private EditText etCargoName = null;
	private ImageView ivCargo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		super.initData();

		Intent intent = this.getIntent();
		this.mCargoType = (CargoType) intent
				.getSerializableExtra(SFGlobal.EXTRA_CARGOTYPE);
	}

	@Override
	protected void initView() {
		super.initView();
		this.setContentView(R.layout.activity_setting_group_add_cargo);

		this.btnBack = (Button) this.findViewById(R.id.btnBack);
		this.tvCargoTypeName = (TextView) this
				.findViewById(R.id.tvCargoTypeName);
		this.btnFinish = (Button) this.findViewById(R.id.btnFinish);
		this.etCargoName = (EditText) this.findViewById(R.id.etCargoName);
		this.ivCargo = (ImageView) this.findViewById(R.id.ivCargo);
	}

	@Override
	protected void setListener() {
		super.setListener();

		this.btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		this.btnFinish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

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

        	Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        	ivCargo.setImageBitmap(bitmap);
        }
	}
}
