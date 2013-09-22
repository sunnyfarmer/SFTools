package sf.tools.peddlers;

import java.util.ArrayList;
import java.util.Calendar;

import sf.log.SFLog;
import sf.tools.peddlers.adapter.AdapterStatisticsShoppingList;
import sf.tools.peddlers.model.ShoppingList;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ListView;

public class ActivityStatisticsShoppingList extends TopActivity {
	public static final String TAG = "ActivityStatisticsShoppingList";

	private ArrayList<ShoppingList> mShoppingListArray = null;

	private ListView lvShoppingList = null;
	private AdapterStatisticsShoppingList mAdapterStatisticsShoppingList = null;

	private Calendar mCalendar = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_statistics_shopping_list);
	    super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.refreshList();
	}

	@Override
	protected void initData() {
		super.initData();
		this.refreshData();
	}
	@Override
	protected void initView() {
		super.initView();
		this.lvShoppingList = (ListView) this.findViewById(R.id.lvShoppingList);

		this.mVHAHeader.setLeftText(R.string.back);
		this.mVHAHeader.setTitleText(R.string.shopping_list);
		this.mVHAHeader.setRightText(R.string.setting);
	}
	@Override
	protected void setListener() {
		super.setListener();

		this.mVHAHeader.setLeftOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		this.mVHAHeader.setRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});
	}

	protected void refreshData() {
		Calendar calendar = this.getmCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		Calendar calendarMin = Calendar.getInstance();
		calendarMin.set(Calendar.YEAR, year);
		calendarMin.set(Calendar.MONTH, month);
		calendarMin.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendarMin.set(Calendar.HOUR_OF_DAY, 0);
		calendarMin.set(Calendar.MINUTE, 0);
		calendarMin.set(Calendar.SECOND, 0);
		calendarMin.set(Calendar.MILLISECOND, 0);
		Calendar calendarMax = Calendar.getInstance();
		calendarMax.setTimeInMillis(calendarMin.getTimeInMillis()+24*3600*1000);

		this.mShoppingListArray = this.mApp.getmDbManager().getmDBShoppingList().queryAll(
				this.mApp.getSettingGroup(),
				calendarMin.getTimeInMillis(),
				calendarMax.getTimeInMillis());
	}
	protected void refreshList() {
		if (this.mAdapterStatisticsShoppingList==null) {
			this.mAdapterStatisticsShoppingList = new AdapterStatisticsShoppingList(this, this.mShoppingListArray);
			this.lvShoppingList.setAdapter(mAdapterStatisticsShoppingList);
			this.lvShoppingList.setOnItemClickListener(mAdapterStatisticsShoppingList);
		} else {
			this.mAdapterStatisticsShoppingList.setmShoppingListArray(mShoppingListArray);
			this.mAdapterStatisticsShoppingList.notifyDataSetChanged();
		}
	}

	protected void showDatePickerDialog() {
		int year = this.getmCalendar().get(Calendar.YEAR);
		int monthOfYear = this.getmCalendar().get(Calendar.MONTH);
		int dayOfMonth = this.getmCalendar().get(Calendar.DAY_OF_MONTH);
		DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					SFLog.d(
							TAG,
							String.format("year:%d, month:%d, day:%d", year, monthOfYear, dayOfMonth)
							);
					getmCalendar().set(Calendar.YEAR, year);
					getmCalendar().set(Calendar.MONTH, monthOfYear);
					getmCalendar().set(Calendar.DAY_OF_MONTH, dayOfMonth);

					refreshData();
					refreshList();
				}
			},
			year, monthOfYear, dayOfMonth);
		mDatePickerDialog.show();
	}

	public Calendar getmCalendar() {
		if (this.mCalendar == null) {
			this.mCalendar = Calendar.getInstance();
		}
		return mCalendar;
	}
}
