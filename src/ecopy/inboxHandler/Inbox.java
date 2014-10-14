package ecopy.inboxHandler;


import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import ecopy.servicepoint_android.R;

public class Inbox extends Declarations{
	public String connection = ExceptionClass.getLocalAddress();
	public String noConnection = ExceptionClass.blankPage();
	public Context context;
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	public void Browser(View v){
		wv = (WebView) v.findViewById(R.id.webview);
		wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    	wv.getSettings().setLoadWithOverviewMode(true);
    	//wv.getSettings().setUseWideViewPort(true);
    	//wv.setWebViewClient(new WebEvents());
    	wv.setWebViewClient(new WebViewClient(){
    		@Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Log.v("Makoy", "error code:" + errorCode + " - " + description);
            }
    		@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
    			if (url.endsWith(".xml")){
    				linkSource = Uri.parse(url);
    				urlArray = url.split("%3b");
    				filename = urlArray[urlArray.length - 1];
    				request = new DownloadManager.Request(linkSource);
    				request.setDescription("Processing Dispatched Service");
    				request.setTitle("Service Point Mobile");
    				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    					request.allowScanningByMediaScanner();
    					request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
    				}
    				request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS+"/MFSR", "MFSR-"+ filename +".xml");
    				dm = (DownloadManager) view.getContext().getSystemService(DOWNLOAD_SERVICE);
    				dm.enqueue(request);
    				Toast.makeText(view.getContext(), filename + " Downloaded", Toast.LENGTH_SHORT).show();
    			}
    			return false;
    		}
    	});
    	wv.setWebChromeClient(new WebChromeClient(){
    		@SuppressWarnings("unused")
    		public void openFileChooser(ValueCallback<Uri> uploads, String acceptType, String capture) {
    			openFileChooser(uploads);
    		}

    		@SuppressWarnings("unused")
    		public void openFileChooser(ValueCallback<Uri> uploads, String acceptType) {
    			openFileChooser(uploads);
    		}
    		public void openFileChooser(ValueCallback<Uri> uploads) {
    			Inbox.this.uploadMsg = uploads;
    			Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
    			chooserIntent.setType("image/*");
    			//captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    			Inbox.this.startActivityForResult(Intent.createChooser(chooserIntent, "Choose Image"),  FILECHOOSER_RESULT); 
    		} 
    	});
    	
    	if(connection.equals("No Network Connection")){
			wv.loadData(noConnection, "text/html", "UTF-8");
		} else {
			wv.loadUrl(url);
		}
	}
	
	public class WebEvents extends WebViewClient{
		@Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading");
                //Toast.makeText(getApplicationContext(), "Loading" ,Toast.LENGTH_SHORT).show();
        }
		@Override
		public void onPageFinished(WebView view, String url){
			if(!redirect){
				loadingFinished = true;
				//progressDialog.dismiss();
			}
			if(loadingFinished && !redirect){
				//Toast.makeText(getApplicationContext(), "Load Successful" ,Toast.LENGTH_SHORT).show();
				//progressDialog.hide();
				//progressDialog.dismiss();
			}else{
				redirect = false;
			}
		}
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	if (requestCode == FILECHOOSER_RESULT) {
    		if(requestCode == FILECHOOSER_RESULT) {
    	        if (null == uploadMsg)
    	            return;
    	        Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
    	        uploadMsg.onReceiveValue(result);
    	        uploadMsg = null;
    	    }
    		this.uploadMsg = null;
    	}
    }
}
