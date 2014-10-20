package ecopy.processXML;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Declarations extends Activity{
	public Spinner selectXML;
	public List<String> xmlFiles;
	public ArrayAdapter<String> adapter;
	public Button saveButton;
	public File downloadsFolder, xmlFile;
	public File [] mfsrXMLFiles;
	public DocumentBuilderFactory XMLFactory;
	public DocumentBuilder XMLBuilder;
	public Document xml;
	public String storage = Environment.getExternalStorageDirectory().getPath() +"/"+ Environment.DIRECTORY_DOWNLOADS +"/MFSR/";
	public NodeList TechnicalMonitoring, ClientInformation, MeterReadingBefore, MeterReadingAfter;
	//fields
	public TextView qrCode, seID, timeDispatched, referenceNo, workController, company, customerNo,
		companyAddress, emailAddress, mfpModel, contactPerson, contactNo, contract, nature,
		dateInstall, backJobNo, prevRefNo, complaint;
	public TextView beforeCopyBW, beforePrintBW, beforeScanBW, beforeFaxBW, beforeCopyFC, 
		beforePrintFC, beforeScanFC, beforeBWTotal, beforeFCTotal;
	public TextView afterCopyBW, afterPrintBW, afterScanBW, afterFaxBW, afterCopyFC, 
		afterPrintFC, afterScanFC, afterBWTotal, afterFCTotal;
}
