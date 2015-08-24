package com.ohhonghong.utility;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ohhonghong.bangto.BankActivity;
import com.ohhonghong.bangto.GroupMenuActivity;
import com.ohhonghong.bangto.MainActivity;
import com.ohhonghong.bangto.MemberActivity;
import com.ohhonghong.data.ListDataBank;
import com.ohhonghong.data.ListDataGroup;
import com.ohhonghong.data.ListDataMember;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/*
 * Params - �񵿱� �۾��� �ʿ��� ������ �ڷ���
 * 			ex)������Ʈ�� ������ ��û�� ������ �Ķ���Ͱ�(ID,PW ��....)
 * 
 * Progress - �񵿱� ����� ��û�� ����ɶ� ���� ������ �ڷ���.
 * 			  ������ �ڷ����� ���� ����Ѵ�.
 * 
 * Result - �������κ��� �������� �� �����Ϳ� �˸´� �ڷ����� �����ڰ� ����
 * 			�ַ� JSON, XML���� �������� �ǹǷ� String�� ���� ����Ѵ�.
 */
public class PayBackAsyncTask extends AsyncTask<String, Integer, String> {

	BankActivity context;
	ProgressDialog dialog;

	LoadManager load;// ���Ӱ� ��û�� ����ϴ� ��ü ����

	public PayBackAsyncTask(BankActivity context) {
		this.context = context;
		load = new LoadManager("select_payback",context.group);
	}

	// ��׶��� �۾� �������� �ؾ��� �������� �� �޼��忡 �ۼ��ϸ� �Ǵµ�,
	// �� �޼���� UI�����忡 ���� �۵��ϹǷ� UI�� ������ �� �ִ�.
	// ���� �� Ÿ�ֿ̹� ����ٸ� �����ִ� �۾����� �� �� �ִ�.
	protected void onPreExecute() {
		super.onPreExecute();

		dialog = new ProgressDialog(context.getActivity());
		// dialog.setCancelable(false);
		dialog.show();
	}

	// �񵿱������� �۵��� �޼����̸�, �ַ� ���ξ�����ʹ� ������
	// ������Ʈ�� �����̳� ������ �߻��ϴ� �뵵�� ����ϸ� �ȴ�.
	// ��ǻ� �����ڰ� ���� �����忡�� run�޼���� ����ϴ�
	// 'String...' ������ �Ķ���ͷ� �Ķ���� ���� ������� ���� �� �ִ�.
	protected String doInBackground(String... params) {

		// �������� ��û�õ�
		String data = load.request();

		return data;
	}

	// ��׶��� �޼��尡 ���������� ��ĥ�� ȣ��Ǵ� �޼���.
	// UI�����忡 ���� ȣ��ǹǷ�, UI�����带 ������ �� �ִ�.
	// ���� ����ٸ� �׸� ������ �� �� �ִ�.
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		int n = 0;
		dialog.dismiss();
		//Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		System.out.println("PayBack");
		// ���������� �������κ��� �����͸� ������ ������ ������ �� �޼����̹Ƿ�,
		// ����Ͱ� ������ ArrayList�� ���Ž�������!
		ArrayList<ListDataBank> dataList = context.mAdapter.lst;
		dataList.removeAll(dataList);
		
		try {
			JSONObject o = new JSONObject(result);
			//JSONArray array = new JSONArray(result);
			JSONArray array = o.getJSONArray("PayBack");
			ListDataBank dataVo = null;
			JSONObject obj = null;
			System.out.println("1");
			for (int i = 0; i < array.length(); i++) {
				obj = array.getJSONObject(n);
				dataVo = new ListDataBank();

				//System.out.println("2obj.getString(name):" + obj.getString("email"));
				dataVo.setId(obj.getInt("id"));
				dataVo.setTvTo(obj.getString("to"));
				dataVo.setTvFrom(obj.getString("from"));
				dataVo.setTvMoney(obj.getString("howMuch"));
				
				dataList.add(dataVo);

				context.mListView.invalidateViews();
				n++;
			}
			
			context.mListView.invalidate();

			System.out.println("array.length():" + array.length());
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}