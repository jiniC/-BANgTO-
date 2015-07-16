package com.ohhonghong.bangto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class PersonalInfoActivity extends Activity  {
	ImageButton ibNext;
	Spinner bankspinner;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		ibNext = (ImageButton) findViewById(R.id.ibNext);
		bankspinner = (Spinner) findViewById(R.id.spinner1);
		
		
		ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
				PersonalInfoActivity.this, R.array.bankname,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bankspinner.setAdapter(adapter1);

		
		ibNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), TabMainActivity.class);
				startActivity(intent);
				
			}
		});
				
	}

}
