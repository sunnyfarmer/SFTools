package sf.tools.peddlers.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Set;

import sf.log.SFLog;
import sf.tools.peddlers.R;
import sf.tools.peddlers.SFPeddlersApp;
import sf.utils.SFUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SFBitmapManager {
	public static final String TAG = "SFBitmapManager";

	public static final String DIRECTORY_NAME = "image";

	public static LinkedHashMap<Integer, Bitmap> bitmapArray = new LinkedHashMap<Integer, Bitmap>();
	public static long bitmapBytesCount = 0;
	public static final long MAX_BITMAP_BYTES_COUNT = 12 * 1024 * 1024;

	public static void clearBitmap() {
		//每次清理一半
		if (bitmapBytesCount >= MAX_BITMAP_BYTES_COUNT) {
			int bitmapCount = bitmapArray.size();
			Set<Integer> keyset = bitmapArray.keySet();
			int cot = 0;
			for (Integer integer : keyset) {
				bitmapArray.remove(integer);
				cot++;
				if (cot >= bitmapCount/2) {
					break;
				}
			}
		}
	}
	public static void addBitmapBytes(Bitmap bitmap) {
		long bytesCount = bitmapBytesCount(bitmap);
		bitmapBytesCount += bytesCount;
	}
	public static void updateBitmapBytesCount() {
		Set<Integer> keyArray = bitmapArray.keySet();
		bitmapBytesCount = 0;
		for (Integer integer : keyArray) {
			Bitmap bm = bitmapArray.get(integer);
			long bytesCount = bitmapBytesCount(bm);
			bitmapBytesCount += bytesCount;
		}
	}
	public static Bitmap getBitmapFromArray(int cargoId) {
		Bitmap bitmap = bitmapArray.get(cargoId);
		//将刚取过的图片放在顶端,最后才释放
		bitmapArray.remove(cargoId);
		bitmapArray.put(cargoId, bitmap);
		printKeySet(bitmapArray.keySet());
		return bitmap;
	}
	public static void printKeySet(Set<Integer> keyset) {
		String text = "";
		for (Integer integer : keyset) {
			text += integer+",";
		}
		SFLog.d(TAG, text);
	}
	public static void putBitmapIntoArray(int cargoId, Bitmap bitmap) {
		clearBitmap();
		bitmapArray.put(cargoId, bitmap);
		addBitmapBytes(bitmap);
	}

	public static void saveBitmap(SFPeddlersApp app, Bitmap bitmap,
			int cargoId) {
		if (bitmap != null) {
			FileOutputStream fos;
			try {
				String filename = String.format(Locale.US, "%d.png", cargoId);
				fos = app.openFileOutput(filename, Context.MODE_PRIVATE);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			} catch (FileNotFoundException e) {
				SFLog.e(TAG, e.getMessage(), e);
			}
		}
	}
	public static Bitmap getBitmap(int cargoId, SFPeddlersApp app) {
		Bitmap bitmap = null;
		String filename = String.format(Locale.US, "%d.png", cargoId);
		try {
			bitmap = getBitmapFromArray(cargoId);
			if (bitmap==null) {
				FileInputStream fis = app.openFileInput(filename);
				bitmap = BitmapFactory.decodeStream(fis);
				putBitmapIntoArray(cargoId, bitmap);
			}
		} catch (FileNotFoundException e) {
			bitmap = BitmapFactory.decodeResource(app.getResources(), R.drawable.ic_launcher);
			SFLog.e(TAG, "file not exist : "+filename);
		} catch (Exception e) {
			SFLog.e(TAG, "decode bitmap error : "+cargoId);
		}
		return bitmap;
	}
	public static void removeBitmap(int cargoId, SFPeddlersApp app) {
		String filename = String.format(Locale.US, "%d.png", cargoId);
		app.deleteFile(filename);
	}

	@SuppressLint("NewApi")
	public static long bitmapBytesCount(Bitmap bitmap) {
		long bytesCount = 0;
		if (SFUtils.sdkVersion() >= 12) {
			bytesCount = bitmap.getByteCount();
		} else {
			bytesCount = bitmap.getRowBytes() * bitmap.getHeight();
		}
		return bytesCount;
	}
}
