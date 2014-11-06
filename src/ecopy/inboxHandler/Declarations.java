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
	public static String link = "http://172.16.85.101/zukami/CompositeView.aspx?a=8ae76b00-d529-46c0-8ded-57fa58367409&ID=da20fd54-be00-44f4-a2a1-d22f25202041";
	public String url = link + "";
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
