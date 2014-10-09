package ecopy.inboxHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class Declarations extends Activity{
	public ValueCallback<Uri> uploadMsg;
	public final static int FILECHOOSER_RESULT = 1;
	public String filePath;
	public WebView wv;
	public ProgressDialog progressDialog;
	public static String link = "http://122.55.83.215/zukami/login.aspx?AppID=8ae76b00-d529-46c0-8ded-57fa58367409";
	public String url = link + ""; //ECopy Support
	public boolean loadingFinished = true, redirect = false;
	public String welcome = "Welcome \n ECOPY CORPORATION  \n                                ITDEPT";
	
}
