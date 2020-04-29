package com.example.firstwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("first", "onEnabled()");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        super.onUpdate(context, manager, ids);
        Log.e("first", "onUpdate()");
        SharedPreferences pref = context.getSharedPreferences(Config.WIDGET_PREF, Context.MODE_PRIVATE);
        for (int id: ids) {
            updateWidget(context, manager, pref, id);
        }
    }

    @Override
    public void onDeleted(Context context, int[] ids) {
        super.onDeleted(context, ids);
        Log.i("first", "onDeleted()");
        SharedPreferences.Editor edit = context.getSharedPreferences(Config.WIDGET_PREF, Context.MODE_PRIVATE).edit();
        for (int id: ids) {
            edit.remove(Config.WIDGET_TEXT + id);
            edit.remove(Config.WIDGET_COLOR + id);
        }
        edit.apply();
    }


    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.v("first", "onDisabled()");
    }

    static void updateWidget(Context context, AppWidgetManager manager, SharedPreferences pref, int widgetID) {
        String text = pref.getString(Config.WIDGET_TEXT + widgetID, null);
        if (text == null) return;
        int color = pref.getInt(Config.WIDGET_COLOR + widgetID, 0);

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget);
        view.setTextViewText(R.id.textView, text);
        view.setInt(R.id.textView, "setBackgroundColor", color);
        manager.updateAppWidget(widgetID, view);
    }
}
