package sf.tools.chart.entity;

public class SFLineChartEntity {
	public static final String TAG = "SFLineChartEntity";

	public String title = "";
	public float value = 0.0f;

	public SFLineChartEntity(String title, float value) {
		this.title = title;
		this.value = value;
	}

	public boolean isInitialized() {
		if ("".equals(this.title) || title==null) {
			return false;
		} else {
			return true;
		}
	}
}
