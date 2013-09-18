package sf.tools.peddlers.db.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import sf.tools.peddlers.db.DBController;
import sf.tools.peddlers.db.DataStructure.DSCargo;
import sf.tools.peddlers.model.Cargo;
import sf.tools.peddlers.model.CargoType;
import sf.tools.peddlers.model.Model;
import sf.tools.peddlers.utils.SFGlobal;

public class DBCargo extends DBController {
	public static final String TAG = "DBCargo";

	public DBCargo(Context context) {
		super(context);
		this.mTableName = DSCargo.TB_NAME;
	}

	public int upsert(Cargo cargo) {
		if (cargo==null) {
			return SFGlobal.DB_MSG_UNKNOWN_OBJECT;
		}
		if (cargo.getmCargoType()==null || cargo.getmCargoType().getmCargoTypeId()==Model.ID_UNDEFINED) {
			return SFGlobal.DB_MSG_UNKNOWN_CARGO_TYPE;
		}
		Cargo cargoInDB = this.query(cargo.getmCargoType(), cargo.getmCargoName());
		if (cargoInDB!=null) {
			return SFGlobal.DB_MSG_SAME_COLUMN;
		}
		boolean dbRs = false;
		if (cargo.getmCargoId()!=Model.ID_UNDEFINED) {
			dbRs = this.update(cargo);
		} else {
			dbRs = this.insert(cargo);
		}
		if (dbRs) {
			return SFGlobal.DB_MSG_OK;
		} else {
			return SFGlobal.DB_MSG_ERROR;
		}
	}

	public boolean insert(Cargo cargo) {
		if (cargo==null ||
			cargo.getmCargoName()==null ||
			cargo.getmCargoType()==null ||
			cargo.getmCargoType().getmCargoTypeId()==Model.ID_UNDEFINED) {
			return false;
		}
		boolean insertRs = super.insert(cargo);
		if (insertRs) {
			Cargo cargoInDB = this.query(cargo.getmCargoType(), cargo.getmCargoName());
			cargo.setmCargoId(cargoInDB.getmCargoId());
			return true;
		}
		return false;
	}

	public boolean delete(CargoType cargoType) {
		if (cargoType==null || cargoType.getmCargoTypeId()==Model.ID_UNDEFINED) {
			return false;
		}
		this.getDbShoppingList().delete(cargoType);
		int rowDeleted = this.delete(
				String.format("%s=?", DSCargo.COL_CARGO_TYPE_ID),
				new String[] {
					String.valueOf(cargoType.getmCargoTypeId())
					});
		return rowDeleted>0 ? true : false;
	}
	public boolean delete(Cargo cargo) {
		if (cargo==null ||
			cargo.getmCargoId()==Model.ID_UNDEFINED ||
			cargo.getmCargoType()==null ||
			cargo.getmCargoType().getmCargoTypeId()==Model.ID_UNDEFINED) {
			return false;
		}
		this.getDbShoppingList().delete(cargo);
		int rowDeleted = this.delete(
				String.format(
						"%s=? and %s=?",
						DSCargo.COL_CARGO_ID,
						DSCargo.COL_CARGO_TYPE_ID
						),
				new String[] {
					String.valueOf(cargo.getmCargoId()),
					String.valueOf(cargo.getmCargoType().getmCargoTypeId())
				});
		return rowDeleted>0 ? true : false;
	}

	public boolean update(Cargo cargo) {
		if (cargo==null ||
			cargo.getmCargoId()==Model.ID_UNDEFINED ||
			cargo.getmCargoType()==null ||
			cargo.getmCargoType().getmCargoTypeId()==Model.ID_UNDEFINED) {
			return false;
		}
		int rowAffected = this.update(
				cargo,
				String.format("%s=?", DSCargo.COL_CARGO_ID),
				new String[] {
					String.valueOf(cargo.getmCargoId())
				});
		return rowAffected>0 ? true : false;
	}

	public Cargo query(int cargoId) {
		Cargo cargo = null;
		Cursor cursor = this.query(
				DSCargo.COLUMNS,
				String.format("%s=?", DSCargo.COL_CARGO_ID),
				new String[] {String.valueOf(cargoId)},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			cargo = this.parseCursor(cursor, null);
		}
		return cargo;
	}

	public Cargo query(CargoType cargoType, String cargoName) {
		if (cargoName==null ||
			cargoType==null ||
			cargoType.getmCargoTypeId()==Model.ID_UNDEFINED ||
			cargoType.getmSettingGroup()==null ||
			cargoType.getmSettingGroup().getmSettingGroupId()==null) {
			return null;
		}
		Cargo cargo = null;
		Cursor cursor = this.query(
				DSCargo.COLUMNS,
				String.format(
						"%s=? and %s=?",
						DSCargo.COL_CARGO_TYPE_ID,
						DSCargo.COL_CARGO_NAME
						),
				new String[] {
					String.valueOf(cargoType.getmCargoTypeId()),
					cargoName
				},
				"1");
		if (cursor!=null && cursor.moveToNext()) {
			cargo = this.parseCursor(cursor, cargoType);
		}
		return cargo;
	}

	public ArrayList<Cargo> queryAll(CargoType cargoType) {
		if (cargoType==null ||
			cargoType.getmCargoTypeId()==Model.ID_UNDEFINED) {
			return null;
		}
		ArrayList<Cargo> cargoArray = new ArrayList<Cargo>();
		Cursor cursor = this.query(
				DSCargo.COLUMNS,
				String.format("%s=?", DSCargo.COL_CARGO_TYPE_ID),
				new String[] {
					String.valueOf(cargoType.getmCargoTypeId())
					},
				null);
		while (cursor!=null && cursor.moveToNext()) {
			Cargo cargo = this.parseCursor(cursor, cargoType);
			cargoArray.add(cargo);
		}
		return cargoArray;
	}

	private Cargo parseCursor(Cursor cursor, CargoType cargoType) {
		Cargo cargo = null;
		if (cursor!=null) {
			int id = cursor.getInt(cursor.getColumnIndex(DSCargo.COL_CARGO_ID));
			String name = cursor.getString(cursor.getColumnIndex(DSCargo.COL_CARGO_NAME));
			if (cargoType==null) {
				int cargoTypeId = cursor.getInt(cursor.getColumnIndex(DSCargo.COL_CARGO_TYPE_ID));
				cargoType = this.getDbCargoType().query(cargoTypeId);
			}

			cargo = new Cargo(name, cargoType);
			cargo.setmCargoId(id);
		}
		return cargo;
	}
}
