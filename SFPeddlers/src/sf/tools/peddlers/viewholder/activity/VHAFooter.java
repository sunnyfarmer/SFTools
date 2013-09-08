package sf.tools.peddlers.viewholder.activity;

import sf.tools.peddlers.ActivityFirstFeeling;
import sf.tools.peddlers.ActivitySettingGroup;
import sf.tools.peddlers.ActivityStatisticsOverView;
import sf.tools.peddlers.R;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VHAFooter {
	public static final String TAG = "VHAFooter";

	private Button btnInSelling = null;
	private Button btnOrganizing = null;
	private Button btnStatistics = null;

	private Activity mActivity = null;

	public VHAFooter(Activity activity) {
		this.mActivity = activity;
		this.initView();
		this.setListener();
	}

	private void initView() {
		this.btnInSelling = (Button) this.mActivity.findViewById(R.id.btnInSelling);
		this.btnOrganizing = (Button) this.mActivity.findViewById(R.id.btnOrganizing);
		this.btnStatistics = (Button) this.mActivity.findViewById(R.id.btnStatistics);
	}

	private void setListener() {
		this.btnInSelling.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoActivity(ActivityFirstFeeling.class);
			}
		});
		this.btnOrganizing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoActivity(ActivitySettingGroup.class);
			}
		});
		this.btnStatistics.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoActivity(ActivityStatisticsOverView.class);
			}
		});
	}
	protected void gotoActivity(Class<?> activityClass) {
		Intent intent = new Intent(this.mActivity, activityClass);
		this.mActivity.startActivity(intent);
	}

	public void setCheckedButton(Button btn) {
		boolean isCheckedButtonFound = false;
		if (this.btnInSelling.equals(btn) || this.btnOrganizing.equals(btn) || this.btnStatistics.equals(btn)) {
			isCheckedButtonFound = true;
		}
		if (!isCheckedButtonFound) {
			return;
		}
		this.btnInSelling.setBackgroundResource(R.drawable.tab_selector);
		this.btnOrganizing.setBackgroundResource(R.drawable.tab_selector);
		this.btnStatistics.setBackgroundResource(R.drawable.tab_selector);
		
		btn.setBackgroundResource(R.drawable.tab_selected_selector);
	}

	
}
