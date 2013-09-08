package sf.tools.chart;

import sf.math.SFMath;
import sf.utils.SFAndroidSize;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Camera.PreviewCallback;
import android.util.AttributeSet;

public class SFYValueChart extends SFChart {
	public static final String TAG = "SFYValueChart";

	// min\max value on the screen(根据entityMin/entityMax定义出来的Y轴范围)
	protected float mYMinValue = 0.0f;
	protected float mYMaxValue = 10000.0f;

	// min\max value displaying on the screen(即当前界面上显示最大最小值)
	protected float mYDisplayMinValue = mYMinValue;
	protected float mYDisplayMaxValue = mYMaxValue;

	// min\max value of the chart entity(即X轴中要显示的点,最大最小值)
	protected float mEntityYMaxValue = mYDisplayMaxValue;
	protected float mEntityYMinValue = mYDisplayMinValue;

	// steps of Y Value
	protected int mStepsOfValue = 10;
	protected float mYValueGap = (mYDisplayMaxValue-mYDisplayMinValue)/mStepsOfValue;

	public SFYValueChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (this.isInEditMode()) {
			return;
		}

		this.drawSteps(canvas);

		this.drawValues(canvas);
	}

	/**
	 * 绘制Y轴上的刻度
	 * @param canvas
	 */
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

	/**
	 * 绘制Y轴上的刻度对应的值
	 * @param canvas
	 */
	private void drawValues(Canvas canvas) {
		this.mPaint.setTextSize(SFAndroidSize.dp2Px((Activity)this.getContext(), this.mTextSizeOfAxis));
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


	public float getmYMinValue() {
		return mYMinValue;
	}


	public float getmYMaxValue() {
		return mYMaxValue;
	}


	public float getmYDisplayMinValue() {
		return mYDisplayMinValue;
	}


	public float getmYDisplayMaxValue() {
		return mYDisplayMaxValue;
	}


	public float getmEntityYMaxValue() {
		return mEntityYMaxValue;
	}


	public float getmEntityYMinValue() {
		return mEntityYMinValue;
	}
	public int getmStepsOfValue() {
		return mStepsOfValue;
	}
	public void setEntityRange(float entityMinValue, float entityMaxValue) {
		this.mEntityYMinValue = entityMinValue;
		this.mEntityYMaxValue = entityMaxValue;

		float[] totalRange = SFYValueChart.getTotalRangeByEntityRange(this.mEntityYMinValue, this.mEntityYMaxValue, this.mStepsOfValue);
		this.mYMinValue = totalRange[0];
		this.mYMaxValue = totalRange[1];

		this.mYDisplayMaxValue = this.mYMaxValue;
		this.mYDisplayMinValue = this.mYMinValue;

		// update the gap of y vale
		this.mYValueGap = (mYDisplayMaxValue-mYDisplayMinValue)/mStepsOfValue;
	}
	public void setmStepsOfValue(int mStepsOfValue) {
		this.mStepsOfValue = mStepsOfValue;

		// update the "display range" and "y value range"
		this.setEntityRange(this.mEntityYMinValue, this.mEntityYMaxValue);
	}
	private static float[] getTotalRangeByEntityRange(float minEntity, float maxEntity, float steps) {
		float[] range = new float[2];

		float gapOfEntity = (maxEntity-minEntity) / steps;
		int powerOfGapEntity = SFMath.getPower(gapOfEntity);
		double lastPositionOfMax = maxEntity%Math.pow(10.0f, powerOfGapEntity);
		double previousPositionOfMax = (int)(maxEntity/Math.pow(10.0f, powerOfGapEntity)) * Math.pow(10.0f, powerOfGapEntity);
		double lastPositoinOfMin = minEntity%Math.pow(10.0f, powerOfGapEntity);
		double previousPositionOfMin = (int)(minEntity/Math.pow(10.0f, powerOfGapEntity)) * Math.pow(10.0f, powerOfGapEntity);

		if (lastPositionOfMax > 5*Math.pow(10.0f, powerOfGapEntity-1)) {
			range[1] = (float) (previousPositionOfMax + Math.pow(10.f, powerOfGapEntity));
		} else {
			range[1] = (float) (previousPositionOfMax + 5*Math.pow(10.0f, powerOfGapEntity-1));
		}

		if (lastPositoinOfMin > 5*Math.pow(10.0f, powerOfGapEntity-1)) {
			range[0] = (float) (previousPositionOfMin + 5*Math.pow(10.0f, powerOfGapEntity-1));
		} else {
			range[0] = (float) previousPositionOfMin;
		}

		return range;
	}
}
