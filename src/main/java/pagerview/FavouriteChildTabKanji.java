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
 *クラス名：  FavouriteChildTabKanji
 *日付: 2014/02/20
 *著作権表示: N/A
 *変更ログ:
 *20140301  LINHNC add  class GetFavoriteTaskKJ
 *			LINHNC update  function onCreateView
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140220 HoangLA 説明 */
public class FavouriteChildTabKanji extends SherlockFragment{
	public static ListView _listView;
	private static Cursor fullword;
	 static RowObject _arrayRowObject[];
	 static LayoutInflater _inflater;
	 static ViewGroup _container ;
	 static View rootView ;
	 static RowAdapterForFavourite adapter ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.tab_favourite_kanji, container,
				false);
		new GetFavoriteTaskKJ().doInBackground();
		return rootView;
	}
	/*
	 *クラス名：  GetFavoriteTaskKJ
	 *日付: 2014/02/20
	 *著作権表示: N/A
	 *変更ログ:
	 *	     
	 * 日 著者 説明
	 * --------------------------------------------------------* 
	 20140220 LINHNC 説明 */
	private class GetFavoriteTaskKJ extends AsyncTask<Void, Void, RowObject[]> {

		@Override
		protected RowObject[] doInBackground(Void... arg0) {
			// load listview favorite 

			fullword = ResultViewController.db.getFavorite("kj");
			
			if (fullword.moveToFirst()) {
				int range = fullword.getCount();
				_arrayRowObject = new RowObject[range] ;
				for(int i = 0;i<range;i++)
				{
					_arrayRowObject[i] = new RowObject(R.drawable.favourite_added, fullword.getString(1).toString(),"FavoriteKJ");
						fullword.moveToNext();
				}
				fullword.close();
				adapter = new RowAdapterForFavourite(getActivity(),
						R.layout.listview_item_row_favourite, _arrayRowObject,"FavoriteKJ");
			}
				else if(adapter != null)
					adapter=null; // clear when null
				if(adapter != null)
				{
				_listView = (ListView) rootView.findViewById(R.id.listViewFavourite_kanji);
				_listView.setAdapter(adapter);
 				_listView.setOnItemClickListener(new OnItemClickListener() {
					@SuppressLint("NewApi")
					@Override
					public void onItemClick(AdapterView<?> parent,
							View view, int position, long id) {
						// search meaning word in row clicked
						 ResultViewController.his_mode = 2;
						 CharSequence word = _arrayRowObject[position].title;
						 String str_id = ResultViewController.db.getId((String) word,"kj");
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
