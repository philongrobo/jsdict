package jsdictmain;

import java.lang.reflect.Field;

import pagerview.ViewPagerAdapter;
import vn.team5b1.jsdict.R;


import com.actionbarsherlock.app.SherlockFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class StartActivity extends SherlockFragment {
	public int position;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.viewpager_main, container, false);
		// Locate the ViewPager in viewpager_main.xml
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		// Set the ViewPagerAdapter into ViewPager
		try
		{
		mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), getActivity()));
		}
		catch(Exception ex){	
		}
		if (position == 0)
			mViewPager.setCurrentItem(0);
		else if (position == 1)
			mViewPager.setCurrentItem(1);
		else if (position == 2)
			mViewPager.setCurrentItem(2);
		return view;
	}

	@SuppressLint("NewApi")
	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}