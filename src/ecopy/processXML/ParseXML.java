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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
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
	ecopy.servicepoint_android.Declarations superClass = new ecopy.servicepoint_android.Declarations();
	ecopy.servicepoint_android.NavigationDrawerFragment nav = new ecopy.servicepoint_android.NavigationDrawerFragment();
	private String storage = superClass.getStorageDestination();
	private String finish = superClass.getFinishedDestination();
	
	public void XMLProcessor(View v){
		thisView = v;
		selectXML = (Spinner) thisView.findViewById(R.id.selectXML);
		xmlReader();
		adapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, xmlFiles);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectXML.setAdapter(adapter);
		selectXML.setOnItemSelectedListener(this);
		initFields();
		initJava();
        Buttons();
        //db.deleteAll();
        timeFormat = new SimpleDateFormat("hh:mm:ss  aa");
        windowManager = (WindowManager) thisView.getContext().getSystemService(WINDOW_SERVICE);
	}
	
	public void initTimeInPop(){
		inflater = (LayoutInflater) thisView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		popup = inflater.inflate(R.layout.timepopup, null);
		window = new PopupWindow(popup, 300,250);
		window.setAnimationStyle(R.style.PopupAnimation);
		window.setBackgroundDrawable(new ColorDrawable());
		window.setOutsideTouchable(true);
		window.setFocusable(true);
		timeinPopup = (TextView) popup.findViewById(R.id.timein);
		window.showAtLocation(thisView, Gravity.CENTER, 0, 0);
		getTime = (EditText) popup.findViewById(R.id.getTime);
		getTimeButton = (Button) popup.findViewById(R.id.getTimeButton);
		getTimeButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (getTime.getText().toString().equals(qrCodeString)){
					currentDate = new Date();
					currentTime = timeFormat.format(currentDate);
					timeinPopup.setText(currentTime);
					insertTimeQuery();
					getTimeButton.setEnabled(false);
					getTimeRecord();
					window.dismiss();
				} else {
					Toast.makeText(thisView.getContext(), "QR Code Didn't Matched, Try Again",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void initTravelPop(){
		inflater2 = (LayoutInflater) thisView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		travelPopup = inflater2.inflate(R.layout.travelpopup, null);
		travel = new PopupWindow(travelPopup, windowManager.getDefaultDisplay().getWidth(),400);
		travel.setAnimationStyle(R.style.PopupAnimation);
		travel.setBackgroundDrawable(new ColorDrawable());
		travel.setOutsideTouchable(true);
		travel.setFocusable(true);
		//
		travelType = (Spinner) travelPopup.findViewById(R.id.type);
		travelTypes = new ArrayList<String>();
		travelTypes.add("Jeepney");
		travelTypes.add("Tricycle");
		travelTypes.add("Bus");
		travelTypes.add("LRT");
		travelTypes.add("MRT");
		travelTypes.add("Taxi");
		travelTypes.add("Pedicab");
		travelTypes.add("None");
		travelTypes.add("Own car/motorcycle");
		travelAdapter = new ArrayAdapter<String>(travelPopup.getContext(), R.layout.spinner, travelTypes);
		travelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		travelType.setAdapter(travelAdapter);
		travelType.setOnItemSelectedListener(this);
		travel.showAtLocation(thisView, Gravity.CENTER, 0, 0);
		fareLabel = (TextView) travelPopup.findViewById(R.id.fareLabel);
		fare = (EditText) travelPopup.findViewById(R.id.fare);
		fare.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		from = (EditText) travelPopup.findViewById(R.id.fromLocation);
		to = (EditText) travelPopup.findViewById(R.id.toLocation);
		onsiteFee = (EditText) travelPopup.findViewById(R.id.onsiteFee);
		onsiteFee.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		saveTravel = (Button) travelPopup.findViewById(R.id.closePopup);
		saveTravel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (travelCheck()){
					double onsite = 0;
					if (onsiteFee.getText().toString().equals("")){
						onsite = 0;
					} else {
						onsite = Double.parseDouble(onsiteFee.getText().toString());
					}
					total = Double.parseDouble(fare.getText().toString()) + onsite;
					insertTravelQuery();
					travel.dismiss();
					nav.explicitReload(1);
					selectXML.setSelection(setSpinnerIndex(selectXML,selectedXML));
				} else {
					Toast.makeText(thisView.getContext(), "Please Complete/Fix Travel Record",Toast.LENGTH_SHORT).show();
				}
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
		password = (EditText) thisView.findViewById(R.id.password);
		remarks = (EditText) thisView.findViewById(R.id.remarks);
		timeIn = (EditText) thisView.findViewById(R.id.timeIn);
		timeOut = (EditText) thisView.findViewById(R.id.timeOut);
	}
	
	public void initJava(){
		paymentMethods = new ArrayList<String>();
		onsiteStatuses = new ArrayList<String>();
		parts = new ArrayList<String>();
		unfinished = new ArrayList<String>();
		carryover = new ArrayList<String>();
		blank = new ArrayList<String>();
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
	
	public boolean hasXmlChecker(String fileDownloaded){
		String formatString = fileDownloaded.replace("%20", " ");
		boolean proceed = true;
		downloadsFolder = new File(storage);
		mfsrXMLFiles = downloadsFolder.listFiles();
		for (int i = 0; i<mfsrXMLFiles.length; ++i){
			String fileName = mfsrXMLFiles[i].getName();
			if (fileName.equals(formatString)){
				proceed = false;
				break; 
			}
		}
		
		return proceed;
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
					formID= xmlParser.nextText();
					xmlParser.nextTag();
					qrCodeString = xmlParser.nextText();
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
				if (name.equals("ServiceInformation")){
					xmlParser.nextTag();
					paymentMethod = xmlParser.nextText();
					xmlParser.nextTag();
					eTicket.setText(xmlParser.nextText());
				}
				if (name.equals("Maintenances")){
					int x = 0;
					paymentMethods.clear();
					onsiteStatuses.clear();
					parts.clear();
					unfinished.clear();
					carryover.clear();
					approvalTypes.clear();
					paymentMethods.add("Select an item");
					onsiteStatuses.add("Select an item");
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
							parts.add(xmlParser.nextText());
						}
						else if  (xmlParser.getName().equals("Unfinished")){
							unfinished.add(xmlParser.nextText());
						}
						else if  (xmlParser.getName().equals("Carry-Over")){
							carryover.add(xmlParser.nextText());
						} else {
							break;
						}
					}while (x < 20);
					
					paymentAdapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, paymentMethods);
					paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					payment.setAdapter(paymentAdapter);
					onsiteAdapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, onsiteStatuses);
					onsiteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					onsite.setAdapter(onsiteAdapter);
					approvalAdapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, approvalTypes);
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
					"<FormID>" + formID + "</FormID>" +
					"<Timestamp>" + timeStamp + "</Timestamp>" +
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
			    		"<Password>" + password.getText() + "</Password>" +
			    		"<Remarks>" + remarks.getText() + "</Remarks>" +
			    		"<TimeIN>" + timeIn.getText() + "</TimeIN>" +
			    		"<TimeOUT>" + timeOut.getText() + "</TimeOUT>" +
			    	"</ServiceInformation>" +
			    	transpo +
				"</MFSR>";
		xmlWriter(xml);
	}
	
	public void xmlWriter(String xml){
		finishFolder = new File (finish, company.getText() +"-"+ referenceNo.getText() + ".xml");
		try {
			xmlWriter = new FileWriter (finishFolder);
			xmlWriter.append(xml);
			xmlWriter.flush();
			xmlWriter.close();
			Toast.makeText(thisView.getContext(), "File generated in DOWNLOADS/MFSR_FINISH folder",Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Buttons(){
		saveXML = (Button) thisView.findViewById(R.id.saveXML);
		saveXML.setEnabled(false);
		saveXML.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (mainFormCheck()){
					currentDate = new Date();
					String timeout = timeFormat.format(currentDate);
					timeOut.setText(timeout);
					updateServiceQuery();
					getDetails();
					xmlBuilder();
					deleteXml();
					nav.explicitReload(2);
					selectXML.setSelection(setSpinnerIndex(selectXML,selectedXML));
				} else {
					Toast.makeText(thisView.getContext(), "Please Complete The Form",Toast.LENGTH_SHORT).show();
				}
			}
		});
		addTravel = (Button) thisView.findViewById(R.id.addTravel);
		addTravel.setEnabled(false);
		addTravel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				initTravelPop();
			}
		});
		saveOnly = (Button) thisView.findViewById(R.id.saveOnly);
		saveOnly.setEnabled(false);
		saveOnly.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				updateServiceQuery();
				nav.explicitReload(1);
				Toast.makeText(thisView.getContext(), "Successful! Please Proceed to your Actual Service",Toast.LENGTH_SHORT).show();
			}
		});
		sketchmate = (Button) thisView.findViewById(R.id.sketchmate);
		sketchmate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				runApp("com.xiledsystems.sketchmateads", thisView);
			}
		});
		draw = (Button) thisView.findViewById(R.id.draw);
		draw.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				runApp("com.android.thewongandonly.QuickDraw", thisView);
			}
		});
	}	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		switch(parent.getId()){
		case R.id.selectXML:
			selectedXML = parent.getItemAtPosition(position).toString();
			xmlLoader(view, selectedXML+".xml");
			currentDate = new Date();
			currentTime = timeFormat.format(currentDate);
			timeStamp = currentTime;
			popupManager();
			getTravelRecord();
			break;
		case R.id.payment:
			selectedPayment = parent.getItemAtPosition(position).toString();
			break;
		case R.id.onsite:
			selectedOnsite = parent.getItemAtPosition(position).toString();
			cascadeOnsite(selectedOnsite);
			break;
		case R.id.pending:
			selectedPending = parent.getItemAtPosition(position).toString();
			break;
		case R.id.approval:
			selectedApproval = parent.getItemAtPosition(position).toString();
			setAuthentication(selectedApproval);
			break;
		case R.id.type:
			selectedTravel = parent.getItemAtPosition(position).toString();
			if (selectedTravel.equals("Own car/motorcycle")){
				fareLabel.setText("Gas:");
			} else {
				fareLabel.setText("Fare:");
			}
			
			if (selectedTravel.equals("None")){
				fare.setText("0");
				fare.setEnabled(false);
				onsiteFee.setText("0");
			} else {
				fare.setText("");
				fare.setEnabled(true);
				onsiteFee.setText("");
			}
			break;
		}
	}

	public void cascadeOnsite(String Onsite){
		if (Onsite.equals("Parts/Supplies Requisition")){
			pendingAdapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, parts);
			pendingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			pending.setAdapter(pendingAdapter);
			pending.setSelection(setSpinnerIndex(pending, tempPending));
		} else if (Onsite.equals("Unfinished")){
			pendingAdapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, unfinished);
			pendingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			pending.setAdapter(pendingAdapter);
			pending.setSelection(setSpinnerIndex(pending, tempPending));
		} else if (Onsite.equals("Carry Over")){
			pendingAdapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, carryover);
			pendingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			pending.setAdapter(pendingAdapter);
			pending.setSelection(setSpinnerIndex(pending, tempPending));
		} else if (Onsite.equals("Finished")){
			blank.add("Select an item");
			pendingAdapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, blank);
			pendingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			pending.setAdapter(pendingAdapter);
		} else {
			blank.add("Select an item");
			pendingAdapter = new ArrayAdapter<String>(thisView.getContext(), R.layout.spinner, blank);
			pendingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			pending.setAdapter(pendingAdapter);
		}
	}
	
	public void setAuthentication(String approval){
		if (approval.equals("Actual Signature")){
			password.setEnabled(false);
			sketchmate.setEnabled(true);
			draw.setEnabled(true);
		} else if (approval.equals("Password Protection System")){
			sketchmate.setEnabled(false);
			draw.setEnabled(false);
			///password.setEnabled(true);
		} else {
			sketchmate.setEnabled(false);
			draw.setEnabled(false);
			password.setText("");
			//password.setEnabled(false);
		}
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
	
	public void insertTimeQuery(){
		db.timeRecord(selectedXML, timeinPopup.getText().toString());
		updateServiceQuery();
	}
	
	public void updateServiceQuery(){
		db.updateServiceRecord(password.getText()+"", selectedXML, beforeCopyBW.getText() + "", 
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
				selectedPayment , selectedOnsite, selectedApproval, selectedPending,
				timeOut.getText()+"");
	}
	
	public void insertTravelQuery(){
		currentDate = new Date();
		currentTime = timeFormat.format(currentDate);
		String startTime = currentTime;
		db.endRunningTravel(selectedXML, startTime);
		db.travelRecord(startTime, "", selectedXML, selectedTravel, fare.getText().toString(), from.getText().toString(), to.getText().toString(), onsiteFee.getText().toString(), total+"");
	}
	
	public String timeConverter(int hour, int mins){
		String timeHour = "";
		String timeMins = "";
		String state = "";
		if (hour >= 12 || hour != 0){
			int thisHour = hour - 12;
			timeHour = thisHour + "";
			state = " PM";
		} else if (hour == 0){
			timeHour = "12";
			state = " AM";
		} else if (hour < 10){
			timeHour = "0"+hour;
			state = " AM";
		} else {
			timeHour = hour+ "";
			state = " AM";
		}
		if (mins < 10){
			timeMins = "0"+mins;
		} else {
			timeMins = mins + "";
		}
		return timeHour + ":" + mins + state;
	}
	
	public void getTimeRecord(){
		timeData = db.getTimeData(selectedXML);
		if (timeData.equals("")){
			Toast.makeText(thisView.getContext(), "No Time-in",Toast.LENGTH_SHORT).show();
			timeIn.setText("No Time-in");
			qrCode.setText("No Time-in");
			payment.setSelection(setSpinnerIndex(payment, paymentMethod));
			initTimeInPop();
			saveOnly.setEnabled(false);
			saveXML.setEnabled(false);
		} else {
			Toast.makeText(thisView.getContext(), timeData +"",Toast.LENGTH_SHORT).show();
			timeIn.setText(timeData);
			qrCode.setText(qrCodeString);
			getDetails();
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
		int beforeBW = Integer.parseInt(beforeCopyBW.getText().toString()) + Integer.parseInt(beforePrintBW.getText().toString()) + 
				Integer.parseInt(beforeScanBW.getText().toString()) + Integer.parseInt(beforeFaxBW.getText().toString());
		beforeBWTotal.setText(beforeBW+"");
		int beforeFC = Integer.parseInt(beforeCopyFC.getText().toString()) + Integer.parseInt(beforePrintFC.getText().toString()) + 
				Integer.parseInt(beforeScanFC.getText().toString());
		beforeFCTotal.setText(beforeFC+"");
		afterCopyBW.setText(data[9]+"");
		afterPrintBW.setText(data[10]+"");
		afterScanBW.setText(data[11]+"");
		afterFaxBW.setText(data[12]+"");
		afterCopyFC.setText(data[13]+"");
		afterPrintFC.setText(data[14]+"");
		afterScanFC.setText(data[15]+"");
		int afterBW = Integer.parseInt(afterCopyBW.getText().toString()) + Integer.parseInt(afterPrintBW.getText().toString()) + 
				Integer.parseInt(afterScanBW.getText().toString()) + Integer.parseInt(afterFaxBW.getText().toString());
		afterBWTotal.setText(afterBW+"");
		int afterFC = Integer.parseInt(afterCopyFC.getText().toString()) + Integer.parseInt(afterPrintFC.getText().toString()) + 
				Integer.parseInt(afterScanFC.getText().toString());
		afterFCTotal.setText(afterFC+"");
		eTicket.setText(data[18]+"");
		repair.setText(data[19]+"");
		remarks.setText(data[20]+"");
		payment.setSelection(setSpinnerIndex(payment, data[21]+""));
		onsite.setSelection(setSpinnerIndex(onsite, data[22]+""));
		approval.setSelection(setSpinnerIndex(approval, data[23]+""));
		tempPending = data[24] +"";
		//pending.setSelection(setSpinnerIndex(pending, data[24]+""));
		password.setText(data[25]+"");
		timeOut.setText(data[26]+"");
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
		String [] tableHeader = {SQLVariables.START,SQLVariables.TYPE,SQLVariables.TOTAL,SQLVariables.FROM,SQLVariables.TO};
		int [] tableRow = {R.id.timeStartLabel, R.id.travelTypeLabel, R.id.totalLabel, R.id.startTimeLabel,  R.id.endTimeLabel};
		travelList = new SimpleCursorAdapter(thisView.getContext(), R.layout.travellist, 
				selector, tableHeader, tableRow, 0);
		travelData = (ListView) thisView.findViewById(R.id.travelRecordsList);
		travelData.setAdapter(travelList);
		travelData.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				selectedPosition = position;
				alertdialog = new AlertDialog.Builder(thisView.getContext());
				alertdialog.setTitle("Delete entry");
			    alertdialog.setMessage("Are you sure you want to delete this entry?");
			    alertdialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	db.deleteTravelData(travelList.getItemId(selectedPosition), selectedXML);
			        	nav.explicitReload(1);
			        	selectXML.setSelection(setSpinnerIndex(selectXML,selectedXML));
			        }
			     });
			    alertdialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { }
			     });
			    alertdialog.setIcon(android.R.drawable.ic_dialog_alert);
			    alertdialog.show();
				return false;
			}
		});
		transpo = db.getTranspoDetails(selectedXML);
	}

	public boolean mainFormCheck(){
		Boolean proceed = false;
		if (beforeCopyBW.getText().toString().equals("")
				|| beforeCopyBW.getText().toString().equals("")
				|| beforePrintBW.getText().toString().equals("")
				|| beforeScanBW.getText().toString().equals("")
				|| beforeFaxBW.getText().toString().equals("")
				|| beforeCopyFC.getText().toString().equals("")
				|| beforePrintFC.getText().toString().equals("")
				|| beforeScanFC.getText().toString().equals("")
				|| afterCopyBW.getText().toString().equals("")
				|| afterPrintBW.getText().toString().equals("")
				|| afterScanBW.getText().toString().equals("")
				|| afterFaxBW.getText().toString().equals("")
				|| afterCopyFC.getText().toString().equals("")
				|| afterPrintFC.getText().toString().equals("")
				|| afterScanFC.getText().toString().equals("")
				|| selectedPayment.equals("Select an item")
				|| selectedOnsite.equals("Select an item")
				|| selectedApproval.equals("Select an item")
				|| repair.getText().toString().equals("")
				|| remarks.getText().toString().equals("")
				){
			proceed = false;
		}else{
			proceed = true;
		}
		return proceed;
	}

	public boolean travelCheck(){
		Boolean proceed = false;
		if(fare.getText().toString().equals("")
				|| from.getText().toString().equals("")
				|| to.getText().toString().equals("")
				|| (!(isNumeric(fare.getText().toString())))
				){
			proceed = false;
		} else {
			proceed = true;
		}
		return proceed;
	}
	
	public boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	public void runApp(String appName, View v){
		PackageManager pm = v.getContext().getPackageManager();
		Intent appStartIntent = pm.getLaunchIntentForPackage(appName);
		if (null != appStartIntent)
		{
		    thisView.getContext().startActivity(appStartIntent);
		}
	}

	public void popupManager(){
		if (db.checkTravelCount(selectedXML)){
			getTimeRecord();
			addTravel.setEnabled(true);
		} else {
			initTravelPop();
			addTravel.setEnabled(true);
		}
	}
	
	public void deleteXml(){
		db.cleanUp(selectedXML);
		downloadsFolder = new File(storage + selectedXML+".xml");
		downloadsFolder.delete();
	}
}

