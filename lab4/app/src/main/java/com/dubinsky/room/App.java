package com.dubinsky.room;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    public static App app;
    public static DB db;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        db = Room.databaseBuilder(this, DB.class, "Контакты").build();
    }
}
