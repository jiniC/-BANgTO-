package com.ohhonghong.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ohhonghong.bangto.MemoActivity;
import com.ohhonghong.data.ListDataMemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;

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
public class MemoAsyncTask extends AsyncTask<String, Integer, String> {

	MemoActivity context;
	ProgressDialog dialog;

	LoadManager load;// ���Ӱ� ��û�� ����ϴ� ��ü ����
	
	private SimpleDateFormat m_date_format = null;
	private SimpleDateFormat m_time_format = null;
	

	public MemoAsyncTask(MemoActivity context) {
		this.context = context;
		load = new LoadManager("select_memo");
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
		System.out.println("Memo");
		// ���������� �������κ��� �����͸� ������ ������ ������ �� �޼����̹Ƿ�,
		// ����Ͱ� ������ ArrayList�� ���Ž�������!
		ArrayList<ListDataMemo> dataList = context.mAdapter.lst;
		dataList.removeAll(dataList);
		m_date_format = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		m_time_format = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
		;
		
		try {
			JSONObject o = new JSONObject(result);
			//JSONArray array = new JSONArray(result);
			JSONArray array = o.getJSONArray("Memo");
			ListDataMemo dataVo = null;
			JSONObject obj = null;
			System.out.println("1");
			for (int i = 0; i < array.length(); i++) {
				obj = array.getJSONObject(n);
				dataVo = new ListDataMemo();

				dataVo.setDate(m_date_format.format(new Date()));
				dataVo.setData1(obj.getString("who"));
				dataVo.setData2(obj.getString("memo"));
				
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