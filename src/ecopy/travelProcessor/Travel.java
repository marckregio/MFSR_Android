package ecopy.travelProcessor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import ecopy.servicepoint_android.R;

public class Travel extends Declarations  implements OnItemSelectedListener{
	private View thisView;
	ecopy.servicepoint_android.Declarations storageDestination = new ecopy.servicepoint_android.Declarations();
	private String storage = storageDestination.getStorageDestination();
	private String finish = storageDestination.getFinishedDestination();
	
	public void TravelProcessor(View v){
		thisView = v;
		selectXML = (Spinner) thisView.findViewById(R.id.selectXML);
		xmlReader();
		adapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_spinner_dropdown_item, xmlFiles);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectXML.setAdapter(adapter);
		selectXML.setOnItemSelectedListener(this);
		Buttons();
	}
	public void Buttons(){
		addTravel = (Button) thisView.findViewById(R.id.addTravel);
		addTravel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				showAddPopup();
			}
		});
	}
	
	public void showAddPopup(){
		inflater = (LayoutInflater) thisView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		popup = inflater.inflate(R.layout.travelpopup, null);
		window = new PopupWindow(popup, 300,260);
		window.setAnimationStyle(R.style.PopupAnimation);
		window.setBackgroundDrawable(new ColorDrawable());
		window.setOutsideTouchable(true);
		window.setFocusable(true);
		timeFormat = new SimpleDateFormat("hh:mm:ss  aa");
		timeinPopup = (TextView) popup.findViewById(R.id.timein);
		window.showAtLocation(thisView, Gravity.CENTER, 0, 0);
		//popupButtons();
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
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
