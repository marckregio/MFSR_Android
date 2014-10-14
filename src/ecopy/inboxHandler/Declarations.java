package ecopy.inboxHandler;

import android.app.Activity;
import android.app.DownloadManager;
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
	public static String link = "http://172.16.85.100/zukami/taskinbox.aspx?a=0a5c5741-7f5f-4289-93a2-624cb99a0059";
	//public static String link = "http://m.facebook.com";
	public String url = link + ""; //ECopy Support
	public boolean loadingFinished = true, redirect = false;
	public String welcome = "Welcome \n ECOPY CORPORATION  \n                                ITDEPT";
	public DownloadManager.Request request;
	public DownloadManager dm;
	public Uri linkSource;
	public String [] urlArray;
	public String filename;
}
