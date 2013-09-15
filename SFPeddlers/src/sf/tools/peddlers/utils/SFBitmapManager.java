package sf.tools.peddlers.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import sf.log.SFLog;
import sf.tools.peddlers.R;
import sf.tools.peddlers.SFPeddlersApp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SFBitmapManager {
	public static final String TAG = "SFBitmapManager";

	public static final String DIRECTORY_NAME = "image";

	public static void saveBitmap(SFPeddlersApp app, Bitmap bitmap,
			int cargoId) {
		if (bitmap != null) {
			FileOutputStream fos;
			try {
				String filename = String.format("%d.png", cargoId);
				fos = app.openFileOutput(filename, Context.MODE_PRIVATE);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			} catch (FileNotFoundException e) {
				SFLog.e(TAG, e.getMessage(), e);
			}
			
		}
	}
	public static Bitmap getBitmap(int cargoId, SFPeddlersApp app) {
		Bitmap bitmap = null;
		String filename = String.format("%d.png", cargoId);
		try {
			FileInputStream fis = app.openFileInput(filename);
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			bitmap = BitmapFactory.decodeResource(app.getResources(), R.drawable.ic_launcher);
			SFLog.e(TAG, "file not exist : "+filename);
		}
		return bitmap;
	}
	public static void removeBitmap(int cargoId, SFPeddlersApp app) {
		String filename = String.format("%d.png", cargoId);
		app.deleteFile(filename);
	}
}
