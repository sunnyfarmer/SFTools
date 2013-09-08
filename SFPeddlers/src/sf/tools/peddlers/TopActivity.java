package sf.tools.peddlers;

import sf.tools.peddlers.viewholder.activity.VHAFooter;
import sf.tools.peddlers.viewholder.activity.VHAHeader;

public class TopActivity extends BaseActivity {
	public static final String TAG = "TopActivity";

	protected VHAHeader mVHAHeader = null;
	protected VHAFooter mVHAFooter = null;

	@Override
	protected void initView() {
		super.initView();
		this.mVHAHeader = new VHAHeader(this);
		this.mVHAFooter = new VHAFooter(this);
	}
}
