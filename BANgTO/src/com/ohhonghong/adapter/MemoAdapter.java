package com.ohhonghong.adapter;

import java.util.ArrayList;

import com.ohhonghong.bangto.R;
import com.ohhonghong.data.ListDataGroup;
import com.ohhonghong.data.ListDataMember;
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
	
	public int getCount() {
		return lst.size();
	}
	
	public Object getItem(int arg0) {
		return null;
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
		
		TextView date = (TextView)view.findViewById(R.id.tv_date);
		TextView data1 = (TextView)view.findViewById(R.id.tv_user);
		TextView data2 = (TextView)view.findViewById(R.id.tv_sendmsg);
		
		ListDataMemo dataVo = (ListDataMemo)lst.get(position);
		
		date.setText(dataVo.getDate());
		data1.setText(dataVo.getData1());
		data2.setText(dataVo.getData2());
		
		return view;
	}
	
}
