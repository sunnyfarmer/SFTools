package sf.tools.peddlers.viewholder.activity;

import sf.tools.peddlers.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VHAHeader {
	public static final String TAG = "VHAHeader";

	private Button btnLeft = null;
	private TextView tvTitle = null;
	private Button btnRight = null;

	private Activity mActivity = null;

	public VHAHeader(Activity activity) {
		this.mActivity = activity;
		this.initView();
	}
	private void initView() {
		this.btnLeft = (Button) this.mActivity.findViewById(R.id.btnLeft);
		this.btnRight = (Button) this.mActivity.findViewById(R.id.btnRight);
		this.tvTitle = (TextView) this.mActivity.findViewById(R.id.tvTitle);
	}

	public void showLeft() {
		this.btnLeft.setVisibility(View.VISIBLE);
	}
	public void hideLeft() {
		this.btnLeft.setVisibility(View.INVISIBLE);
	}
	public void setLeftText(String text) {
		this.btnLeft.setText(text);
	}
	public void setLeftText(int resId) {
		this.setLeftText(this.mActivity.getText(resId).toString());
	}
	public void setLeftOnClickListener(OnClickListener onClickListener) {
		this.btnLeft.setOnClickListener(onClickListener);
	}
	public void showRight() {
		this.btnRight.setVisibility(View.VISIBLE);
	}
	public void hideRight() {
		this.btnRight.setVisibility(View.INVISIBLE);
	}
	public void setRightText(String text) {
		this.btnRight.setText(text);
	}
	public void setRightText(int resId) {
		this.setRightText(this.mActivity.getText(resId).toString());
	}
	public void setRightOnClickListener(OnClickListener onClickListener) {
		this.btnRight.setOnClickListener(onClickListener);
	}
	public void setTitleText(String title) {
		this.tvTitle.setText(title);
	}
	public void setTitleText(int resId) {
		this.setTitleText(this.mActivity.getText(resId).toString());
	}
}
