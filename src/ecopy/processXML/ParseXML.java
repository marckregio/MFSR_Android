package ecopy.processXML;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import ecopy.servicepoint_android.R;

public class ParseXML extends Declarations implements OnItemSelectedListener{
	private View thisView;
	ecopy.servicepoint_android.Declarations storageDestination = new ecopy.servicepoint_android.Declarations();
	private String storage = storageDestination.getStorageDestination();
	ecopy.servicepoint_android.Declarations finishDestination = new ecopy.servicepoint_android.Declarations();
	private String finish = finishDestination.getStorageDestination();
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
		save = false;
		qrCode = (TextView) thisView.findViewById(R.id.qrCode);
		seID= (TextView) thisView.findViewById(R.id.seID);
		timeDispatched = (TextView) thisView.findViewById(R.id.timeDispatched);
		referenceNo = (TextView) thisView.findViewById(R.id.referenceNo);
		workController = (TextView) thisView.findViewById(R.id.workController);
		company = (TextView) thisView.findViewById(R.id.company);
		customerNo = (TextView) thisView.findViewById(R.id.customerNo);
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
		//
		beforeCopyBW = (EditText) thisView.findViewById(R.id.beforeCopyBW);
		beforePrintBW = (EditText) thisView.findViewById(R.id.beforePrintBW);
		beforeScanBW = (EditText) thisView.findViewById(R.id.beforeScanBW);
		beforeFaxBW = (EditText) thisView.findViewById(R.id.beforeFaxBW);
		beforeCopyFC = (EditText) thisView.findViewById(R.id.beforeCopyFC);
		beforePrintFC = (EditText) thisView.findViewById(R.id.beforePrintFC);
		beforeScanFC = (EditText) thisView.findViewById(R.id.beforeScanFC);
		beforeBWTotal = (EditText) thisView.findViewById(R.id.beforeBW);
		beforeFCTotal = (EditText) thisView.findViewById(R.id.beforeFC);
		//
		afterCopyBW = (EditText) thisView.findViewById(R.id.afterCopyBW);
		afterPrintBW = (EditText) thisView.findViewById(R.id.afterPrintBW);
		afterScanBW = (EditText) thisView.findViewById(R.id.afterScanBW);
		afterFaxBW = (EditText) thisView.findViewById(R.id.afterFaxBW);
		afterCopyFC = (EditText) thisView.findViewById(R.id.afterCopyFC);
		afterPrintFC = (EditText) thisView.findViewById(R.id.afterPrintFC);
		afterScanFC = (EditText) thisView.findViewById(R.id.afterScanFC);
		afterBWTotal = (EditText) thisView.findViewById(R.id.afterBW);
		afterFCTotal = (EditText) thisView.findViewById(R.id.afterFC);
		//
		payment = (Spinner) thisView.findViewById(R.id.payment);
		paymentMethods = new ArrayList<String>();
		eTicket = (EditText) thisView.findViewById(R.id.eTicket);
		repair = (EditText) thisView.findViewById(R.id.repair);
		remarks = (EditText) thisView.findViewById(R.id.remarks);
		timeIn = (EditText) thisView.findViewById(R.id.timeIn);
		timeOut = (EditText) thisView.findViewById(R.id.timeOut);
	}

	public void xmlReader(){
		xmlFiles = new ArrayList<String>();
		downloadsFolder = new File(storage);
		mfsrXMLFiles = downloadsFolder.listFiles();
		for (int i = 0; i<mfsrXMLFiles.length; ++i){
			String fileName = mfsrXMLFiles[i].getName();
			xmlFiles.add(fileName.substring(0, fileName.length() - 4)+"");
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
				if (name.equals("TechnicalMonitoring")){ 
					xmlParser.nextTag();
					qrCode.setText(xmlParser.nextText());
					xmlParser.nextTag();
					seID.setText(xmlParser.nextText());
					xmlParser.nextTag();
					timeDispatched.setText(xmlParser.nextText());
				}
				if (name.equals("ClientInformation")){
					xmlParser.nextTag();
					referenceNo.setText(xmlParser.nextText());
					xmlParser.nextTag();
					workController.setText(xmlParser.nextText());
					xmlParser.nextTag();
					company.setText(xmlParser.nextText());
					xmlParser.nextTag();
					customerNo.setText(xmlParser.nextText());
					xmlParser.nextTag();
					companyAddress.setText(xmlParser.nextText());
					xmlParser.nextTag();
					emailAddress.setText(xmlParser.nextText());
					xmlParser.nextTag();
					mfpModel.setText(xmlParser.nextText());
					xmlParser.nextTag();
					contactPerson.setText(xmlParser.nextText());
					xmlParser.nextTag();
					contactNo.setText(xmlParser.nextText());
					xmlParser.nextTag();
					contract.setText(xmlParser.nextText());
					xmlParser.nextTag();
					nature.setText(xmlParser.nextText());
					xmlParser.nextTag();
					dateInstall.setText(xmlParser.nextText());
					xmlParser.nextTag();
					backJobNo.setText(xmlParser.nextText());
					xmlParser.nextTag();
					prevRefNo.setText(xmlParser.nextText());
					xmlParser.nextTag();
					complaint.setText(xmlParser.nextText());
				}
				/*
				if (save == true){
					if (name.equals("MeterReadingBefore")){
						xmlParser.nextTag();
						beforeCopyBW.setText(xmlParser.nextText());
						xmlParser.nextTag();
						beforePrintBW.setText(xmlParser.nextText());
						xmlParser.nextTag();
						beforeScanBW.setText(xmlParser.nextText());
						xmlParser.nextTag();
						beforeFaxBW.setText(xmlParser.nextText());
						xmlParser.nextTag();
						beforeCopyFC.setText(xmlParser.nextText());
						xmlParser.nextTag();
						beforePrintFC.setText(xmlParser.nextText());
						xmlParser.nextTag();
						beforeScanFC.setText(xmlParser.nextText());
						xmlParser.nextTag();
						beforeBWTotal.setText(xmlParser.nextText());
						xmlParser.nextTag();
						beforeFCTotal.setText(xmlParser.nextText());
					}
					if (name.equals("MeterReadingAfter")){
						xmlParser.nextTag();
						afterCopyBW.setText(xmlParser.nextText());
						xmlParser.nextTag();
						afterPrintBW.setText(xmlParser.nextText());
						xmlParser.nextTag();
						afterScanBW.setText(xmlParser.nextText());
						xmlParser.nextTag();
						afterFaxBW.setText(xmlParser.nextText());
						xmlParser.nextTag();
						afterCopyFC.setText(xmlParser.nextText());
						xmlParser.nextTag();
						afterPrintFC.setText(xmlParser.nextText());
						xmlParser.nextTag();
						afterScanFC.setText(xmlParser.nextText());
						xmlParser.nextTag();
						afterBWTotal.setText(xmlParser.nextText());
						xmlParser.nextTag();
						afterFCTotal.setText(xmlParser.nextText());
					}
					if (name.equals("ServiceInformation")){
						xmlParser.nextTag();
						//payment.setText(xmlParser.nextText());
						xmlParser.nextTag();
						eTicket.setText(xmlParser.nextText());
						xmlParser.nextTag();
						repair.setText(xmlParser.nextText());
						xmlParser.nextTag();
						remarks.setText(xmlParser.nextText());
						xmlParser.nextTag();
						timeIn.setText(xmlParser.nextText());
						xmlParser.nextTag();
						timeOut.setText(xmlParser.nextText());
					}
				}*/
				if (name.equals("PaymentMethodMaintenance")){
					do{
						xmlParser.nextTag();
						paymentMethods.add(xmlParser.nextText());
						paymentAdapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_spinner_dropdown_item, paymentMethods);
						paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						payment.setAdapter(paymentAdapter);
					}while (xmlParser.getName().equals("pm"));
				}
				break;
			}	
			eventType = xmlParser.next();
		}
	}
	
	public void xmlWriter(){
		
	}
	
	public void Buttons(){
		saveXML = (Button) thisView.findViewById(R.id.saveXML);
		saveXML.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				xmlWriter();
			}
		});
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		xmlLoader(view, parent.getItemAtPosition(position).toString()+".xml");
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
