package com.ohhonghong.adapter;

import java.util.ArrayList;

import com.ohhonghong.bangto.R;
import com.ohhonghong.data.ListDataGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroupAdapter extends BaseAdapter{
	
	public ArrayList<ListDataGroup> lst = new ArrayList<ListDataGroup>();
	public Context context;
	public LayoutInflater infalter;
	
	public GroupAdapter(Context context) {
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
			view = infalter.inflate(R.layout.groupmenu_item, parent, false);
		}else{
			view = convertView;
		}
		
		TextView GroupName = (TextView)view.findViewById(R.id.tvGroupName);
		
		ListDataGroup dataVo = (ListDataGroup)lst.get(position);
		
		GroupName.setText(dataVo.getGroupName());
		
		return view;
	}

	
}