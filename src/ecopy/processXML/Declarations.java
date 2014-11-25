package ecopy.processXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import ecopy.databaseHelper.DatabaseHelper;

public class Declarations extends Activity{
	public WindowManager windowManager;
	public Spinner selectXML, payment, onsite, approval, pending, travelType;
	public String selectedXML, selectedPayment, selectedOnsite, selectedApproval, selectedPending, selectedTravel;
	public List<String> xmlFiles, paymentMethods, onsiteStatuses, approvalTypes, parts, unfinished, carryover, blank, travelTypes;
	public ArrayAdapter<String> adapter, paymentAdapter, onsiteAdapter, approvalAdapter, pendingAdapter, travelAdapter;
	public File downloadsFolder, finishFolder;
	public FileInputStream xmlFile;
	public FileWriter xmlWriter;
	public File [] mfsrXMLFiles;
	public XmlPullParser xmlParser;
	public XmlPullParserFactory xmlFactory;
	public String xml, submissionNo, instanceID, formID, qrCodeString, transpo, paymentMethod;
	public NodeList TechnicalMonitoring, ClientInformation, MeterReadingBefore, MeterReadingAfter;
	//fields
	public TextView qrCode, seID, timeDispatched, referenceNo, workController, company, customerNo,
		companyAddress, emailAddress, mfpModel, contactPerson, contactNo, contract, nature,
		dateInstall, backJobNo, prevRefNo, complaint;
	public EditText beforeCopyBW, beforePrintBW, beforeScanBW, beforeFaxBW, beforeCopyFC, 
		beforePrintFC, beforeScanFC, beforeBWTotal, beforeFCTotal;
	public EditText afterCopyBW, afterPrintBW, afterScanBW, afterFaxBW, afterCopyFC, 
		afterPrintFC, afterScanFC, afterBWTotal, afterFCTotal;
	public EditText timeIn, timeOut, eTicket, repair, remarks, password, getTime;
	//Buttons
	public Button saveXML,getTimeButton, addTravel, saveOnly, sketchmate, draw;
	//Popup
	public View popup, travelPopup;
	public LayoutInflater inflater, inflater2;
	public PopupWindow window, travel;
	public SimpleDateFormat timeFormat;
	public Date currentDate;
	public String currentTime, timeData;
	public TextView timeinPopup, fareLabel;
	public EditText fare, onsiteFee, from, to;
	public Button saveTravel;
	//AlertDialog
	public AlertDialog.Builder alertdialog;
	public int selectedPosition;
	//Database
	public DatabaseHelper db;
	public Cursor selector;
	public SimpleCursorAdapter travelList;
	public ListView travelData;
}
