package ecopy.processXML;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
		try {
			xmlFactory = xmlFactory.newInstance();
			xmlParser = xmlFactory.newPullParser();
			xmlFile = new FileInputStream(storage + filename);
			xmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			xmlParser.setInput(xmlFile, null);
			
			xmlParser(xmlParser);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void xmlParser(XmlPullParser xmlParser) throws XmlPullParserException,IOException{
		String name;
		int eventType = xmlParser.getEventType();
		while(eventType != XmlPullParser.END_DOCUMENT){
			switch(eventType){
			case XmlPullParser.START_TAG:
				name = xmlParser.getName();
				//Toast.makeText(thisView.getContext(), +"" ,Toast.LENGTH_SHORT).show();
				if (name.equals("QRCode")){ qrCode.setText(xmlParser.nextText()); }
				//if (name.equals("SEEmployeeID")){ seID.setText(xmlParser.nextText()); }
				//if (name.equals("TimeDispatched")){ timeDispatched.setText(xmlParser.nextText()); }
				//if (name.equals("ReferenceNo")){ referenceNo.setText(xmlParser.nextText()); }
				//if (name.equals("WorkController")){ workController.setText(xmlParser.nextText()); }
				//if (name.equals("CompanyName")){ company.setText(xmlParser.nextText()); }
				//if (name.equals("CustomerNo")){ customerNo.setText(xmlParser.nextText()); }
				break;
			}	
			eventType = xmlParser.next();
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
