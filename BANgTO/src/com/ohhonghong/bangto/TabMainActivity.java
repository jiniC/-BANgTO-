package com.ohhonghong.bangto;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

public class TabMainActivity  extends FragmentActivity implements ActionBar.TabListener {

	LinearLayout layout_member, layout_bank, layout_manage;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getApplicationContext(), getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

<<<<<<< HEAD
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
=======
		TabSpec tabSpec1 = tabHost.newTabSpec("TAG1").setIndicator(tabwidget01);
		tabSpec1.setContent(new Intent(this,MemberActivity.class));
		tabHost.addTab(tabSpec1);
>>>>>>> 2ff74ba3ac53441f6590536bc8d8d627955e2589

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}
		public class SectionsPagerAdapter extends FragmentPagerAdapter {
			Context mContext;

			public SectionsPagerAdapter(Context mContext, FragmentManager fm) {
				super(fm);
				this.mContext = mContext;
			}

			@Override
			public Fragment getItem(int position) {
				// getItem is called to instantiate the fragment for the given page.
				// Return a DummySectionFragment (defined as a static inner class
				// below) with the page number as its lone argument.
				switch (position) {
				case 0:
					return new BankActivity(mContext);
				case 1:
					return new BankActivity(mContext);
				case 2:
					return new BankActivity(mContext);
				}
				return null;
			}

			@Override
			public int getCount() {
				// Show 3 total pages.
				return 3;
			}
	}

		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(tab.getPosition());
		}

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
}
