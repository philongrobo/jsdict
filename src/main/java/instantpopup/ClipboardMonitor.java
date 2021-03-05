package instantpopup;

import java.util.ArrayList;
import java.util.List;

import jsdictmain.ResultViewController;

import com.memetix.mst.language.Language;

import database.Converter;

import translation.TransClass;

import vn.team5b1.jsdict.R;
import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/*
*クラス名： CliboardMonitor
*日付: 20140224
*著作権表示: NA
*変更ログ: NA
* 日			 著者 		説明
* --------------------------------------------------------* 
20140224 	CongTT 		create ClipboardFilpper class*/
@SuppressLint("NewApi")
public class ClipboardMonitor extends StandOutWindow implements
		OnClickListener, OnItemSelectedListener {

	/** Image type to be monitored */
	private NotificationManager mNM;
	private MonitorTask mTask = new MonitorTask();
	private ClipboardManager mCM;
	private Handler handler;
	public View view;
	public boolean modeDictionary;
	Language language1;
	Language language2;
	Spinner spinner1;
	Spinner spinner2;
	TextView txtResult;
	EditText edtSearchWord;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void createAndAttachView(int id, FrameLayout frame) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.body, frame, true);
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		handler = new Handler();
		mCM = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		mCM.setText(null);
		txtResult = (TextView) view
				.findViewById(R.id.txtViewResult);
		edtSearchWord = (EditText) view.findViewById(R.id.edtSearch);
		// Load data to spinner
		spinner1 = (Spinner) view.findViewById(R.id.lang1_Popup);
		spinner2 = (Spinner) view.findViewById(R.id.lang2_Popup);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getBaseContext(), R.array.language1_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(this);
		spinner2.setAdapter(adapter);
		spinner2.setOnItemSelectedListener(this);
		try {
			mTask.start();
			EditText edt = (EditText) view.findViewById(R.id.edtSearch);
			edt.setText("Nihongo");
			Button btnSearch = (Button) view.findViewById(R.id.btnSearchPopup);
			btnSearch.setOnClickListener(this);
		} catch (Exception ex) {

		}
	}

	public void RunTranslate() {
		txtResult.setText("Translating...");
		new TransClass(language1, language2, edtSearchWord.getText().toString()) {
			protected void onPostExecute(Boolean result) {
				txtResult.setText(translatedText);
			}
		}.execute();
	}

	@Override
	public void onDestroy() {
		try {
			mNM.cancel(R.string.clip_monitor_service);
			mTask.cancel();
		} catch (Exception ex) {

		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
	}

	/**
	 * Monitor task: monitor new text clips in global system clipboard and new
	 * image clips in browser download directory
	 */
	private class MonitorTask extends Thread {
		private Handler handler = new Handler();
		private volatile boolean mKeepRunning = false;

		public MonitorTask() {
			super("ClipboardMonitor");
		}

		public void cancel() {
			mKeepRunning = false;
			interrupt();
		}

		@Override
		public void run() {
			mKeepRunning = true;
			while (true) {
				doTask();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ignored) {
				}
				if (!mKeepRunning) {
					break;
				}
			}
		}

		private void doTask() {
			if (mCM.hasText()) {
				final String newClip = mCM.getText().toString();
				handler.post(new Runnable() {
					public void run() {
						EditText edt = (EditText) view
								.findViewById(R.id.edtSearch);
						edt.setText(newClip.toString());
						mCM.setText(null);
					}
				});
			}
		}
	}

	@Override
	public String getAppName() {
		return "JS Dictionary";
	}

	@Override
	public int getAppIcon() {
		return android.R.drawable.ic_menu_add;
	}

	// every window is initially same size
	@Override
	public StandOutLayoutParams getParams(int id, Window window) {
		return new StandOutLayoutParams(id, 400, 300,
				StandOutLayoutParams.AUTO_POSITION,
				StandOutLayoutParams.AUTO_POSITION, 100, 100);
	}

	// we want the system window decorations, we want to drag the body, we want
	// the ability to hide windows, and we want to tap the window to bring to
	// front
	@Override
	public int getFlags(int id) {
		return StandOutFlags.FLAG_DECORATION_SYSTEM
				| StandOutFlags.FLAG_BODY_MOVE_ENABLE
				| StandOutFlags.FLAG_WINDOW_HIDE_ENABLE
				| StandOutFlags.FLAG_WINDOW_BRING_TO_FRONT_ON_TAP
				| StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE
				| StandOutFlags.FLAG_WINDOW_PINCH_RESIZE_ENABLE;
	}

	// return an Intent that creates a new MultiWindow
	@Override
	public Intent getPersistentNotificationIntent(int id) {
		return new Intent(this, ResultViewController.class);
	}

	@Override
	public int getHiddenIcon() {
		return android.R.drawable.ic_menu_info_details;
	}

	@Override
	public String getHiddenNotificationTitle(int id) {
		return getAppName();
	}

	@Override
	public String getHiddenNotificationMessage(int id) {
		String restore = getResources().getString(R.string.strRestore);
		return restore;
	}
	
	@Override
	public Animation getCloseAnimation(int id) {
		// TODO Auto-generated method stub
		Bundle data = new Bundle();
		data.putString("close", "1");
		sendData(id, TogglePopup.class, DEFAULT_ID, 0,
				data);
		return super.getCloseAnimation(id);
	}


	// return an Intent that restores the MultiWindow
	@Override
	public Intent getHiddenNotificationIntent(int id) {
		return StandOutWindow.getShowIntent(this, getClass(), id);
	}

	@Override
	public Animation getShowAnimation(int id) {
		if (isExistingId(id)) {
			// restore
			return AnimationUtils.loadAnimation(this,
					android.R.anim.slide_in_left);
		} else {
			// show
			return super.getShowAnimation(id);
		}
	}

	@Override
	public Animation getHideAnimation(int id) {
		return AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);
	}

	@Override
	public List<DropDownListItem> getDropDownItems(int id) {
		List<DropDownListItem> items = new ArrayList<DropDownListItem>();
		items.add(new DropDownListItem(android.R.drawable.ic_menu_preferences,
				"JSDict Dictionary", new Runnable() {

					@Override
					public void run() {
						modeDictionary = true;
						Toast.makeText(ClipboardMonitor.this,
								R.string.strJSDict,
								Toast.LENGTH_SHORT).show();
					}
				}));
		items.add(new DropDownListItem(android.R.drawable.ic_menu_preferences,
				"Bing Dictionary", new Runnable() {

					@Override
					public void run() {
						modeDictionary = false;
						Toast.makeText(ClipboardMonitor.this,
								R.string.strBing,
								Toast.LENGTH_SHORT).show();

					}
				}));
		return items;
	}

	@Override
	public void onReceiveData(int id, int requestCode, Bundle data,
			Class<? extends StandOutWindow> fromCls, int fromId) {
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSearchPopup:
			if(modeDictionary == false){
				RunTranslate();
			}
			else
			{
				Cursor fullword = null;
				String meaning = "";
				//TODO JSdict mode
					if(language1 == Language.VIETNAMESE && language2 == Language.JAPANESE)
					{
						 fullword = ResultViewController.db.getFullWord(edtSearchWord.getText().toString(),"vj");
							if(fullword.moveToFirst())
								meaning = fullword.getString(2).toString();
							else
								meaning = getResources().getString(R.string.error_no_words);
					}
					else if(language1 == Language.JAPANESE && language2 == Language.VIETNAMESE)
					{
						 fullword = ResultViewController.db.getFullWord(edtSearchWord.getText().toString(),"jv");
						if(fullword.moveToFirst())
							meaning = fullword.getString(2).toString();
						else
							meaning = getResources().getString(R.string.error_no_words);
					}
					else
					{
						meaning = edtSearchWord.getText().toString();
					}
				txtResult.setText(Html.fromHtml(Converter.Html_ConverterIns("",meaning)),TextView.BufferType.SPANNABLE);
			}
			break;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// // TODO Auto-generated method stub
		switch (spinner1.getSelectedItemPosition()) {
		case 0:
			language1 = Language.ENGLISH;
			break;
		case 1:
			language1 = Language.VIETNAMESE;
			break;
		case 2:
			language1 = Language.JAPANESE;
			break;
		}
		switch (spinner2.getSelectedItemPosition()) {
		case 0:
			language2 = Language.ENGLISH;
			break;
		case 1:
			language2 = Language.VIETNAMESE;
			break;
		case 2:
			language2 = Language.JAPANESE;
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}