package ecopy.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper implements SQLVariables{
	public SQLiteDatabase db;
	public ContentValues query;
	public Cursor selector;
	
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(db);
	}

	public void timeRecord (String referenceNo, String timeIn, String timeOut){
		query = new ContentValues(3);
		query.put(KEY_NAME, referenceNo);
		query.put(TIME_IN, timeIn);
		query.put(TIME_OUT, timeOut);
		this.getWritableDatabase().insert(TABLE_NAME, KEY_NAME, query);
		Log.v("Marck Regio","Successful");
	}

	public void getData(){
		selector = db.rawQuery("Select * from "+ TABLE_NAME, null);
		db = this.getReadableDatabase();
		String [] data = null;
		int i = 0;
		if (selector.moveToFirst()){
			do {
				//data [selector.getColumnIndex(TABLE_NAME)] = selector.getString(0);
				Log.v("Makoy", i+"");
				i++;
			} while (selector.moveToNext());
		}
		db.close();
		//return data;
	}
	
	public void deleteAll(){
		this.getWritableDatabase().delete(TABLE_NAME, null, null);
	}
}
