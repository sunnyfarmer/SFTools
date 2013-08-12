package sf.tools.peddlers;

import android.app.Activity;
import android.os.Bundle;

public class TopActivity extends Activity {
	public static final String TAG = "TopActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
}
