package jsdictmain;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Splash extends Activity {
	 private ProgressBar mProgress;
     private int mProgressStatus = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		 mProgress = (ProgressBar) findViewById(R.id.progress_bar);
		 new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                /* Create an Intent that will start the Menu-Activity. */
	                Intent mainIntent = new Intent(Splash.this,ResultViewController.class);
	                Splash.this.startActivity(mainIntent);
	                Splash.this.finish();
	            }
	        }, 3000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
