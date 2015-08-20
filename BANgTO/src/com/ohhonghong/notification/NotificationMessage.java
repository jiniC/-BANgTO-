package com.ohhonghong.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationMessage extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    TextView tv = new TextView(this);
	    tv.setText("위의 Notification이 사라진 것을 확인하셨나요? :)");
	    setContentView(tv);
	    
	    NotificationManager nm = 
	    	(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    
	    // Cancel Notification
	    nm.cancel(1234);
	}

}
