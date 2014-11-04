package ecopy.processXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;

import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Declarations extends Activity{
	public Spinner selectXML, payment, onsite;
	public String selectedPayment, selectedOnsite;
	public List<String> xmlFiles, paymentMethods, onsiteStatuses;
	public ArrayAdapter<String> adapter, paymentAdapter, onsiteAdapter;
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
}
