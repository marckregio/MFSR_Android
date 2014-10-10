package ecopy.servicepoint_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	public static final int SplashTime = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				Intent intentToSplash = new Intent(SplashScreen.this,MainActivity.class);
				startActivity(intentToSplash);
				SplashScreen.this.finish();
				
				//overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
			}
        	
        }, SplashTime);
    }
}

