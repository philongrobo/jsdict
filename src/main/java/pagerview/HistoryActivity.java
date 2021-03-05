package pagerview;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
/*
 *クラス名：  HistoryActivity
 *日付: 2014/02/20
 *著作権表示: N/A
 *変更ログ:
 *20140301  LINHNC add  class GetFavoriteTaskHis
 *			LINHNC update  function onCreateView
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140220 HoangLA 説明 */
public class HistoryActivity extends SherlockFragment {
	public  ListView _listView;
	private static Cursor fullword;
	 static RowObjectForHistory _arrayRowObject[] = null;
	 static LayoutInflater _inflater;
	 static ViewGroup _container ;
	 static View rootView ;
	 public static RowAdapterForHistory adapter  = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 rootView = inflater
				.inflate(R.layout.tab_history, container, false);
		 new GetFavoriteTaskHis().doInBackground(); 
		 return rootView;
	}
	/*
	 *クラス名：  GetFavoriteTaskHis
	 *日付: 2014/02/20
	 *著作権表示: N/A
	 *変更ログ:
	 *	     
	 * 日 著者 説明
	 * --------------------------------------------------------* 
	 20140220 LINHNC 説明 */
		 	private class GetFavoriteTaskHis extends AsyncTask<Void, Void, RowObjectForHistory[]> {

		 		@Override
		 		protected RowObjectForHistory[] doInBackground(Void... arg0) {
		 			// load listview history words 
		 			fullword = ResultViewController.db.getHistory();
		 			if (fullword.moveToFirst()) {
		 				int range = fullword.getCount();
		 				final int favorited =  R.drawable.favourite_added;
		 				final int not_favorited =  R.drawable.favourite_not_add;
		 				_arrayRowObject =  new RowObjectForHistory[range] ;
		 				for(int i = 0;i<range;i++)
		 				{	
		 					String id = fullword.getString(1);
		 					String table = fullword.getString(3);
		 					//check word is favorited or not for display
		 					if(ResultViewController.db.checkFavorite(id,table))
		 						_arrayRowObject[i] =  new RowObjectForHistory(R.drawable.discard, favorited,fullword.getString(2),table);
		 					else
		 						_arrayRowObject[i] =  new RowObjectForHistory(R.drawable.discard,not_favorited,fullword.getString(2),table);
		 					fullword.moveToNext();
		 				}
		 				fullword.close();
		 				adapter = new RowAdapterForHistory(getActivity(),
		 						R.layout.listview_item_row_history, _arrayRowObject);
		 			}
		 				else if(adapter != null)
		 					adapter=null; // clear when null
		 				if(adapter != null)
		 				{
			 			_listView = (ListView) rootView.findViewById(R.id.listViewHistory);
		 				_listView.setAdapter(adapter);
		 				_listView.setOnItemClickListener(new OnItemClickListener() {
							@SuppressLint("NewApi")
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// search meaning word in row clicked
								 if(_arrayRowObject[position].table.equals("jv"))
									 ResultViewController.his_mode = 0;
								 else if (_arrayRowObject[position].table.equals("vj"))
									 ResultViewController.his_mode = 1;
								 else if (_arrayRowObject[position].table.equals("kj"))
									 ResultViewController.his_mode = 2;
								 else if (_arrayRowObject[position].table.equals("myDict"))
									 ResultViewController.myDict_mode = 0;
								 CharSequence word = _arrayRowObject[position].title;
								 String str_id = ResultViewController.db.getId((String) word, _arrayRowObject[position].table);
								// Use SearchView Outside ResultView
								 ResultViewController.isOutside = true;
								 ResultViewController.searchView.setTag(str_id);
								 ResultViewController.searchView.setQuery(word, true);
							}

						});
		 				}
		 				return _arrayRowObject;
		 		}
		 	}
}