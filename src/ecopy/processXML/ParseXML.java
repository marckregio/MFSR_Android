package ecopy.processXML;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import ecopy.databaseHelper.DatabaseHelper;
import ecopy.databaseHelper.SQLVariables;
import ecopy.servicepoint_android.R;

public class ParseXML extends Declarations implements OnItemSelectedListener{
	private View thisView;
	ecopy.servicepoint_android.Declarations storageDestination = new ecopy.servicepoint_android.Declarations();
	ecopy.servicepoint_android.NavigationDrawerFragment nav = new ecopy.servicepoint_android.NavigationDrawerFragment();
	private String storage = storageDestination.getStorageDestination();
	private String finish = storageDestination.getFinishedDestination();
	public void XMLProcessor(View v){
		thisView = v;
		selectXML = (Spinner) thisView.findViewById(R.id.selectXML);
		xmlReader();
		adapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_spinner_dropdown_item, xmlFiles);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectXML.setAdapter(adapter);
		selectXML.setOnItemSelectedListener(this);
		initFields();
		initJava();
        Buttons();
        //db.deleteAll();
	}
	
	public void initTimeInPop(){
		inflater = (LayoutInflater) thisView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		popup = inflater.inflate(R.layout.timepopup, null);
		window = new PopupWindow(popup, 300,220);
		window.setAnimationStyle(R.style.PopupAnimation);
		window.setBackgroundDrawable(new ColorDrawable());
		window.setOutsideTouchable(true);
		window.setFocusable(true);
		timeFormat = new SimpleDateFormat("hh:mm:ss  aa");
		timeinPopup = (TextView) popup.findViewById(R.id.timein);
		window.showAtLocation(thisView, Gravity.CENTER, 0, 0);
		getTime = (Button) popup.findViewById(R.id.getTime);
		getTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currentDate = new Date();
				currentTime = timeFormat.format(currentDate);
				timeinPopup.setText(currentTime);
				insertTimeQuery();
			}
		});
		proceed = (Button) popup.findViewById(R.id.closePopup);
		proceed.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getTimeRecord();
				window.dismiss();
			}
		});
	}
	
	public void initTravelPop(){
		inflater2 = (LayoutInflater) thisView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		travelPopup = inflater2.inflate(R.layout.travelpopup, null);
		travel = new PopupWindow(travelPopup, 300,490);
		travel.setAnimationStyle(R.style.PopupAnimation);
		travel.setBackgroundDrawable(new ColorDrawable());
		travel.setOutsideTouchable(true);
		travel.setFocusable(true);
		//
		travelType = (Spinner) travelPopup.findViewById(R.id.type);
		travelTypes = new ArrayList<String>();
		travelTypes.add("Tricycle");
		travelTypes.add("Jeepney");
		travelTypes.add("Taxi");
		travelTypes.add("Bus");
		travelTypes.add("Train");
		travelTypes.add("Pedicab");
		travelAdapter = new ArrayAdapter<String>(travelPopup.getContext(), android.R.layout.simple_spinner_dropdown_item, travelTypes);
		travelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		travelType.setAdapter(travelAdapter);
		travelType.setOnItemSelectedListener(this);
		travel.showAtLocation(thisView, Gravity.CENTER, 0, 0);
		fare = (EditText) travelPopup.findViewById(R.id.fare);
		saveTravel = (Button) travelPopup.findViewById(R.id.closePopup);
		timeFormat = new SimpleDateFormat("hh:mm:ss  aa");
		start = (TimePicker) travelPopup.findViewById(R.id.timeStartPicker);
		end = (TimePicker) travelPopup.findViewById(R.id.timeEndPicker);
		saveTravel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				insertTravelQuery();
				travel.dismiss();
				nav.explicitReload(1);
			}
		});
	}
	
	public void initFields(){
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
		payment.setOnItemSelectedListener(this);
		eTicket = (EditText) thisView.findViewById(R.id.eTicket);
		repair = (EditText) thisView.findViewById(R.id.repair);
		onsite = (Spinner) thisView.findViewById(R.id.onsite);
		onsite.setOnItemSelectedListener(this);
		pending = (Spinner) thisView.findViewById(R.id.pending);
		pending.setOnItemSelectedListener(this);
		approval = (Spinner) thisView.findViewById(R.id.approval);
		approval.setOnItemSelectedListener(this);
		remarks = (EditText) thisView.findViewById(R.id.remarks);
		timeIn = (EditText) thisView.findViewById(R.id.timeIn);
		timeOut = (EditText) thisView.findViewById(R.id.timeOut);
	}
	
	public void initJava(){
		paymentMethods = new ArrayList<String>();
		onsiteStatuses = new ArrayList<String>();
		pendingReasons = new ArrayList<String>();
		approvalTypes = new ArrayList<String>();
		db = new DatabaseHelper(thisView.getContext());
		
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
					submissionNo = xmlParser.nextText();
					xmlParser.nextTag();
					instanceID = xmlParser.nextText();
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
					//xmlParser.nextTag();
					//repair.setText(xmlParser.nextText());
					//xmlParser.nextTag();
					//remarks.setText(xmlParser.nextText());
					//xmlParser.nextTag();
					//timeIn.setText(xmlParser.nextText());
					//xmlParser.nextTag();
					//timeOut.setText(xmlParser.nextText());
				}
				*/
				if (name.equals("Maintenances")){
					int x = 0;
					paymentMethods.clear();
					onsiteStatuses.clear();
					pendingReasons.clear();
					approvalTypes.clear();
					paymentMethods.add("Select an item");
					onsiteStatuses.add("Select an item");
					pendingReasons.add("Select an item");
					approvalTypes.add("Select an item");
					do{
						
						xmlParser.nextTag();
						x++;
						if (xmlParser.getName().equals("PaymentMethod")){
							paymentMethods.add(xmlParser.nextText());
						} 
						else if  (xmlParser.getName().equals("OnsiteStatuses")){
							onsiteStatuses.add(xmlParser.nextText());
						}
						else if  (xmlParser.getName().equals("ApprovalType")){
							approvalTypes.add(xmlParser.nextText());
						}
						else if  (xmlParser.getName().equals("Parts-Supplies-Requisition")){
							pendingReasons.add(xmlParser.nextText());
						}
						else if  (xmlParser.getName().equals("Unfinished")){
							pendingReasons.add(xmlParser.nextText());
						}
						else if  (xmlParser.getName().equals("Carry-Over")){
							pendingReasons.add(xmlParser.nextText());
						}
						else{
							break;
						}
					}while (x < 20);
					
					paymentAdapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_spinner_dropdown_item, paymentMethods);
					paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					payment.setAdapter(paymentAdapter);
					pendingAdapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_spinner_dropdown_item, pendingReasons);
					pendingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					pending.setAdapter(pendingAdapter);
					onsiteAdapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_spinner_dropdown_item, onsiteStatuses);
					onsiteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					onsite.setAdapter(onsiteAdapter);
					approvalAdapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_spinner_dropdown_item, approvalTypes);
					approvalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					approval.setAdapter(approvalAdapter);
				}
				break;
			}	
			eventType = xmlParser.next();
		}
	}
	
	public void xmlBuilder(){
		xml = 	"<MFSR>" +
					"<ReferenceNo>" + referenceNo.getText() + "</ReferenceNo>" +
					"<QRCode>" + qrCode.getText() + "</QRCode>" +
					"<TimeinRadio>Time-In</TimeinRadio>" +
					"<SubmissionNo>" + submissionNo + "</SubmissionNo>" +
					"<InstanceID>" + instanceID + "</InstanceID>" +
			    	"<MeterReadingBefore>" +
			    		"<CopyBW>"+ beforeCopyBW.getText() +"</CopyBW>" +
			    		"<PrintBW>" + beforePrintBW.getText() + "</PrintBW>" +
			    		"<ScanBW>" + beforeScanBW.getText() + "</ScanBW>" +
			    		"<FaxBW>" + beforeFaxBW.getText() + "</FaxBW>" +
			    		"<CopyFC>" + beforeCopyFC.getText() + "</CopyFC>" +
			    		"<PrintFC>" + beforePrintFC.getText() + "</PrintFC>" +
			    		"<ScanFC>" + beforeScanFC.getText() + "</ScanFC>" +
			    		"<BeforeBW>" + beforeBWTotal.getText() + "</BeforeBW>" +
			    		"<BeforeFC>" + beforeFCTotal.getText() + "</BeforeFC>" +
			    	"</MeterReadingBefore>" +
			    	"<MeterReadingAfter>" +
		    			"<CopyBW>"+ afterCopyBW.getText() +"</CopyBW>" +
		    			"<PrintBW>" + afterPrintBW.getText() + "</PrintBW>" +
		    			"<ScanBW>" + afterScanBW.getText() + "</ScanBW>" +
		    			"<FaxBW>" + afterFaxBW.getText() + "</FaxBW>" +
		    			"<CopyFC>" + afterCopyFC.getText() + "</CopyFC>" +
		    			"<PrintFC>" + afterPrintFC.getText() + "</PrintFC>" +
		    			"<ScanFC>" + afterScanFC.getText() + "</ScanFC>" +
		    			"<AfterBW>" + afterBWTotal.getText() + "</AfterBW>" +
		    			"<AfterFC>" + afterFCTotal.getText() + "</AfterFC>" +
			    	"</MeterReadingAfter>" +
			    	"<ServiceInformation>" +
			    		"<PaymentMethod>" + selectedPayment + "</PaymentMethod>" +
			    		"<EticketNo>" + eTicket.getText() + "</EticketNo>" +
			    		"<RepairDetails>" + repair.getText() + "</RepairDetails>" +
			    		"<OnSiteDetails>" + selectedOnsite + "</OnSiteDetails>" +
			    		"<PendingReason>" + selectedPending + "</PendingReason>" +
			    		"<ApprovalType>" + selectedApproval + "</ApprovalType>" +
			    		"<Remarks>" + remarks.getText() + "</Remarks>" +
			    		"<TimeIN>09:00</TimeIN>" +
			    		"<TimeOUT>12:00</TimeOUT>" +
			    	"</ServiceInformation>" +
				"</MFSR>";
		xmlWriter(xml);
	}
	
	public void xmlWriter(String xml){
		finishFolder = new File (finish, referenceNo.getText() + ".xml");
		try {
			xmlWriter = new FileWriter (finishFolder);
			xmlWriter.append(xml);
			xmlWriter.flush();
			xmlWriter.close();
			Toast.makeText(thisView.getContext(), referenceNo.getText() + ".xml Generated",Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Buttons(){
		saveXML = (Button) thisView.findViewById(R.id.saveXML);
		saveXML.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (fieldCheck()){
					xmlBuilder();
				} else {
					Toast.makeText(thisView.getContext(), "Please Complete The Form",Toast.LENGTH_SHORT).show();
				}
			}
		});
		addTravel = (Button) thisView.findViewById(R.id.addTravel);
		addTravel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				initTravelPop();
			}
		});
		saveOnly = (Button) thisView.findViewById(R.id.saveOnly);
		saveOnly.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				updateServiceQuery();
				Toast.makeText(thisView.getContext(), "Successful! Please Proceed to your Actual Service",Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		switch(parent.getId()){
		case R.id.selectXML:
			selectedXML = parent.getItemAtPosition(position).toString();
			xmlLoader(view, selectedXML+".xml");
			getTimeRecord();
			getDetails();
			getTravelRecord();
			break;
		case R.id.payment:
			selectedPayment = parent.getItemAtPosition(position).toString();
			break;
		case R.id.onsite:
			selectedOnsite = parent.getItemAtPosition(position).toString();
			break;
		case R.id.pending:
			selectedPending = parent.getItemAtPosition(position).toString();
			break;
		case R.id.approval:
			selectedApproval = parent.getItemAtPosition(position).toString();
			break;
		case R.id.type:
			selectedTravel = parent.getItemAtPosition(position).toString();
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	public void insertTimeQuery(){
		db.timeRecord(selectedXML,"", timeinPopup.getText() +"", "");
	}
	
	public void updateServiceQuery(){
		db.updateServiceRecord(selectedXML, beforeCopyBW.getText() + "", 
				beforePrintBW.getText() + "", beforeScanBW.getText() + "", 
				beforeFaxBW.getText() + "", beforeCopyFC.getText() + "", 
				beforePrintFC.getText() + "", beforeScanFC.getText() + "", 
				beforeBWTotal.getText() + "", beforeFCTotal.getText() + "", 
				afterCopyBW.getText() + "", afterPrintBW.getText() + "", 
				afterScanBW.getText() + "", afterFaxBW.getText() + "", 
				afterCopyFC.getText() + "", afterPrintFC.getText() + "", 
				afterScanFC.getText() + "", afterBWTotal.getText() + "", 
				afterFCTotal.getText() + "", eTicket.getText() + "" ,
				repair.getText() + "", remarks.getText() + "",
				selectedPayment , selectedOnsite, selectedApproval, selectedPending);
	}
	
	public void insertTravelQuery(){
		String startTime = start.getCurrentHour() + ":" + start.getCurrentMinute();
		String endTime = end.getCurrentHour() + ":" + end.getCurrentMinute();
		db.travelRecord(startTime, endTime, selectedXML, selectedTravel, fare.getText()+"Php");
	}
	
	public void getTimeRecord(){
		timeData = db.getTimeData(selectedXML);
		if (timeData.equals("")){
			Toast.makeText(thisView.getContext(), "No Time-in",Toast.LENGTH_SHORT).show();
			timeIn.setText("No Time-in");
			initTimeInPop();
			saveOnly.setEnabled(false);
			saveXML.setEnabled(false);
		} else {
			Toast.makeText(thisView.getContext(), timeData +"",Toast.LENGTH_SHORT).show();
			timeIn.setText(timeData);
			saveOnly.setEnabled(true);
			saveXML.setEnabled(true);
		}
	}
	
	public void getDetails(){
		String [] data = db.getDetails(selectedXML);
		beforeCopyBW.setText(data[0]+"");
		beforePrintBW.setText(data[1]+"");
		beforeScanBW.setText(data[2]+"");
		beforeFaxBW.setText(data[3]+"");
		beforeCopyFC.setText(data[4]+"");
		beforePrintFC.setText(data[5]+"");
		beforeScanFC.setText(data[6]+"");
		beforeBWTotal.setText(data[7]+"");
		beforeFCTotal.setText(data[8]+"");
		afterCopyBW.setText(data[9]+"");
		afterPrintBW.setText(data[10]+"");
		afterScanBW.setText(data[11]+"");
		afterFaxBW.setText(data[12]+"");
		afterCopyFC.setText(data[13]+"");
		afterPrintFC.setText(data[14]+"");
		afterScanFC.setText(data[15]+"");
		afterBWTotal.setText(data[16]+"");
		afterFCTotal.setText(data[17]+"");
		eTicket.setText(data[18]+"");
		repair.setText(data[19]+"");
		remarks.setText(data[20]+"");
		payment.setSelection(setSpinnerIndex(payment, data[21]+""));
		onsite.setSelection(setSpinnerIndex(onsite, data[22]+""));
		approval.setSelection(setSpinnerIndex(approval, data[23]+""));
		pending.setSelection(setSpinnerIndex(pending, data[24]+""));
	}
	
	public int setSpinnerIndex(Spinner spin, String val){
		int index = 0;

		for (int i=0;i<spin.getCount();i++){
			if (spin.getItemAtPosition(i).equals(val)){
				index = i;
			}
		}
		return index;
	}
	
	@SuppressWarnings("deprecation")
	public void getTravelRecord(){
		selector = db.getTravelRecord(selectedXML);
		String [] tableHeader = {SQLVariables.START,SQLVariables.TYPE,SQLVariables.FARE,SQLVariables.END};
		int [] tableRow = {R.id.startTimeLabel, R.id.travelTypeLabel, R.id.fareLabel, R.id.endTimeLabel};
		travelList = new SimpleCursorAdapter(thisView.getContext(), R.layout.travellist, 
				selector, tableHeader, tableRow, 0);
		travelData = (ListView) thisView.findViewById(R.id.travelRecordsList);
		travelData.setAdapter(travelList);
	}
	
	public Boolean fieldCheck(){
		Boolean proceed = false;
		if (beforeCopyBW.getText().equals("")
				|| beforeCopyBW.getText().equals("")
				|| beforePrintBW.getText().equals("")
				|| beforeScanBW.getText().equals("")
				|| beforeFaxBW.getText().equals("")
				|| beforeCopyFC.getText().equals("")
				|| beforePrintFC.getText().equals("")
				|| beforeScanFC.getText().equals("")
				|| afterCopyBW.getText().equals("")
				|| afterPrintBW.getText().equals("")
				|| afterScanBW.getText().equals("")
				|| afterFaxBW.getText().equals("")
				|| afterCopyFC.getText().equals("")
				|| afterPrintFC.getText().equals("")
				|| afterScanFC.getText().equals("")
				|| selectedPayment.equals("Select an item")
				|| selectedOnsite.equals("Select an item")
				|| selectedApproval.equals("Select an item")
				|| selectedPending.equals("Select an item")
				|| eTicket.getText().equals("")
				|| repair.getText().equals("")
				|| remarks.getText().equals("")
				){
			proceed = false;
		}else{
			proceed = true;
		}
		return proceed;
	}
}

