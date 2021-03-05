package options;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Watson.OnOptionsItemSelectedListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class SettingViewController extends Activity {
	Preferences mReaderPreferences;
	boolean stateChanged;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.strSetting);
		mReaderPreferences = new Preferences();
		getFragmentManager().beginTransaction()
				.add(R.id.settingsFragment, mReaderPreferences).commit();
		stateChanged = getIntent().getBooleanExtra("STATE_CHANGE", false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		onBackPressed();
		return super.onKeyUp(keyCode, event);
	}

	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (stateChanged == true) {
			Intent _intent = new Intent(this, ResultViewController.class);
			_intent.putExtra("STATE_CHANGE", stateChanged);
			startActivity(_intent);
		}
		super.onBackPressed();
	}
}
