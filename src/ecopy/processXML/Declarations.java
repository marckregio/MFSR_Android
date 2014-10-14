package ecopy.processXML;

import java.util.List;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Declarations extends Activity{
	public Spinner selectXML;
	public List<String> xmlItems;
	public ArrayAdapter<String> adapter;
	public Button saveButton;
}
