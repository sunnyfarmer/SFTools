package sf.tools.peddlers.test;

import sf.tools.peddlers.db.DBController;
import android.content.Context;

public abstract class TestApp {
	public static final String TAG = "TestApp";
	protected Context mContext = null;
	protected DBController mDBController = null;

	public TestApp(Context context) {
		this.mContext = context;
	}
	public abstract void test();

	public static void testAll(Context context) {
		new DBSettingGroupTest(context).test();
	}
}
