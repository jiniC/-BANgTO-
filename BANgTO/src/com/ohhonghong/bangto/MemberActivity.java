package com.ohhonghong.bangto;

import com.ohhonghong.adapter.MemberAdapter;
import com.ohhonghong.utility.GroupAsyncTask;
import com.ohhonghong.utility.MemberAsyncTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MemberActivity extends Fragment {
	
	public MemberAsyncTask task;
	public ListView mListView;
	public MemberAdapter mAdapter;
	
	ImageButton add_member_btn;

	public Context mContext;

	public MemberActivity(Context context) {
		mContext = context;
	}

	@Override
	public View  onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.member, null);
		

		mListView = (ListView) view.findViewById(R.id.member_list);
		mAdapter = new MemberAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		conntectCheck();
		
		return view;

	}
	
	// ������ �����͸� �������� ���� ���� ��Ʈ��ũ ���º��� Ȯ��
	public void conntectCheck() {
		ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			// fetch data
			// Toast.makeText(this,"��Ʈ��ũ �������Դϴ�.", Toast.LENGTH_SHORT).show();

			task = new MemberAsyncTask(MemberActivity.this);
			task.execute("");

		} else {
			// display error
			Toast.makeText(getActivity(), "��Ʈ��ũ ���¸� Ȯ���Ͻʽÿ�", Toast.LENGTH_SHORT).show();
		}
	}
}
