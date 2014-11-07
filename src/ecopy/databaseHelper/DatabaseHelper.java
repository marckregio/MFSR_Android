package ecopy.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
		getWritableDatabase().insert(TABLE_NAME, KEY_NAME, query);
	}

	public Cursor getContent(){
		selector =  getReadableDatabase().query(TABLE_NAME, new String[] {KEY_ID, KEY_NAME, TIME_IN, TIME_OUT}, null, null, null, null, null);
		return selector;
	}
	
	public void deleteAll(){
		this.getWritableDatabase().delete(TABLE_NAME, null, null);
	}
}
