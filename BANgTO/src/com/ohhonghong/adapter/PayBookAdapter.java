package com.ohhonghong.adapter;

import java.util.ArrayList;

import com.ohhonghong.bangto.R;
import com.ohhonghong.data.ListDataBank;
import com.ohhonghong.data.ListDataGroup;
import com.ohhonghong.data.ListDataMember;
import com.ohhonghong.data.ListDataMoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PayBookAdapter extends BaseAdapter{
	
	public ArrayList<ListDataMoney> lst = new ArrayList<ListDataMoney>();
	public Context context;
	public LayoutInflater infalter;
	
	public PayBookAdapter(Context context) {
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
			view = infalter.inflate(R.layout.money_item, parent, false);
		}else{
			view = convertView;
		}
		
		TextView money_data = (TextView)view.findViewById(R.id.money_data);
		TextView money_plus = (TextView)view.findViewById(R.id.money_plus);
		TextView money_minus = (TextView)view.findViewById(R.id.money_minus);
		TextView money_balance = (TextView)view.findViewById(R.id.money_balance);
		TextView money_content = (TextView)view.findViewById(R.id.money_content);

		
		ListDataMoney dataVo = (ListDataMoney)lst.get(position);
		
		money_data.setText(dataVo.getMoney_data());
		money_plus.setText(dataVo.getMoney_plus());
		money_minus.setText(dataVo.getMoney_minus());
		money_balance.setText(dataVo.getMoney_balance());
		money_content.setText(dataVo.getMoney_content());
		
		return view;
	}
	
}
