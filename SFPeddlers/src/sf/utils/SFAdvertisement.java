package sf.utils;

import sf.log.SFLog;
import net.youmi.android.AdManager;
import net.youmi.android.smart.SmartBannerManager;
import net.youmi.android.spot.SpotDialogLinstener;
import net.youmi.android.spot.SpotManager;
import android.content.Context;

public class SFAdvertisement {
	public static final String TAG = "SFAdvertisement";

	public static void init(Context context) {
		AdManager.getInstance(context).init("ec5afc7d80074930","70bde6655e968616", false);
	}

	public static void showSmartBanner(Context context) {
		SmartBannerManager.init(context);
		SmartBannerManager.show(context);
	}

	public static void showSpotAd(Context context) {
		SpotManager.getInstance(context).loadSpotAds();
		SpotManager.getInstance(context).showSpotAds(context, new SpotDialogLinstener() {
			@Override
			public void onShowSuccess() {
				SFLog.d(TAG, "success youmi");
			}
			@Override
			public void onShowFailed() {
				SFLog.d(TAG, "fail youmi");
			}
			
			@Override
			public void onClicked() {
				SFLog.d(TAG, "click youmi");
			}
		});
	}
}
