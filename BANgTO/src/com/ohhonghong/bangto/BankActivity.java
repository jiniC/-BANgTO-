package com.ohhonghong.bangto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.ohhonghong.adapter.MemberAdapter;
import com.ohhonghong.adapter.PayBackAdapter;
import com.ohhonghong.data.ListDataBank;
import com.ohhonghong.data.ListDataGroup;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
	public String group; // ��� groupName
	int i; // ����Ʈ�� ����
	private ArrayList<ListDataBank> mListData = new ArrayList<ListDataBank>(); // ����Ʈ�� ������
	
	public BankActivity(Context context,String group) {
		mContext = context;
		this.group = group;
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
		
		
		mListView.setOnItemLongClickListener(new ListViewItemLongClickListener());
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
						final String to = etTo.getText().toString();
						final String from = etFrom.getText().toString();
						final String money = etMoney.getText().toString();
						//mAdapter.addItem(to, from, money);

						Calendar calendar = Calendar.getInstance();
						int sec = calendar.get(Calendar.MINUTE);

						Timer timer = new Timer();
						timer.schedule(timerTask, sec + 60000);
						
						Thread thread = new Thread() {
							@Override
							public void run() {
								HttpClient httpClient = new DefaultHttpClient();
								String urlString = "http://119.205.252.231:8080/BANgToServer/insert_payback.jsp";
								String TAG = "ing";
								try {
									URI url = new URI(urlString);

									HttpPost httpPost = new HttpPost();
									httpPost.setURI(url);

									List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
									nameValuePairs.add(new BasicNameValuePair("id", "test"));
									nameValuePairs.add(new BasicNameValuePair("groupName", "test"));
									nameValuePairs.add(new BasicNameValuePair("from", from));
									nameValuePairs.add(new BasicNameValuePair("to", to));
									nameValuePairs.add(new BasicNameValuePair("howMuch", money));
									
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
	
	
	/*��Ŭ���� ����*/
	private class ListViewItemLongClickListener implements AdapterView.OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			
			AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
			

			i = ((ListDataBank) mListView.getItemAtPosition(position)).getId();
					
			Log.d(i+"", "hyunhye");
			
			// '��' ��ư�� Ŭ���Ǹ�
			alertDlg.setPositiveButton("������", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					//////////////////////////////////////////////////////////////////////////
					//////////////////////////////////////////////////////////////////////////
					Thread thread = new Thread() {
						@Override
						public void run() {
							HttpClient httpClient = new DefaultHttpClient();
							String urlString = "http://119.205.252.231:8080/BANgToServer/delete_payback.jsp";
							String TAG = "ing";
							try {
								URI url = new URI(urlString);

								HttpPost httpPost = new HttpPost();
								httpPost.setURI(url);
								
								List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
								nameValuePairs.add(new BasicNameValuePair("id", i+""));
								
								Log.d(i+"", "hyunhye2");

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

					// �Ʒ� method�� ȣ������ ���� ���, ������ item�� ȭ�鿡 ��� ��������.
					mAdapter.notifyDataSetChanged();
					dialog.dismiss(); // AlertDialog�� �ݴ´�.
				}
			});

			// '�ƴϿ�' ��ư�� Ŭ���Ǹ�
			alertDlg.setNegativeButton("�����̿�", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss(); // AlertDialog�� �ݴ´�.
				}
			});

			alertDlg.setMessage("������ ���� �����̳���?");
			alertDlg.show();
			return false;
		}

	}
}
