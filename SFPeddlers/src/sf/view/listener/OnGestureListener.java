package sf.view.listener;

import sf.log.SFLog;
import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnGestureListener implements OnTouchListener {
	public static final String TAG = "OnGestureListener";

	private GestureDetector mGestureDetector = null;
	private ScaleGestureDetector mScaleGestureDetector = null;
	private Context mContext = null;

	public OnGestureListener(Context context) {
		this.mContext = context;
		this.mGestureDetector = new GestureDetector(this.mContext, new GestureListener());
		this.mScaleGestureDetector = new ScaleGestureDetector(this.mContext, new ScaleListener());
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (this.mGestureDetector.onTouchEvent(event)) {
			//上\下\左\右滑动
			return true;
		}
		if (this.mScaleGestureDetector.onTouchEvent(event)) {
			//缩放手势
			return true;
		}
		return false;
	}

	public boolean onSwipeTop() {
		return false;
	}
	public boolean onSwipeBottom() {
		return false;
	}
	public boolean onSwipeLeft() {
		return false;
	}
	public boolean onSwipeRight() {
		return false;
	}
	public boolean onPinch(float scale, PointF focusPoint) {
		return false;
	}

	private final class GestureListener extends SimpleOnGestureListener {
		private static final int SWIPE_THRESHOLD = 100;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			boolean result = false;
			float diffY = e2.getY() - e1.getY();
			float diffX = e2.getX() - e1.getX();
			if (Math.abs(diffX) > Math.abs(diffY)) {//swipe left/right
				if (Math.abs(diffX) > SWIPE_THRESHOLD &&
						Math.abs(velocityX)>SWIPE_VELOCITY_THRESHOLD) {//横向距离\速度达标
					if (diffX>0) {
						result = onSwipeRight();
					} else {
						result = onSwipeLeft();
					}
				}
			} else {//swipe top/bottom
				if (Math.abs(diffY)>SWIPE_THRESHOLD &&
						Math.abs(velocityY)>SWIPE_VELOCITY_THRESHOLD) {//纵向距离\速度达标
					if (diffY>0) {
						result = onSwipeBottom();
					} else {
						result = onSwipeTop();
					}
				}
			}
			return result;
		}
	}

	private final class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float scaleFactor = detector.getScaleFactor();
			PointF focusPoint = new PointF(detector.getFocusX(), detector.getFocusY());
			return onPinch(scaleFactor, focusPoint);
		}
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			return true;
		}
		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
		}
	}
}
