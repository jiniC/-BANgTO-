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
 * Params - 비동기 작업시 필요한 데이터 자료형
 * 			ex)웹사이트에 데이터 요청시 전송할 파라미터값(ID,PW 등....)
 * 
 * Progress - 비동기 방식의 요청이 진행될때 사용될 데이터 자료형.
 * 			  숫자형 자료형을 많이 사용한다.
 * 
 * Result - 웹서버로부터 가져오게 될 데이터에 알맞는 자료형을 개발자가 결정
 * 			주로 JSON, XML등을 가져오게 되므로 String을 많이 사용한다.
 */
public class MemoAsyncTask extends AsyncTask<String, Integer, String> {

	MemoActivity context;
	ProgressDialog dialog;

	LoadManager load;// 접속과 요청을 담당하는 객체 선언
	
	

	public MemoAsyncTask(MemoActivity context) {
		this.context = context;
		load = new LoadManager("select_memo",context.group);
	}

	// 백그라운드 작업 수행전에 해야할 업무등을 이 메서드에 작성하며 되는데,
	// 이 메서드는 UI쓰레드에 의해 작동하므로 UI를 제어할 수 있다.
	// 따라서 이 타이밍에 진행바를 보여주는 작업등을 할 수 있다.
	protected void onPreExecute() {
		super.onPreExecute();

		dialog = new ProgressDialog(context.getActivity());
		// dialog.setCancelable(false);
		dialog.show();
	}

	// 비동기방식으로 작동할 메서드이며, 주로 메인쓰레드와는 별도로
	// 웹사이트의 연동이나 지연이 발생하는 용도로 사용하면 된다.
	// 사실상 개발자가 정의 쓰레드에서 run메서드와 비슷하다
	// 'String...' 가변형 파라미터로 파라미터 개수 상관없이 넣을 수 있다.
	protected String doInBackground(String... params) {

		// 웹서버에 요청시도
		String data = load.request();

		return data;
	}

	// 백그라운드 메서드가 업무수행을 마칠때 호출되는 메서드.
	// UI쓰레드에 의해 호출되므로, UI쓰레드를 제어할 수 있다.
	// 따라서 진행바를 그만 나오게 할 수 있다.
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		int n = 0;
		dialog.dismiss();
		//Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		System.out.println("Memo");
		// 최종적으로 웹서버로부터 데이터를 완전히 가져온 시점은 이 메서드이므로,
		// 어댑터가 보유한 ArrayList를 갱신시켜주자!
		ArrayList<ListDataMemo> dataList = context.mAdapter.lst;
		dataList.removeAll(dataList);
	
		
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

				dataVo.setUsername(obj.getString("who"));
				dataVo.setMemo(obj.getString("memo"));
				dataVo.setDate(obj.getString("date"));
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