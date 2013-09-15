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
import android.widget.LinearLayout;

public class VHAFooter {
	public static final String TAG = "VHAFooter";

	private LinearLayout footer = null;
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
		this.footer = (LinearLayout) this.mActivity.findViewById(R.id.footer);
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
		if (!this.mActivity.getClass().equals(activityClass)) {
			Intent intent = new Intent(this.mActivity, activityClass);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.mActivity.startActivity(intent);
		}
	}

	public void setCheckedButton(Button btn) {
		boolean isCheckedButtonFound = false;
		if (this.btnInSelling.equals(btn) || this.btnOrganizing.equals(btn) || this.btnStatistics.equals(btn)) {
			isCheckedButtonFound = true;
		}
		if (!isCheckedButtonFound) {
			return;
		}
		this.btnInSelling.setBackgroundResource(R.drawable.selector_tab);
		this.btnOrganizing.setBackgroundResource(R.drawable.selector_tab);
		this.btnStatistics.setBackgroundResource(R.drawable.selector_tab);
		
		btn.setBackgroundResource(R.drawable.selector_tab_selected);
	}

	public Button getBtnInSelling() {
		return btnInSelling;
	}
	public Button getBtnOrganizing() {
		return btnOrganizing;
	}
	public Button getBtnStatistics() {
		return btnStatistics;
	}
	public void showFooter() {
		this.footer.setVisibility(View.VISIBLE);
	}
	public void hideFooter() {
		this.footer.setVisibility(View.GONE);
	}
}
