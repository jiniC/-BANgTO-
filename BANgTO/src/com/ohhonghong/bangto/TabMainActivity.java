package com.ohhonghong.bangto;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class TabMainActivity extends FragmentActivity implements ActionBar.TabListener {

   SectionsPagerAdapter mSectionsPagerAdapter;
   ViewPager mViewPager;
   String group;
   String email;
   
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
      
      actionBar.addTab(actionBar.newTab().setIcon(R.drawable.t_member3).setTabListener(this));
      actionBar.addTab(actionBar.newTab().setIcon(R.drawable.t_bank2).setTabListener(this));
      actionBar.addTab(actionBar.newTab().setIcon(R.drawable.t_money).setTabListener(this));
      actionBar.addTab(actionBar.newTab().setIcon(R.drawable.t_chat).setTabListener(this));
      
      ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#d70000"));
      actionBar.setStackedBackgroundDrawable(colorDrawable);
      
      //mTabs.add(new SamplePagerItem(getString(R.string.tab_stream),Color.BLUE,Color.GRAY));
      Intent intent = getIntent();
      group = intent.getStringExtra("group");
      email = intent.getStringExtra("email3");
      Log.d(email, "emailtab");
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
            return new MemberActivity(mContext,group);
         case 1:
            return new BankActivity(mContext,group);
         case 2:
            return new MoneyActivity(mContext,group);
         case 3:
            return new MemoActivity(mContext,group,email);
         }
         return null;
      }

      @Override
      public int getCount() {
         // Show 3 total pages.
         return 4;
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
   
   /*
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
   }*/
}