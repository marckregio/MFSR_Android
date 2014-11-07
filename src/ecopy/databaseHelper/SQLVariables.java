package ecopy.databaseHelper;

public interface SQLVariables {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "mfsr.db";
	
	public static final String TABLE_NAME = "Service";
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "referenceNo";
	public static final String TIME_IN = "timein";
	public static final String TIME_OUT = "timeout";
	
	public static final String TABLE_CREATE = 
			"create table " + TABLE_NAME + "("
			+ KEY_ID + " integer primary key autoincrement,"
			+ KEY_NAME + " text not null,"
			+ TIME_IN + " text,"
			+ TIME_OUT + " text"
			+ ");" ;
	
	
}
