package com.example.firstwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Config extends Activity {

    int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;

    public final static String WIDGET_PREF = "WIDGET_PREF";
    public final static String WIDGET_TEXT = "WIDGET_TEXT_";
    public final static String WIDGET_COLOR = "WIDGET_COLOR_";

    @Override
    public void onCreate(Bundle params) {
        super.onCreate(params);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_CANCELED, resultValue);
        setContentView(R.layout.config);
    }

    public void onClick(View view) {
        EditText editText = findViewById(R.id.editText);
        RadioGroup radio = findViewById(R.id.radio);
        int color = 0;
        switch (radio.getCheckedRadioButtonId()) {
            case R.id.radioRed:
                color = R.color.red;
                break;
            case R.id.radioOrange:
                color = R.color.orange;
                break;
            case R.id.radioYellow:
                color = R.color.yellow;
                break;
            case R.id.radioGreen:
                color = R.color.green;
                break;
            case R.id.radioLightBlue:
                color = R.color.light_blue;
                break;
            case R.id.radioDarkBlue:
                color = R.color.dark_blue;
                break;
            case R.id.radioViolet:
                color = R.color.violet;
                break;
        }

        SharedPreferences pref = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(WIDGET_TEXT + widgetId, editText.getText().toString());
        edit.putInt(WIDGET_COLOR + widgetId, getResources().getColor(color));
        edit.apply();

        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        Widget.updateWidget(this, manager, pref, widgetId);

        setResult(RESULT_OK, resultValue);
        finish();

    }
}
