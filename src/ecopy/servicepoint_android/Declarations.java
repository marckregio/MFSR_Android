package ecopy.servicepoint_android;

import java.io.File;

import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.WindowManager;

public class Declarations extends ActionBarActivity{
	public static FragmentManager fragmentManager;
	public String storage = Environment.getExternalStorageDirectory().getPath() +"/"+ Environment.DIRECTORY_DOWNLOADS +"/MFSR/";
	public String finishStorage = Environment.getExternalStorageDirectory().getPath() +"/"+ Environment.DIRECTORY_DOWNLOADS +"/MFSR_FINISH/";
	public File storageDestination, finishDestination;
	
	public String getStorageDestination(){
		return storage;
	}
	
	public String getFinishedDestination(){
		return finishStorage;
	}
	
	
}
