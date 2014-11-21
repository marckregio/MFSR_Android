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

	public void timeRecord (String referenceNo, String timeIn){
		query = new ContentValues();
		query.put(REFERENCENO, referenceNo);
		query.put(TIME_IN, timeIn);
		this.getWritableDatabase().insert(SERVICE_TABLE, REFERENCENO, query);
		Log.v("Marck Regio","Time Record Successful");
	}
	
	public void updateServiceRecord(String password, String referenceNo, String beforeCopyBW, String beforePrintBW,
			String beforeScanBW, String beforeFaxBW, String beforeCopyFC, 
			String beforePrintFC, String beforeScanFC, String beforeBWTotal, 
			String beforeFCTotal, String afterCopyBW, String afterPrintBW,
			String afterScanBW, String afterFaxBW, String afterCopyFC, 
			String afterPrintFC, String afterScanFC, String afterBWTotal, 
			String afterFCTotal, String eTicket, String repair, String remarks,
			String selectedPayment, String selectedOnsite,
			String selectedApproval, String selectedPending, String timeOut){
		query = new ContentValues();
		query.put("password", password);
		query.put("beforeCopyBW", beforeCopyBW);
		query.put("beforePrintBW", beforePrintBW);
		query.put("beforeScanBW", beforeScanBW);
		query.put("beforeFaxBW", beforeFaxBW);
		query.put("beforeCopyFC", beforeCopyFC);
		query.put("beforePrintFC", beforePrintFC);
		query.put("beforeScanFC", beforeScanFC);
		query.put("beforeBWTotal", beforeBWTotal);
		query.put("beforeFCTotal", beforeFCTotal);
		query.put("afterCopyBW", afterCopyBW);
		query.put("afterPrintBW", afterPrintBW);
		query.put("afterScanBW", afterScanBW);
		query.put("afterFaxBW", afterFaxBW);
		query.put("afterCopyFC", afterCopyFC);
		query.put("afterPrintFC", afterPrintFC);
		query.put("afterScanFC", afterScanFC);
		query.put("afterBWTotal", afterBWTotal);
		query.put("afterFCTotal", afterFCTotal);
		query.put("eTicket",eTicket);
		query.put("repair", repair);
		query.put("remarks", remarks);
		query.put("selectedPayment", selectedPayment);
		query.put("selectedOnsite", selectedOnsite);
		query.put("selectedApproval", selectedApproval);
		query.put("selectedPending", selectedPending);
		query.put("timeout", timeOut);
		this.getWritableDatabase().update(SERVICE_TABLE, 
				query, 
				REFERENCENO +"=?", 
				new String [] {referenceNo});
		db.close();
		Log.v("Marck Regio","Update Successful");
	}
	
	public void travelRecord (String startTime, String endTime, String referenceNo, String type, String fare, String from, String to, String other){
		query = new ContentValues();
		query.put(START, startTime);
		query.put(END, endTime);
		query.put(REF, referenceNo);
		query.put(TYPE, type);
		query.put(FARE, fare);
		query.put(FROM, from);
		query.put(TO, to);
		query.put(OTHERS, other);
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
	
	
	public String [] getDetails(String filter){
		db = this.getReadableDatabase();
		String [] data = new String [27];
		
		selector = db.rawQuery("Select beforeCopyBW, beforePrintBW, " +
			" beforeScanBW, beforeFaxBW, beforeCopyFC, " +
			" beforePrintFC, beforeScanFC, beforeBWTotal, " +
			" beforeFCTotal, afterCopyBW, afterPrintBW, " +
			" afterScanBW, afterFaxBW, afterCopyFC, " +
			" afterPrintFC, afterScanFC, afterBWTotal, " + 
			" afterFCTotal, eTicket, repair, remarks, " +
			" selectedPayment, selectedOnsite, " +
			" selectedApproval, selectedPending, password, timeout from " + 
			SERVICE_TABLE + " Where referenceNo ='" + filter + "'", null);

		if (selector.moveToFirst()){
			data[0] = selector.getString(selector.getColumnIndex("beforeCopyBW"));
			data[1] = selector.getString(selector.getColumnIndex("beforePrintBW"));
			data[2] = selector.getString(selector.getColumnIndex("beforeScanBW"));
			data[3] = selector.getString(selector.getColumnIndex("beforeFaxBW"));
			data[4] = selector.getString(selector.getColumnIndex("beforeCopyFC"));
			data[5] = selector.getString(selector.getColumnIndex("beforePrintFC"));
			data[6] = selector.getString(selector.getColumnIndex("beforeScanFC"));
			data[7] = selector.getString(selector.getColumnIndex("beforeBWTotal"));
			data[8] = selector.getString(selector.getColumnIndex("beforeFCTotal"));
			data[9] = selector.getString(selector.getColumnIndex("afterCopyBW"));
			data[10] = selector.getString(selector.getColumnIndex("afterPrintBW"));
			data[11] = selector.getString(selector.getColumnIndex("afterScanBW"));
			data[12] = selector.getString(selector.getColumnIndex("afterFaxBW"));
			data[13] = selector.getString(selector.getColumnIndex("afterCopyFC"));
			data[14] = selector.getString(selector.getColumnIndex("afterPrintFC"));
			data[15] = selector.getString(selector.getColumnIndex("afterScanFC"));
			data[16] = selector.getString(selector.getColumnIndex("afterBWTotal"));
			data[17] = selector.getString(selector.getColumnIndex("afterFCTotal"));
			data[18] = selector.getString(selector.getColumnIndex("eTicket"));
			data[19] = selector.getString(selector.getColumnIndex("repair"));
			data[20] = selector.getString(selector.getColumnIndex("remarks"));
			data[21] = selector.getString(selector.getColumnIndex("selectedPayment"));
			data[22] = selector.getString(selector.getColumnIndex("selectedOnsite"));
			data[23] = selector.getString(selector.getColumnIndex("selectedApproval"));
			data[24] = selector.getString(selector.getColumnIndex("selectedPending"));
			data[25] = selector.getString(selector.getColumnIndex("password"));
			data[26] = selector.getString(selector.getColumnIndex("timeout"));
		}
		db.close();
		return data;
	}
	
	public Cursor getTravelRecord(String referenceNo){
		selector = getReadableDatabase().query(TRAVEL_TABLE, 
				new String[] {"_id",START,END,REF,TYPE,FARE,FROM,TO,OTHERS},
				REF +" = ?",
				new String[] {referenceNo},
				null,null,START+" DESC");
		db.close();
		return selector;
	}
	
	public String getTranspoDetails(String filter){
		db = this.getReadableDatabase();
		StringBuilder sb = new StringBuilder();
		String data = "";
		selector = db.rawQuery("Select startTime, endTime, referenceNo, type, fare, fromLocation, toLocation, otherfee from "+ TRAVEL_TABLE + " Where referenceNo = '" + filter + "'", null);
		if (selector.moveToFirst()){
			do{
				//Log.v("Transpo",selector.getString(0));
				String xml = "<Transportation>" +
						"<Start>" + selector.getString(selector.getColumnIndex("startTime")) + "</Start>" +
						"<End>" + selector.getString(selector.getColumnIndex("endTime")) + "</End>" +
						"<Fare>" + selector.getString(selector.getColumnIndex("fare")) + "</Fare>" +
						"<Type>" + selector.getString(selector.getColumnIndex("type")) + "</Type>" +
						"<From>" + selector.getString(selector.getColumnIndex("fromLocation")) + "</From>" +
						"<To>" + selector.getString(selector.getColumnIndex("toLocation")) + "</To>" +
						"<Other>" + selector.getString(selector.getColumnIndex("otherfee")) + "</Other>" +
						"</Transportation>";
				sb.append(xml);
			}while(selector.moveToNext());
		}
		data = sb.toString();
		return data;
	}
	
	public void deleteTravelData(long id, String referenceNo){
		db = this.getReadableDatabase();
		selector = db.rawQuery("Delete from "+ TRAVEL_TABLE + " Where _id =" + id + " AND " + REF + " = '" + referenceNo +"'", null);
		while (selector.moveToNext()) {

	    }
		db.close();
	}
	
	public void deleteAll(){
		this.getWritableDatabase().delete(SERVICE_TABLE, null, null);
		this.getWritableDatabase().delete(TRAVEL_TABLE, null, null);
	}
}
