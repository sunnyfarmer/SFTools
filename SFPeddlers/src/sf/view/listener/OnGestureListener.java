package sf.view.listener;

import sf.log.SFLog;
import sf.math.SFMath;
import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnGestureListener implements OnTouchListener {
	public static final String TAG = "OnGestureListener";

	private GestureDetector mGestureDetector = null;
	private Context mContext = null;

	private PointF[] mPinchBeginPointArray = new PointF[2];
	private PointF[] mPinchEndPointArray = new PointF[2];

	public OnGestureListener(Context context) {
		this.mContext = context;
		this.mGestureDetector = new GestureDetector(this.mContext, new GestureListener());
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (this.mGestureDetector.onTouchEvent(event)) {
			//上\下\左\右滑动
			return true;
		}
		if (this.pinchDetector(v, event)) {
			//缩放手势
			return true;
		}
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

	public boolean pinchDetector(View v, MotionEvent event) {
		int eventAction = event.getAction();
		int pointerCount = event.getPointerCount();
		SFLog.d(TAG, "action : " + eventAction);
		switch (eventAction) {
		case MotionEvent.ACTION_DOWN:
			SFLog.d(TAG, "ACTION_DOWN");
			if (pointerCount==2) {
				SFLog.d(TAG, "pinch begin");
				this.mPinchBeginPointArray[0].x = event.getX(0);
				this.mPinchBeginPointArray[0].y = event.getY(0);
				this.mPinchBeginPointArray[1].x = event.getX(1);
				this.mPinchBeginPointArray[1].y = event.getY(1);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (pointerCount==2) {
				SFLog.d(TAG, "pinch move");
				this.mPinchEndPointArray[0].x = event.getX(0);
				this.mPinchEndPointArray[0].y = event.getY(0);
				this.mPinchEndPointArray[1].x = event.getX(1);
				this.mPinchEndPointArray[1].y = event.getY(1);

				double originDistance = SFMath.lengthOfLine(this.mPinchBeginPointArray[0], this.mPinchBeginPointArray[1]);
				double nowDistance = SFMath.lengthOfLine(this.mPinchEndPointArray[0], this.mPinchEndPointArray[1]);
				float scale = (float) (nowDistance / originDistance);
				float scaleRatio = Math.abs(scale-1.0f) * 0.1f;
				if (scale > 1.0f) {
					scale = 1.0f + scaleRatio;
				} else {
					scale = 1.0f - scaleRatio;
				}
				onPinch(scale);
			}
			break;
		default:
			break;
		}
		return false;
	}
	public boolean onPinch(float scale) {
		return false;
	}
}
