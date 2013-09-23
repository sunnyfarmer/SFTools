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

	public static final int MAX_REFERENCE_BITMAP_WIDTH = 512;
	public static final int MAX_REFERENCE_BITMAP_HEIGHT = 512;

	/**
	 * 清理Bitmap内存
	 */
	private static void clearBitmap() {
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
	private static void addBitmapBytes(Bitmap bitmap) {
		long bytesCount = bitmapBytesCount(bitmap);
		bitmapBytesCount += bytesCount;
	}
	@SuppressWarnings("unused")
	private static void updateBitmapBytesCount() {
		Set<Integer> keyArray = bitmapArray.keySet();
		bitmapBytesCount = 0;
		for (Integer integer : keyArray) {
			Bitmap bm = bitmapArray.get(integer);
			long bytesCount = bitmapBytesCount(bm);
			bitmapBytesCount += bytesCount;
		}
	}
	private static Bitmap getBitmapFromArray(int cargoId) {
		Bitmap bitmap = bitmapArray.get(cargoId);
		if (bitmap!=null) {
			//将刚取过的图片放在顶端,最后才释放
			bitmapArray.remove(cargoId);
			bitmapArray.put(cargoId, bitmap);
			printKeySet(bitmapArray.keySet());
		}
		return bitmap;
	}
	private static void printKeySet(Set<Integer> keyset) {
		String text = "";
		for (Integer integer : keyset) {
			text += integer+",";
		}
		SFLog.d(TAG, text);
	}
	private static void putBitmapIntoArray(int cargoId, Bitmap bitmap) {
		clearBitmap();
		bitmapArray.put(cargoId, bitmap);
		addBitmapBytes(bitmap);
	}

	/**
	 * 保存Cargo的Bitmap
	 * @param app
	 * @param bitmap
	 * @param cargoId
	 */
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
	/**
	 * 获得Cargo的Bitmap
	 * @param cargoId
	 * @param app
	 * @return
	 */
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
	/**
	 * 删除Cargo的Bitmap
	 * @param cargoId
	 * @param app
	 */
	public static void removeBitmap(int cargoId, SFPeddlersApp app) {
		String filename = String.format(Locale.US, "%d.png", cargoId);
		app.deleteFile(filename);
	}
	/**
	 * 计算Bitmap所占内存
	 * @param bitmap
	 * @return
	 */
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

	public static Bitmap loadBitmap(String filePath, int reqWidth, int reqHeight) {
	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(filePath, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filePath, options);
	}
	public static Bitmap loadBitmap(FileInputStream fis, int reqWidth, int reqHeight) {
	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeStream(fis, null, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeStream(fis, null, options);
	}
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	        // Calculate ratios of height and width to requested height and width
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	
	        // Choose the smallest ratio as inSampleSize value, this will guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }

	    return inSampleSize;
	}
}
