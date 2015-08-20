package com.ohhonghong.bangto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.ohhonghong.bangto.MoneyActivity.myDBHelper;
import com.ohhonghong.data.ListDataBank;
import com.ohhonghong.notification.NotificationBuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class BankActivity extends Fragment {
	public ListView mListView = null;
	public CustomAdapter mAdapter = null;
	ImageButton ib_manage_add;
	View dlgview;
	EditText etTo, etFrom, etMoney;
	
	Context mContext;
	
	SQLiteDatabase sqlDB1,sqlDB2;
	myDBHelper myHelper;
	
	public BankActivity(Context context) {
		mContext = context;
	}
	
	@Override
	public View  onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.bank, null);
		
		mListView = (ListView) view.findViewById(R.id.listview);
		mAdapter = new CustomAdapter(mContext);
		mListView.setAdapter(mAdapter);

		mAdapter.addItem("신현혜", "최서진", "1000원");
		mAdapter.addItem("신현혜", "최서진", "2500원");
		mAdapter.addItem("신현혜", "최서진", "1000원");
		mAdapter.addItem("신현혜", "최서진", "3000원");
		mAdapter.addItem("신현혜", "최서진", "5000원");
		mAdapter.addItem("신현혜", "최서진", "21000원");
		mAdapter.addItem("신현혜", "최서진", "1000원");
		mAdapter.addItem("최서진", "신현혜", "1000원");
		mAdapter.addItem("최서진", "신현혜", "1000원");
		mAdapter.addItem("최서진", "신현혜", "2000원");
		mAdapter.addItem("최서진", "신현혜", "15000원");
		mAdapter.addItem("최서진", "신현혜", "1000원");
		mAdapter.addItem("최서진", "신현혜", "10000원");
		mAdapter.addItem("신현혜", "최서진", "1000원");
		mAdapter.addItem("신현혜", "최서진", "1000원");

		ib_manage_add = (ImageButton) view.findViewById(R.id.ib_manage_add);

		ib_manage_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dlgview = (View) View.inflate(getActivity(), R.layout.bank_add_dialog, null);
				AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());

				dlg.setView(dlgview);
				etTo = (EditText) dlgview.findViewById(R.id.etTo);
				etFrom = (EditText) dlgview.findViewById(R.id.etFrom);
				etMoney = (EditText) dlgview.findViewById(R.id.etMoney);

				// 입력된 내용을 받아드리겠다. (확인 버튼)
				dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String to = etTo.getText().toString();
						String from = etFrom.getText().toString();
						String money = etMoney.getText().toString();
						mAdapter.addItem(to, from, money);
						
			 			   
						 Calendar calendar = Calendar.getInstance();
						 int sec = calendar.get(Calendar.MINUTE);
						  
						 Timer timer = new Timer();
					     timer.schedule(timerTask,sec+60000);  
					}
				});

				// 취소버튼 눌렀을 때
				dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dlg.show();
			}
		});
		return view;
	}

	class ViewHolder {
		public TextView mTo;

		public TextView mFrom;

		public TextView mMoney;
	}

	class CustomAdapter extends BaseAdapter {
		private Context mContext = null;
		private ArrayList<ListDataBank> mListData = new ArrayList<ListDataBank>();

		public CustomAdapter(Context mContext) {
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

		public void addItem(String tvTo, String tvFrom, String tvMoney) {
			ListDataBank addInfo = null;
			addInfo = new ListDataBank();
			addInfo.tvTo = tvTo;
			addInfo.tvFrom = tvFrom;
			addInfo.tvMoney = tvMoney;

			mListData.add(addInfo);
		}

		public void remove(int position) {
			mListData.remove(position);
			dataChange();
		}

		/*
		 * public void sort() { Collections.sort(mListData,
		 * ListDataManage.ALPHA_COMPARATOR); dataChange(); }
		 */
		public void dataChange() {
			mAdapter.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();

				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.bank_item, null);

				holder.mTo = (TextView) convertView.findViewById(R.id.tvTo);
				holder.mFrom = (TextView) convertView.findViewById(R.id.tvFrom);
				holder.mMoney = (TextView) convertView.findViewById(R.id.tvMoney);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ListDataBank mData = mListData.get(position);

			holder.mTo.setText(mData.tvTo);
			holder.mFrom.setText(mData.tvFrom);
			holder.mMoney.setText(mData.tvMoney);

			return convertView;
		}

	}
	
	TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
			
			Intent i = new Intent(getActivity(), NotificationBuilder.class);
			startActivity(i);
		}        
	};	
}
