package ecopy.inboxHandler;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import ecopy.servicepoint_android.MainActivity;
import ecopy.servicepoint_android.PageHandler;
import ecopy.servicepoint_android.R;

public class Inbox extends Declarations{
	public String connection = ExceptionClass.getLocalAddress();
	public String noConnection = ExceptionClass.blankPage();
	private View thisView;
	ecopy.servicepoint_android.Declarations storageDestination = new ecopy.servicepoint_android.Declarations();
	private String storage = storageDestination.getStorageDestination();
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	public void Browser(View v){
		thisView = v;
		wv = (WebView) thisView.findViewById(R.id.webview);
		progress = (ProgressBar) thisView.findViewById(R.id.progressBar);
		wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    	wv.getSettings().setLoadWithOverviewMode(true);
    	//wv.getSettings().setUseWideViewPort(true);
    	wv.setWebViewClient(new WebEvents());
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
    				cookie = CookieManager.getInstance().getCookie(url);
    				request.addRequestHeader("cookie", cookie);
    				request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS+"/MFSR", filename);
    				dm = (DownloadManager) view.getContext().getSystemService(DOWNLOAD_SERVICE);
    				dm.enqueue(request);
    				Toast.makeText(view.getContext(), filename + " Downloaded", Toast.LENGTH_SHORT).show();
    				//ecopy.servicepoint_android.Declarations.fragmentManager.beginTransaction().replace(R.id.container, PageHandler.newInstance(3)).commit();
    			}
    			return false;
    		}
    	});
    	wv.setWebChromeClient(new WebChromeClient() {
    		
    		@Override
    		public void onProgressChanged(WebView view, int newProgress) {			
    			Inbox.this.setValue(newProgress);
    			super.onProgressChanged(view, newProgress);
    		}
    		
    		@SuppressWarnings("unused")
    		public void openFileChooser(ValueCallback<Uri> uploads, String acceptType, String capture) {
    			openFileChooser(uploads);
    		}

    		@SuppressWarnings("unused")
    		public void openFileChooser(ValueCallback<Uri> uploads, String acceptType) {
    			openFileChooser(uploads);
    		}
    		public void openFileChooser(ValueCallback<Uri> uploads) {
    			uploadMsg = uploads;
    			chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
    			chooserIntent.addCategory(Intent.CATEGORY_OPENABLE); 
    			chooserIntent.setType("image/*");
    			//captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    			((MainActivity) thisView.getContext()).startActivityForResult(Intent.createChooser(chooserIntent, "Choose Finished Service File"),  FILECHOOSER_RESULT); 
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
		Toast.makeText(thisView.getContext(), "here", Toast.LENGTH_SHORT).show();
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
	
	public void setValue(int progress) {
		this.progress.setProgress(progress);		
	}
}
