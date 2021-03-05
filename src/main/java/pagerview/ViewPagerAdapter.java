package pagerview;

import vn.team5b1.jsdict.R;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	// Declare the number of ViewPager pages
	final int PAGE_COUNT = 3;
	private String titles[];

	public ViewPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		titles = context.getResources().getStringArray(R.array.viewpager_tab);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {

		// Open FragmentTab1.java
		case 0:
			HistoryActivity historytab1 = new HistoryActivity();
			return historytab1;

			// Open FragmentTab2.java
		case 1:
			FavouriteActivity favouritetab2 = new FavouriteActivity();
			return favouritetab2;
		case 2:
			WoDActivity woDtab3 = new WoDActivity();
			return woDtab3;
		}
		return null;
	}

	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}
}