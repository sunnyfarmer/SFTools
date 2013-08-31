package sf.tools.peddlers;

import java.util.ArrayList;

import sf.tools.peddlers.adapter.AdapterFirstFeeling;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.test.TestApp;

import android.content.Intent;
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
	private ArrayList<FirstFeeling> mFirstFeelingArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
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
				Intent intent = new Intent(ActivityFirstFeeling.this, ActivityStatistic.class);
				ActivityFirstFeeling.this.startActivity(intent);
			}
		});
		this.btnOrganizing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityFirstFeeling.this, ActivitySettingGroup.class);
				ActivityFirstFeeling.this.startActivity(intent);
			}
		});
		this.lvFirstFeeling.setOnItemClickListener(this.mFirstFeelingAdapter);
	}

}
