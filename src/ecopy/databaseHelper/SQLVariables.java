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
			"create table " + SERVICE_TABLE + "(_id integer primary key autoincrement,"
			+ REFERENCENO + " text not null,"
			+ PASSWORD + " text,"
			+ TIME_IN + " text,"
			+ TIME_OUT + " text,"
			+ "beforeCopyBW text,"
			+ "beforePrintBW text,"
			+ "beforeScanBW text,"
			+ "beforeFaxBW text,"
			+ "beforeCopyFC text,"
			+ "beforePrintFC text,"
			+ "beforeScanFC text,"
			+ "beforeBWTotal text,"
			+ "beforeFCTotal text,"
			+ "afterCopyBW text,"
			+ "afterPrintBW text,"
			+ "afterScanBW text,"
			+ "afterFaxBW text,"
			+ "afterCopyFC text,"
			+ "afterPrintFC text,"
			+ "afterScanFC text,"
			+ "afterBWTotal text,"
			+ "afterFCTotal text,"
			+ "eTicket text,"
			+ "repair text,"
			+ "remarks text,"
			+ "selectedPayment text,"
			+ "selectedOnsite text,"
			+ "selectedApproval text,"
			+ "selectedPending text"
			+ ");" ;
	
	public static final String TRAVEL_TABLE = "Travel";
	public static final String START = "startTime";
	public static final String END = "endTime";
	public static final String REF = "referenceNo";
	public static final String TYPE = "type";
	public static final String FARE = "fare";
	public static final String FROM = "fromLocation";
	public static final String TO = "toLocation";
	public static final String OTHERS = "otherfee";
	public static final String TOTAL = "total";
	
	public static final String TRAVEL_CREATE =
			"create table " + TRAVEL_TABLE + "(_id integer primary key autoincrement,"
			+ START +" text not null,"
			+ END +" text not null,"
			+ REF +" text not null,"
			+ TYPE +" text not null,"
			+ FARE +" text not null,"
			+ FROM +" text not null,"
			+ TO +" text not null,"
			+ OTHERS +" text not null,"
			+ TOTAL +" text not null"
			+ ");";
	//.Hey Dont forget to add spaces between static String and Data type
}
