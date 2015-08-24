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

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.ohhonghong.adapter.GroupAdapter;
import com.ohhonghong.data.ListDataGroup;
import com.ohhonghong.invite.DeepLinkActivity;
import com.ohhonghong.utility.GroupAsyncTask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupMenuActivity extends Activity {

	public GroupAsyncTask task;
	public ListView mListView;
	public GroupAdapter mAdapter;

	// private ListView mListView = null;
	// private GroupAdapter mAdapter = null;
	View dlgview;
	ImageButton groupAddButton;
	EditText etGroupName, etMemberName;
	TextView tvGroup;
	
	private ArrayList<ListDataGroup> mListData = new ArrayList<ListDataGroup>();
	
	Typeface childFont;

	private static final int REQUEST_INVITE = 0;
	// Local Broadcast receiver for receiving invites
	private BroadcastReceiver mDeepLinkReceiver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupmenu);

	    NotificationManager nm = 
		    	(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		    
		    // Cancel Notification
		    nm.cancel(1234);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();

		groupAddButton = (ImageButton) findViewById(R.id.groupAddButton);

		mListView = (ListView) findViewById(R.id.listview);
		mAdapter = new GroupAdapter(this);
		/*
		mAdapter.notifyDataSetInvalidated();
		mAdapter.notifyDataSetChanged() ;*/
		mListView.setAdapter(mAdapter);
		conntectCheck();

		if (savedInstanceState == null) {
			// No savedInstanceState, so it is the first launch of this activity
			Intent intent = getIntent();
			if (AppInviteReferral.hasReferral(intent)) {
				// In this case the referral data is in the intent launching the
				// MainActivity,
				// which means this user already had the app installed. We do
				// not have to
				// register the Broadcast Receiver to listen for Play Store
				// Install information
				launchDeepLinkActivity(intent);
			}
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// 두가지 방법 모두 사용가능하다.
				// Data data = (Data) parent.getItemAtPosition(position);
				// Data data = mList.get(position);

				// 다음 액티비티로 넘길 Bundle 데이터를 만든다.
				// Bundle extras = new Bundle();
				// extras.putString("title", data.getTitle());
				// extras.putString("description", data.getDescription());
				// extras.putInt("color", data.getColor());
				//String groupName = mListData.get(position).groupName;
				//Log.i( mListData.get(position).groupName, "hyunhye");
				
				//ListData m = data.get(position);
				TextView GroupName = (TextView)view.findViewById(R.id.tvGroupName);
				
				Intent intent = new Intent(getApplicationContext(), TabMainActivity.class);
				intent.putExtra("group",GroupName.getText());
				
				// 위에서 만든 Bundle을 인텐트에 넣는다.
				// intent.putExtras(extras);
				// 액티비티를 생성한다.
				startActivity(intent);
			}
		});

		mListView.setOnItemLongClickListener(new ListViewItemLongClickListener());
		groupAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				dlgview = (View) View.inflate(GroupMenuActivity.this, R.layout.groupmenu_add_dialog, null);
				AlertDialog.Builder dlg = new AlertDialog.Builder(GroupMenuActivity.this);

				dlg.setView(dlgview);
				etGroupName = (EditText) dlgview.findViewById(R.id.etGroupName);
				etMemberName = (EditText) dlgview.findViewById(R.id.etMemberName);
				etMemberName.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						onInviteClicked();
					}
				});

				// 입력된 내용을 받아드리겠다. (확인 버튼)
				dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						final String groupName = etGroupName.getText().toString();

						Thread thread = new Thread() {
							@Override
							public void run() {
								HttpClient httpClient = new DefaultHttpClient();
								String urlString = "http://119.205.252.231:8080/BANgToServer/insert_group.jsp";
								String TAG = "ing";
								try {
									URI url = new URI(urlString);

									HttpPost httpPost = new HttpPost();
									httpPost.setURI(url);

									List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
									nameValuePairs.add(new BasicNameValuePair("groupName", groupName));
									nameValuePairs.add(new BasicNameValuePair("member", "test"));

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
						mAdapter.notifyDataSetChanged() ;
						mAdapter.notifyDataSetInvalidated();
						*/
						conntectCheck();
					}
				});

				// 취소버튼 눌렀을 때
				dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dlg.show();

			}

		});
	}

	/**/

	// 웹에서 데이터를 가져오기 전에 먼저 네트워크 상태부터 확인
	public void conntectCheck() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			// fetch data
			// Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();

			task = new GroupAsyncTask(this);
			task.execute("");

		} else {
			// display error
			Toast.makeText(this, "네트워크 상태를 확인하십시오", Toast.LENGTH_SHORT).show();
		}
	}

	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	@Override
	protected void onStart() {
		super.onStart();
		registerDeepLinkReceiver();
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterDeepLinkReceiver();
	}

	/**
	 * User has clicked the 'Invite' button, launch the invitation UI with the
	 * proper title, message, and deep link
	 */
	// [START on_invite_clicked]
	private void onInviteClicked() {
		Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
				.setMessage(getString(R.string.invitation_message))
				.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link))).build();
		startActivityForResult(intent, REQUEST_INVITE);
	}
	// [END on_invite_clicked]

	// [START on_activity_result]
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ",
		// resultCode=" + resultCode);

		if (requestCode == REQUEST_INVITE) {
			if (resultCode == RESULT_OK) {
				// Check how many invitations were sent and log a message
				// The ids array contains the unique invitation ids for each
				// invitation sent
				// (one for each contact select by the user). You can use these
				// for analytics
				// as the ID will be consistent on the sending and receiving
				// devices.
				String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
				// Log.d(TAG, getString(R.string.sent_invitations_fmt,
				// ids.length));
			} else {
				// Sending failed or it was canceled, show failure message to
				// the user
				// showMessage(getString(R.string.send_failed));
				Toast.makeText(getApplicationContext(), "failed..", Toast.LENGTH_LONG);
			}
		}
	}
	// [END on_activity_result]

	/*
	 * private void showMessage(String msg) { ViewGroup container = (ViewGroup)
	 * findViewById(R.id.snackbar_layout); Snackbar.make(container, msg,
	 * Snackbar.LENGTH_SHORT).show(); }
	 */

	/**
	 * There are two broadcast receivers in this application. The first is
	 * ReferrerReceiver, it is a global receiver declared in the manifest. It
	 * receives broadcasts from the Play Store and then broadcasts messages to
	 * the local broadcast receiver, which is registered here. Since the
	 * broadcast is asynchronous, it can occur after the app has started, so
	 * register for the notification immediately in onStart. The Play Store
	 * broadcast should be very soon after the app is first opened, so this
	 * receiver should trigger soon after start
	 */
	// [START register_unregister_launch]
	private void registerDeepLinkReceiver() {
		// Create local Broadcast receiver that starts DeepLinkActivity when a
		// deep link
		// is found
		mDeepLinkReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (AppInviteReferral.hasReferral(intent)) {
					launchDeepLinkActivity(intent);
				}
			}
		};

		IntentFilter intentFilter = new IntentFilter(getString(R.string.action_deep_link));
		LocalBroadcastManager.getInstance(this).registerReceiver(mDeepLinkReceiver, intentFilter);
	}

	private void unregisterDeepLinkReceiver() {
		if (mDeepLinkReceiver != null) {
			LocalBroadcastManager.getInstance(this).unregisterReceiver(mDeepLinkReceiver);
		}
	}

	/**
	 * Launch DeepLinkActivity with an intent containing App Invite information
	 */

	private void launchDeepLinkActivity(Intent intent) {
		// Log.d(TAG, "launchDeepLinkActivity:" + intent);
		Intent newIntent = new Intent(intent).setClass(this, DeepLinkActivity.class);
		startActivity(newIntent);
	}
	// [END register_unregister_launch]

	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	
	/*롱클릭시 삭제*/
	private class ListViewItemLongClickListener implements AdapterView.OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			
			AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
			alertDlg.setTitle(R.string.alert_title_question);
			
			tvGroup = (TextView)view.findViewById(R.id.tvGroupName);
			
			// '예' 버튼이 클릭되면
			alertDlg.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					//////////////////////////////////////////////////////////////////////////
					//////////////////////////////////////////////////////////////////////////
					Thread thread = new Thread() {
						@Override
						public void run() {
							HttpClient httpClient = new DefaultHttpClient();
							String urlString = "http://119.205.252.231:8080/BANgToServer/delete_group.jsp";
							String TAG = "ing";
							try {
								URI url = new URI(urlString);

								HttpPost httpPost = new HttpPost();
								httpPost.setURI(url);
								
								List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
								nameValuePairs.add(new BasicNameValuePair("groupName", tvGroup.getText().toString()));
								
								Log.d(tvGroup.getText().toString(), "hyunhye");

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

					// 아래 method를 호출하지 않을 경우, 삭제된 item이 화면에 계속 보여진다.
					mAdapter.notifyDataSetChanged();
					dialog.dismiss(); // AlertDialog를 닫는다.
					conntectCheck();
					
					 NotificationManager nm = 
						    	(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
					 nm.cancel(1234);
				}
			});

			// '아니오' 버튼이 클릭되면
			alertDlg.setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss(); // AlertDialog를 닫는다.
				}
			});

			alertDlg.setMessage("그룹을 삭제하시겠습니까?");
			alertDlg.show();
			return true;
		}

	}
}