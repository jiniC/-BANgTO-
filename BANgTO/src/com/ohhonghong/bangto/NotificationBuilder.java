package com.ohhonghong.bangto;

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
import android.util.Log;
import android.widget.Toast;

public class NotificationBuilder extends Activity{

	private NotificationManager nm;
	private Vibrator mVibe;	
	private MediaPlayer music;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("a", "hae2");
			// Get Notification Service
			nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			
			PendingIntent intent = PendingIntent.getActivity(
					NotificationBuilder.this, 0, 
					new Intent(NotificationBuilder.this, GroupMenuActivity.class), 0);
			
			// Create Notification Object
			Notification notification =
				new Notification(R.drawable.pushicon,
						"µ· °±±â", System.currentTimeMillis()+60);
			
			
			notification.setLatestEventInfo(NotificationBuilder.this, 
					"BANgTO °øÁö", "¾ÆÁ÷ °±¾Æ¾ß ÇÒ µ·ÀÌ ÀÖ½À´Ï´Ù.", intent);
			
			
			nm.notify(1234, notification);
			mVibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
			mVibe.vibrate(600);
			
			music = MediaPlayer.create(NotificationBuilder.this, R.raw.android);
			music.start();		
			
			 
			Toast.makeText(NotificationBuilder.this, "Notification Registered.", 
					Toast.LENGTH_SHORT).show();

    }

}

	