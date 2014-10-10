package ecopy.processXML;


import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import ecopy.servicepoint_android.R;

public class ParseXML extends Declarations implements OnItemSelectedListener{
	public void XMLProcessor(View v){
		selectXML = (Spinner) v.findViewById(R.id.selectXML);
		String [] xmlItems = {"one.xml","two.xml","three.xml"};
		adapter = new ArrayAdapter<String>(ParseXML.this,android.R.layout.simple_spinner_item,xmlItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectXML.setAdapter(adapter);
        selectXML.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
