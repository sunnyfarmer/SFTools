package sf.tools.peddlers.test;

import java.util.ArrayList;

import sf.tools.peddlers.db.controller.DBSettingGroup;
import sf.tools.peddlers.model.SettingGroup;
import android.content.Context;

public class DBSettingGroupTest extends TestApp {
	public static final String TAG = "DBSettingGroupTest";

	private DBSettingGroup mDBSettingGroup = null;

	public DBSettingGroupTest(Context context) {
		super(context);
		this.mDBSettingGroup = new DBSettingGroup(context);
	}

	public void test() {
		this.mDBSettingGroup.insert("group1");
		this.mDBSettingGroup.insert("group2");
		this.mDBSettingGroup.insert("group3");

		SettingGroup settingGroup = this.mDBSettingGroup.query("group1");

		ArrayList<SettingGroup> settingGroupArray = this.mDBSettingGroup.queryAll();

		settingGroup = settingGroupArray.get(0);
		settingGroup = this.mDBSettingGroup.queryById(settingGroup.getmSettingGroupId());

		settingGroup.setmSettingGroupName("group11");
		this.mDBSettingGroup.update(settingGroup);

		settingGroupArray = this.mDBSettingGroup.queryAll();
		for (SettingGroup sg : settingGroupArray) {
			this.mDBSettingGroup.delete(sg.getmSettingGroupName());
		}

		settingGroupArray = this.mDBSettingGroup.queryAll();
	}
}
