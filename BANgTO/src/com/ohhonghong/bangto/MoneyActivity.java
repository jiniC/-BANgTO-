package com.ohhonghong.bangto;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MoneyActivity extends Activity {
	ListView money_list;
	ImageButton plus_btn;
	ImageView money_imgv;
	DatePicker money_dlg_dp;
	EditText money_dlg_edt1, money_dlg_edt2;
	RadioButton money_dlg_radio_btn_in, money_dlg_radio_btn_out;

	GroupAdapter groupadapter;
	View moneyview;
	
	String year, month, day, allday;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.money);
		money_list = (ListView) findViewById(R.id.money_list);
		plus_btn = (ImageButton) findViewById(R.id.plus_btn);
		money_imgv = (ImageView) findViewById(R.id.money_imgv);
		money_dlg_dp = (DatePicker) findViewById(R.id.money_dlg_dp);
		money_dlg_edt1 = (EditText) findViewById(R.id.money_dlg_edt1);
		money_dlg_edt2 = (EditText) findViewById(R.id.money_dlg_edt2);
		money_dlg_radio_btn_in = (RadioButton) findViewById(R.id.money_dlg_radio_btn_in);
		money_dlg_radio_btn_out = (RadioButton) findViewById(R.id.money_dlg_radio_btn_out);

		groupadapter=new GroupAdapter(this);
		money_list.setAdapter(groupadapter);
		
		groupadapter.addItem("2015/12/11", "10000", "1000000", "100000", "서진이가 빌림");
		groupadapter.addItem("2015/7/1", "20000", "0", "30000", "서진이가 빌림");
		groupadapter.addItem("2015/7/1", "10000", "0", "40000", "서진이가 빌림");
		groupadapter.addItem("2015/7/1", "10000", "0", "10000", "서진이가 빌림");
		groupadapter.addItem("2015/7/1", "10000", "0", "10000", "서진이가 빌림");
		groupadapter.addItem("2015/7/1", "10000", "0", "10000", "서진이가 빌림");
		
		
		plus_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				moneyview = (View) View.inflate(MoneyActivity.this, R.layout.money_add_dialog, null);
				AlertDialog.Builder dlg = new AlertDialog.Builder(MoneyActivity.this);
				dlg.setView(moneyview);

				dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						money_dlg_dp = (DatePicker) moneyview.findViewById(R.id.money_dlg_dp);
						money_dlg_edt1 = (EditText) moneyview.findViewById(R.id.money_dlg_edt1);
						money_dlg_radio_btn_in = (RadioButton) moneyview.findViewById(R.id.money_dlg_radio_btn_in);
						money_dlg_radio_btn_out = (RadioButton) moneyview.findViewById(R.id.money_dlg_radio_btn_out);
						money_dlg_edt2 = (EditText) moneyview.findViewById(R.id.money_dlg_edt2);

						//year = Integer.toString(money_dlg_dp.getYear());
						month = Integer.toString(money_dlg_dp.getMonth() + 1);
						day = Integer.toString(money_dlg_dp.getDayOfMonth());
						allday=month+"/"+day;
						// etName = dlgET1.getText().toString();
						// tvName.setText(etName);

					}
				});

				dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						Toast tMsg=Toast.makeText(MoneyActivity.this, "취소 :)", Toast.LENGTH_LONG);
						tMsg.show();
					}
				});
				dlg.show();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class ViewHolder {
		// 날짜
		public TextView money_data;
		// 내용
		public TextView money_content;
		// +
		public TextView money_plus;
		// -
		public TextView money_minus;
		// 잔액
		public TextView money_balance;
	   }

	   class GroupAdapter extends BaseAdapter {
	      private Context mContext = null;
	      private ArrayList<ListDataMoney> mListData = new ArrayList<ListDataMoney>();

	      public GroupAdapter(Context mContext) {
	         super();
	         this.mContext = mContext;
	      }

	      @Override
	      public int getCount() {
	         return mListData.size();
	      }

	      @Override
	      public Object getItem(int position) {
	         return mListData.get(position);
	      }

	      @Override
	      public long getItemId(int position) {
	         return position;
	      }

	      @Override
	      public View getView(int position, View convertView, ViewGroup parent) {
	         ViewHolder holder;
	         if (convertView == null) {
	            holder = new ViewHolder();

	            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(R.layout.money_item, null);

	            holder.money_data = (TextView) convertView.findViewById(R.id.money_data);
	            holder.money_plus = (TextView) convertView.findViewById(R.id.money_plus);
	            holder.money_minus = (TextView) convertView.findViewById(R.id.money_minus);
	            holder.money_balance = (TextView) convertView.findViewById(R.id.money_balance);
	            holder.money_content = (TextView) convertView.findViewById(R.id.money_content);

	            convertView.setTag(holder);
	         } else {
	            holder = (ViewHolder) convertView.getTag();
	         }

	         ListDataMoney mData = mListData.get(position);

	         holder.money_data.setText(mData.money_data);
	         holder.money_plus.setText(mData.money_plus);
	         holder.money_minus.setText(mData.money_minus);
	         holder.money_balance.setText(mData.money_balance);
	         holder.money_content.setText(mData.money_content);

	         return convertView;
	      }

	      public void addItem(String tvGroupdata,String tvGroupplus,String tvGroupminus,String tvGroupbalance,String tvGroupcontext) {
	    	  ListDataMoney addInfo = null;
	         addInfo = new ListDataMoney();
	         
	         addInfo.money_data = tvGroupdata;
	         addInfo.money_plus = tvGroupplus;
	         addInfo.money_minus = tvGroupminus;
	         addInfo.money_balance = tvGroupbalance;
	         addInfo.money_content = tvGroupcontext;
	         
	         mListData.add(addInfo);
	      }

	      public void remove(int position) {
	         mListData.remove(position);
	         dataChange();
	      }

	      public void dataChange() {
	    	  groupadapter.notifyDataSetChanged();
	      }

	   }
}
