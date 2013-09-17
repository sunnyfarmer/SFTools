package sf.utils;

import java.util.UUID;

public class SFUtils {
	public static final String TAG = "SFUtils";

	public static String produceUniqueId() {
		String uniqueId = UUID.randomUUID().toString();
		return uniqueId;
	}

	public static int sdkVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}
}
