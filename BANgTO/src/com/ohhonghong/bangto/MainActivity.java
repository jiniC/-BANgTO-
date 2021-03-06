package com.ohhonghong.bangto;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {

   private static final String TAG = "ExampleActivity";
   private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

   private ProgressDialog mConnectionProgressDialog;
   private GoogleApiClient mGoogleApiClient;
   private ConnectionResult mConnectionResult;
   
   String personName;
   String email ;

   //Button sign_in_button;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      startActivity(new Intent(this, LoadingActivity.class));
      setContentView(R.layout.login);
      final ActionBar actionBar = getActionBar();
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayShowHomeEnabled(false);
      actionBar.hide();
      mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
            .addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
      // Progress bar to be displayed if the connection failure is not
      // resolved.
      mConnectionProgressDialog = new ProgressDialog(this);
      mConnectionProgressDialog.setMessage("Signing in...");

      //sign_in_button = (Button) findViewById(R.id.sign_in_button);
      findViewById(R.id.sign_in_button).setOnClickListener(this);

   }

   @Override
   protected void onStart() {
      super.onStart();
      mGoogleApiClient.connect();
   }

   @Override
   protected void onStop() {
      super.onStop();
      if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
   }

   @Override
   public void onConnectionFailed(ConnectionResult result) {
      // TODO Auto-generated method stub
      if (mConnectionProgressDialog.isShowing()) {
         // The user clicked the sign-in button already. Start to resolve
         // connection errors. Wait until onConnected() to dismiss the
         // connection dialog.
         if (result.hasResolution()) {
            try {
               result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
            } catch (SendIntentException e) {
            	mGoogleApiClient.connect();
            }
         }
      }
      // Save the result and resolve the connection failure upon a user click.
      mConnectionResult = result;
   }

   @Override
   protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
      if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK) {
         mConnectionResult = null;
         mGoogleApiClient.connect();
      }
   }

   @Override
   public void onConnected(Bundle connectionHint) {
      // TODO Auto-generated method stub
       //String accountName = mPlusClient.getAccountName();
       //Toast.makeText(this, accountName + " is connected.",
       //Toast.LENGTH_LONG).show();
	   
	   /*구글 정보 가져오기*/
	   Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
	   personName = currentPerson.getDisplayName();
	   email = Plus.AccountApi.getAccountName(mGoogleApiClient);
	   Log.d(personName, "personName");
	   Log.d(email, "email");

      Intent intent = new Intent(this, PersonalInfoActivity.class);
      intent.putExtra("userName",personName);
      intent.putExtra("email", email);
      startActivity(intent);
   }

   @Override
   public void onConnectionSuspended(int cause) {
      // TODO Auto-generated method stub
	   mGoogleApiClient.connect();
   }

   @Override
   public void onClick(View view) {
      // TODO Auto-generated method stub

      if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnected()) {
         if (mConnectionResult == null) {
            mConnectionProgressDialog.show();
         } else {
            try {
               mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
            } catch (SendIntentException e) {
               // Try connecting again.
               mConnectionResult = null;
               mGoogleApiClient.connect();
            }
         }
      }
   }
}