package com.guifeng.helloandroid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class DynamicReceiver extends BroadcastReceiver {
    private static final String DYNAMICACTION = "com.example.hasee.myapplication2.MyDynamicFilter";
    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals("dynamicFilter")) {    //动作检测

            Bundle bundle = intent.getExtras();
            //TODO:添加Notification部分


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("已收藏")
                    .setContentText(bundle.getString("name"))
                    .setSmallIcon(R.mipmap.empty_star)
                    .setTicker("您有一条新消息")
                    .setAutoCancel(true);
            Intent mIntent = new Intent(context, MainActivity.class);
            mIntent.putExtra("flag",1);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            String channelID = "1";

            String channelName = "channel_name";

            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(channel);

            builder.setChannelId(channelID);


            Notification notify = builder.build();
            notificationManager.notify(0, notify);

        }

    }
}
