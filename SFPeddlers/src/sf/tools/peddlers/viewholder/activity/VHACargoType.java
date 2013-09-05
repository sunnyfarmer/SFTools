package sf.tools.peddlers.viewholder.activity;

import java.util.ArrayList;
import java.util.Set;

import sf.tools.peddlers.ActivityShopping;
import sf.tools.peddlers.R;
import sf.tools.peddlers.TopActivity;
import sf.tools.peddlers.model.CargoType;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class VHACargoType {
	public static final String TAG = "VHACargoType";

	private RadioGroup rgCargoType = null;
	private TopActivity mActivity = null;
	private ArrayList<CargoType> mCargoTypeArray = null;

	private OnCargoTypeChangedListener mOnCargoTypeChangedListener = null;

	public interface OnCargoTypeChangedListener {
		public void onCargoTypeChanged(CargoType cargoType);
	}

	public VHACargoType(TopActivity activity, ArrayList<CargoType> cargoTypeArray) {
		this.mActivity = activity;
		this.mCargoTypeArray = cargoTypeArray;

		this.initData();
		this.initView();
		this.setListener();
	}

	private void initData() {
		
	}
	private void initView() {
		this.rgCargoType = (RadioGroup) this.mActivity.findViewById(R.id.rgCargoType);
		this.refreshCargoType();
	}
	private void setListener() {
		this.rgCargoType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton)group.findViewById(checkedId);
				CargoType cargoType = (CargoType) rb.getTag();
				if (mOnCargoTypeChangedListener!=null) {
					mOnCargoTypeChangedListener.onCargoTypeChanged(cargoType);
				}
			}
		});
	}
	public void refreshCargoType() {
		this.rgCargoType.removeAllViews();

		for (CargoType cargoType : this.mCargoTypeArray) {
			RadioButton rb = new RadioButton(this.mActivity);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			rb.setLayoutParams(params);
			rb.setGravity(Gravity.CENTER);
			rb.setBackgroundResource(R.drawable.tab_checkbox_selector);
			rb.setButtonDrawable(android.R.color.transparent);
			rb.setText(cargoType.getmCargoTypeName());
			rb.setTag(cargoType);

			this.rgCargoType.addView(rb);
		}
	}

	public OnCargoTypeChangedListener getmOnCargoTypeChangedListener() {
		return mOnCargoTypeChangedListener;
	}

	public void setmOnCargoTypeChangedListener(
			OnCargoTypeChangedListener mOnCargoTypeChangedListener) {
		this.mOnCargoTypeChangedListener = mOnCargoTypeChangedListener;
	}
}
