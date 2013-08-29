package sf.tools.peddlers.model;

import android.content.ContentValues;

public interface Model {
	public static int ID_UNDEFINED = -1;
	public ContentValues getContentValues();
}
