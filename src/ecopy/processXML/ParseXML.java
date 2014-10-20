package ecopy.processXML;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ecopy.servicepoint_android.R;

public class ParseXML extends Declarations implements OnItemSelectedListener{
	private View thisView;
	public void XMLProcessor(View v){
		thisView = v;
		selectXML = (Spinner) v.findViewById(R.id.selectXML);
		xmlReader();
		adapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_spinner_dropdown_item, xmlFiles);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectXML.setAdapter(adapter);
		selectXML.setOnItemSelectedListener(this);
		initFields();
        Buttons();
	}
	
	public void initFields(){
		qrCode = (TextView) thisView.findViewById(R.id.qrCode);
		seID= (TextView) thisView.findViewById(R.id.seID);
		timeDispatched = (TextView) thisView.findViewById(R.id.timeDispatched);
		referenceNo = (TextView) thisView.findViewById(R.id.referenceNo);
		workController = (TextView) thisView.findViewById(R.id.workController);
		company = (TextView) thisView.findViewById(R.id.company);
		companyAddress = (TextView) thisView.findViewById(R.id.companyAddress);
		emailAddress = (TextView) thisView.findViewById(R.id.emailAddress);
		mfpModel = (TextView) thisView.findViewById(R.id.mfpModel);
		contactPerson = (TextView) thisView.findViewById(R.id.contactPerson);
		contactNo = (TextView) thisView.findViewById(R.id.contactNo);
		contract = (TextView) thisView.findViewById(R.id.contract);
		nature = (TextView) thisView.findViewById(R.id.nature);
		dateInstall = (TextView) thisView.findViewById(R.id.dateInstall);
		backJobNo = (TextView) thisView.findViewById(R.id.backJobNo);
		prevRefNo = (TextView) thisView.findViewById(R.id.prevRefNo);
		complaint= (TextView) thisView.findViewById(R.id.complaint);
		///
		beforeCopyBW = (TextView) thisView.findViewById(R.id.beforeCopyBW);
		beforePrintBW = (TextView) thisView.findViewById(R.id.beforePrintBW);
		beforeScanBW = (TextView) thisView.findViewById(R.id.beforeScanBW);
		beforeFaxBW = (TextView) thisView.findViewById(R.id.beforeFaxBW);
		beforeCopyFC = (TextView) thisView.findViewById(R.id.beforeCopyFC);
		beforePrintFC = (TextView) thisView.findViewById(R.id.beforePrintFC);
		beforeScanFC = (TextView) thisView.findViewById(R.id.beforeScanFC);
		beforeBWTotal = (TextView) thisView.findViewById(R.id.beforeBW);
		beforeFCTotal = (TextView) thisView.findViewById(R.id.beforeFC);
		//
		afterCopyBW = (TextView) thisView.findViewById(R.id.afterCopyBW);
		afterPrintBW = (TextView) thisView.findViewById(R.id.afterPrintBW);
		afterScanBW = (TextView) thisView.findViewById(R.id.afterScanBW);
		afterFaxBW = (TextView) thisView.findViewById(R.id.afterFaxBW);
		afterCopyFC = (TextView) thisView.findViewById(R.id.afterCopyFC);
		afterPrintFC = (TextView) thisView.findViewById(R.id.afterPrintFC);
		afterScanFC = (TextView) thisView.findViewById(R.id.afterScanFC);
		afterBWTotal = (TextView) thisView.findViewById(R.id.afterBW);
		afterFCTotal = (TextView) thisView.findViewById(R.id.afterFC);
	}
	
	public void xmlReader(){
		xmlFiles = new ArrayList<String>();
		downloadsFolder = new File(storage);
		mfsrXMLFiles = downloadsFolder.listFiles();
		for (int i = 0; i<mfsrXMLFiles.length; ++i){
			xmlFiles.add(mfsrXMLFiles[i].getName()+"");
		}
	}
	
	public void xmlLoader(View v, String filename){
		//Toast.makeText(v.getContext(), storage + filename, Toast.LENGTH_SHORT).show();
		xmlFile = new File(storage + filename);
		XMLFactory = DocumentBuilderFactory.newInstance();
		try {
			XMLBuilder = XMLFactory.newDocumentBuilder();
			xml =  XMLBuilder.parse(xmlFile);
			xml.getDocumentElement().normalize();
			//
			TechnicalMonitoring = xml.getElementsByTagName("MFSR");
			//Node qr = TechnicalMonitoring.item(0);
			//NodeList ef = (NodeList) ((Element) ((NodeList)((Element) qr).getElementsByTagName("QRCode")).item(0)).getChildNodes();
			qrCode.setText(((Node) ef.item(0)).getNodeValue() +"");
			 
			
			
			for (int x = 0; x < TechnicalMonitoring.getLength(); x++){
				//Element fstElmnt = (Element) fstNode;
				//NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("CompanyName");
			    //Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
			    //NodeList fstNm = fstNmElmnt.getChildNodes();
				
			    //company.setText(((Node) fstNm.item(0)).getNodeValue() +"");
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void xmlWriter(){
		
	}
	public void Buttons(){
		//saveButton = (Button) v.findViewById(R.id.saveButton);

	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		xmlLoader(view, parent.getItemAtPosition(position).toString());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
