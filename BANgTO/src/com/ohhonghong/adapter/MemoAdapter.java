package com.ohhonghong.adapter;

import java.util.ArrayList;

import com.ohhonghong.bangto.R;
import com.ohhonghong.data.ListDataMemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MemoAdapter extends BaseAdapter{
	
	public ArrayList<ListDataMemo> lst = new ArrayList<ListDataMemo>();
	public Context context;
	public LayoutInflater infalter;
	

	
	public MemoAdapter(Context context) {
		this.context = context;
		infalter = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	/*public void add(ListDataMemo parm_data) {
		lst.add(parm_data);
		notifyDataSetChanged();
	}
	*/
	public int getCount() {
		return lst.size();
	}
	
	public Object getItem(int i) {
		return lst.get(i);
	}
	
	public long getItemId(int arg0) {
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView==null){
			view = infalter.inflate(R.layout.memo_item_send, parent, false);
		}else{
			view = convertView;
		}
		
		TextView username = (TextView)view.findViewById(R.id.tv_user);
		TextView memo = (TextView)view.findViewById(R.id.tv_sendmsg);
		TextView date = (TextView)view.findViewById(R.id.tv_date);
		ListDataMemo dataVo = (ListDataMemo)lst.get(position);
		
		username.setText(dataVo.getUsername());
		memo.setText(dataVo.getMemo());
		date.setText(dataVo.getDate());
		
		return view;
	}
	
}







//private class MemoAdapter extends BaseAdapter {
//	private LayoutInflater m_inflater = null;
//
//	private ArrayList<ListDataMemo> m_data_list;
//
//	public MemoAdapter(ArrayList<ListDataMemo> items) {
//		m_data_list = items;
//
//		m_inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//
//	public void add(ListDataMemo parm_data) {
//		m_data_list.add(parm_data);
//
//		notifyDataSetChanged();
//	}
//
//	@Override
//	public int getCount() {
//		return m_data_list.size();
//	}
//
//	@Override
//	public ListDataMemo getItem(int position) {
//		return m_data_list.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public int getItemViewType(int position) {
//		return m_data_list.get(position).type;
//	}
//
//	@Override
//	public int getViewTypeCount() {
//		return 3;
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View view = null;
//
//		int type = getItemViewType(position);
//
//
//		if (convertView == null) {
//
//			switch (type) {
//			case 0:
//				view = m_inflater.inflate(R.layout.memo_item_receive, null);
//				break;
//			case 1:
//				view = m_inflater.inflate(R.layout.memo_item_send, null);
//				break;
//			}
//		} else {
//			view = convertView;
//		}
//
//		ListDataMemo data = m_data_list.get(position);
//
//		if (data != null) {
//
//			if (type == 0) {
//				TextView user_tv = null, msg_tv = null, date_tv = null;
//				user_tv = (TextView) view.findViewById(R.id.tv_user);
//				msg_tv = (TextView) view.findViewById(R.id.tv_receivemsg);
//				date_tv = (TextView) view.findViewById(R.id.tv_date);
//
//				user_tv.setText(m_user_name);
//				msg_tv.setText(data.data1);
//				date_tv.setText(data.data2);
//			} else if (type == 1) {
//				TextView msg_tv = null, date_tv = null;
//				msg_tv = (TextView) view.findViewById(R.id.tv_sendmsg);
//				date_tv = (TextView) view.findViewById(R.id.tv_date);
//
//				msg_tv.setText(data.data1);
//				date_tv.setText(data.data2);
//			}
//		}
//		return view;
//	}
//}
