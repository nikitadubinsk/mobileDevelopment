package com.dubinsky.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class DB extends RoomDatabase {
    public abstract UserDao userDao();
}
