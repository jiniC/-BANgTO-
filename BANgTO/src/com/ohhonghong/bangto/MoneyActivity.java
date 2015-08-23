package com.ohhonghong.bangto;

import java.util.ArrayList;

import com.ohhonghong.adapter.PayBackAdapter;
import com.ohhonghong.adapter.PayBookAdapter;
import com.ohhonghong.data.ListDataMoney;
import com.ohhonghong.utility.PayBackAsyncTask;
import com.ohhonghong.utility.PayBookAsyncTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
	
	
	public PayBookAsyncTask task;
	public ListView mListView;
	public PayBookAdapter mAdapter;
	
	ImageButton plus_btn;
	ImageView money_imgv;
	DatePicker money_dlg_dp;
	EditText money_dlg_edt1, money_dlg_edt2;
	RadioButton money_dlg_radio_btn_in, money_dlg_radio_btn_out;

	View moneyview;


	String year, month, day, allday;
	String valueplus, valueminus, valueall, contents;
	String dbdate,dbvalueplus, dbvalueminus,dbvalueall, dbcontents;
	String sum;
	int valueallsum = 0;
	Context mContext;
	public String group;

	public MoneyActivity(Context context,String group) {
		mContext = context;
		this.group = group;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.money, null);

		plus_btn = (ImageButton) view.findViewById(R.id.plus_btn);
		money_imgv = (ImageView) view.findViewById(R.id.money_imgv);
		money_dlg_dp = (DatePicker) view.findViewById(R.id.money_dlg_dp);
		money_dlg_edt1 = (EditText) view.findViewById(R.id.money_dlg_edt1);
		money_dlg_edt2 = (EditText) view.findViewById(R.id.money_dlg_edt2);
		money_dlg_radio_btn_in = (RadioButton) view.findViewById(R.id.money_dlg_radio_btn_in);
		money_dlg_radio_btn_out = (RadioButton) view.findViewById(R.id.money_dlg_radio_btn_out);

		mListView = (ListView) view.findViewById(R.id.money_list);
		mAdapter = new PayBookAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		conntectCheck();
		
		plus_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				moneyview = (View) View.inflate(getActivity(), R.layout.money_add_dialog, null);
				AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
				dlg.setView(moneyview);

				dlg.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {

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
						
						
						
						int a =  Integer.parseInt(sum);
						int b = Integer.parseInt(valueplus);
						int c =  Integer.parseInt(valueminus);
						valueallsum =  a+b - c + 0;
						valueall = Integer.toString(valueallsum);
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
						// set the title of the Alert Dialog

						alertDialogBuilder.setTitle("SAVE YOUR MONEY");

						// set dialog message
						alertDialogBuilder.setMessage("���� �Ͻðڽ��ϱ�?").setCancelable(false)
								.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								
								// Toast.makeText(
								// mContext, "����ΰ� ����Ǿ����ϴ� :)", 0) .show();
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

				dlg.setNegativeButton("���", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						Toast tMsg = Toast.makeText(getActivity(), "��� :)", Toast.LENGTH_LONG);
						tMsg.show();
					}
				});
				dlg.show();
			}

		});
		return view;
	}

	// ������ �����͸� �������� ���� ���� ��Ʈ��ũ ���º��� Ȯ��
		public void conntectCheck() {
			ConnectivityManager connMgr = (ConnectivityManager) getActivity()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

			if (networkInfo != null && networkInfo.isConnected()) {
				// fetch data
				// Toast.makeText(this,"��Ʈ��ũ �������Դϴ�.", Toast.LENGTH_SHORT).show();

				task = new PayBookAsyncTask(MoneyActivity.this);
				task.execute("");

			} else {
				// display error
				Toast.makeText(getActivity(), "��Ʈ��ũ ���¸� Ȯ���Ͻʽÿ�", Toast.LENGTH_SHORT).show();
			}
		}
		
	

	// ���� ��ư�� �������� ��
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
