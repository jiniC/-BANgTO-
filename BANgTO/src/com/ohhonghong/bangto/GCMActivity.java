package com.ohhonghong.bangto;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GCMActivity extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        registGCM();
	    }
	    
	    private void registGCM() {
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			
			final String regId = GCMRegistrar.getRegistrationId(this);
			
			if("".equals(regId))   //���� ���̵忡�� regId.equals("")�� �Ǿ� �ִµ� Exception�� ���ϱ� ���� ����
				GCMRegistrar.register(this, GCMIntentService.SEND_ID);
			else
				Log.d("==============", regId);
	    }
}
