package sf.tools.chart.listener;

import android.content.Context;
import android.graphics.PointF;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import sf.log.SFLog;
import sf.tools.chart.SFLineChart;
import sf.view.listener.OnGestureListener;

public class SFLineChartOnGestureListener extends OnGestureListener{
	public static final String TAG = "SFLineChartOnGestureListener";

	private SFLineChart mSFLineChart = null;

	public SFLineChartOnGestureListener(Context context, SFLineChart sfLineChart) {
		super(context);
		this.mSFLineChart = sfLineChart;
	}

//	@Override
//	public boolean onSwipeBottom() {
//		SFLog.d(TAG, "onSwipeBottom");
//		return true;
//	}
	@Override
	public boolean onSwipeLeft() {
		SFLog.d(TAG, "onSwipeLeft");
		this.mSFLineChart.setmIndexOfBeginDisplayingEntity(this.mSFLineChart.getmIndexOfBeginDisplayingEntity()+1);
		return true;
	}
	@Override
	public boolean onSwipeRight() {
		SFLog.d(TAG, "onSwipeRight");
		this.mSFLineChart.setmIndexOfBeginDisplayingEntity(this.mSFLineChart.getmIndexOfBeginDisplayingEntity()-1);
		return true;
	}
//	@Override
//	public boolean onSwipeTop() {
//		SFLog.d(TAG, "onSwipeTop");
//		return true;
//	}

	@Override
	public boolean onPinch(float scale, PointF focusPoint) {
		SFLog.d(TAG, String.format("onPinch : %f, focusX : %f, focusY : %f", scale, focusPoint.x, focusPoint.y));
		if (scale>1.0f) {
			this.mSFLineChart.setmNumOfDisplayingEntity(this.mSFLineChart.getmNumOfDisplayingEntity()+1);
		} else {
			this.mSFLineChart.setmNumOfDisplayingEntity(this.mSFLineChart.getmNumOfDisplayingEntity()-1);
		}
		return true;
	}

}
