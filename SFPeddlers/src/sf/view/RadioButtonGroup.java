package sf.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class RadioButtonGroup extends LinearLayout{
	public static final String TAG = "RadioButtonGroup";

	private HashMap<String, RadioButton> mRadioButtonMap = new HashMap<String, RadioButton>();
	private String mCheckedValue = null;
	private OnCheckedChangeListener mCheckedChangeListener = null;

	public RadioButtonGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOrientation(LinearLayout.VERTICAL);
	}

	public void setValues(ArrayList<String> valueArray) {
		this.mRadioButtonMap.clear();
		this.removeAllViews();
		for (String string : valueArray) {
			this.addValue(string);
		}
	}
	public void addValue(String value) {
		RadioButton rb = new RadioButton(this.getContext());
		rb.setText(value);
		rb.setTextColor(Color.BLACK);
		rb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setCheckedValue(((RadioButton) v).getText().toString());
			}
		});
		this.mRadioButtonMap.put(value, rb);

		this.addView(rb);
	}

	public void setCheckedValue(String value) {
		this.mCheckedValue = value;

		for (String title : mRadioButtonMap.keySet()) {
			RadioButton radioButton = mRadioButtonMap.get(title);
			if (title.equals(value)) {
				radioButton.setChecked(true);
			} else {
				radioButton.setChecked(false);				
			}
		}

		if (this.mCheckedChangeListener!=null) {
			this.mCheckedChangeListener.onCheckedChange(value);
		}
	}

	public void setOnCheckedChangeListener(OnCheckedChangeListener checkedChangeListener) {
		this.mCheckedChangeListener = checkedChangeListener;
	}

	public interface OnCheckedChangeListener {
		public void onCheckedChange(String value);
	}
}
