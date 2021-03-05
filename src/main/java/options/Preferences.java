package options;

import instantpopup.TogglePopup;
import java.util.Locale;

import vn.team5b1.jsdict.R;
import wei.mark.standout.StandOutWindow;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Preferences extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {
	private SharedPreferences appPrefs;
	public static Context context;
	public static View layout;
	Locale myLocale;
	boolean stateChanged;
	Preference exercisesPref;
	PreferenceScreen rateThisApp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		stateChanged = false;
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		this.appPrefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		addPreferencesFromResource(R.xml.preferences);
		/*final CheckBoxPreference checkboxPrefJSDict = (CheckBoxPreference) getPreferenceManager()
				.findPreference("JSDictPref");
		final CheckBoxPreference checkboxPrefPersonal = (CheckBoxPreference) getPreferenceManager()
				.findPreference("PersonalPref");*/
		/*checkboxPrefJSDict
				.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						Toast.makeText(getActivity(), "JSDict is selected",
								Toast.LENGTH_SHORT).show();
						return true;
					}
				});
		checkboxPrefPersonal
				.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						Toast.makeText(getActivity(),
								"Personal Dictionary is selected",
								Toast.LENGTH_SHORT).show();
						return true;
					}
				});*/
		final String appPackageName = "vn.team5b1.jsdict"; // getPackageName()
															// from Context or
															// Activity object
		rateThisApp = (PreferenceScreen) getPreferenceManager().findPreference(
				"rateThisApp");
		rateThisApp
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						// TODO Auto-generated method stub
						try {
							startActivity(new Intent(Intent.ACTION_VIEW, Uri
									.parse("market://details?id="
											+ appPackageName)));
						} catch (android.content.ActivityNotFoundException anfe) {
							startActivity(new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("http://play.google.com/store/apps/details?id="
											+ appPackageName)));
						}
						return false;
					}
				});
		exercisesPref = (Preference) findPreference("checkForLanguage");
		exercisesPref
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						stateChanged = true;
						// TODO Auto-generated method stub
						String textValue = newValue.toString();
						int index = ((ListPreference) exercisesPref)
								.findIndexOfValue(textValue);
						((ListPreference) exercisesPref).setValueIndex(index);
						if (index == 0) {
							setLocale("ja");
							Toast.makeText(getActivity(),
									R.string.strLanguage, Toast.LENGTH_LONG).show();
						} else if (index == 1) {
							setLocale("en");
							Toast.makeText(getActivity(),
									R.string.strLanguage, Toast.LENGTH_LONG).show();
						} else {
							setLocale("vi");
							Toast.makeText(getActivity(),
									R.string.strLanguage, Toast.LENGTH_LONG).show();
						}
						return false;
					}
				});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		getPreferenceManager().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}

	public void startClipboardMonitor() {
		StandOutWindow.closeAll(getActivity(), TogglePopup.class);
		StandOutWindow.show(getActivity(), TogglePopup.class,
				StandOutWindow.DEFAULT_ID);
	}

	public void stopClipboardMonitor() {
		StandOutWindow.closeAll(getActivity(), TogglePopup.class);
	}

	public void Stop(View view) {
		stopClipboardMonitor();
	}

	public void Start(View view) {
		startClipboardMonitor();

	}

	// Language
	public void setLocale(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		SharedPreferences prefs = getActivity().getSharedPreferences(
				"CommonPrefs", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("LANGUAGE", lang);
		editor.commit();
		// Reload current fragment
		Intent intent = getActivity().getIntent();
		getActivity().finish();
		intent.putExtra("STATE_CHANGE", true);
		startActivity(intent);
		getActivity().overridePendingTransition(0, 0);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub

	}

}