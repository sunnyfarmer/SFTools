package sf.tools.chart;

import sf.log.SFLog;
import sf.math.SFMath;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SFChart extends View {
	public static final String TAG = "SFChart";

	protected Paint mPaint = new Paint();

	//padding of chart
	protected int mXPaddingLeft = 10;
	protected int mXPaddingRight = 10;
	protected int mYPaddingTop = 10;
	protected int mYPaddingBottom = 10;

	// property of X\Y AXIS
	protected int mStrokeColorOfAxis = Color.GRAY;
	protected float mStrokeSizeOfAxis = 2.0f;
	protected int mTextColorOfAxis = Color.BLACK;
	protected float mTextSizeOfAxis = 12.0f;
	protected Point mBeginPointOfXAxis = new Point();
	protected Point mEndPointOfXAxis = new Point();
	protected float mPixelsOfXAxis = 0.0f;
	protected Point mBeginPointOfYAxis = new Point();
	protected Point mEndPointOfYAxis = new Point();
	protected float mPixelsOfYAxis = 0.0f;

	public SFChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		SFLog.d(TAG, "SFChart onDraw");

		this.drawXAxis(canvas);
		this.drawYAxis(canvas);
	}

	private void drawXAxis(Canvas canvas) {
		int width = this.getWidth();
		int height = this.getHeight();
//		int width = canvas.getWidth();
//		int height = canvas.getHeight();

		this.mPaint.setStrokeWidth(this.mStrokeSizeOfAxis);
		this.mPaint.setColor(this.mStrokeColorOfAxis);

		//calculate property of X Axis
		this.mBeginPointOfXAxis.x = this.mXPaddingLeft;
		this.mBeginPointOfXAxis.y = height-this.mYPaddingBottom;
		this.mEndPointOfXAxis.x = width-this.mXPaddingRight;
		this.mEndPointOfXAxis.y = height-this.mYPaddingBottom;

		//calculate pixels of X Axis
		this.mPixelsOfXAxis = (float)SFMath.lengthOfLine(this.mBeginPointOfXAxis, this.mEndPointOfXAxis);

		//draw x AXIS
		canvas.drawLine(
				this.mBeginPointOfXAxis.x,
				this.mBeginPointOfXAxis.y,
				this.mEndPointOfXAxis.x,
				this.mEndPointOfXAxis.y,
				this.mPaint
				);
		//draw the angle of X AXIS
		canvas.drawLine(
				this.mEndPointOfXAxis.x,
				this.mEndPointOfXAxis.y,
				this.mEndPointOfXAxis.x-5,
				this.mEndPointOfXAxis.y-5,
				this.mPaint
				);
		canvas.drawLine(
				this.mEndPointOfXAxis.x,
				this.mEndPointOfXAxis.y,
				this.mEndPointOfXAxis.x-5,
				this.mEndPointOfXAxis.y+5,
				this.mPaint
				);
	}

	private void drawYAxis(Canvas canvas) {
//		int height = canvas.getHeight();
		int width = this.getWidth();
		int height = this.getHeight();

		this.mPaint.setStrokeWidth(this.mStrokeSizeOfAxis);
		this.mPaint.setColor(this.mStrokeColorOfAxis);

		//calculate property of X Axis
		this.mBeginPointOfYAxis.x = this.mXPaddingLeft;
		this.mBeginPointOfYAxis.y = height-this.mYPaddingBottom;
		this.mEndPointOfYAxis.x = this.mXPaddingLeft;
		this.mEndPointOfYAxis.y = this.mYPaddingTop;

		//calculate pixels of Y Axis
		this.mPixelsOfYAxis = (float) SFMath.lengthOfLine(this.mBeginPointOfYAxis, this.mEndPointOfYAxis);

		//draw y AXIS
		canvas.drawLine(
				this.mBeginPointOfYAxis.x,
				this.mBeginPointOfYAxis.y,
				this.mEndPointOfYAxis.x,
				this.mEndPointOfYAxis.y,
				this.mPaint
				);
		//draw the andgle of Y AXIS
		canvas.drawLine(
				this.mEndPointOfYAxis.x,
				this.mEndPointOfYAxis.y,
				this.mEndPointOfYAxis.x-5,
				this.mEndPointOfYAxis.y+5,
				this.mPaint
				);
		canvas.drawLine(
				this.mEndPointOfYAxis.x,
				this.mEndPointOfYAxis.y,
				this.mEndPointOfYAxis.x+5,
				this.mEndPointOfYAxis.y+5,
				this.mPaint
				);
	}
}
