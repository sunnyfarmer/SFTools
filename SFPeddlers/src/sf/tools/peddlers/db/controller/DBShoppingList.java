package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSShoppingList;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.Cargo.CUSTOMER_BEHAVIOR;
import sf.tools.peddlers.model.CargoInList;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Characteristic;
import sf.tools.peddlers.model.CharacteristicItem;
import sf.tools.peddlers.model.CharacteristicItemInList;
import sf.tools.peddlers.model.FirstFeeling;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.model.SettingGroup;
import sf.tools.peddlers.model.ShoppingList;
import sf.utils.SFUtils;

public class DBShoppingList extends DBController {
	public static final String TAG = "DBShoppingList";

	public DBShoppingList(Context context) {
		super(context);
		this.mTableName = DSShoppingList.TB_NAME;
	}

	public boolean insert(ShoppingList shoppingList) {
		if (shoppingList==null ||
			shoppingList.getmFirstFeeling()==null ||
			shoppingList.getmFirstFeeling().getmFirstFeelingId()==Model.ID_UNDEFINED ||
			shoppingList.getmSettingGroup()==null ||
			shoppingList.getmSettingGroup().getmSettingGroupId()==null ||
			shoppingList.getmCharacteristic()==null) {
			return false;
		}
		for (Characteristic characteristic : shoppingList.getmCharacteristic()) {
			if (characteristic.getmSelectedCharacteristicItem()==null ||
				characteristic.getmSelectedCharacteristicItem().getmCharacteristicItemId()==Model.ID_UNDEFINED) {
				return false;
			}
		}
		for (Cargo cargo : shoppingList.getmRelatedCargo()) {
			if (cargo.getmCargoId()==Model.ID_UNDEFINED ||
				cargo.getmBehavior().equals(CUSTOMER_BEHAVIOR.CB_NONE)) {
				return false;
			}
		}
		//生成id
		shoppingList.setmShoppingListId(SFUtils.produceUniqueId());
		//插入购物单
		boolean rsInsert = super.insert(shoppingList);
		if (rsInsert) {
			//插入购物者特征
			for (CharacteristicItem characteristicItem : shoppingList.getmSelectedCharacteristicItem()) {
				CharacteristicItemInList characteristicItemInList = new CharacteristicItemInList(characteristicItem, shoppingList);
				this.getDbCharacteristicItemInList().insert(characteristicItemInList);
			}
			//插入选购商品
			for (Cargo cargo : shoppingList.getmRelatedCargo()) {
				CargoInList cargoInList = new CargoInList(cargo, shoppingList, cargo.getmBehavior());
				this.getDbCargoInList().insert(cargoInList);
			}
			return true;
		}
		return false;
	}

	public boolean delete(ShoppingList shoppingList) {
		if (shoppingList==null ||
			shoppingList.getmShoppingListId()==null) {
			return false;
		}
		//先删除购物者特征
		this.getDbCharacteristicItemInList().delete(shoppingList);
		//删除商品
		this.getDbCargoInList().delete(shoppingList);
		//删除购物单
		int rowDeleted = this.delete(
				String.format("%s=?", DSShoppingList.COL_SHOPPING_LIST_ID),
				new String[] {shoppingList.getmShoppingListId()});
		return rowDeleted>0 ? true : false;
	}
	public boolean delete(SettingGroup settingGroup) {
		if (settingGroup==null ||
				settingGroup.getmSettingGroupId()==null) {
			return false;
		}
		Cursor cursor = this.query(
				DSShoppingList.COLUMNS,
				String.format("%s=?", DSShoppingList.COL_SETTING_GROUP_ID),
				new String[] {settingGroup.getmSettingGroupId()},
				null);
		while (cursor!=null && cursor.moveToNext()) {
			String shoppingListId = cursor.getString(cursor.getColumnIndex(DSShoppingList.COL_SHOPPING_LIST_ID));
			ShoppingList sl = new ShoppingList(null, settingGroup, null);
			sl.setmShoppingListId(shoppingListId);
			this.delete(sl);
		}
		return true;
	}
	public boolean delete(FirstFeeling firstFeeling) {
		if (firstFeeling==null ||
				firstFeeling.getmFirstFeelingId()==Model.ID_UNDEFINED) {
			return false;
		}
		Cursor cursor = this.query(
				DSShoppingList.COLUMNS,
				String.format("%s=?", DSShoppingList.COL_FIRST_FEELING_ID),
				new String[] {String.valueOf(firstFeeling.getmFirstFeelingId())},
				null);
		while (cursor!=null && cursor.moveToNext()) {
			String shoppingListId = cursor.getString(cursor.getColumnIndex(DSShoppingList.COL_SHOPPING_LIST_ID));
			ShoppingList sl = new ShoppingList(null, null, null);
			sl.setmShoppingListId(shoppingListId);
			this.delete(sl);
		}
		return true;
	}
	public boolean delete(Characteristic characteristic) {
		if (characteristic==null || characteristic.getmCharacteristicId()==Model.ID_UNDEFINED) {
			return false;
		}
		ArrayList<CharacteristicItem> characteristicItemArray = this.getDbCharacteristicItem().queryAll(characteristic);
		for (CharacteristicItem characteristicItem : characteristicItemArray) {
			this.delete(characteristicItem);
		}
		return true;
	}
	public boolean delete(CharacteristicItem characteristicItem) {
		if (characteristicItem==null || characteristicItem.getmCharacteristicItemId()==Model.ID_UNDEFINED) {
			return false;
		}
		ArrayList<CharacteristicItemInList> characteristicItemInListArray =
				this.getDbCharacteristicItemInList().queryAll(characteristicItem);
		for (CharacteristicItemInList characteristicItemInList : characteristicItemInListArray) {
			ShoppingList sl = characteristicItemInList.getmShoppingList();
			this.delete(sl);
		}
		return true;
	}
	public boolean delete(CargoType cargoType) {
		if (cargoType==null || cargoType.getmCargoTypeId()==Model.ID_UNDEFINED) {
			return false;
		}
		ArrayList<Cargo> cargoArray = this.getDbCargo().queryAll(cargoType);
		for (Cargo cargo : cargoArray) {
			this.delete(cargo);
		}
		return true;
	}
	public boolean delete(Cargo cargo) {
		if (cargo==null || cargo.getmCargoId()==Model.ID_UNDEFINED) {
			return false;
		}
		ArrayList<CargoInList> cargoInListArray = this.getDbCargoInList().queryAll(cargo);
		for (CargoInList cargoInList : cargoInListArray) {
			ShoppingList sl = cargoInList.getmShoppingList();
			this.delete(sl);
		}
		return true;
	}

	public boolean update(ShoppingList shoppingList) {
		if (shoppingList==null ||
			shoppingList.getmFirstFeeling()==null ||
			shoppingList.getmFirstFeeling().getmFirstFeelingId()==Model.ID_UNDEFINED ||
			shoppingList.getmSettingGroup()==null ||
			shoppingList.getmSettingGroup().getmSettingGroupId()==null ||
			shoppingList.getmCharacteristic()==null) {
			return false;
		}
		for (Characteristic characteristic : shoppingList.getmCharacteristic()) {
			if (characteristic.getmSelectedCharacteristicItem()==null ||
				characteristic.getmSelectedCharacteristicItem().getmCharacteristicItemId()==Model.ID_UNDEFINED) {
				return false;
			}
		}
		for (Cargo cargo : shoppingList.getmRelatedCargo()) {
			if (cargo.getmCargoId()==Model.ID_UNDEFINED ||
				cargo.getmBehavior().equals(CUSTOMER_BEHAVIOR.CB_NONE)) {
				return false;
			}
		}
		//先修改购物者特征
		this.getDbCharacteristicItemInList().delete(shoppingList);
		for (CharacteristicItem characteristicItem : shoppingList.getmSelectedCharacteristicItem()) {
			CharacteristicItemInList characteristicItemInList = new CharacteristicItemInList(characteristicItem, shoppingList);
			this.getDbCharacteristicItemInList().insert(characteristicItemInList);
		}
		//修改商品
		this.getDbCargoInList().delete(shoppingList);
		for (Cargo cargo : shoppingList.getmRelatedCargo()) {
			CargoInList cargoInList = new CargoInList(cargo, shoppingList, cargo.getmBehavior());
			this.getDbCargoInList().insert(cargoInList);
		}
		//修改购物单
		int rowAffected = this.update(
				shoppingList,
				String.format("%s=?", DSShoppingList.COL_SHOPPING_LIST_ID),
				new String[] {shoppingList.getmShoppingListId()});
		return rowAffected>0 ? true : false;
	}

	public ShoppingList query(String shoppingListId) {
		if (shoppingListId==null) {
			return null;
		}
		ShoppingList shoppingList = null;
		Cursor cursor = this.query(
				DSShoppingList.COLUMNS,
				String.format("%s=?", DSShoppingList.COL_SHOPPING_LIST_ID),
				new String[] {shoppingListId},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			shoppingList = this.parseCursor(cursor, null);
		}
		return shoppingList;
	}

	public ArrayList<ShoppingList> queryAll(SettingGroup settingGroup, long timeMin, long timeMax) {
		if (settingGroup==null ||
				settingGroup.getmSettingGroupId()==null) {
				return null;
			}
			ArrayList<ShoppingList> shoppingListArray = new ArrayList<ShoppingList>();
			Cursor cursor = this.query(
					false,
					DSShoppingList.COLUMNS,
					String.format("%s=? and %s>=? and %s<=?", DSShoppingList.COL_SETTING_GROUP_ID, DSShoppingList.COL_SHOPPING_TIMESTAMP, DSShoppingList.COL_SHOPPING_TIMESTAMP),
					new String[] {settingGroup.getmSettingGroupId(), String.valueOf(timeMin), String.valueOf(timeMax)},
					null,null,
					String.format("%s DESC", DSShoppingList.COL_SHOPPING_TIMESTAMP),
					null);
			while (cursor!=null && cursor.moveToNext()) {
				ShoppingList shoppingList = this.parseCursor(cursor, settingGroup);
				shoppingListArray.add(shoppingList);
			}
			return shoppingListArray;
	}

	public ArrayList<ShoppingList> queryAll(SettingGroup settingGroup) {
		if (settingGroup==null ||
			settingGroup.getmSettingGroupId()==null) {
			return null;
		}
		ArrayList<ShoppingList> shoppingListArray = new ArrayList<ShoppingList>();
		Cursor cursor = this.query(
				false,
				DSShoppingList.COLUMNS,
				String.format("%s=?", DSShoppingList.COL_SETTING_GROUP_ID),
				new String[] {settingGroup.getmSettingGroupId()},
				null,null,
				String.format("%s DESC", DSShoppingList.COL_SHOPPING_TIMESTAMP),
				null);
		while (cursor!=null && cursor.moveToNext()) {
			ShoppingList shoppingList = this.parseCursor(cursor, settingGroup);
			shoppingListArray.add(shoppingList);
		}
		return shoppingListArray;
	}

	private ShoppingList parseCursor(Cursor cursor, SettingGroup settingGroup) {
		ShoppingList shoppingList = null;
		if (cursor!=null) {
			String shoppingListId = cursor.getString(cursor.getColumnIndex(DSShoppingList.COL_SHOPPING_LIST_ID));
			int firstFeelingId = cursor.getInt(cursor.getColumnIndex(DSShoppingList.COL_FIRST_FEELING_ID));
			FirstFeeling firstFeeling = this.getDbFirstFeeling().query(firstFeelingId);
			String settingGroupId = cursor.getString(cursor.getColumnIndex(DSShoppingList.COL_SETTING_GROUP_ID));
			settingGroup = this.getDbSettingGroup().queryById(settingGroupId);
			long timeStamp = cursor.getLong(cursor.getColumnIndex(DSShoppingList.COL_SHOPPING_TIMESTAMP));

			shoppingList = new ShoppingList(firstFeeling, settingGroup, null);
			shoppingList.setmShoppingListId(shoppingListId);
			shoppingList.setmTimestamp(timeStamp);

			//获得特征
			ArrayList<CharacteristicItemInList> characteristicItemInListArray = this.getDbCharacteristicItemInList().queryAll(shoppingList);
			ArrayList<Characteristic> characteristicArray = new ArrayList<Characteristic>();
			for (CharacteristicItemInList characteristicItemInList : characteristicItemInListArray) {
				Characteristic characteristic = characteristicItemInList.getmCharacteristicItem().getmCharacteristic();
				characteristic.setmSelectedCharacteristicItemString(characteristicItemInList.getmCharacteristicItem().getmCharacteristicItemName());
				characteristicArray.add(characteristic);
			}
			shoppingList.setmCharacteristic(characteristicArray);


			//获得商品
			ArrayList<Cargo> buyCargo = new ArrayList<Cargo>();
			ArrayList<Cargo> lookCargo = new ArrayList<Cargo>();
			ArrayList<Cargo> relatedCargo = new ArrayList<Cargo>();
			ArrayList<CargoInList> cargoInListArray = this.getDbCargoInList().queryAll(shoppingList);
			for (CargoInList cargoInList : cargoInListArray) {
				Cargo cargo = cargoInList.getmCargo();
				CUSTOMER_BEHAVIOR behavior = cargoInList.getmUserBehavior();
				cargo.setmBehavior(behavior);
				switch(behavior) {
				case CB_LOOK:
					lookCargo.add(cargo);
					relatedCargo.add(cargo);
					break;
				case CB_BUY:
					buyCargo.add(cargo);
					relatedCargo.add(cargo);
					break;
				case CB_NONE:
					break;
				}
			}
			shoppingList.setmLookCargo(lookCargo);
			shoppingList.setmBuyCargo(buyCargo);
			shoppingList.setmRelatedCargo(relatedCargo);
		}
		return shoppingList;
	}
}
