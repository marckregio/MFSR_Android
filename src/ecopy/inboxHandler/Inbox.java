package ecopy.inboxHandler;


import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import ecopy.servicepoint_android.R;

public class Inbox extends Declarations{
	ecopy.servicepoint_android.NavigationDrawerFragment nav = new ecopy.servicepoint_android.NavigationDrawerFragment();
	public String connection = ExceptionClass.getLocalAddress();
	public String noConnection = ExceptionClass.blankPage();
	public String reloadPage = ExceptionClass.reloadPage();
	private View thisView;
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	public void Browser(View v, String page){
		thisView = v;
		wv = (WebView) thisView.findViewById(R.id.webview);
		progress = (ProgressBar) thisView.findViewById(R.id.progressBar);
		wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    	wv.getSettings().setLoadWithOverviewMode(true);
    	//wv.getSettings().setUseWideViewPort(true);
    	wv.getSettings().setBuiltInZoomControls(true);
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
    				urlArray = url.split(";");
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
    				
    				try{
    					Toast.makeText(view.getContext(), "PLEASE WAIT", Toast.LENGTH_SHORT).show();
    					Thread.sleep(3000);
    					nav.explicitReload(1);
    				} catch (InterruptedException e) {
    					Thread.currentThread().interrupt();
    				}
    				
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
    			showIntent(uploads);
    		}

    		@SuppressWarnings("unused")
    		public void openFileChooser(ValueCallback<Uri> uploads, String acceptType) {
    			showIntent(uploads);
    		}
    		@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploads) {
    			showIntent(uploads);
    		} 
    	});
   	

    	if(connection.equals("No Network Connection")){
			wv.loadData(noConnection, "text/html", "UTF-8");
		} else {
			if (page.equals("inbox")){
				wv.loadUrl(inbox);
			}
			else if (page.equals("upload")){
				wv.loadData(reloadPage, "text/html", "UTF-8");
				runUploader();
			}
		}
	}
	
	public void showIntent(ValueCallback<Uri> uploads){
		uploadMsg = uploads;
		chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
		chooserIntent.addCategory(Intent.CATEGORY_OPENABLE); 
		chooserIntent.setType("image/*");
		//captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		((MainActivity) thisView.getContext()).startActivityForResult(Intent.createChooser(chooserIntent, "Choose Finished Service File"),  FILECHOOSER_RESULT); 
	}
	
	
	public void runUploader(){
		PackageManager pm = thisView.getContext().getPackageManager();
		Intent appStartIntent = pm.getLaunchIntentForPackage("ecopy.serviceuploader");
		if (null != appStartIntent)
		{
		    thisView.getContext().startActivity(appStartIntent);
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
	
	public void setValue(int progress) {
		this.progress.setProgress(progress);		
	}
}
