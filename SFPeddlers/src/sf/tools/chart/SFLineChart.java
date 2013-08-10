package sf.tools.chart;

import java.util.ArrayList;

import sf.tools.chart.entity.SFLineChartEntity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

public class SFLineChart extends SFYValueChart {
	public static final String TAG = "SFLineChart";

	private ArrayList<SFLineChartEntity> mLinentityList = null;

	public SFLineChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (this.isInEditMode()) {
			return;
		}
		this.addLineChartEntity(new SFLineChartEntity("拖鞋1", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋2", 2500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋3", 3500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋4", 4500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋5", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋6", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋7", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋8", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋9", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("拖鞋10", 1500.0f));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// TODO Auto-generated method stub
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
		//TODO
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
