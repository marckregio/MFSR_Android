package ecopy.inboxHandler;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ecopy.servicepoint_android.R;

public class Inbox extends Declarations{
	public String connection = ExceptionClass.getLocalAddress();
	public String noConnection = ExceptionClass.blankPage();
	
	@SuppressLint("SetJavaScriptEnabled")
	public void Browser(View v){
		wv = (WebView) v.findViewById(R.id.webview);
		wv.getSettings().setJavaScriptEnabled(true);
    	wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    	wv.getSettings().setLoadWithOverviewMode(true);
    	//wv.getSettings().setUseWideViewPort(true);
    	wv.setWebViewClient(new WebEvents());
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
	
	public void Reload(){
		wv.reload();
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
