package pagerview;


import com.actionbarsherlock.app.SherlockFragment;

import database.Converter;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import java.util.Random;

import vn.team5b1.jsdict.R;
import jsdictmain.ResultViewController;
/*
 *クラス名：  RowAdapter
 *日付: 2014/02/19
 *著作権表示: N/A
 *変更ログ:
 *20140301  LINHNC update  function onCreateView
 *	     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140229 HOANGLA 説明 */
public class WoDActivity extends SherlockFragment {
	public static String wod = "";
	@SuppressLint("NewApi")
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.tab_wod, container, false);
	        //Time
	        Time t = new Time();
	        t.setToNow();
	        int month = t.month+1;
	        int day = t.monthDay;
	        int year = t.year;
	        String timeID = Integer.toString(day)+Integer.toString(month)+Integer.toString(year);
	        String timeString = Integer.toString(day)+"/"+Integer.toString(month)+"/"+Integer.toString(year);
	        // getword
	        Random r = new Random(Long.parseLong(timeID));
	        int id = r.nextInt(73826);
	        Cursor word = ResultViewController.db.getFullWord2(Integer.toString(id),"jv");
	        //dislay word
	        WebView _webView = (WebView) view.findViewById(R.id.mean_field) ;
	        _webView.setBackgroundColor(0x00000000);
	        _webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
	        if(word.moveToFirst())
	        wod =Converter.Html_ConverterIns(word.getString(1),word.getString(2));
	        _webView.loadDataWithBaseURL(null, wod, "text/html","utf-8", null);
	        //Display current date
	        TextView tv = (TextView) view.findViewById(R.id.date_text);

	        tv.setText(timeString);
	        return view;
	    }
	}