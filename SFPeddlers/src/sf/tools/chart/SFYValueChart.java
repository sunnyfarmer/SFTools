package sf.tools.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

public class SFYValueChart extends SFChart {
	public static final String TAG = "SFYValueChart";

	// min\max value on the screen
	protected float mYMinValue = 0.0f;
	protected float mYMaxValue = 9000.0f;

	// min\max value displaying on the screen
	protected float mYDisplayMinValue = mYMinValue;
	protected float mYDisplayMaxValue = mYMaxValue;

	// steps of Y Value
	protected int mStepsOfValue = 10;
	protected float mYValueGap = (mYMaxValue-mYMinValue)/mStepsOfValue;

	protected float mScale = 1.0f;

	public SFYValueChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		this.drawSteps(canvas);

		this.drawValues(canvas);
	}

	private void drawSteps(Canvas canvas) {
		// draw n-2 steps on the Y Axis Of the chart
		this.mPaint.setStrokeWidth(this.mStrokeSizeOfAxis);
		this.mPaint.setColor(this.mStrokeColorOfAxis);
		for (int cot = 1; cot < this.mStepsOfValue; cot++) {
			float xBeginOfStep = this.mBeginPointOfYAxis.x;
			float yBeginOfStep = this.mBeginPointOfYAxis.y-cot*this.mPixelsOfYAxis/this.mStepsOfValue;
			float xEndOfStep = this.mBeginPointOfYAxis.x+5;
			float yEndOfStep = yBeginOfStep;
			canvas.drawLine(
					xBeginOfStep,
					yBeginOfStep,
					xEndOfStep,
					yEndOfStep,
					this.mPaint
					);
		}
	}

	private void drawValues(Canvas canvas) {
		this.mPaint.setTextSize(this.mTextSizeOfAxis);
		this.mPaint.setColor(this.mTextColorOfAxis);

		float maxWidthOfValueText = 0.0f;
		for (int cot = 1; cot < this.mStepsOfValue; cot++) {
			// calculate the size of value text
			String valueText = String.valueOf(this.mYDisplayMinValue+cot*this.mYValueGap);
			Rect rectOfValueText = new Rect();
			this.mPaint.getTextBounds(valueText, 0, valueText.length(), rectOfValueText);
			float widthOfValueText = rectOfValueText.width();
			float heightOfValueText = rectOfValueText.height();
			if (widthOfValueText > maxWidthOfValueText) {
				maxWidthOfValueText = widthOfValueText;
			}

			// draw value
			float xOfText = 0;
			float yOfText = this.mBeginPointOfYAxis.y-cot*this.mPixelsOfYAxis/this.mStepsOfValue + heightOfValueText/2;
			canvas.drawText(
					valueText,
					xOfText,
					yOfText,
					this.mPaint
					);
		}

		//if the padding of Y Axis is not bigger than ValueText, make the padding bigger
		maxWidthOfValueText += 1.0f;
		if (this.mXPaddingLeft < maxWidthOfValueText) {
			this.mXPaddingLeft = (int) Math.ceil(maxWidthOfValueText);
			this.invalidate();
		}
	}
}
