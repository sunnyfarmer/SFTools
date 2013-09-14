package sf.tools.peddlers.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable{
	public static int ID_UNDEFINED = -1;
	public ContentValues getContentValues(){
		return null;
	}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
    }

    public static final Parcelable.Creator<Model> CREATOR
            = new Parcelable.Creator<Model>() {
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
    
    private Model(Parcel in) {
    }
}
