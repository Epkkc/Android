package com.elegion.myfirstapplication.Model;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.elegion.myfirstapplication.db.DataBase;

public class App extends Application {

    private DataBase mMusicDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mMusicDatabase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "music_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public DataBase getMusicDatabase() {
        return mMusicDatabase;
    }
}
