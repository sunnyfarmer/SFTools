package sf.tools.peddlers.db.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSCargoInList;
import sf.tools.peddlers.db.DataStructure.DSCharacteristicItemInList;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.RankListItem;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.model.Cargo.CUSTOMER_BEHAVIOR;

public class DBRankList extends DBController {
	public static final String TAG = "DBRankList";

	public DBRankList(Context context) {
		super(context);
	}

	/**
	 * select tb_cargo_in_list.cargo_id, count(tb_characteristic_item_in_list.characteristic_item_id)  from tb_characteristic_item_in_list, tb_cargo_in_list
	 * where tb_characteristic_item_in_list.shopping_list_id=tb_cargo_in_list.shopping_list_id
	 * and tb_characteristic_item_in_list.characteristic_item_id = 2
	 * and tb_cargo_in_list.user_behavior=2
	 * group by tb_cargo_in_list.cargo_id;
	 * 
	 * @param characteristicItem
	 * @return
	 */
	public ArrayList<RankListItem> queryLookRank(CharacteristicItem characteristicItem) {
		ArrayList<RankListItem> itemArray = new ArrayList<RankListItem>();
		//TODO begin
		Cursor cursor = this.getmDatabase().rawQuery(
				String.format(
				"select %s.%s, count(%s.%s)" +
				"from %s, %s" +
				"where %s.%s=%s.%s" +
				"and %s.%s = ?" +
				"and %s.%s = ?" +
				"group by %s.%s;",
				DSCargoInList.TB_NAME, DSCargoInList.COL_CARGO_ID,
				DSCharacteristicItemInList.TB_NAME, DSCharacteristicItemInList.COL_CHARACTERISTIC_ITEM_ID,
				DSCharacteristicItemInList.TB_NAME, DSCargoInList.TB_NAME,
				DSCharacteristicItemInList.TB_NAME, DSCharacteristicItemInList.COL_SHOPPING_LIST_ID,
				DSCargoInList.TB_NAME, DSCargoInList.COL_SHOPPING_LIST_ID,
				DSCharacteristicItemInList.TB_NAME, DSCharacteristicItemInList.COL_CHARACTERISTIC_ITEM_ID,
				DSCargoInList.TB_NAME, DSCargoInList.COL_USER_BEHAVIOR,
				DSCargoInList.TB_NAME, DSCargoInList.COL_CARGO_ID
				),
				new String[] {
					String.valueOf(characteristicItem.getmCharacteristicItemId()),
					String.valueOf(CUSTOMER_BEHAVIOR.CB_LOOK.ordinal())
				});
		while (cursor.moveToNext()) {
			int cargoId = cursor.getInt(0);
			int quantity = cursor.getInt(1);
			Cargo cargo = this.getDbCargo().query(cargoId);
			RankListItem item = new RankListItem();
			item.setmCargo(cargo);
			item.setQuantity(quantity);
			itemArray.add(item);
		}
		//TODO end
//
//		ArrayList<SettingGroup> sgArray = this.getDbSettingGroup().queryAll();
//		for (SettingGroup settingGroup : sgArray) {
//			ArrayList<CargoType> ctArray = this.getDbCargoType().queryAll(settingGroup);
//			for (CargoType cargoType : ctArray) {
//				ArrayList<Cargo> cArray = this.getDbCargo().queryAll(cargoType);
//				for (Cargo cargo : cArray) {
//					RankListItem item = new RankListItem();
//					item.setmCargo(cargo);
//					item.setQuantity(10000);
//					itemArray.add(item);
//				}
//			}
//		}

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

	public HashMap<CharacteristicItem, Integer> queryLookQuantity(Cargo cargo, Characteristic characteristic) {
		HashMap<CharacteristicItem, Integer> rsMap = new HashMap<CharacteristicItem, Integer>();
		for (CharacteristicItem characteristicItem : characteristic.getmCharacteristicItemArray()) {
			int quantity = this.queryLookQuantity(cargo, characteristicItem);
			rsMap.put(characteristicItem, quantity);
		}
		return rsMap;
	}

	public int queryLookQuantity(Cargo cargo, CharacteristicItem characteristicItem) {
		//TODO
		return 100;
	}

	public HashMap<CharacteristicItem, Integer> queryBuyQuantity(Cargo cargo, Characteristic characteristic) {
		HashMap<CharacteristicItem, Integer> rsMap = new HashMap<CharacteristicItem, Integer>();
		for (CharacteristicItem characteristicItem : characteristic.getmCharacteristicItemArray()) {
			int quantity = this.queryLookQuantity(cargo, characteristicItem);
			rsMap.put(characteristicItem, quantity);
		}
		return rsMap;
	}

	public int queryBuyQuantity(Cargo cargo, CharacteristicItem characteristicItem) {
		//TODO
		return 100;
	}

}
