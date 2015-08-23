package com.ohhonghong.bangto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.ohhonghong.adapter.MemberAdapter;
import com.ohhonghong.adapter.PayBackAdapter;
import com.ohhonghong.data.ListDataBank;
import com.ohhonghong.notification.NotificationBuilder;
import com.ohhonghong.utility.MemberAsyncTask;
import com.ohhonghong.utility.PayBackAsyncTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

public class BankActivity extends Fragment {

	public PayBackAsyncTask task;
	public ListView mListView;
	public PayBackAdapter mAdapter;

	ImageButton ib_manage_add;
	View dlgview;
	EditText etTo, etFrom, etMoney;

	Context mContext;

	public BankActivity(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.bank, null);

		mListView = (ListView) view.findViewById(R.id.listview);
		mAdapter = new PayBackAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		conntectCheck();

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

				// �Էµ� ������ �޾Ƶ帮�ڴ�. (Ȯ�� ��ư)
				dlg.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String to = etTo.getText().toString();
						String from = etFrom.getText().toString();
						String money = etMoney.getText().toString();
						//mAdapter.addItem(to, from, money);

						Calendar calendar = Calendar.getInstance();
						int sec = calendar.get(Calendar.MINUTE);

						Timer timer = new Timer();
						timer.schedule(timerTask, sec + 60000);
					}
				});

				// ��ҹ�ư ������ ��
				dlg.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
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

			task = new PayBackAsyncTask(BankActivity.this);
			task.execute("");

		} else {
			// display error
			Toast.makeText(getActivity(), "��Ʈ��ũ ���¸� Ȯ���Ͻʽÿ�", Toast.LENGTH_SHORT).show();
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
