package com.elegion.data.di;

import android.arch.persistence.room.Room;
import android.util.Log;

import com.elegion.data.database.BehanceDao;
import com.elegion.data.AppDelegate;
import com.elegion.data.Storage;
import com.elegion.data.database.BehanceDatabase;

import toothpick.config.Module;

/**
 * Created by tanchuev on 23.04.2018.
 */


public class AppModule extends Module {

    public static final String TAG = AppModule.class.getSimpleName();

    private final AppDelegate mApp;

    private BehanceDatabase database;

    private BehanceDao dao;

    public AppModule(AppDelegate mApp) {
        this.mApp = mApp;
        database = provideDatabase();
        dao = provideBehanceDao();
        Log.i(TAG, "AppModule: INIT APP");
        bind(AppDelegate.class).toInstance(mApp);
        bind(Storage.class).toInstance(provideStorage());
        bind(BehanceDatabase.class).toInstance(database);
        bind(BehanceDao.class).toInstance(dao);
        bind(String.class).withName("TestName").toInstance("TestFromAppModule");
    }


    AppDelegate provideApp() {
        return mApp;
    }


    BehanceDatabase provideDatabase() {
        Log.i(TAG, "AppModule: PROVIDE DB");

        return Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();
    }


    BehanceDao provideBehanceDao() {
        return database.getBehanceDao();
    }


    Storage provideStorage() {
        return new Storage(dao);
    }
}
