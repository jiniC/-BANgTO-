package com.ohhonghong.bangto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PersonalInfoActivity extends Activity{
	ImageButton ibNext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		ibNext = (ImageButton) findViewById(R.id.ibNext);
		
		ibNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), GroupMenuActivity.class);
				startActivity(intent);
			}
		});
				
	}
}
