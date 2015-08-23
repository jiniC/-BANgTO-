package com.ohhonghong.utility;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ohhonghong.bangto.GroupMenuActivity;
import com.ohhonghong.bangto.MainActivity;
import com.ohhonghong.data.ListDataGroup;

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
public class MyAsyncTask extends AsyncTask<String, Integer, String> {

	GroupMenuActivity context;
	ProgressDialog dialog;

	LoadManager load;// ���Ӱ� ��û�� ����ϴ� ��ü ����

	public MyAsyncTask(Context context) {
		this.context = (GroupMenuActivity) context;

		load = new LoadManager();
		

	}

	// ��׶��� �۾� �������� �ؾ��� �������� �� �޼��忡 �ۼ��ϸ� �Ǵµ�,
	// �� �޼���� UI�����忡 ���� �۵��ϹǷ� UI�� ������ �� �ִ�.
	// ���� �� Ÿ�ֿ̹� ����ٸ� �����ִ� �۾����� �� �� �ִ�.
	protected void onPreExecute() {
		super.onPreExecute();

		dialog = new ProgressDialog(context);
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
		System.out.println("Group");
		// ���������� �������κ��� �����͸� ������ ������ ������ �� �޼����̹Ƿ�,
		// ����Ͱ� ������ ArrayList�� ���Ž�������!
		ArrayList<ListDataGroup> dataList = context.mAdapter.lst;
		dataList.removeAll(dataList);
		
		try {
			JSONObject o = new JSONObject(result);
			//JSONArray array = new JSONArray(result);
			JSONArray array = o.getJSONArray("Group");
			ListDataGroup dataVo = null;
			JSONObject obj = null;
			System.out.println("1");
			for (int i = 0; i < array.length(); i++) {
				obj = array.getJSONObject(n);
				dataVo = new ListDataGroup();

				System.out.println("2obj.getString(name):" + obj.getString("groupName"));
				dataVo.setGroupName(obj.getString("groupName"));
				Log.i("hye4", "conn");
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