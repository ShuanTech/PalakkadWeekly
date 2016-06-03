package com.shuan.palakkadweekly.push;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.shuan.palakkadweekly.Activities.NewsReadActivity;
import com.shuan.palakkadweekly.Main;
import com.shuan.palakkadweekly.R;


public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1000;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    String msg;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);


        if (!extras.isEmpty() && GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            msg = intent.getStringExtra("msg");
            sendNotification(msg);

        }

        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReciver.completeWakefulIntent(intent);

    }

    private void sendNotification(String msg) {

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.customnotification);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, Main.class);
        intent.putExtra("news", msg);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notify)
                .setContentIntent(pIntent)
                .setContent(remoteViews)
                .setAutoCancel(true);

        remoteViews.setImageViewResource(R.id.notify_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notify_txt, msg);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());


    }
}