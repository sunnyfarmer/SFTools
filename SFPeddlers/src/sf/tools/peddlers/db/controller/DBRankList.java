package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.RankListItem;
import sf.tools.peddlers.model.SettingGroup;

public class DBRankList extends DBController {
	public static final String TAG = "DBRankList";

	public DBRankList(Context context) {
		super(context);
	}

	public ArrayList<RankListItem> queryLookRank(CharacteristicItem characteristicItem) {
		ArrayList<RankListItem> itemArray = new ArrayList<RankListItem>();
		//TODO
		ArrayList<SettingGroup> sgArray = this.getDbSettingGroup().queryAll();
		for (SettingGroup settingGroup : sgArray) {
			ArrayList<CargoType> ctArray = this.getDbCargoType().queryAll(settingGroup);
			for (CargoType cargoType : ctArray) {
				ArrayList<Cargo> cArray = this.getDbCargo().queryAll(cargoType);
				for (Cargo cargo : cArray) {
					RankListItem item = new RankListItem();
					item.setmCargo(cargo);
					item.setQuantity(10000);
					itemArray.add(item);
				}
			}
		}

		return itemArray;
	}

	public ArrayList<RankListItem> queryBuyRank(CharacteristicItem characteristicItem) {
		ArrayList<RankListItem> itemArray = new ArrayList<RankListItem>();
		//TODO
		ArrayList<SettingGroup> sgArray = this.getDbSettingGroup().queryAll();
		for (SettingGroup settingGroup : sgArray) {
			ArrayList<CargoType> ctArray = this.getDbCargoType().queryAll(settingGroup);
			for (CargoType cargoType : ctArray) {
				ArrayList<Cargo> cArray = this.getDbCargo().queryAll(cargoType);
				for (Cargo cargo : cArray) {
					RankListItem item = new RankListItem();
					item.setmCargo(cargo);
					item.setQuantity(10000);
					itemArray.add(item);
				}
			}
		}
		
		return itemArray;
	}
}
