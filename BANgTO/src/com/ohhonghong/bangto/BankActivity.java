package com.ohhonghong.bangto;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class BankActivity extends Activity {
	public ListView mListView = null;
	public CustomAdapter mAdapter = null;
	ImageButton ib_manage_add;
	View dlgview;
	EditText etTo, etFrom, etMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bank);

		mListView = (ListView) findViewById(R.id.listview);
		mAdapter = new CustomAdapter(this);
		mListView.setAdapter(mAdapter);

		mAdapter.addItem("������", "�ּ���", "1000��");
		mAdapter.addItem("������", "�ּ���", "2500��");
		mAdapter.addItem("������", "�ּ���", "1000��");
		mAdapter.addItem("������", "�ּ���", "3000��");
		mAdapter.addItem("������", "�ּ���", "5000��");
		mAdapter.addItem("������", "�ּ���", "21000��");
		mAdapter.addItem("������", "�ּ���", "1000��");
		mAdapter.addItem("�ּ���", "������", "1000��");
		mAdapter.addItem("�ּ���", "������", "1000��");
		mAdapter.addItem("�ּ���", "������", "2000��");
		mAdapter.addItem("�ּ���", "������", "15000��");
		mAdapter.addItem("�ּ���", "������", "1000��");
		mAdapter.addItem("�ּ���", "������", "10000��");
		mAdapter.addItem("������", "�ּ���", "1000��");
		mAdapter.addItem("������", "�ּ���", "1000��");

		ib_manage_add = (ImageButton) findViewById(R.id.ib_manage_add);

		ib_manage_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dlgview = (View) View.inflate(BankActivity.this, R.layout.bank_add_dialog, null);
				AlertDialog.Builder dlg = new AlertDialog.Builder(BankActivity.this);

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
						mAdapter.addItem(to, from, money);
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
}
