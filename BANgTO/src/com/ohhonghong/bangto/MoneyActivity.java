package com.ohhonghong.bangto;

import java.util.ArrayList;

import com.ohhonghong.data.ListDataMoney;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MoneyActivity extends Fragment {
	ListView money_list;
	ImageButton plus_btn;
	ImageView money_imgv;
	DatePicker money_dlg_dp;
	EditText money_dlg_edt1, money_dlg_edt2;
	RadioButton money_dlg_radio_btn_in, money_dlg_radio_btn_out;

	GroupAdapter groupadapter;
	View moneyview;

	SQLiteDatabase sqlDB1,sqlDB2;
	myDBHelper myHelper; 

	String year, month, day, allday;
	String valueplus, valueminus, valueall, contents;
	String dbdate,dbvalueplus, dbvalueminus,dbvalueall, dbcontents;
	String sum;
	int valueallsum = 0;
	Context mContext;

	public MoneyActivity(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.money, null);

		money_list = (ListView) view.findViewById(R.id.money_list);
		plus_btn = (ImageButton) view.findViewById(R.id.plus_btn);
		money_imgv = (ImageView) view.findViewById(R.id.money_imgv);
		money_dlg_dp = (DatePicker) view.findViewById(R.id.money_dlg_dp);
		money_dlg_edt1 = (EditText) view.findViewById(R.id.money_dlg_edt1);
		money_dlg_edt2 = (EditText) view.findViewById(R.id.money_dlg_edt2);
		money_dlg_radio_btn_in = (RadioButton) view.findViewById(R.id.money_dlg_radio_btn_in);
		money_dlg_radio_btn_out = (RadioButton) view.findViewById(R.id.money_dlg_radio_btn_out);

		myHelper = new myDBHelper(getActivity());
		groupadapter = new GroupAdapter(getActivity());
		money_list.setAdapter(groupadapter);
		
		sqlDB1 = myHelper.getReadableDatabase();
		Cursor cursor; // db의 결과를 받을 수 있는 클래스(cursor)
		cursor = sqlDB1.rawQuery("select * from moneyTBL;", null);
		while(cursor.moveToNext()){
			if(cursor.isFirst()){ continue;}
			dbdate = cursor.getString(0); 
			dbvalueplus = cursor.getString(1); 
			dbvalueminus = cursor.getString(2);
			dbvalueall =  cursor.getString(3);
			dbcontents = cursor.getString(4); 
			
			groupadapter.addItem(dbdate, dbvalueplus, dbvalueminus, dbvalueall, dbcontents);
			
		}
		cursor.close();
		sqlDB1.close();
		
		plus_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				moneyview = (View) View.inflate(getActivity(), R.layout.money_add_dialog, null);
				AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
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

						year = Integer.toString(money_dlg_dp.getYear());
						month = Integer.toString(money_dlg_dp.getMonth() + 1);
						day = Integer.toString(money_dlg_dp.getDayOfMonth());
						allday = year + "/" + month + "/" + day;
						contents = money_dlg_edt1.getText().toString();

						if (money_dlg_radio_btn_in.isChecked()) {
							valueplus = money_dlg_edt2.getText().toString();
							valueminus = "0";
						} else {
							valueplus = "0";
							valueminus = money_dlg_edt2.getText().toString();
						}
						
						sqlDB2 = myHelper.getReadableDatabase();
						Cursor cursor; // db의 결과를 받을 수 있는 클래스(cursor)
						cursor = sqlDB2.rawQuery("select * from moneyTBL;", null);
						while(cursor.moveToNext()){
							sum =  cursor.getString(3).toString();
						}
						cursor.close();
						sqlDB2.close();
						
						int a =  Integer.parseInt(sum);
						int b = Integer.parseInt(valueplus);
						int c =  Integer.parseInt(valueminus);
						valueallsum =  a+b - c + 0;
						valueall = Integer.toString(valueallsum);
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
						// set the title of the Alert Dialog

						alertDialogBuilder.setTitle("SAVE YOUR MONEY");

						// set dialog message
						alertDialogBuilder.setMessage("저장 하시겠습니까?").setCancelable(false)
								.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								sqlDB1 = myHelper.getWritableDatabase();

								sqlDB1.execSQL("insert into moneyTBL VALUES ('" + allday + "', '" + valueplus + "', '"
										+ valueminus + "', '" + valueall + "', '" + contents + "');");
								groupadapter.addItem(allday, valueplus, valueminus, valueall, contents);
								sqlDB1.close();
								// Toast.makeText(
								// mContext, "가계부가 저장되었습니다 :)", 0) .show();
							}
						}).setNegativeButton("No", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if no is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();

							}
						}); //

						alertDialogBuilder.show();

					}
				});

				dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						Toast tMsg = Toast.makeText(getActivity(), "취소 :)", Toast.LENGTH_LONG);
						tMsg.show();
					}
				});
				dlg.show();
			}

		});
		return view;
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

		public void addItem(String tvGroupdata, String tvGroupplus, String tvGroupminus, String tvGroupbalance,
				String tvGroupcontext) {
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

	public class myDBHelper extends SQLiteOpenHelper {
		public myDBHelper(Context moneyActivity) {
			super(moneyActivity, "MoneyDB", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("create table moneyTBL (date CHAR(50), plusmoney CHAR(50), minusmoney CHAR(50), allmoney CHAR(70),contents CHAR(100));");
			db.execSQL("insert into moneyTBL VALUES ('0000/0/0','0','0','0','-')");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("drop table if exists moneyTBL;"); // 이미 그룹테이블이 있으면 지운다.
			onCreate(db); // 새로운 테이블을 만듦
		}
	}

	// 라디오 버튼을 선택했을 때
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub

		switch (arg1) {

		case R.id.money_dlg_radio_btn_in:
			valueplus = "0";
			valueminus = money_dlg_edt1.getText().toString();
			valueall = "10000";
			break;

		case R.id.money_dlg_radio_btn_out:
			valueminus = "0";
			valueplus = money_dlg_edt1.getText().toString();
			valueall = "10000";
			break;
		}
	}
}
