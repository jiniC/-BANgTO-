package com.ohhonghong.bangto;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.ohhonghong.adapter.GroupAdapter;
import com.ohhonghong.invite.DeepLinkActivity;
import com.ohhonghong.utility.MyAsyncTask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class GroupMenuActivity extends Activity {

	public MyAsyncTask task;
	public ListView mListView;
	public GroupAdapter mAdapter;

	// private ListView mListView = null;
	// private GroupAdapter mAdapter = null;
	View dlgview;
	ImageButton groupAddButton;
	EditText etGroupName, etMemberName;

	Typeface childFont;

	private static final int REQUEST_INVITE = 0;
	// Local Broadcast receiver for receiving invites
	private BroadcastReceiver mDeepLinkReceiver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupmenu);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();
		
		groupAddButton = (ImageButton) findViewById(R.id.groupAddButton);
	
		mListView = (ListView) findViewById(R.id.listview);
		mAdapter = new GroupAdapter(this);
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

				Intent intent = new Intent(getApplicationContext(), TabMainActivity.class);

				// 위에서 만든 Bundle을 인텐트에 넣는다.
				// intent.putExtras(extras);
				// 액티비티를 생성한다.
				startActivity(intent);
			}
		});

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
						String groupName = etGroupName.getText().toString();
						// mAdapter.addItem(groupName);
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

	// 웹에서 데이터를 가져오기 전에 먼저 네트워크 상태부터 확인
	public void conntectCheck() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			// fetch data
			// Toast.makeText(this,"네트워크 연결중입니다.", Toast.LENGTH_SHORT).show();

			task = new MyAsyncTask(this);
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
}