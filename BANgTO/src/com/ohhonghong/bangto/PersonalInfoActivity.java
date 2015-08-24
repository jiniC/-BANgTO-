package com.ohhonghong.bangto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class PersonalInfoActivity extends Activity {
	ImageButton ibNext;
	Spinner bankspinner;
	EditText account;
	String bank, acc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		ibNext = (ImageButton) findViewById(R.id.ibNext);
		bankspinner = (Spinner) findViewById(R.id.spinner);
		account = (EditText) findViewById(R.id.account);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();

		ArrayAdapter adapter1 = ArrayAdapter.createFromResource(PersonalInfoActivity.this, R.array.bankname,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bankspinner.setAdapter(adapter1);
		bankspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
				// TODO Auto-generated method stub
				bank = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				bank = "±§¡÷¿∫«‡";
			}
		});

		ibNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				acc = account.getText().toString();

				Thread thread = new Thread() {
					@Override
					public void run() {
						HttpClient httpClient = new DefaultHttpClient();
						String urlString = "http://119.205.252.231:8080/BANgToServer/insert_personal_info.jsp";
						String TAG = "ing";
						try {
							URI url = new URI(urlString);

							HttpPost httpPost = new HttpPost();
							httpPost.setURI(url);

							List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
							
							nameValuePairs.add(new BasicNameValuePair("email", "testEmail"));
							nameValuePairs.add(new BasicNameValuePair("username", "testUsername"));
							nameValuePairs.add(new BasicNameValuePair("bank", bank));
							nameValuePairs.add(new BasicNameValuePair("account", acc));
							
							httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

							HttpResponse response = httpClient.execute(httpPost);
							String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

							Log.d(TAG, responseString);
						} catch (URISyntaxException e) {
							Log.e(TAG, e.getLocalizedMessage());
							e.printStackTrace();
						} catch (ClientProtocolException e) {
							Log.e(TAG, e.getLocalizedMessage());
							e.printStackTrace();
						} catch (IOException e) {
							Log.e(TAG, e.getLocalizedMessage());
							e.printStackTrace();
						}

					}
				};

				thread.start();

				Intent intent = new Intent(getApplicationContext(), GroupMenuActivity.class);
				startActivity(intent);

			}
		});

	}

}