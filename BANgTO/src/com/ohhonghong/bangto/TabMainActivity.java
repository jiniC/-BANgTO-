package com.ohhonghong.bangto;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabMainActivity extends TabActivity {

	LinearLayout layout_member, layout_bank, layout_manage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		layout_member = (LinearLayout) findViewById(R.id.layout_member);
		layout_bank = (LinearLayout) findViewById(R.id.layout_bank);
		layout_manage = (LinearLayout) findViewById(R.id.layout_manage);

		TabHost tabHost = getTabHost();

		ImageView tabwidget01 = new ImageView(this);
		tabwidget01.setImageResource(R.drawable.tab_member);
		
		ImageView tabwidget02 = new ImageView(this);
		tabwidget02.setImageResource(R.drawable.tab_bank);
		
		ImageView tabwidget03 = new ImageView(this);
		tabwidget03.setImageResource(R.drawable.tab_manage);

		TabSpec tabSpec1 = tabHost.newTabSpec("TAG1").setIndicator(tabwidget01);
		tabSpec1.setContent(R.id.layout_member);
		tabHost.addTab(tabSpec1);

		TabSpec tabSpec2 = tabHost.newTabSpec("TAG2").setIndicator(tabwidget02);
		tabSpec2.setContent(new Intent(this,BankActivity.class));
		tabHost.addTab(tabSpec2);

		TabSpec tabSpec3 = tabHost.newTabSpec("TAG2").setIndicator(tabwidget03);
		tabSpec3.setContent(R.id.layout_manage);
		tabHost.addTab(tabSpec3);

		tabHost.setCurrentTab(0);
	}
}
