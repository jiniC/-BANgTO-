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

import com.ohhonghong.adapter.MemberAdapter;
import com.ohhonghong.adapter.MemoAdapter;
import com.ohhonghong.data.ListDataMemo;
import com.ohhonghong.utility.MemberAsyncTask;
import com.ohhonghong.utility.MemoAsyncTask;

import android.app.Activity;
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
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MemoActivity extends Fragment {

	public MemoAsyncTask task;
	public ListView mListView;
	public MemoAdapter mAdapter;

	private String m_user_name = "최서진";

	private SimpleDateFormat m_date_format = null;
	private SimpleDateFormat m_time_format = null;

	ImageButton sendbutton;
	TextView message;

	Context mContext;
	public String group;

	ArrayList<ListDataMemo> data_list = new ArrayList<ListDataMemo>();

	public MemoActivity(Context context, String group) {
		mContext = context;
		this.group = group;
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
				final String memo = message.getText().toString();
				m_date_format = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
				m_time_format = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);

				final String date = m_date_format + " " + m_time_format;
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
							nameValuePairs.add(new BasicNameValuePair("id", "test"));
							nameValuePairs.add(new BasicNameValuePair("groupName", "test"));
							nameValuePairs.add(new BasicNameValuePair("who", "test"));
							nameValuePairs.add(new BasicNameValuePair("date", date));
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
				ListDataMemo data = null;
				data = new ListDataMemo();
				data.setUsername("test");
				data.setMemo(sendmessage);
				mAdapter.add(data);

				mListView.smoothScrollToPosition(mAdapter.getCount() - 1);

				data_list.add(data);
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