package com.ohhonghong.bangto;

import java.util.ArrayList;

import com.ohhonghong.data.ListDataMember;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MemberActivity extends Fragment {
	ImageButton add_member_btn;
	ListView member_list;
	GroupAdapter groupadapter2;

	Context mContext;

	public MemberActivity(Context context) {
		mContext = context;
	}

	@Override
	public View  onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.member, null);
		
		member_list = (ListView) view.findViewById(R.id.member_list);

		groupadapter2 = new GroupAdapter(getActivity());
		member_list.setAdapter(groupadapter2);

		groupadapter2.addItem("������", "��������", "91353211234567");
		groupadapter2.addItem("������", "��������", "91353211234567");
		groupadapter2.addItem("������", "��������", "91353211234567");
		groupadapter2.addItem("������", "��������", "91353211234567");
		groupadapter2.addItem("������", "��������", "91353211234567");
		groupadapter2.addItem("������", "��������", "91353211234567");
		
		return view;

	}

	class ViewHolder {
		// �̸�
		public TextView member_name;
		// ����
		public TextView member_bank;
		// ���¹�ȣ
		public TextView member_account;
	}

	class GroupAdapter extends BaseAdapter {
		private Context mContext = null;
		private ArrayList<ListDataMember> mListData2 = new ArrayList<ListDataMember>();

		public GroupAdapter(Context mContext) {
			super();
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			return mListData2.size();
		}

		@Override
		public Object getItem(int position) {
			return mListData2.get(position);
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
				convertView = inflater.inflate(R.layout.member_item, null);

				holder.member_name = (TextView) convertView.findViewById(R.id.member_name);
				holder.member_bank = (TextView) convertView.findViewById(R.id.member_bank);
				holder.member_account = (TextView) convertView.findViewById(R.id.member_account);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ListDataMember mData = mListData2.get(position);

			holder.member_name.setText(mData.member_name);
			holder.member_bank.setText(mData.member_bank);
			holder.member_account.setText(mData.member_account);

			return convertView;
		}

		public void addItem(String tvGroupname, String tvGroupbank, String tvGroupaccount) {
			ListDataMember addInfo = null;
			addInfo = new ListDataMember();

			addInfo.member_name = tvGroupname;
			addInfo.member_bank = tvGroupbank;
			addInfo.member_account = tvGroupaccount;

			mListData2.add(addInfo);
		}

		public void remove(int position) {
			mListData2.remove(position);
			dataChange();
		}

		public void dataChange() {
			groupadapter2.notifyDataSetChanged();
		}

	}
}
