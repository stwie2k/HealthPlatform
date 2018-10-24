package com.guifeng.helloandroid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

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

       //实现动态widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
          String _name=bundle.getString("name");
            remoteViews.setTextViewText(R.id.appwidget_text, "已收藏 "+_name);

            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("flag",1);
            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_image, pi); //设置点击事件

            ComponentName componentName = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(componentName, remoteViews);



        }

    }
}
