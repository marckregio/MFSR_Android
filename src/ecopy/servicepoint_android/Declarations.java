package ecopy.servicepoint_android;

import java.io.File;

import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

public class Declarations extends ActionBarActivity{
	public static FragmentManager fragmentManager;
	public String storage = Environment.getExternalStorageDirectory().getPath() +"/"+ Environment.DIRECTORY_DOWNLOADS +"/MFSR/";
	public File storageDestination;
	
	public String getStorageDestination(){
		return storage;
	}
}
