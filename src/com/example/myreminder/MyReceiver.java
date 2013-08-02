package com.example.myreminder;

import java.text.SimpleDateFormat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

	NotificationManager nm;

	@Override
	public void onReceive(Context context, Intent intent) {

		int notif_id = intent.getExtras().getInt("NotifID");
		String note_data = intent.getExtras().getString("note_data");

		
		SimpleDateFormat outputTimeFormat = new SimpleDateFormat("HH:mm");
		String formattedTime = outputTimeFormat.format(System.currentTimeMillis());
		
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = outputDateFormat.format(System.currentTimeMillis());
		
		nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		CharSequence from = "My Reminder";
		CharSequence message = note_data;
		Intent i = new Intent();
		i.putExtra("note_data", note_data);
		i.putExtra("formattedTime", formattedTime);
		i.putExtra("formattedDate", formattedDate);
		i.setClass(context, AddTime.class);

		PendingIntent contentIntent = PendingIntent.getActivity(context, notif_id, i,0);

		Notification notif = new Notification(R.drawable.ic_launcher,
				"Notification Test...", System.currentTimeMillis());
		notif.flags = Notification.FLAG_AUTO_CANCEL;
		notif.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE |Notification.DEFAULT_SOUND;
		notif.setLatestEventInfo(context, from, message, contentIntent);
		nm.notify(notif_id, notif);

	}

}
