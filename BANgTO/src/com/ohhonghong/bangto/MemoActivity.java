package com.ohhonghong.bangto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

	/*private SimpleDateFormat m_date_format = null;
	private SimpleDateFormat m_time_format = null;*/

	ImageButton sendbutton;
	TextView message;
	
	Context mContext;
	public String group;

	public MemoActivity(Context context,String group) {
		mContext = context;
		this.group = group;
	}

	
	@Override
	public View  onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.memo, null);
		
		final ArrayList<ListDataMemo> data_list = new ArrayList<ListDataMemo>();

		
		mListView = (ListView) view.findViewById(R.id.var_list);
		mAdapter = new MemoAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		conntectCheck();
		

		/*m_date_format = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		m_time_format = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);*/

		sendbutton = (ImageButton) view.findViewById(R.id.sendbutton);
		message = (TextView) view.findViewById(R.id.message);

		sendbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
<<<<<<< HEAD
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
							nameValuePairs.add(new BasicNameValuePair("groupName", group));
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
=======

//				String sendmessage = message.getText().toString();
//				message.setText("");
//				ListDataMemo data = null;
//				data = new ListDataMemo((byte) 1, sendmessage, m_date_format.format(new Date()));
//
//				m_adapter.add(data);
//				
//				m_list.smoothScrollToPosition(m_adapter.getCount() - 1);
//				
//				data_list.add(data);
>>>>>>> 2bd29c906293d5aebb30e317ef053d869007802e
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

//	private class MemoAdapter extends BaseAdapter {
//		private LayoutInflater m_inflater = null;
//
//		private ArrayList<ListDataMemo> m_data_list;
//
//		public MemoAdapter(ArrayList<ListDataMemo> items) {
//			m_data_list = items;
//
//			m_inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		}
//
//
//		public void add(ListDataMemo parm_data) {
//			m_data_list.add(parm_data);
//
//			notifyDataSetChanged();
//		}
//
//		@Override
//		public int getCount() {
//			return m_data_list.size();
//		}
//
//		@Override
//		public ListDataMemo getItem(int position) {
//			return m_data_list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public int getItemViewType(int position) {
//			return m_data_list.get(position).type;
//		}
//
//		@Override
//		public int getViewTypeCount() {
//			return 3;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			View view = null;
//
//			int type = getItemViewType(position);
//
//
//			if (convertView == null) {
//
//				switch (type) {
//				case 0:
//					view = m_inflater.inflate(R.layout.memo_item_receive, null);
//					break;
//				case 1:
//					view = m_inflater.inflate(R.layout.memo_item_send, null);
//					break;
//				}
//			} else {
//				view = convertView;
//			}
//
//			ListDataMemo data = m_data_list.get(position);
//
//			if (data != null) {
//
//				if (type == 0) {
//					TextView user_tv = null, msg_tv = null, date_tv = null;
//					user_tv = (TextView) view.findViewById(R.id.tv_user);
//					msg_tv = (TextView) view.findViewById(R.id.tv_receivemsg);
//					date_tv = (TextView) view.findViewById(R.id.tv_date);
//
//					user_tv.setText(m_user_name);
//					msg_tv.setText(data.data1);
//					date_tv.setText(data.data2);
//				} else if (type == 1) {
//					TextView msg_tv = null, date_tv = null;
//					msg_tv = (TextView) view.findViewById(R.id.tv_sendmsg);
//					date_tv = (TextView) view.findViewById(R.id.tv_date);
//
//					msg_tv.setText(data.data1);
//					date_tv.setText(data.data2);
//				}
//			}
//			return view;
//		}
//	}

	// 웹에서 데이터를 가져오기 전에 먼저 네트워크 상태부터 확인
		public void conntectCheck() {
			ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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