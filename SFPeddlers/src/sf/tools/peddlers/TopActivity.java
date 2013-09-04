package sf.tools.peddlers;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class TopActivity extends Activity {
	public static final String TAG = "TopActivity";

	protected SFPeddlersApp mApp = null;
	protected EditText mEditText = null;
	protected AlertDialog mAlertDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mApp = (SFPeddlersApp)this.getApplicationContext();

		this.initData();
		this.initView();
		this.setListener();
	}
	protected void initData() {
	}
	protected void initView() {
	}
	protected void setListener() {
	}
	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	public void showInputDialog(String title, String defaultInputMsg, final OnInputConfirmedListener onInputConfirmedListener) {
		if (this.mAlertDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			if (this.mEditText==null) {
				this.mEditText = new EditText(this);
			}
			this.mEditText.setId(Integer.MAX_VALUE);
			builder.setView(this.mEditText);
			builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String input = TopActivity.this.mEditText.getText().toString();
					if (onInputConfirmedListener!=null) {
						onInputConfirmedListener.onInputConfirmed(input);
					}
				}
			});
			this.mAlertDialog = builder.create();
		}
		this.mAlertDialog.setTitle(title);
		this.mEditText.setText(defaultInputMsg);
		if (!this.mAlertDialog.isShowing()) {
			this.mAlertDialog.show();
		}
	}
	public interface OnInputConfirmedListener {
		public void onInputConfirmed(String inputMsg);
	}
}
