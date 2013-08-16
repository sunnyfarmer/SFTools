package sf.tools.chart.listener;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import sf.log.SFLog;
import sf.view.listener.OnGestureListener;

public class SFLineChartOnGestureListener extends OnGestureListener{
	public static final String TAG = "SFLineChartOnGestureListener";

	public SFLineChartOnGestureListener(Context context) {
		super(context);
	}

//	@Override
//	public boolean onSwipeBottom() {
//		SFLog.d(TAG, "onSwipeBottom");
//		return true;
//	}
//	@Override
//	public boolean onSwipeLeft() {
//		SFLog.d(TAG, "onSwipeLeft");
//		return true;
//	}
//	@Override
//	public boolean onSwipeRight() {
//		SFLog.d(TAG, "onSwipeRight");
//		return true;
//	}
//	@Override
//	public boolean onSwipeTop() {
//		SFLog.d(TAG, "onSwipeTop");
//		return true;
//	}

	@Override
	public boolean onPinch(float scale) {
		SFLog.d(TAG, "onPinch : " + scale);
		return true;
	}

}
