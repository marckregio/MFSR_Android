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
		db.execSQL(SERVICE_CREATE);
		db.execSQL(TRAVEL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + SERVICE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS" + TRAVEL_TABLE);
		onCreate(db);
	}

	public void timeRecord (String referenceNo,String password, String timeIn, String timeOut){
		query = new ContentValues(4);
		query.put(REFERENCENO, referenceNo);
		query.put(PASSWORD, password);
		query.put(TIME_IN, timeIn);
		query.put(TIME_OUT, timeOut);
		this.getWritableDatabase().insert(SERVICE_TABLE, REFERENCENO, query);
		Log.v("Marck Regio","Time Record Successful");
	}
	
	public void travelRecord (String startTime, String endTime, String referenceNo, String type, String fare){
		query = new ContentValues(5);
		query.put(START, startTime);
		query.put(END, endTime);
		query.put(REF, referenceNo);
		query.put(TYPE, type);
		query.put(FARE, fare);
		this.getWritableDatabase().insert(TRAVEL_TABLE, START, query);
		Log.v("Marck Regio","Travel Record Successful");
	}

	public String getTimeData(String filter){
		db = this.getReadableDatabase();
		selector = db.rawQuery("Select timeIn from "+ SERVICE_TABLE + " Where referenceNo = '" + filter +"'", null);
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
	
	public Cursor getTravelRecord(){
		selector = getReadableDatabase().query(TRAVEL_TABLE, 
				new String[] {"_id",START,END,REF,TYPE,FARE},
				null,null,null,null,null);
		return selector;
	}
	
	public void deleteAll(){
		this.getWritableDatabase().delete(SERVICE_TABLE, null, null);
		this.getWritableDatabase().delete(TRAVEL_TABLE, null, null);
	}
}
