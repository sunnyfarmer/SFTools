package sf.tools.peddlers.adapter;

import sf.tools.peddlers.SFPeddlersApp;
import sf.tools.peddlers.TopActivity;
import android.widget.BaseAdapter;

public abstract class SFBaseAdapter extends BaseAdapter {
	public static final String TAG = "SFBaseAdapter";

	protected TopActivity mActivity = null;
	protected SFPeddlersApp mApp = null;

	public SFBaseAdapter(TopActivity activity) {
		this.mActivity = activity;
		this.mApp = this.mActivity.getmApp();
	}
}
