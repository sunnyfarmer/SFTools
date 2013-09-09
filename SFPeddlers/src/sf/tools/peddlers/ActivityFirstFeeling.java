package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterFirstFeeling;
import sf.tools.peddlers.model.FirstFeeling;
import android.os.Bundle;
import android.widget.ListView;

public class ActivityFirstFeeling extends TopActivity {
	public static final String TAG = "FirstFeelingActivity";

	private ListView lvFirstFeeling = null;

	private AdapterFirstFeeling mFirstFeelingAdapter = null;
	private ArrayList<FirstFeeling> mFirstFeelingArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_first_feeling);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		super.initData();
		this.mFirstFeelingArray = new ArrayList<FirstFeeling>();
		this.mFirstFeelingArray.add(new FirstFeeling("年轻女孩", null));
		this.mFirstFeelingArray.add(new FirstFeeling("中年妇女", null));
		this.mFirstFeelingArray.add(new FirstFeeling("老龄妇女", null));
		this.mFirstFeelingArray.add(new FirstFeeling("年轻男孩", null));
		this.mFirstFeelingArray.add(new FirstFeeling("中年男子", null));
		this.mFirstFeelingArray.add(new FirstFeeling("老龄男人", null));

		this.mFirstFeelingAdapter = new AdapterFirstFeeling(this, this.mFirstFeelingArray);
	}

	@Override
	protected void initView() {
		super.initView();

		this.lvFirstFeeling = (ListView) this.findViewById(R.id.lvFirstFeeling);
		this.lvFirstFeeling.setAdapter(this.mFirstFeelingAdapter);

		this.mVHAHeader.setTitleText(R.string.first_feeling);
		this.mVHAHeader.hideLeft();
		this.mVHAHeader.hideRight();
	}

	@Override
	protected void setListener() {
		super.setListener();
		this.lvFirstFeeling.setOnItemClickListener(this.mFirstFeelingAdapter);
	}

}
