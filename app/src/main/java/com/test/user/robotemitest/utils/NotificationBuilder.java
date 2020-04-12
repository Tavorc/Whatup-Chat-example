package com.test.user.robotemitest.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.test.user.robotemitest.R;
import com.test.user.robotemitest.view.activity.ContactChatActivity;

public class NotificationBuilder {

    private static final String NOTIFICATION_CHANNEL_ID = "Default Notifications";
    private static final String NOTIFICATION_CHANNEL_NAME = "Default Notifications";
    private final  static  String ID_CONTACT="id_contact";

    private String mText;
    private int mSmallIcon;
    private Context mContext;
    private Notification mNotification;

    public NotificationBuilder(Context context) {
        this.mContext = context;
    }

    public NotificationBuilder setText(String text) {
        this.mText = text;
        return this;
    }

    public NotificationBuilder setSmallIcon(int resId) {
        this.mSmallIcon = resId;
        return this;
    }

    /**
     *  Function that build the notification
     * @param id
     * @return
     */
    public NotificationBuilder build(int id) {
        Intent intent = new Intent(mContext, ContactChatActivity.class);
        intent.putExtra(ID_CONTACT, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent =
                TaskStackBuilder.create(mContext)
                        .addNextIntentWithParentStack(intent)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        buildChannel((NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE));

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(mSmallIcon)
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentText(mText)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        this.mNotification = notificationBuilder.build();
        return this;
    }

    private void buildChannel(NotificationManager manager) {
        int importance = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            manager.createNotificationChannel(mChannel);
        }
    }


    public void show() {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), mNotification);
    }

}