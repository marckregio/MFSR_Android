package ecopy.databaseHelper;

public interface SQLVariables {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "mfsr.db";
	
	public static final String SERVICE_TABLE = "Service";
	public static final String REFERENCENO = "referenceNo";
	public static final String TIME_IN = "timein";
	public static final String TIME_OUT = "timeout";
	public static final String PASSWORD = "password";
	
	public static final String SERVICE_CREATE = 
			"create table " + SERVICE_TABLE + "(id integer primary key autoincrement,"
			+ REFERENCENO + " text not null,"
			+ PASSWORD + " text,"
			+ TIME_IN + " text,"
			+ TIME_OUT + " text"
			+ ");" ;
	
	public static final String TRAVEL_TABLE = "Travel";
	public static final String START = "startTime";
	public static final String END = "endTime";
	public static final String REF = "referenceNo";
	public static final String TYPE = "type";
	public static final String FARE = "fare";
	
	public static final String TRAVEL_CREATE =
			"create table " + TRAVEL_TABLE + "(id integer primary key increment,"
			+ START +" text not null,"
			+ END +" text not null,"
			+ REF +" text not null,"
			+ TYPE +" text not null,"
			+ FARE +" text not null"
			+ ");";
	//.Hey Dont forget to add spaces between static String and Data type
}
