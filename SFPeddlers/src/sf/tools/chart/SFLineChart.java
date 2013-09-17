package sf.tools.chart;

import java.util.ArrayList;

import sf.log.SFLog;
import sf.math.SFMath;
import sf.tools.chart.entity.SFLineChartEntity;
import sf.tools.chart.listener.SFLineChartOnGestureListener;
import sf.utils.SFAndroidSize;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;

public class SFLineChart extends SFYValueChart {
	public static final String TAG = "SFLineChart";

	private ArrayList<SFLineChartEntity> mLinentityList = null;

	private float mSizeOfLine = 2.0f;
	private int mColorOfLine = Color.BLACK;
	private float mSizeOfPointInLine = 8.0f;
	private int mColorOfPointInLine = Color.RED;

	private int mNumOfDisplayingEntity = 0;
	private int mIndexOfBeginDisplayingEntity = 0;

	public SFLineChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (this.isInEditMode()) {
			return;
		}

//		//TODO: to be deleted
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋1", 15.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋2", 25.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋3", 35.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋4", 45.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋5", 55.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋6", 60.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋7", 65.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋8", 75.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋9", 85.0f));
//		this.addLineChartEntity(new SFLineChartEntity("拖鞋10", 15.0f));
//		this.setEntityRange(10.0f, 85.0f);
//		this.setmStepsOfValue(10);
//		this.setmNumOfDisplayingEntity(4);
//		this.setmIndexOfBeginDisplayingEntity(0);

		this.setOnTouchListener(new SFLineChartOnGestureListener(context, this));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.drawSteps(canvas);
		this.drawTitle(canvas);
		this.drawLines(canvas);
	}

	private void drawSteps(Canvas canvas) {
		if (this.getmLinentityList()==null || this.getmLinentityList().size()<=0) {
			return;
		}
		//init the paint
		this.mPaint.setStrokeWidth(this.mStrokeSizeOfAxis);
		this.mPaint.setColor(this.mStrokeColorOfAxis);

		//draw step
		float pixelsOfEachStep = this.mPixelsOfXAxis / (this.getmNumOfDisplayingEntity()+1);
		for (int cot = 0; cot < this.getmNumOfDisplayingEntity(); cot++) {
			float xBeginOfStep = this.mBeginPointOfXAxis.x + pixelsOfEachStep * (cot+1);
			float yBeginOfStep = this.mBeginPointOfXAxis.y;
			float xEndOfStep = xBeginOfStep;
			float yEndOfStep = yBeginOfStep - 5;
			canvas.drawLine(xBeginOfStep, yBeginOfStep, xEndOfStep, yEndOfStep, this.mPaint);
		}
	}

	private void drawTitle(Canvas canvas) {
		if (this.getmLinentityList()==null || this.getmLinentityList().size()<=0) {
			return;
		}
		int height = this.getHeight();
		//init the paint
		this.mPaint.setTextSize(SFAndroidSize.dp2Px((Activity)this.getContext(), this.mTextSizeOfAxis));
		this.mPaint.setColor(this.mTextColorOfAxis);

		//draw step
		float pixelsOfEachStep = this.mPixelsOfXAxis / (this.getmNumOfDisplayingEntity()+1);
		float maxHeightOfTitle = 0.0f;
		for (int cot = 0; cot < this.getmNumOfDisplayingEntity(); cot++) {
			//calculate the size of Title
			int indexOfEntity = this.getmIndexOfBeginDisplayingEntity()+cot;
			String title = this.getmLinentityList().get(indexOfEntity).title;
			Rect rectOfTitle = SFAndroidSize.textSize(mPaint, title);

			//record the max height of title
			if (maxHeightOfTitle < rectOfTitle.height()) {
				maxHeightOfTitle = rectOfTitle.height();
			}

			float x = this.mBeginPointOfXAxis.x + pixelsOfEachStep * (cot+1) - (rectOfTitle.width()/2);
			float y = height;
			canvas.drawText(title, x, y, this.mPaint);
		}

		if (this.mYPaddingBottom < maxHeightOfTitle) {
			this.mYPaddingBottom = (int) Math.ceil(maxHeightOfTitle);
			this.invalidate();
		}
	}

	private void drawLines(Canvas canvas) {
		if (this.getmLinentityList()==null || this.getmLinentityList().size()<=0) {
			return;
		}
		ArrayList<PointF> pointArray = new ArrayList<PointF>();
		//draw point
		this.mPaint.setStrokeWidth(this.mSizeOfPointInLine);
		this.mPaint.setColor(this.mColorOfPointInLine);
		float pixelsOfEachStep = this.mPixelsOfXAxis / (this.getmNumOfDisplayingEntity()+1);
		for (int cot = 0; cot < this.getmNumOfDisplayingEntity(); cot++) {
			int indexOfEntity = this.getmIndexOfBeginDisplayingEntity()+cot;
			SFLineChartEntity lineChartEntity = this.mLinentityList.get(indexOfEntity);
			float xOfPoint = this.mBeginPointOfXAxis.x + pixelsOfEachStep * (cot+1);
			float yOfPoint = 
					this.mBeginPointOfYAxis.y - 
					this.mPixelsOfYAxis * (lineChartEntity.value-this.mYDisplayMinValue)/(this.mYDisplayMaxValue-this.mYDisplayMinValue);
			canvas.drawPoint(xOfPoint, yOfPoint, this.mPaint);

			//draw value
			String valueText = String.valueOf(lineChartEntity.value);
			Rect sizeOfText = SFAndroidSize.textSize(mPaint, valueText);
			canvas.drawText(valueText, xOfPoint-sizeOfText.width()/2, yOfPoint-this.mSizeOfPointInLine, mPaint);

			//record the points
			pointArray.add(new PointF(xOfPoint, yOfPoint));
		}
		//draw line
		this.mPaint.setStrokeWidth(this.mSizeOfLine);
		this.mPaint.setColor(this.mColorOfLine);
		for (int cot = 0; cot < pointArray.size()-1; cot++) {
			PointF p1 = pointArray.get(cot);
			PointF p2 = pointArray.get(cot+1);
			p1.x = this.makeDataBackIntoRange(this.mBeginPointOfXAxis.x, this.mEndPointOfXAxis.x, p1.x);
			p1.y = this.makeDataBackIntoRange(this.mEndPointOfYAxis.y, this.mBeginPointOfYAxis.y, p1.y);
			p2.x = this.makeDataBackIntoRange(this.mBeginPointOfXAxis.x, this.mEndPointOfXAxis.x, p2.x);
			p2.y = this.makeDataBackIntoRange(this.mEndPointOfYAxis.y, this.mBeginPointOfYAxis.y, p2.y);
			canvas.drawLine(p1.x, p1.y, p2.x, p2.y, this.mPaint);
		}
	}

	private float makeDataBackIntoRange(float min, float max, float data) {
		float rs = data;

		//先排大小
		float[] minMax = SFMath.getMinMax(min, max);
		min = minMax[0];
		max = minMax[1];

		//如果小于min,则让rs等于min;如果大于max,则让rs等于max
		if (!SFMath.dataInRange(min, max, data)) {
			if (data < min) {
				rs = min;
			} else if (data > max){
				rs = max;
			}
		}

		return rs;
	}

	public boolean addLineChartEntity(SFLineChartEntity entity) {
		if (entity.isInitialized()) {
			for (SFLineChartEntity lineChartEntity : this.getmLinentityList()) {
				if (lineChartEntity.title.equals(entity.title)) {
					return false;
				}
			}
			this.getmLinentityList().add(entity);
			return true;
		} else {
			return false;
		}
	}

	public void clearLineEntityList() {
		if (this.mLinentityList!=null) {
			this.mLinentityList.clear();
		}
	}

	public ArrayList<SFLineChartEntity> getmLinentityList() {
		if (this.mLinentityList==null) {
			this.mLinentityList = new ArrayList<SFLineChartEntity>();
		}
		return this.mLinentityList;
	}

	public int getmNumOfDisplayingEntity() {
		return mNumOfDisplayingEntity;
	}
	public int getmIndexOfBeginDisplayingEntity() {
		return mIndexOfBeginDisplayingEntity;
	}
	public void setmNumOfDisplayingEntity(int mNumOfDisplayingEntity) {
		if (mNumOfDisplayingEntity > this.getmLinentityList().size()) {
			mNumOfDisplayingEntity = this.getmLinentityList().size();
		} else if (mNumOfDisplayingEntity < 1) {
			mNumOfDisplayingEntity = 1;
		}
		this.mNumOfDisplayingEntity = mNumOfDisplayingEntity;
		this.invalidate();
	}
	public void setmIndexOfBeginDisplayingEntity(int mIndexOfBeginDisplayingEntity) {
		SFLog.d(TAG, "index:"+mIndexOfBeginDisplayingEntity+", size:"+this.mLinentityList.size());
		if (mIndexOfBeginDisplayingEntity >= 0 && mIndexOfBeginDisplayingEntity < this.mLinentityList.size()-this.getmNumOfDisplayingEntity()) {
			this.mIndexOfBeginDisplayingEntity = mIndexOfBeginDisplayingEntity;
		}
		this.invalidate();
	}
}
