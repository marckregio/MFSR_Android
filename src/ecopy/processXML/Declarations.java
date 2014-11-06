package ecopy.processXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;

import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

public class Declarations extends Activity{
	public Spinner selectXML, payment, onsite, approval, pending;
	public String selectedPayment, selectedOnsite, selectedApproval, selectedPending;
	public List<String> xmlFiles, paymentMethods, onsiteStatuses, approvalTypes, pendingReasons;
	public ArrayAdapter<String> adapter, paymentAdapter, onsiteAdapter, approvalAdapter, pendingAdapter;
	public File downloadsFolder, finishFolder;
	public FileInputStream xmlFile;
	public FileWriter xmlWriter;
	public File [] mfsrXMLFiles;
	public XmlPullParser xmlParser;
	public XmlPullParserFactory xmlFactory;
	public String xml;
	public NodeList TechnicalMonitoring, ClientInformation, MeterReadingBefore, MeterReadingAfter;
	//fields
	public TextView qrCode, seID, timeDispatched, referenceNo, workController, company, customerNo,
		companyAddress, emailAddress, mfpModel, contactPerson, contactNo, contract, nature,
		dateInstall, backJobNo, prevRefNo, complaint;
	public EditText beforeCopyBW, beforePrintBW, beforeScanBW, beforeFaxBW, beforeCopyFC, 
		beforePrintFC, beforeScanFC, beforeBWTotal, beforeFCTotal;
	public EditText afterCopyBW, afterPrintBW, afterScanBW, afterFaxBW, afterCopyFC, 
		afterPrintFC, afterScanFC, afterBWTotal, afterFCTotal;
	public EditText timeIn, timeOut, eTicket, repair, remarks;
	//Buttons
	public Button saveXML;
	//Popup
	public View popup;
	public LayoutInflater inflater;
	public PopupWindow window;
	
}
