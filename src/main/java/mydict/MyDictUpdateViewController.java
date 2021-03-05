package mydict;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 *クラス名：  MyDictUpdateViewController
 *日付: 2014/03/02
 *著作権表示: N/A
 *変更ログ:
 *20140303  LINHNC add  function onBackPressed
 *			LINHNC update function  onOptionsItemSelected
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140302 LINHNC 説明 */
@SuppressLint("NewApi")
public class MyDictUpdateViewController extends Activity {

	public static String word = "";
	public static String meaning = "";
	public static boolean isUpdate = false;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_my_dict);
		Button btnSave = (Button) findViewById(R.id.SaveButton);
		Button btnDelete = (Button) findViewById(R.id.DeleteButton);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		final EditText word_E = (EditText) findViewById(R.id.word);
		final EditText meaning_E = (EditText) findViewById(R.id.meaning);
		word_E.setText(word);
		meaning_E.setText(meaning);
		if (isUpdate)
			word_E.setFocusable(false);
		else
			word_E.setFocusable(true);
		btnSave.setOnClickListener(new OnClickListener() {
			// clicked on Save button
			@Override
			public void onClick(View v) {

				String word_temp = word_E.getText().toString();
				String meaning_temp = meaning_E.getText().toString();
				if (word_temp.equals("") || meaning_temp.equals("")) {
					Toast.makeText(getBaseContext(), "Word or Meaning is null",
							Toast.LENGTH_LONG).show();
				} else if (ResultViewController.db.checkExistMyDict(word_temp)
						&& isUpdate == false) {
					word_E.setEnabled(false);
					Toast.makeText(getBaseContext(), "Word existed in My Dict",
							Toast.LENGTH_LONG).show();
				} else {
					setResult(10001, getIntent());
					ResultViewController.db.addMyDict(word_temp, meaning_temp);
					onBackPressed();
				}
			}
		});

		btnDelete.setOnClickListener(new OnClickListener() {
			// clicked on Delete button
			@Override
			public void onClick(View v) {
				// Delete word in my dictionary
				ResultViewController.db.deleteMyDict(word);
				setResult(10001, getIntent());
				onBackPressed();
			}
		});
	}

	@Override
	public void onBackPressed() {
		word = null;
		meaning = null;
		final EditText word_E = (EditText) findViewById(R.id.word);
		final EditText meaning_E = (EditText) findViewById(R.id.meaning);
		word_E.setText("");
		meaning_E.setText("");
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// clicked home button
		switch (item.getItemId()) {
		case android.R.id.home: {
			onBackPressed();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}
}
