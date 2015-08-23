package com.ohhonghong.adapter;

import java.util.ArrayList;

import com.ohhonghong.bangto.R;
import com.ohhonghong.data.ListDataGroup;
import com.ohhonghong.data.ListDataMember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MemberAdapter extends BaseAdapter{
	
	public ArrayList<ListDataMember> lst = new ArrayList<ListDataMember>();
	public Context context;
	public LayoutInflater infalter;
	
	public MemberAdapter(Context context) {
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
			view = infalter.inflate(R.layout.member_item, parent, false);
		}else{
			view = convertView;
		}
		
		TextView member_name = (TextView)view.findViewById(R.id.member_name);
		TextView member_bank = (TextView)view.findViewById(R.id.member_bank);
		TextView member_account = (TextView)view.findViewById(R.id.member_account);
		
		ListDataMember dataVo = (ListDataMember)lst.get(position);
		
		member_name.setText(dataVo.getMember_name());
		member_bank.setText(dataVo.getMember_bank());
		member_account.setText(dataVo.getMember_account());
		
		return view;
	}
	
}
