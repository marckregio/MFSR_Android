package ecopy.inboxHandler;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class Declarations extends Activity{
	public ValueCallback<Uri> uploadMsg;
	public final static int FILECHOOSER_RESULT = 1;
	public String filePath;
	public WebView wv;
	public ProgressBar progress;
	public ProgressDialog progressDialog;
	public static String linkInbox = "http://122.55.83.215/zukami/CompositeView.aspx?a=8ae76b00-d529-46c0-8ded-57fa58367409&ID=b7dd24ba-ec1c-4446-baad-f6144403e5fc";
	//public static String linkInbox = "http://172.16.85.22/zukami/CompositeView.aspx?a=8ae76b00-d529-46c0-8ded-57fa58367409&ID=782fac88-c09f-4be2-8d51-e502e44ebc11";
	
	public String inbox = linkInbox + "";
	public static String linkUpload = "http://122.55.83.215/Zukami/FillForm.aspx?a=8ae76b00-d529-46c0-8ded-57fa58367409&FT=1&ListID=8ebf4918-7f9f-44d7-80e6-95161f3c4277";
	public String upload = linkUpload + "";
	public boolean loadingFinished = true, redirect = false;
	public String welcome = "Welcome \n ECOPY CORPORATION  \n                                ITDEPT";
	public DownloadManager.Request request;
	public DownloadManager dm;
	public Uri linkSource;
	public String [] urlArray;
	public String filename;
	public String cookie;
	public Intent chooserIntent;
}
