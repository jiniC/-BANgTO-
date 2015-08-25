package com.ohhonghong.adapter;

import java.util.ArrayList;

import com.ohhonghong.bangto.R;
import com.ohhonghong.data.ListDataBank;
import com.ohhonghong.data.ListDataGroup;
import com.ohhonghong.data.ListDataMember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PayBackAdapter extends BaseAdapter{
	
	public ArrayList<ListDataBank> lst = new ArrayList<ListDataBank>();
	public Context context;
	public LayoutInflater infalter;
	
	public PayBackAdapter(Context context) {
		this.context = context;
		infalter = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}
	
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
			view = infalter.inflate(R.layout.bank_item, parent, false);
		}else{
			view = convertView;
		}
		
		TextView tvTo = (TextView)view.findViewById(R.id.tvTo);
		TextView tvFrom = (TextView)view.findViewById(R.id.tvFrom);
		TextView tvMoney = (TextView)view.findViewById(R.id.tvMoney);
		
		ListDataBank dataVo = (ListDataBank)lst.get(position);
		
		tvTo.setText(dataVo.getTvTo());
		tvFrom.setText(dataVo.getTvFrom());
		tvMoney.setText(dataVo.getTvMoney());
		
		return view;
	}
	
}
