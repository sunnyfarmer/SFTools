package sf.tools.chart;

import java.util.ArrayList;

import sf.tools.chart.entity.SFLineChartEntity;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class SFLineChart extends SFYValueChart {
	public static final String TAG = "SFLineChart";

	private ArrayList<SFLineChartEntity> mLinentityList = null;

	public SFLineChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.addLineChartEntity(new SFLineChartEntity("ÑªÑ¹Öµ", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("ÑªÑõÖµ", 2500.0f));
		this.addLineChartEntity(new SFLineChartEntity("ÑªÌÇÖµ", 3500.0f));
		this.addLineChartEntity(new SFLineChartEntity("Ö¬·¾ÂÊ", 4500.0f));
		this.addLineChartEntity(new SFLineChartEntity("¹Ç÷À¼¡ÂÊ", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("¼¡ÈâÂÊ", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("Âö²«", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("¹Ç÷À¼¡ÂÊ", 1500.0f));
		this.addLineChartEntity(new SFLineChartEntity("¹Ç÷À¼¡ÂÊ", 1500.0f));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// TODO Auto-generated method stub
	}

	public boolean addLineChartEntity(SFLineChartEntity entity) {
		if (entity.isInitialized()) {
			for (SFLineChartEntity lineChartEntity : this.mLinentityList) {
				if (lineChartEntity.title.equals(entity.title)) {
					return false;
				}
			}
			this.mLinentityList.add(entity);
			return true;
		} else {
			return false;
		}
	}
}
