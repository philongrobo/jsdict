package jsdictmain;

import java.util.Random;

import mydict.MyDictUpdateViewController;

import vn.team5b1.jsdict.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;

import database.Converter;

;

@SuppressLint("NewApi")
public class HomeViewController extends SherlockFragment {
	private WebView _webView;
	private ImageButton tglFavorite;
	private ImageButton tglMyDict;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_first_screen, container,
				false);
		_webView = (WebView) rootView.findViewById(R.id.WoD_webView);
		_webView.setBackgroundColor(0x00000000);
		_webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		tglFavorite = (ImageButton) rootView.findViewById(R.id.imgAddToFavourite);
		tglMyDict = (ImageButton) rootView.findViewById(R.id.imgAddToMyDict);
		String meaning = "";
        Time t = new Time();
        t.setToNow();
        int month = t.month+1;
        int day = t.monthDay;
        int year = t.year;
        String timeID = Integer.toString(day)+Integer.toString(month)+Integer.toString(year);
        // getword
        Random r = new Random(Long.parseLong(timeID));
        int id = r.nextInt(73826);
        final Cursor word = ResultViewController.db.getFullWord2(Integer.toString(id),"jv");
        //dislay word
        if(word.moveToFirst())
        	meaning =Converter.Html_ConverterIns(word.getString(1),word.getString(2));	
		_webView.loadDataWithBaseURL(null, meaning, "text/html",
				"utf-8", null);
		if(tglFavorite != null){
		if (ResultViewController.db.checkFavoriteByWord(word.getString(1),"jv"))
			 {				
					tglFavorite
							.setBackgroundResource(R.drawable.favourite_added);
				} else {
					tglFavorite
							.setBackgroundResource(R.drawable.favourite_not_add);
				}
			
		tglFavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!ResultViewController.db.checkFavoriteByWord(word.getString(1),"jv")) {
					ResultViewController.db.addFavorite(word.getString(1),
							"jv");
					tglFavorite
							.setBackgroundResource(R.drawable.favourite_added);
				} else {
					tglFavorite
							.setBackgroundResource(R.drawable.favourite_not_add);
					ResultViewController.db.deleteFavorite(word.getString(1),
							"jv");
				}
			}
		});
		
		tglMyDict.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ResultViewController.db.checkExistMyDict(word.getString(1)))
				{
					MyDictUpdateViewController.isUpdate = true;
					Cursor mdWord = ResultViewController.db.getFullWord(word.getString(1), "myDict");
					mdWord.moveToFirst();
					MyDictUpdateViewController.word = mdWord.getString(2);
					MyDictUpdateViewController.meaning = mdWord.getString(3);
				}
				else
				{
					MyDictUpdateViewController.isUpdate = false;
					MyDictUpdateViewController.word = word.getString(1);
					MyDictUpdateViewController.meaning ="";
				}
				Intent intent = new Intent(getActivity().getBaseContext(),
						MyDictUpdateViewController.class);
				startActivityForResult(intent, 10001);
			}
		});
		} 
		return rootView;
	}

}

