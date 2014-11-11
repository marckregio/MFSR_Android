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

	public void timeRecord (String referenceNo,String password, String timeIn, String timeOut){
		query = new ContentValues(4);
		query.put(KEY_NAME, referenceNo);
		query.put(KEY_PASSWORD, password);
		query.put(TIME_IN, timeIn);
		query.put(TIME_OUT, timeOut);
		this.getWritableDatabase().insert(TABLE_NAME, KEY_NAME, query);
		Log.v("Marck Regio","Successful");
	}

	public String getTimeData(String filter){
		db = this.getReadableDatabase();
		selector = db.rawQuery("Select timeIn from "+ TABLE_NAME + " Where referenceNo = '" + filter +"'", null);
		String data = "";
		if(selector.moveToFirst()) {
			do{
				Log.v("makoy", selector.getString(0));
				data = selector.getString(0) +"";
			}while(selector.moveToNext());
		}
		db.close();
		return data;
	}
	
	public void deleteAll(){
		this.getWritableDatabase().delete(TABLE_NAME, null, null);
	}
}
