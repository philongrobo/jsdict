package jsdictmain;

import vn.team5b1.jsdict.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import com.actionbarsherlock.app.SherlockFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

;

@SuppressLint("NewApi")
public class SearchScrActivity extends SherlockFragment {
	private WebView _webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_search_screen, container,
				false);
		_webView = (WebView) rootView.findViewById(R.id.webViewResult);
		_webView.setBackgroundColor(0x00000000);
		_webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		
		String meaning;
		meaning = "";		
		_webView.loadDataWithBaseURL(null, meaning, "text/html",
				"utf-8", null);
		return rootView;
	}

}

