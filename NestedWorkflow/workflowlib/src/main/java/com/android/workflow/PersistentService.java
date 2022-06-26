package com.android.workflow;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.android.workflow.R;


public abstract class PersistentService extends Service
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if(currentapiVersion > Build.VERSION_CODES.LOLLIPOP_MR1)
			makePersistentPie();
		else
			makePersistent();
	}

	@TargetApi(27)
	private void makePersistentPie()
	{
		createChannels();
		Notification.Builder builder =  new Notification.Builder(this)
				.setChannelId("technogym_id")
				.setOngoing(true)
				.setContentTitle(null)
				.setSmallIcon(R.drawable.ic_launcher);
		startForeground( 99, builder.build() );
	}

	@Override
	public boolean stopService(Intent name)
	{
		stopForeground(true);
		return super.stopService(name);
	}

	private void makePersistent()
	{
		Notification.Builder nb = new Notification.Builder(this);
		nb.setSmallIcon(0);
		nb.setContentTitle(null);
		nb.setWhen(System.currentTimeMillis());

	//    Notification note = nb.
	    		
		Notification note = nb.getNotification() ;// 	new Notification( 0, null, System.currentTimeMillis() );
	    note.flags |= Notification.FLAG_NO_CLEAR;
	    startForeground( 99, note );		
	}

	@TargetApi(27)
	private void createChannels() {
		// create android channel
		NotificationChannel androidChannel = new NotificationChannel("technogym_id", "channel_technogym", NotificationManager.IMPORTANCE_DEFAULT);
		// Sets whether notifications posted to this channel should display notification lights
		androidChannel.enableLights(true);
		// Sets whether notification posted to this channel should vibrate.
		androidChannel.enableVibration(true);
		// Sets the notification light color for notifications posted to this channel
		androidChannel.setLightColor(Color.GREEN);
		// Sets whether notifications posted to this channel appear on the lockscreen or not
		androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		nm.createNotificationChannel(androidChannel);
	}

}
