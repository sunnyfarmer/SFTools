package sf.tools.peddlers.model;

import sf.log.SFLog;
import sf.tools.peddlers.db.DataStructure.DSCargo;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class Cargo extends Model{
	public static final String TAG = "Cargo";

	public static enum CUSTOMER_BEHAVIOR {
		CB_NONE,
		CB_LOOK,
		CB_BUY
	};

	private int mCargoId = ID_UNDEFINED;
	private String mCargoName = null;
	private CargoType mCargoType = null;

	private CUSTOMER_BEHAVIOR mBehavior = CUSTOMER_BEHAVIOR.CB_NONE;

    public static final Parcelable.Creator<Cargo> CREATOR = new Parcelable.Creator<Cargo>() {
		public Cargo createFromParcel(Parcel in) {
		    return new Cargo(in);
		}
		
		public Cargo[] newArray(int size) {
		    return new Cargo[size];
		}
	};
	public Cargo(Parcel in) {
		super(in);
		this.mCargoId = in.readInt();
		this.mCargoName = in.readString();
		this.mCargoType = in.readParcelable(null);
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeInt(this.mCargoId);
		out.writeString(mCargoName);
		out.writeParcelable(mCargoType, flags);
	}

	public Cargo(String cargoName, CargoType cargoType) {
		this.setmCargoName(cargoName);
		this.setmCargoType(cargoType);
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		if (this.mCargoId != Model.ID_UNDEFINED) {
			values.put(DSCargo.COL_CARGO_ID, this.mCargoId);
		}
		values.put(DSCargo.COL_CARGO_NAME, this.mCargoName);
		values.put(DSCargo.COL_CARGO_TYPE_ID, this.mCargoType.getmCargoTypeId());
		return values;
	}


	public int getmCargoId() {
		return mCargoId;
	}

	public void setmCargoId(int mCargoId) {
		this.mCargoId = mCargoId;
	}

	public String getmCargoName() {
		return mCargoName;
	}
	public void setmCargoName(String mCargoName) {
		this.mCargoName = mCargoName;
	}
	public CargoType getmCargoType() {
		return mCargoType;
	}
	public void setmCargoType(CargoType mCargoType) {
		this.mCargoType = mCargoType;
	}

	public CUSTOMER_BEHAVIOR getmBehavior() {
		return mBehavior;
	}

	public void setmBehavior(CUSTOMER_BEHAVIOR mBehavior) {
		this.mBehavior = mBehavior;
	}

	public static CUSTOMER_BEHAVIOR getBehaviorByOriginValue(int originValue) {
		CUSTOMER_BEHAVIOR rs = CUSTOMER_BEHAVIOR.CB_NONE;
		if (originValue >= CUSTOMER_BEHAVIOR.values().length) {
			SFLog.e(TAG, "超出CUSTOMER_BEHAVIOR范围:"+originValue);
		} else {
			rs = CUSTOMER_BEHAVIOR.values()[originValue];
		}
		return rs;
	}
}
