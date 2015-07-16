package com.ohhonghong.bangto;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class TabMainActivity extends FragmentActivity implements ActionBar.TabListener {

   LinearLayout layout_member, layout_bank, layout_manage;
   SectionsPagerAdapter mSectionsPagerAdapter;
   ViewPager mViewPager;

   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.tab);
      final ActionBar actionBar = getActionBar();
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayShowHomeEnabled(false);
      // Create the adapter that will return a fragment for each of the three
      // primary sections of the app.
      mSectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getSupportFragmentManager());

      // Set up the ViewPager with the sections adapter.
      mViewPager = (ViewPager) findViewById(R.id.pager);
      mViewPager.setAdapter(mSectionsPagerAdapter);

      mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
         @Override
         public void onPageSelected(int position) {
            actionBar.setSelectedNavigationItem(position);
         }
      });

      /*
      // For each of the sections in the app, add a tab to the action bar.
      for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
         // Create a tab with text corresponding to the page title defined by
         // the adapter. Also specify this Activity object, which implements
         // the TabListener interface, as the callback (listener) for when
         // this tab is selected.
         actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
      }*/
      
      
      
      actionBar.addTab(actionBar.newTab().setText("���").setTabListener(this));
      actionBar.addTab(actionBar.newTab().setText("�ְ�ޱ�").setTabListener(this));
      actionBar.addTab(actionBar.newTab().setText("�����").setTabListener(this));
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
            return new MemberActivity(mContext);
         case 1:
            return new BankActivity(mContext);
         case 2:
            return new MoneyActivity(mContext);
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
      mViewPager.setCurrentItem(tab.getPosition());
   }

   @Override
   public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
      // TODO Auto-generated method stub\
   }
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}