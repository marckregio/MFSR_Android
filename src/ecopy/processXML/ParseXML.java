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
	public void XMLProcessor(View v){
		selectXML = (Spinner) v.findViewById(R.id.selectXML);
		xmlReader();
		adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, xmlFiles);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		company = (TextView) v.findViewById(R.id.company);
		selectXML.setAdapter(adapter);
		selectXML.setOnItemSelectedListener(this);
        Buttons(v);
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
			NodeList node = xml.getElementsByTagName("MFSR");
			
			for (int x = 0; x < node.getLength(); x++){
				Node fstNode = node.item(x);
				Element fstElmnt = (Element) fstNode;
				NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("CompanyName");
			    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
			    NodeList fstNm = fstNmElmnt.getChildNodes();
			    company.setText(((Node) fstNm.item(0)).getNodeValue() +"");
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
		xmlLoader(view, parent.getItemAtPosition(position).toString());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
