package ecopy.travelProcessor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

public class Declarations extends Activity{
	public Spinner selectXML;
	public ArrayAdapter<String> adapter;
	public List<String> xmlFiles;
	public File downloadsFolder;
	public File [] mfsrXMLFiles;
	public Button addTravel, saveTravel;
	//Popup
	public View popup;
	public LayoutInflater inflater;
	public PopupWindow window;
	public SimpleDateFormat timeFormat;
	public Date currentDate;
	public String currentTime, timeData;
	public TextView timeinPopup;
}
