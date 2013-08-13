package sf.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RadioButtonGroup extends RadioGroup{
	public static final String TAG = "RadioButtonGroup";

	private HashMap<String, RadioButton> mRadioButtonMap = new HashMap<String, RadioButton>();

	public RadioButtonGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOrientation(LinearLayout.VERTICAL);
	}

	public void setValues(ArrayList<String> valueArray) {
		this.removeAllViews();
		this.mRadioButtonMap.clear();
		for (String string : valueArray) {
			this.addValue(string);
		}
	}
	public void addValue(String value) {
		RadioButton rb = new RadioButton(this.getContext());
		rb.setText(value);
		rb.setTextColor(Color.BLACK);
		this.mRadioButtonMap.put(value, rb);

		this.addView(rb);

	}

}
