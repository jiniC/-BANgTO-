package com.ohhonghong.notification;

import com.ohhonghong.bangto.R;
import com.ohhonghong.bangto.R.raw;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

public class NotificationBuilder extends Activity{

	private NotificationManager nm;
	private Vibrator mVibe;	
	private MediaPlayer music;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
			// Get Notification Service
			nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			
			PendingIntent intent = PendingIntent.getActivity(
					NotificationBuilder.this, 0, 
					new Intent(NotificationBuilder.this, NotificationMessage.class), 0);
			
			// Create Notification Object
			Notification notification =
				new Notification(android.R.drawable.ic_input_add,
						"µ∑ ∞±±‚", System.currentTimeMillis()+60);
			
			notification.setLatestEventInfo(NotificationBuilder.this, 
					"µ∑", "3¿œ æ»ø° ∞±¿∏ººø‰", intent);
			
			nm.notify(1234, notification);
			mVibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
			mVibe.vibrate(600);
			
			music = MediaPlayer.create(NotificationBuilder.this, R.raw.android);
			music.start();		
			
			 
			Toast.makeText(NotificationBuilder.this, "Notification Registered.", 
					Toast.LENGTH_SHORT).show();

    }

}

	