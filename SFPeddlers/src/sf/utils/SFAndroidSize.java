package sf.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class SFAndroidSize {
	public static final String TAG = "SFANdroidSize";

	public static float dp2Px(Activity mContext, float dps) {
		DisplayMetrics metrics = new DisplayMetrics();
		float density = metrics.density;
		mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		density = metrics.density;
		float pixels = dps * density;
		return pixels;
	}
}
