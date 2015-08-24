package com.ohhonghong.bangto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.ohhonghong.adapter.PayBookAdapter;
import com.ohhonghong.data.ListDataMoney;
import com.ohhonghong.utility.PayBookAsyncTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MoneyActivity extends Fragment {

	public PayBookAsyncTask task;
	public ListView mListView;
	public PayBookAdapter mAdapter;

	ImageButton plus_btn;
	// ImageView money_imgv;
	DatePicker money_dlg_dp;
	EditText money_dlg_edt1, money_dlg_edt2;
	RadioButton money_dlg_radio_btn_in, money_dlg_radio_btn_out;

	View moneyview;

	String year="", month="", day="", allday="";
	String valueplus="", valueminus="", valueall="", contents="";
	//String dbdate, dbvalueplus, dbvalueminus, dbvalueall, dbcontents;
	String sum="0";
	int valueallsum = 0;
	Context mContext;
	public String group;
	
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	int i = 0;



	public MoneyActivity(Context context, String group) {
		mContext = context;
		this.group = group;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.money, null);

		plus_btn = (ImageButton) view.findViewById(R.id.plus_btn);
		// money_imgv = (ImageView) view.findViewById(R.id.money_imgv);
		money_dlg_dp = (DatePicker) view.findViewById(R.id.money_dlg_dp);
		money_dlg_edt1 = (EditText) view.findViewById(R.id.money_dlg_edt1);
		money_dlg_edt2 = (EditText) view.findViewById(R.id.money_dlg_edt2);
		money_dlg_radio_btn_in = (RadioButton) view.findViewById(R.id.money_dlg_radio_btn_in);
		money_dlg_radio_btn_out = (RadioButton) view.findViewById(R.id.money_dlg_radio_btn_out);
		
		
		pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
		i = pref.getInt("i", 0);
		
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
						month = Integer.toString(money_dlg_dp.getMonth() + 1); if(month.length() == 1) month = 0 + month;
						day = Integer.toString(money_dlg_dp.getDayOfMonth()); if(day.length() == 1) day = 0 + day;
						allday = year + "" + month + "" + day;  
						contents = money_dlg_edt1.getText().toString();

						if (money_dlg_radio_btn_in.isChecked()) {
							valueplus = money_dlg_edt2.getText().toString();
							valueminus = "0";
						} else {
							valueplus = "0";
							valueminus = money_dlg_edt2.getText().toString();
						}

						int balance = 0;
						if(mListView.getCount() == 0){
							balance = Integer.parseInt(valueplus) -Integer.parseInt(valueminus) + 0;
							valueall = Integer.toString(balance);
						}else{
							/*���� �ܰ�*/
							//money_balance = (TextView) mListView.findViewById(R.id.money_balance);
							//balance = Integer.parseInt(money_balance.toString())+Integer.parseInt(valueplus) -Integer.parseInt(valueminus) + 0;
							balance = Integer.parseInt(((ListDataMoney) mListView.getItemAtPosition(i)).getMoney_balance())+Integer.parseInt(valueplus) -Integer.parseInt(valueminus) + 0;
							valueall = Integer.toString(balance);
						}

						/*
						 * AlertDialog.Builder alertDialogBuilder = new
						 * AlertDialog.Builder(getActivity()); // set the title
						 * of the Alert Dialog
						 * 
						 * alertDialogBuilder.setTitle("SAVE YOUR MONEY");
						 * 
						 * // set dialog message alertDialogBuilder.setMessage(
						 * "���� �Ͻðڽ��ϱ�?").setCancelable(false)
						 * .setPositiveButton("Yes", new
						 * DialogInterface.OnClickListener() { public void
						 * onClick(DialogInterface dialog, int id) {
						 * 
						 * 
						 * // Toast.makeText( // mContext, "����ΰ� ����Ǿ����ϴ� :)", 0)
						 * .show(); } }).setNegativeButton("No", new
						 * DialogInterface.OnClickListener() { public void
						 * onClick(DialogInterface dialog, int id) { // if no is
						 * clicked, just close // the dialog box and do nothing
						 * dialog.cancel();
						 * 
						 * } });
						 */
						Thread thread = new Thread() {
							@Override
							public void run() {
								HttpClient httpClient = new DefaultHttpClient();
								String urlString = "http://119.205.252.231:8080/BANgToServer/insert_paybook.jsp";
								String TAG = "ing";
								try {
									URI url = new URI(urlString);

									HttpPost httpPost = new HttpPost();
									httpPost.setURI(url);

									List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
									//nameValuePairs.add(new BasicNameValuePair("id", "test"));
									nameValuePairs.add(new BasicNameValuePair("groupName", group));
									nameValuePairs.add(new BasicNameValuePair("date", allday));
									nameValuePairs.add(new BasicNameValuePair("plus", valueplus));
									nameValuePairs.add(new BasicNameValuePair("minus", valueminus));
									nameValuePairs.add(new BasicNameValuePair("balance", valueall));
									nameValuePairs.add(new BasicNameValuePair("content", contents));

									httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

									HttpResponse response = httpClient.execute(httpPost);
									String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

									Log.d(TAG, responseString);
								} catch (URISyntaxException e) {
									Log.e(TAG, e.getLocalizedMessage());
									e.printStackTrace();
								} catch (ClientProtocolException e) {
									Log.e(TAG, e.getLocalizedMessage());
									e.printStackTrace();
								} catch (IOException e) {
									Log.e(TAG, e.getLocalizedMessage());
									e.printStackTrace();
								}

							}
						};

						thread.start();
						/*
						 * alertDialogBuilder.show();
						 */
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
	
	public void onStop() {
		// ���ø����̼��� ȭ�鿡�� �������
		super.onStop();
		SharedPreferences pref = mContext.getSharedPreferences("pref",mContext.MODE_PRIVATE);
		// UI ���¸� �����մϴ�.
		editor = pref.edit();
		// Editor�� �ҷ��ɴϴ�.
		
		// ������ ������ �Է��մϴ�.
		editor.putInt("i", ++i);

		editor.commit();
		// �����մϴ�.
	}

}
