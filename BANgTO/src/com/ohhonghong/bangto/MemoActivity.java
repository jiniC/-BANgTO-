package com.ohhonghong.bangto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

public class MemoActivity extends Activity {
	
	private MemoAdapter m_adapter = null;
	private ListView m_list = null;

	private String m_user_name = "ÃÖ¼­Áø";

	private SimpleDateFormat m_date_format = null;
	private SimpleDateFormat m_time_format = null;

	ImageButton sendbutton;
	TextView message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memo);

		ArrayList<ListDataMemo> data_list = new ArrayList<ListDataMemo>();

		m_adapter = new MemoAdapter(data_list);

		m_list = (ListView) findViewById(R.id.var_list);
		m_list.setAdapter(m_adapter);

		m_date_format = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		m_time_format = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);

		sendbutton = (ImageButton) findViewById(R.id.sendbutton);
		message = (TextView) findViewById(R.id.message);

		sendbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String sendmessage = message.getText().toString();
				message.setText("");
				ListDataMemo data = null;
				data = new ListDataMemo((byte) 1, sendmessage, m_time_format.format(new Date()));

				m_adapter.add(data);
				
				m_list.smoothScrollToPosition(m_adapter.getCount() - 1);
			}
		});
		
		
		m_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// 
				
				return false;
			}
		});
	}

	private class MemoAdapter extends BaseAdapter {
		private LayoutInflater m_inflater = null;

		private ArrayList<ListDataMemo> m_data_list;

		public MemoAdapter(ArrayList<ListDataMemo> items) {
			m_data_list = items;

			m_inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}


		public void add(ListDataMemo parm_data) {
			m_data_list.add(parm_data);

			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return m_data_list.size();
		}

		@Override
		public ListDataMemo getItem(int position) {
			return m_data_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return m_data_list.get(position).type;
		}

		@Override
		public int getViewTypeCount() {
			return 3;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			int type = getItemViewType(position);


			if (convertView == null) {

				switch (type) {
				case 0:
					view = m_inflater.inflate(R.layout.memo_item_receive, null);
					break;
				case 1:
					view = m_inflater.inflate(R.layout.memo_item_send, null);
					break;
				}
			} else {
				view = convertView;
			}

			ListDataMemo data = m_data_list.get(position);

			if (data != null) {

				if (type == 0) {
					TextView user_tv = null, msg_tv = null, date_tv = null;
					user_tv = (TextView) view.findViewById(R.id.tv_user);
					msg_tv = (TextView) view.findViewById(R.id.tv_receivemsg);
					date_tv = (TextView) view.findViewById(R.id.tv_date);

					user_tv.setText(m_user_name);
					msg_tv.setText(data.data1);
					date_tv.setText(data.data2);
				} else if (type == 1) {
					TextView msg_tv = null, date_tv = null;
					msg_tv = (TextView) view.findViewById(R.id.tv_sendmsg);
					date_tv = (TextView) view.findViewById(R.id.tv_date);

					msg_tv.setText(data.data1);
					date_tv.setText(data.data2);
				}
			}
			return view;
		}
	}

}