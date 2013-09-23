package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DatabaseHelper;
import sf.tools.peddlers.db.DataStructure.DSCargoInList;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.Cargo.CUSTOMER_BEHAVIOR;
import sf.tools.peddlers.model.CargoInList;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.model.ShoppingList;

public class DBCargoInList extends DBController {
	public static final String TAG = "DBCargoInList";

	public DBCargoInList(DatabaseHelper databaseHelper, SQLiteDatabase db) {
		super(databaseHelper, db);
		this.mTableName = DSCargoInList.TB_NAME;
	}

	public DBCargoInList(Context context) {
		super(context);
		this.mTableName = DSCargoInList.TB_NAME;
	}

	public boolean insert(CargoInList cargoInList) {
		if (cargoInList==null ||
			cargoInList.getmCargo()==null ||
			cargoInList.getmCargo().getmCargoId()==Model.ID_UNDEFINED ||
			cargoInList.getmShoppingList()==null ||
			cargoInList.getmShoppingList().getmShoppingListId()==null ||
			cargoInList.getmUserBehavior()==CUSTOMER_BEHAVIOR.CB_NONE) {
			return false;
		}
		return super.insert(cargoInList);
	}

	public boolean delete(ShoppingList shoppingList) {
		if (shoppingList==null ||
			shoppingList.getmShoppingListId()==null) {
			return false;
		}
		int rowDeleted = this.delete(
				String.format("%s=?", DSCargoInList.COL_SHOPPING_LIST_ID),
				new String[] {shoppingList.getmShoppingListId()});
		return rowDeleted>0 ? true : false;
	}

	public boolean delete(CargoInList cargoInList) {
		if (cargoInList==null ||
			cargoInList.getmCargo()==null ||
			cargoInList.getmCargo().getmCargoId()==Model.ID_UNDEFINED ||
			cargoInList.getmShoppingList()==null ||
			cargoInList.getmShoppingList().getmShoppingListId()==null) {
			return false;
		}
		int rowDeleted = this.delete(
				String.format(
						"%s=? and %s=?",
						DSCargoInList.COL_CARGO_ID,
						DSCargoInList.COL_SHOPPING_LIST_ID
						),
				new String[] {
					String.valueOf(cargoInList.getmCargo().getmCargoId()),
					cargoInList.getmShoppingList().getmShoppingListId()
				});
		return rowDeleted>0 ? true : false;
	}

	public boolean update(CargoInList cargoInList) {
		if (cargoInList==null ||
			cargoInList.getmCargo()==null ||
			cargoInList.getmCargo().getmCargoId()==Model.ID_UNDEFINED ||
			cargoInList.getmShoppingList()==null ||
			cargoInList.getmShoppingList().getmShoppingListId()==null ||
			cargoInList.getmUserBehavior()==CUSTOMER_BEHAVIOR.CB_NONE) {
			return false;
		}
		int rowAffected = this.update(
				cargoInList,
				String.format(
						"%s=? and %s=?",
						DSCargoInList.COL_CARGO_ID,
						DSCargoInList.COL_SHOPPING_LIST_ID
						),
				new String[] {
					String.valueOf(cargoInList.getmCargo().getmCargoId()),
					cargoInList.getmShoppingList().getmShoppingListId()
				});
		return rowAffected>0 ? true : false;
	}

	public ArrayList<CargoInList> queryAll(Cargo cargo) {
		if (cargo==null || cargo.getmCargoId()==Model.ID_UNDEFINED) {
			return null;
		}
		Cursor cursor = this.query(
				DSCargoInList.COLUMNS,
				String.format("%s=?", DSCargoInList.COL_CARGO_ID),
				new String[] {String.valueOf(cargo.getmCargoId())},
				null);
		ArrayList<CargoInList> cargoInListArray = new ArrayList<CargoInList>();
		while (cursor!=null && cursor.moveToNext()) {
			CargoInList cil = this.parseCursor(cursor, null);
			cargoInListArray.add(cil);
		}
		return cargoInListArray;
	}
	public ArrayList<CargoInList> queryAll(ShoppingList shoppingList) {
		if (shoppingList==null ||
			shoppingList.getmShoppingListId()==null) {
			return null;
		}
		ArrayList<CargoInList> cargoInListArray = new ArrayList<CargoInList>();
		Cursor cursor = this.query(
				DSCargoInList.COLUMNS,
				String.format("%s=?", DSCargoInList.COL_SHOPPING_LIST_ID),
				new String[] {shoppingList.getmShoppingListId()},
				null);
		while (cursor!=null && cursor.moveToNext()) {
			CargoInList cargoInList = this.parseCursor(cursor, shoppingList);
			cargoInListArray.add(cargoInList);
		}
		return cargoInListArray;
	}

	private CargoInList parseCursor(Cursor cursor, ShoppingList shoppingList) {
		CargoInList cargoInList = null;
		if (cursor!=null) {
			int cargoId = cursor.getInt(cursor.getColumnIndex(DSCargoInList.COL_CARGO_ID));
			Cargo cargo = this.getDbCargo().query(cargoId);
			int originValue = cursor.getInt(cursor.getColumnIndex(DSCargoInList.COL_USER_BEHAVIOR));
			CUSTOMER_BEHAVIOR behavior = Cargo.getBehaviorByOriginValue(originValue);

			if (shoppingList==null) {
				String shoppingListId = cursor.getString(cursor.getColumnIndex(DSCargoInList.COL_SHOPPING_LIST_ID));
				shoppingList = this.getDbShoppingList().query(shoppingListId);
			}

			cargoInList = new CargoInList(cargo, shoppingList, behavior);
		}
		return cargoInList;
	}
}
