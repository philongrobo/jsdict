package jsdictmain;

import java.util.Locale;

import options.AboutViewController;
import options.HelpViewController;
import options.SettingViewController;
import com.google.ads.*;
import mydict.MyDictViewController;

import pagerview.PagerViewController;


import inputmethod.DrawKanjiViewController;
import inputmethod.VoiceRecognitionViewController;
import translation.TranslationViewController;
import vn.team5b1.jsdict.R;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsSpinner;

import database.Converter;
import database.DatabaseController;
import android.widget.Spinner;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.SearchManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.database.Cursor;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.BitmapDrawable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Build;
import android.os.Handler;

import android.net.Uri;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.webkit.WebView;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ToggleButton;

@SuppressLint("NewApi")
public class ResultViewController extends SherlockFragmentActivity implements
		OnItemSelectedListener, OnQueryTextListener, OnLayoutChangeListener {

	// Declare Variables
	public static SearchView searchView;
	IcsSpinner Spinner;
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	PagerViewController mMenuAdapter;
	String[] title;
	String[] subtitle;
	int[] mswitch;
	// Status Search View
	boolean isOpen;
	// Status Activity _ Fragment
	boolean isActivity;
	//check from other activity or not
	public static boolean isOutside = false;
	// Decelare Menu
	private Menu _menu = null;
	// declare variable to check status from Recognize
	boolean isSpeech;
	// Radical Search
	String radicalSearch;
	String radical = "";
	int[] icon;
	Fragment fragment0 = new SearchScrActivity();
	StartActivity fragment2 = new StartActivity();
	Fragment fragment3 = new StartActivity();
	Fragment fragment4 = new StartActivity();
	Fragment fragment5 = new MyDictViewController();
	Fragment fragment7 = new HelpViewController();
	Fragment fragmentTran = new TranslationViewController();
	Fragment fragmentAbout = new AboutViewController();
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	public static DatabaseController db;
	private Cursor fullword;
	MenuItem searchMenuItem;
	private String searching_Word;
	String id = "";
	boolean stateChange;
	View viewSearchScreen;
	private boolean doubleBackToExitPressedOnce = false;
	TextToSpeechClass tts;
	// 0 = Japanese-Vietnamese; 1 = Vietnamese - Japanese; 2= Kanji ;
	public static int mode = 0;
	public static final String[] Table_Mode = { "jv", "vj", "kj"};
	int arr_image[] = { R.drawable.spinner_javi, R.drawable.spinner_vija,
			R.drawable.spinner_kanji };
	Locale myLocale;
	String lang;
	public static int his_mode = -1;
	public static int myDict_mode = -1;
	private boolean mReturningWithResult = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from drawer_main.xml
		setContentView(R.layout.drawer_main);
		tts = new TextToSpeechClass(this);
		// get Intent from KanjiRadical
		Intent intent = getIntent();
		// check if language changed
		stateChange = intent.getBooleanExtra("STATE_CHANGE", false);
		// set default language
		if (stateChange == false) {
			SharedPreferences prefs = getSharedPreferences("CommonPrefs",
					Activity.MODE_PRIVATE);
			lang = prefs.getString("LANGUAGE", "");
			if (lang == "")
				lang = "vi";
			myLocale = new Locale(lang);
			Resources res = getResources();
			DisplayMetrics dm = res.getDisplayMetrics();
			Configuration conf = res.getConfiguration();
			conf.locale = myLocale;
			res.updateConfiguration(conf, dm);
		}
		// set and get status of Activity
		isSpeech = false;
		isActivity = false;
		isActivity = intent.getBooleanExtra("IS_NEW_ACTIVITY", false);
		// set status searchview
		isOpen = false;
		if (isActivity == true) {
			isOpen = intent.getBooleanExtra("IS_OPEN_SEARCHVIEW", false);
		}
		radicalSearch = intent.getStringExtra("RADICAL_SEARCH");
		if (radicalSearch == null) {
			radicalSearch = "";
		}
		// Set database
		db = new DatabaseController(this);
		// Get the Title
		mTitle = mDrawerTitle = getTitle();
		// get WebView
		// declare and set SearchView_Radical status
		// get Mode Dictionary
		mode = intent.getIntExtra("MODE_DICT", 0);
		// Generate title
		/*
		 * title = new String[] { "Home", "Instant Lookup", "History",
		 * "Favourites", "Word of the day", "My Dictionary", "Settings", "Help",
		 * "About" };
		 */
		title = getResources().getStringArray(R.array.drawerMain);
		mswitch = new int[] { View.GONE, View.VISIBLE, View.GONE, View.GONE,
				View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, };
		icon = new int[] { R.drawable.action_home, R.drawable.action_lookup,
				R.drawable.action_history, R.drawable.action_favourite,
				R.drawable.action_wod, R.drawable.action_my_dict,
				R.drawable.action_settings, R.drawable.action_help,
				R.drawable.action_about };

		// Locate DrawerLayout in drawer_main.xml
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Locate ListView in drawer_main.xml
		mDrawerList = (ListView) findViewById(R.id.listview_drawer);

		// Set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Pass string arrays to MenuListAdapter
		mMenuAdapter = new PagerViewController(ResultViewController.this, title, mswitch,
				icon);

		// Set the MenuListAdapter to the ListView
		mDrawerList.setAdapter(mMenuAdapter);

		// Capture listview menu item click
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setLogo(R.drawable.menu_btn);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setCustomView(R.layout.topbar);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setBackgroundDrawable(
				(BitmapDrawable) getResources().getDrawable(
						R.drawable.actionbar_background));
		getSupportActionBar().setSplitBackgroundDrawable(
				(BitmapDrawable) getResources().getDrawable(
						R.drawable.actionbar_background));
		// set data to spinner
		Spinner spinner = (Spinner) findViewById(R.id.dict_select_spinner);
		Context subContext = getSupportActionBar().getThemedContext();
		// ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(
		// subContext, R.array.nav_list,
		// com.actionbarsherlock.R.layout.sherlock_spinner_item);
		// list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
		spinner.setAdapter(new MyAdapter(ResultViewController.this,
				R.layout.row_spinner, Table_Mode));
		spinner.setSelection(mode);
		spinner.setOnItemSelectedListener(this);
		// Search View
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) findViewById(R.id.action_websearch);
		searchView.setQuery(radicalSearch, false);
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		if (isOpen == true) {
			searchView.onActionViewExpanded();
		}
		searchView.setQuery(radicalSearch, false);
		// Do something when collapsed/expand
		searchView.addOnLayoutChangeListener(new OnLayoutChangeListener() {

			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				// TODO Auto-generated method stub
				if (searchView.isIconified()) {
					isOpen = false;
				} else {
					isOpen = true;
				}

			}
		});
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				searchView.clearFocus();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		// new ColorDrawable(Color.parseColor("#6d386f")));
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.edit_btn, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
			    AdView adView = (AdView) findViewById(R.id.adView);
			    adView.setVisibility(View.VISIBLE); 
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
			    AdView adView = (AdView) findViewById(R.id.adView);
			    adView.setVisibility(View.INVISIBLE); 
				// Set the title on the action when drawer open
				getSupportActionBar().setTitle(mDrawerTitle);
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
		handleIntent(getIntent());
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new HomeViewController()).commit();
		// Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    adView.loadAd(new AdRequest());
	}

	// Adapter for Spinner
	public class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row_spinner, parent, false);
			ImageView icon = (ImageView) row.findViewById(R.id.imageSpinner);
			icon.setImageResource(arr_image[position]);
			viewSearchScreen = inflater.inflate(
					R.layout.activity_search_screen, null);
			return row;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);

	}

	private void handleIntent(Intent intent) {

		if (Intent.ACTION_VIEW.equals(intent.getAction())) { // handles a
			// click on a search suggestion; launches activity to show // word
			/*
			 * Intent wordIntent = new Intent(this, WordActivity.class);
			 * wordIntent.setData(intent.getData()); startActivity(wordIntent);
			 */
/*			Bundle bl =  intent.getExtras();
			intent.getExtras();
			String temp3 = intent.getStringExtra(SearchManager.QUERY);
			String thisID = intent.getStringExtra(SearchManager.SUGGEST_COLUMN_ICON_1);
			int icon = intent.getIntExtra(SearchManager.SUGGEST_COLUMN_INTENT_DATA ,69);*/
			Uri uri = intent.getData();
			showResult(uri.getLastPathSegment());
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// handles a search query
			// get text from search box
			String query = intent.getStringExtra(SearchManager.QUERY);
			if(isOutside)
			{
				String str_id = (String) searchView.getTag();
				showResult(str_id);
			}
			else
			{
			isOutside = false;
			showResults(query);
			}
		}
	}

	// display result
	private void showResults(String query) {

		// new SearchWordTask().execute(query);
		new SearchWordTask().doInBackground(query);
	}

	private void showResult(String query) {

		// new SearchWordTask().execute(query);
		new SearchWordTaskSuggestion().execute(query);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		_menu = menu;

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		switch (item.getItemId()) {
		case R.id.draw_btn:
			DrawKanji();
			break;
		case R.id.puzzle_btn:
			Radical();
			break;
		case R.id.keyboard_btn:
			searchView.onActionViewExpanded();
			searchView.setFocusable(true);
			searchView.setIconified(false);
			searchView.requestFocusFromTouch();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInputFromInputMethod(searchView.getWindowToken(), 0);
			break;
		case R.id.mic_btn:
			Speech();
			break;
		case R.id.cam_btn:
			Cam();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// ListView click listener in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Locate Position
		switch (position) {
		case 0:
			// ft.replace(R.id.content_frame, fragment0);
			ft.replace(R.id.content_frame, new HomeViewController());
			break;
		/*
		 * case 2: fragmentTran = new Translation();
		 * ft.replace(R.id.content_frame, fragmentTran); break;
		 */
		case 2:
			fragment2 = new StartActivity();
			fragment2.position = 0;
			ft.replace(R.id.content_frame, fragment2);
			break;
		case 3:
			fragment2 = new StartActivity();
			fragment2.position = 1;
			ft.replace(R.id.content_frame, fragment2);
			break;
		case 4:
			fragment2 = new StartActivity();
			fragment2.position = 2;
			ft.replace(R.id.content_frame, fragment2);
			break;
		case 5:
			ft.replace(R.id.content_frame, fragment5);
			break;
		case 6:
			Intent intent = new Intent(this, SettingViewController.class);
			startActivity(intent);
			break;
		case 7:
			ft.replace(R.id.content_frame, fragment7);
			break;
		case 8:
			ft.replace(R.id.content_frame, fragmentAbout);
			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		// Get the title followed by the position
		setTitle(title[position]);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public void onBackPressed() {
		try{
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}
		this.doubleBackToExitPressedOnce = true;
		FragmentTransaction ft = getSupportFragmentManager()
				.beginTransaction();
		ft.replace(R.id.content_frame,  new HomeViewController());
		ft.commit();
		Toast.makeText(this, R.string.clickBack,
				Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
		}catch(Exception ex){
			Toast.makeText(getApplicationContext(), "Please wait", Toast.LENGTH_SHORT).show();
		}
	}

	private class SearchWordTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(final String... arg0) {
			// TODO Auto-generated method stub
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.replace(R.id.content_frame, fragment0);
			ft.commit();
			/*
			 * LayoutInflater inflater = getLayoutInflater(); View view =
			 * inflater.inflate(R.layout.activity_search_screen, this, false);
			 */
			WebView _webview = (WebView) findViewById(R.id.webViewResult);
			Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
			final ToggleButton tglFavorite = (ToggleButton) findViewById(R.id.btnFavorite);
			int _mode;
			if (his_mode == -1)
				_mode = mode;
			else {
				_mode = his_mode;
			}
			if (_mode == 2)
				fullword = db.getFullWordKJ(arg0[0].trim());
			else
				fullword = db.getFullWord(arg0[0].trim(), Table_Mode[_mode]);
			// startnow = android.os.SystemClock.uptimeMillis();
			String meaning_temp = "";
			String word_temp = "";
			String meaning = "";

			// if (fullword.getCount() == 0) {
			String error_no_words1 = getResources().getString(
					R.string.error_no_words);
			if (!fullword.moveToFirst()) {
				searching_Word = arg0[0].trim();
				meaning_temp = error_no_words1;
				myDict_mode =0;
			} else {
				// Save info
				searching_Word = fullword.getString(1);
				id = fullword.getString(0);
				//check from myDict or not
				if (id.length() > 2)
					if(id.substring(0, 2).equals("md"))
						myDict_mode =0;
					else
						myDict_mode =-1;
				// Save History
				if(myDict_mode == -1)
				{
				db.addHistory(fullword.getString(0), searching_Word,
						Table_Mode[_mode]);
				}
				else
				{
				db.addHistory(fullword.getString(0), searching_Word,
							"myDict");
				}
				if (_mode == 2) {
					do {
						word_temp = " ☆ " + fullword.getString(1);
						meaning = meaning
								+ " ※ "
								+ Converter.RomajiToHiragana(fullword
										.getString(4)) + " ";
						meaning = meaning + " ∴ " + fullword.getString(2) + " ";
						meaning = meaning + " ◆ " + fullword.getString(3) + " ";
						meaning_temp = meaning_temp + word_temp + meaning;
						meaning = "";
						fullword.moveToNext();
					} while (!fullword.isAfterLast());
					meaning_temp = Converter.Html_ConverterKJ(meaning_temp);
				} else {
					if(myDict_mode != -1)
					{
						meaning_temp =" ◆ " + meaning_temp + fullword.getString(2);
						word_temp = " ☆ " + fullword.getString(1);
						meaning_temp = Converter.Html_Converter(word_temp,
								meaning_temp);
					}
					else
					{
					meaning_temp = meaning_temp + fullword.getString(2);
					word_temp = " ☆ " + fullword.getString(1);
					meaning_temp = Converter.Html_Converter(word_temp,
							meaning_temp);
					}
				}

			}
			if (_webview != null && btnSpeak != null && tglFavorite != null) {
				if(myDict_mode != -1)
				{
					tglFavorite.setVisibility(View.INVISIBLE);
					btnSpeak.setVisibility(View.INVISIBLE);
				}
				else
					{
					tglFavorite.setVisibility(View.VISIBLE);
					btnSpeak.setVisibility(View.VISIBLE);							
				his_mode = -1;
				if (db.checkFavorite(id, Table_Mode[_mode])) {
					tglFavorite
							.setBackgroundResource(R.drawable.favourite_added);
				}
				tglFavorite.setTag(_mode);
					}
				// btnSpeak.setTag(mode);
				isOutside = false;
				_webview.loadDataWithBaseURL(null, meaning_temp, "text/html",
						"utf-8", null);
				/****************************** TEXT TO SPEECH ******************************/
				btnSpeak.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int _mode = (Integer) tglFavorite.getTag();
						if (searching_Word != null) {
							if (_mode == 0 || _mode == 2) {
								tts.startTST(searching_Word, "ja");
							} else {
								tts.startTST(searching_Word, "vi");
							}
						}
					}
				});
				/****************************** ADD TO FAVORITE ******************************/
				tglFavorite.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int _mode = (Integer) tglFavorite.getTag();
						if (searching_Word != null) {
							if (!db.checkFavorite(id, Table_Mode[_mode])) {
								db.addFavorite(searching_Word,
										Table_Mode[_mode]);
								// db.addFavorite(word_temp, table)
								tglFavorite
										.setBackgroundResource(R.drawable.favourite_added);
							} else {
								db.deleteFavorite(searching_Word,
										Table_Mode[_mode]);
								tglFavorite
										.setBackgroundResource(R.drawable.favourite_not_add);
							}
						}
					}
				});

			} else {
				searchView.setQuery(searching_Word, true);
			}
			// fullword.close();
			return "hihaho";
		}

	}

	private class SearchWordTaskSuggestion extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int _mode;
			if (his_mode == -1)
				_mode = mode;
			else {
				_mode = his_mode;
			}
			fullword = db.getFullWord2(arg0[0].trim(), Table_Mode[_mode]);
			// startnow = android.os.SystemClock.uptimeMillis();
			String meaning_temp = "", word_temp = "";
			id = arg0[0].trim();
			// if (fullword.getCount() == 0) {
			String error_no_words2 = getResources().getString(
					R.string.error_no_words);
			if (!fullword.moveToFirst()) {
				searching_Word = null;
				meaning_temp = error_no_words2;
			} else {
				// fullword.moveToFirst();
				searching_Word = fullword.getString(1);
				// check to save MyDict or not
				if(myDict_mode == -1)
				{
				db.addHistory(fullword.getString(0), searching_Word,
						Table_Mode[_mode]);
				}
				else
				{
				db.addHistory(fullword.getString(0), searching_Word,
							"myDict");
				}
				//check word is in MyDict or not
				if(myDict_mode != -1)
				{
					meaning_temp =" ◆ " + meaning_temp + fullword.getString(2);
					word_temp = " ☆ " + fullword.getString(1);
					meaning_temp = Converter.Html_Converter(word_temp,
							meaning_temp);
				}
				else if (_mode == 2) {
					word_temp = " ☆ " + fullword.getString(1);
					meaning_temp = word_temp + " ※ "
							+ Converter.RomajiToHiragana(fullword.getString(4))
							+ " ";
					meaning_temp = meaning_temp + " ∴ " + fullword.getString(2)
							+ " ";
					meaning_temp = meaning_temp + " ◆ " + fullword.getString(3)
							+ " ";
					meaning_temp = Converter.Html_ConverterKJ(meaning_temp);
					}
					else
					{
					meaning_temp = meaning_temp + fullword.getString(2);
					word_temp = " ☆ " + fullword.getString(1);
					meaning_temp = Converter.Html_Converter(word_temp,
							meaning_temp);
					}
			}
			WebView _webview = (WebView) findViewById(R.id.webViewResult);
			Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
			final ToggleButton tglFavorite = (ToggleButton) findViewById(R.id.btnFavorite);
			
			// check Favorite
			// check webview and button speak
			if (_webview != null && btnSpeak != null && tglFavorite != null) {
							tglFavorite.setTag(_mode);
				/****************************** TEXT TO SPEECH ******************************/
				btnSpeak.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int _mode = (Integer) tglFavorite.getTag();
						if (searching_Word != null) {
							if (_mode == 0 || _mode == 2) {
								tts.startTST(searching_Word, "ja");
							} else {
								tts.startTST(searching_Word, "vi");
							}
						}
					}
				});
				/****************************** ADD TO FAVORITE ******************************/
				tglFavorite.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int _mode = (Integer) tglFavorite.getTag();
						if (searching_Word != null) {
							if (!db.checkFavorite(fullword.getString(0),
									Table_Mode[_mode])) {
								db.addFavorite(fullword.getString(1),
										Table_Mode[_mode]);
								tglFavorite
										.setBackgroundResource(R.drawable.favourite_added);
							} else {
								tglFavorite
										.setBackgroundResource(R.drawable.favourite_not_add);
								db.deleteFavorite(searching_Word,
										Table_Mode[_mode]);
							}
						}
					}
				});
				return meaning_temp;
			} else
				return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.replace(R.id.content_frame, fragment0);
			ft.commit();
			WebView _webview = (WebView) findViewById(R.id.webViewResult);
			if (_webview != null) {
				ToggleButton tglFavorite = (ToggleButton) findViewById(R.id.btnFavorite);
				Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
				if(myDict_mode != -1)
				{
					tglFavorite.setVisibility(View.INVISIBLE);
					btnSpeak.setVisibility(View.INVISIBLE);
			//		tglFavorite.setVisibility(View.GONE);
				}
				else
					{
					tglFavorite.setVisibility(View.VISIBLE);
					btnSpeak.setVisibility(View.VISIBLE);
					int _mode;
					if (his_mode == -1)
						_mode = mode;
					else {
						_mode = his_mode;
					}	
				if (db.checkFavorite(fullword.getString(0), Table_Mode[_mode])) {
					
					tglFavorite
							.setBackgroundResource(R.drawable.favourite_added);
				} else {
					tglFavorite
							.setBackgroundResource(R.drawable.favourite_not_add);
				}
					}
				//make his_mode -> default
				his_mode = -1;
				isOutside = false;
				_webview.loadDataWithBaseURL(null, result, "text/html",
						"utf-8", null);
				searchView.setQuery(searching_Word, false);
				searchView.clearFocus();
			} else
			{	
				searchView.setTag(id);
				isOutside= true;
				searchView.setQuery(searching_Word, true);
			}
		}

	}

	public void Radical() {
		if (isOpen == true) {
			Intent intent = new Intent(this, DrawKanjiViewController.class);
			SearchView search = (SearchView) findViewById(R.id.action_websearch);
			radical = search.getQuery().toString();
			intent.putExtra("Radical", radical);
			intent.putExtra("MODE_DICT", mode);
			startActivity(intent);
			// Toast.makeText(getApplicationContext(), radical, 5).show();
		} else {
			Intent intent = new Intent(this, DrawKanjiViewController.class);
			Toast.makeText(getBaseContext(), R.string.strLoading, 3).show();
			intent.putExtra("MODE_DICT", mode);
			startActivity(intent);
		}

	}

	public void DrawKanji() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		inputmethod.RadicalKanjiSearchViewController FragmentDrawKanji = new inputmethod.RadicalKanjiSearchViewController(
				this);
		ft.replace(R.id.content_frame, FragmentDrawKanji);
		isOpen = true;
		ft.commit();
	}

	public void Speech() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		VoiceRecognitionViewController voiceRecogFragment = new VoiceRecognitionViewController(
				this);
		ft.replace(R.id.content_frame, voiceRecogFragment);
		isOpen = true;
		ft.commit();
	}

	public void Cam() {
		fragmentTran = new TranslationViewController();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.content_frame, fragmentTran);
		ft.commit();
		/*
		 * Intent intent = new Intent(this, Languge.class);
		 * startActivity(intent);
		 */
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// 0 = Japanese-Vietnamese; 1 = Vietnamese - Japanese; 2= Kanji
		mode = position;
if (position == 0) {
			Toast.makeText(getApplicationContext(), R.string.selectMode0,
					Toast.LENGTH_LONG).show();
		} else if (position == 1) {
			Toast.makeText(getApplicationContext(), R.string.selectMode1,
					Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(getApplicationContext(), R.string.selectMode2,
					Toast.LENGTH_LONG).show();	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right,
			int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		// TODO Auto-generated method stub

	}
 	@Override
 	public void onActivityResult(int requestCode, int resultCode, Intent data)
 	{
 		super.onActivityResult(requestCode, resultCode, data);
 		if (resultCode == 10001)
 		{
 	 		FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
		//	ft.replace(R.id.content_frame, fragment5);
			ft.replace(R.id.content_frame, new MyDictViewController());
			ft.commit();
			mReturningWithResult = true;
 			/*MyDictActivity mda = new MyDictActivity();
 			mda.onActivityResult(requestCode, resultCode, null);
 			this.onStart();*/
 		}
 	}
 	@Override
 	protected void onPostResume() {
 	super.onPostResume();
 	if (mReturningWithResult) {
 	// Commit your transactions here.
 	}
 	// Reset the boolean flag back to false for next time.
 	mReturningWithResult = false;
 	}
}