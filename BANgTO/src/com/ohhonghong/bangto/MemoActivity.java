package com.ohhonghong.bangto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.ohhonghong.adapter.MemoAdapter;
import com.ohhonghong.data.ListDataMemo;
import com.ohhonghong.utility.MemoAsyncTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MemoActivity extends Fragment {

	public MemoAsyncTask task;
	public ListView mListView;
	public MemoAdapter mAdapter;

	SimpleDateFormat m_date_format = null;
	SimpleDateFormat m_time_format = null;
	String dateS, timeS;

	ImageButton sendbutton;
	TextView message;
	String memo;
	String date;
	String userName;
	 
	Context mContext;
	public String group;
	public String email;

	ArrayList<ListDataMemo> data_list = new ArrayList<ListDataMemo>();

	public MemoActivity(Context context, String group,String email) {
		mContext = context;
		this.group = group;
		this.email = email;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.memo, null);

		mListView = (ListView) view.findViewById(R.id.var_list);
		mAdapter = new MemoAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		conntectCheck();

		sendbutton = (ImageButton) view.findViewById(R.id.sendbutton);
		message = (TextView) view.findViewById(R.id.message);


		sendbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				memo = message.getText().toString();
				
				
				Log.d(email, "emailmemo");
				
				long now = System.currentTimeMillis();// 현재 시간을 msec으로 구한다.
				Date date = new Date(now);// 현재 시간을 저장 한다.
				m_date_format = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
				m_time_format = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
				dateS = m_date_format.format(date);
				timeS = m_time_format.format(date);

				Thread thread = new Thread() {
					@Override
					public void run() {
						HttpClient httpClient = new DefaultHttpClient();
						String urlString = "http://119.205.252.231:8080/BANgToServer/insert_memo.jsp";
						String TAG = "ing";
						try {
							URI url = new URI(urlString);

							HttpPost httpPost = new HttpPost();
							httpPost.setURI(url);

							List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
							// nameValuePairs.add(new BasicNameValuePair("id",
							// "test"));
							nameValuePairs.add(new BasicNameValuePair("groupName", group));
							nameValuePairs.add(new BasicNameValuePair("who", email));
							nameValuePairs.add(new BasicNameValuePair("date", dateS + " " + timeS));
							nameValuePairs.add(new BasicNameValuePair("memo", memo));

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

				String sendmessage = message.getText().toString();
				message.setText("");

				/*
				 * ListDataMemo data = null; data = new ListDataMemo();
				 * data.setUsername("test"); data.setMemo(sendmessage);
				 * mAdapter.add(data); data_list.add(data);
				 */

				// mListView.smoothScrollToPosition(mAdapter.getCount() - 1);
				mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
				mAdapter.notifyDataSetChanged();
				conntectCheck();
			}
		});

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//

				return false;
			}
		});
		return view;
	}

	// 웹에서 데이터를 가져오기 전에 먼저 네트워크 상태부터 확인
	public void conntectCheck() {
		ConnectivityManager connMgr = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			// fetch data
			// Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();

			task = new MemoAsyncTask(MemoActivity.this);
			task.execute("");

		} else {
			// display error
			Toast.makeText(getActivity(), "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
		}
	}
}