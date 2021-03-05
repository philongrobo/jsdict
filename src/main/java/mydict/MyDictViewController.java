package mydict;

import pagerview.RowAdapter;
import pagerview.RowObject;
import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/*
 *クラス名：  MyDictViewController
 *日付: 2014/03/02
 *著作権表示: N/A
 *変更ログ:
 *20140303  LINHNC add  function onActivityResult
 *			LINHNC add class  GetMyDictTask
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140302 LINHNC 説明 */
@SuppressLint("NewApi")
public class MyDictViewController extends SherlockFragment {
	public static ListView _listView;
	private static Cursor fullword;
	static RowObject _arrayRowObject[] = null;
	static LayoutInflater _inflater;
	static ViewGroup _container;
	static View rootView;
	public static RowAdapter adapter = null;
	boolean isChanged;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater
				.inflate(R.layout.activity_my_dict, container, false);
		Button _newBtn = (Button) rootView.findViewById(R.id.imgAddNewButton);
		_newBtn.setOnClickListener(new OnClickListener() {
			// clicked on Add button
			@Override
			public void onClick(View v) {
				// TODO load MyDictUpdateView
				MyDictUpdateViewController.isUpdate = false;
				Intent intent = new Intent(getActivity().getBaseContext(),
						MyDictUpdateViewController.class);
				startActivityForResult(intent, 10001);

			}
		});
		new GetMyDictTask().doInBackground();
		return rootView;
	}

	/* Loading ListView and set ButtonClickedListener */
	private class GetMyDictTask extends AsyncTask<Void, Void, RowObject[]> {

		@Override
		protected RowObject[] doInBackground(Void... arg0) {
			// TODO Loading ListView
			fullword = ResultViewController.db.getMyDict();

			if (fullword.moveToFirst()) {
				int range = fullword.getCount();
				_arrayRowObject = new RowObject[range];
				for (int i = 0; i < range; i++) {
					_arrayRowObject[i] = new RowObject(R.drawable.edit_btn,
							fullword.getString(2), "myDict");
					fullword.moveToNext();
				}
				fullword.close();
				adapter = new RowAdapter(getActivity(),
						R.layout.listview_item_row_mydict, _arrayRowObject);
			} else if (adapter != null)
				adapter = null; // clear when null
			if (adapter != null) {
				_listView = (ListView) rootView
						.findViewById(R.id.listViewMyDict);
				_listView.setAdapter(adapter);
				_listView.setOnItemClickListener(new OnItemClickListener() {
					// clicked on a Row
					@SuppressLint("NewApi")
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO search word in row clicked
						ResultViewController.myDict_mode = 0;
						CharSequence word = _arrayRowObject[position].title;
						String str_id = ResultViewController.db.getId(
								(String) word, _arrayRowObject[position].table);
						ResultViewController.isOutside = true;
						ResultViewController.searchView.setTag(str_id);
						ResultViewController.searchView.setQuery(word, true);
					}

				});
			}
			return _arrayRowObject;
		}
	}

	/* Execute when MyDictUpdateView return value */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 10001) {
			FragmentActivity fm = (FragmentActivity) getActivity();
			SherlockFragment frg = null;
			frg = (SherlockFragment) fm.getSupportFragmentManager()
					.findFragmentById(R.id.content_frame);
			final android.support.v4.app.FragmentTransaction ft = fm
					.getSupportFragmentManager().beginTransaction();
			ft.detach(frg);
			ft.attach(frg);
			ft.replace(R.id.content_frame, new MyDictViewController());
			ft.commit();
		}
	}

}
