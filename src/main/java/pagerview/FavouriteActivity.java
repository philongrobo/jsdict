package pagerview;


import vn.team5b1.jsdict.R;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 *クラス名：  FavouriteActivity
 *日付: 2014/02/20
 *著作権表示: N/A
 *変更ログ:     
 * 日 著者 説明
 * --------------------------------------------------------* 
 20140220 HoangLA 説明 */
public class FavouriteActivity extends SherlockFragment  {
	FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mTabHost = new FragmentTabHost(getSherlockActivity());

		mTabHost.setup(getSherlockActivity(), getChildFragmentManager(),
				R.layout.tab_favourite);

		// Create Child Tab1
		mTabHost.addTab(mTabHost.newTabSpec("Kanji").setIndicator("Kanji"),
				FavouriteChildTabKanji.class, null);

		// Create Child Tab2
		mTabHost.addTab(mTabHost.newTabSpec("Japanese").setIndicator("Japanese"),
				FavouriteChildTabJap.class, null);		
		
		// Create Child Tab3
		mTabHost.addTab(mTabHost.newTabSpec("Vietnamese").setIndicator("Vietnamese"),
				FavouriteChildTabVie.class, null);
		
		return mTabHost;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTabHost = null;
	}
	
}
