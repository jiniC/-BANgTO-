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
	
	// 웹에서 데이터를 가져오기 전에 먼저 네트워크 상태부터 확인
	public void conntectCheck() {
		ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			// fetch data
			// Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();

			task = new MemberAsyncTask(MemberActivity.this);
			task.execute("");

		} else {
			// display error
			Toast.makeText(getActivity(), "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
		}
	}
}
