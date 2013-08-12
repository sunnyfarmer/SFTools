package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterFirstFeeling;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ActivityFirstFeeling extends TopActivity {
	public static final String TAG = "FirstFeelingActivity";

	private ListView lvFirstFeeling = null;
	private Button btnInSelling = null;
	private Button btnStatistics = null;
	private Button btnOrganizing = null;

	private AdapterFirstFeeling mFirstFeelingAdapter = null;
	private ArrayList<String> mFirstFeelingArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		this.mFirstFeelingArray = new ArrayList<String>();
		this.mFirstFeelingArray.add("年轻女孩");
		this.mFirstFeelingArray.add("中年妇女");
		this.mFirstFeelingArray.add("老龄妇女");
		this.mFirstFeelingArray.add("年轻男孩");
		this.mFirstFeelingArray.add("中年男子");
		this.mFirstFeelingArray.add("老龄男人");

		this.mFirstFeelingAdapter = new AdapterFirstFeeling(this, this.mFirstFeelingArray);
	}

	@Override
	protected void initView() {
		this.setContentView(R.layout.activity_first_feeling);

		this.lvFirstFeeling = (ListView) this.findViewById(R.id.lvFirstFeeling);
		this.lvFirstFeeling.setAdapter(this.mFirstFeelingAdapter);

		this.btnInSelling = (Button) this.findViewById(R.id.btnInSelling);
		this.btnStatistics = (Button) this.findViewById(R.id.btnStatistics);
		this.btnOrganizing = (Button) this.findViewById(R.id.btnOrganizing);
	}

	@Override
	protected void setListener() {
		this.btnInSelling.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		this.btnStatistics.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		this.btnOrganizing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		this.lvFirstFeeling.setOnItemClickListener(this.mFirstFeelingAdapter);
	}

}
