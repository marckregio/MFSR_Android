package ecopy.processXML;


import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import ecopy.servicepoint_android.R;

public class ParseXML extends Declarations implements OnItemSelectedListener{
	public void XMLProcessor(View v){
		selectXML = (Spinner) v.findViewById(R.id.selectXML);
		xmlReader();
		adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, xmlItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectXML.setAdapter(adapter);
		selectXML.setOnItemSelectedListener(this);
        Buttons(v);
	}
	
	public void xmlReader(){
		xmlItems = new ArrayList<String>();
		xmlItems.add("Marck");
		xmlItems.add("Robin");
		xmlItems.add("Pierre");
	}
	
	public void xmlLoader(){
		
	}
	public void Buttons(View v){
		saveButton = (Button) v.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.v("makoy","MAKOY!!");
			}
			
		});
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		Log.v("Makoy",parent.getItemAtPosition(position).toString()+ "");
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
