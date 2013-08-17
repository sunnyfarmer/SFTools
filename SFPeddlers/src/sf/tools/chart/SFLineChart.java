package sf.tools.chart;

import java.util.ArrayList;

import sf.tools.chart.entity.SFLineChartEntity;
import sf.tools.chart.listener.SFLineChartOnGestureListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;

public class SFLineChart extends SFYValueChart {
	public static final String TAG = "SFLineChart";

	private ArrayList<SFLineChartEntity> mLinentityList = null;

	private float mSizeOfLine = 2.0f;
	private int mColorOfLine = Color.BLACK;
	private float mSizeOfPointInLine = 5.0f;
	private int mColorOfPointInLine = Color.RED;

	public SFLineChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (this.isInEditMode()) {
			return;
		}

		//TODO: to be deleted
		this.addLineChartEntity(new SFLineChartEntity("拖鞋1", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋2", 2500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋3", 3500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋4", 4500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋5", 5500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋6", 6000.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋7", 6500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋8", 7500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋9", 8500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋10", 1500.0f));

		this.setOnTouchListener(new SFLineChartOnGestureListener(context));
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
		float pixelsOfEachStep = this.mPixelsOfXAxis / (this.getmLinentityList().size()+1);
		for (int cot = 0; cot < this.getmLinentityList().size(); cot++) {
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
		this.mPaint.setTextSize(this.mTextSizeOfAxis);
		this.mPaint.setColor(this.mTextColorOfAxis);

		//draw step
		float pixelsOfEachStep = this.mPixelsOfXAxis / (this.getmLinentityList().size()+1);
		float maxHeightOfTitle = 0.0f;
		for (int cot = 0; cot < this.getmLinentityList().size(); cot++) {
			//calculate the size of Title
			String title = this.getmLinentityList().get(cot).title;
			Rect rectOfTitle = new Rect();
			this.mPaint.getTextBounds(title, 0, title.length(), rectOfTitle);

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
		//TODO
		if (this.getmLinentityList()==null || this.getmLinentityList().size()<=0) {
			return;
		}
		ArrayList<PointF> pointArray = new ArrayList<PointF>();
		//draw point
		this.mPaint.setStrokeWidth(this.mSizeOfPointInLine);
		this.mPaint.setColor(this.mColorOfPointInLine);
		float pixelsOfEachStep = this.mPixelsOfXAxis / (this.getmLinentityList().size()+1);
		for (int cot = 0; cot < this.mLinentityList.size(); cot++) {
			SFLineChartEntity lineChartEntity = this.mLinentityList.get(cot);
			float xOfPoint = this.mBeginPointOfXAxis.x + pixelsOfEachStep * (cot+1);
			float yOfPoint = 
					this.mBeginPointOfYAxis.y - 
					this.mPixelsOfYAxis * (lineChartEntity.value-this.mYDisplayMinValue)/(this.mYDisplayMaxValue-this.mYDisplayMinValue);
			canvas.drawPoint(xOfPoint, yOfPoint, this.mPaint);

			//record the points
			pointArray.add(new PointF(xOfPoint, yOfPoint));
		}
		//draw line
		this.mPaint.setStrokeWidth(this.mSizeOfLine);
		this.mPaint.setColor(this.mColorOfLine);
		for (int cot = 0; cot < pointArray.size()-1; cot++) {
			PointF p1 = pointArray.get(cot);
			PointF p2 = pointArray.get(cot+1);
			canvas.drawLine(p1.x, p1.y, p2.x, p2.y, this.mPaint);
		}
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

	public ArrayList<SFLineChartEntity> getmLinentityList() {
		if (this.mLinentityList==null) {
			this.mLinentityList = new ArrayList<SFLineChartEntity>();
		}
		return this.mLinentityList;
	}
}
