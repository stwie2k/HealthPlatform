package com.guifeng.helloandroid;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    final String WIDGETSTATICACTION="android.appwidget.action.APPWIDGET_UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //
        //        CharSequence widgetText = context.getString(R.string.appwidget_text);
        //        // Construct the RemoteViews object




        RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局

        Intent i = new Intent(context, MainActivity.class);

        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        updateView.setOnClickPendingIntent(R.id.widget_image, pi); //设置点击事件
        ComponentName me = new ComponentName(context, NewAppWidget.class);
        appWidgetManager.updateAppWidget(me, updateView);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);


        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onReceive(Context context, Intent intent ){
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Bundle bundle = intent.getExtras();
        String name=bundle.getString("name");
       if(name.equals("null"))return;

        if(intent.getAction().equals(WIDGETSTATICACTION)){

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            remoteViews.setTextViewText(R.id.appwidget_text, "今日推荐 "+name);

            Intent i = new Intent(context, Info.class);
            i.putExtras(bundle);
            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_image, pi); //设置点击事件

            ComponentName componentName = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(componentName, remoteViews);


        }
    }

}

