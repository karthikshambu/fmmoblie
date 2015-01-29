package com.strata.firstmilebooks.gcm_registration;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.strata.firstmilebooks.Config;
import com.strata.firstmilebooks.R;
import com.strata.firstmilebooks.activity.MainActivity;

public class GCMNotificationIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	public String title_id;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
    boolean isActivityFound = false;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningTaskInfo> services = activityManager
	            .getRunningTasks(Integer.MAX_VALUE);

	    if (services.get(0).topActivity.getPackageName().toString()
	            .equalsIgnoreCase(getApplicationContext().getPackageName().toString())) {
	        isActivityFound = true;
	    }

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				updateMyActivity(getApplicationContext(), extras);
				if(!isActivityFound){
					for (int i = 0; i < 3; i++) {
						Log.i(TAG,
								"Working... " + (i + 1) + "/5 @ "
										+ SystemClock.elapsedRealtime());
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
						}

					}
					Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
					title_id = (String) extras.get("title_id");
					sendNotification("" + extras.get(Config.MESSAGE_KEY));
				}

				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
		Log.d(TAG, "Preparing to send notification...: " + msg);
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.putExtra("title_id", title_id); // <-- HERE I PUT THE
															// EXTRA VALUE
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
		// new Intent(this, PageZero.class), PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Letz Dine")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		Uri notification = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	     Ringtone ring = RingtoneManager.getRingtone(getApplicationContext(), notification);
	     ring.play();
		Log.d(TAG, "Notification sent successfully.");
	}

	static void updateMyActivity(Context context, Bundle message) {

	    Intent intent = new Intent("com.strata.firstmilebooks.chat");

	    //put whatever data you want to send, if any
	    intent.putExtras(message);

	    //send broadcast
	    context.sendBroadcast(intent);
	}
}
