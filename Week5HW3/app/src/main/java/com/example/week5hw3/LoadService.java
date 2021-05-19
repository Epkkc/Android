package com.example.week5hw3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.TimeUtils;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

public class LoadService extends Service {

    private MyBinder binder = new MyBinder();
    private Interface callback;
    private boolean isDownloading = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    private boolean downloadIsInProcess = false;


    public class MyBinder extends Binder {
        public LoadService getService() {
            return LoadService.this;
        }

        public void setCallback(Interface callback) {
            LoadService.this.callback = callback;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    public void startDownload() {
        downloadIsInProcess = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.loadingEnded();
                downloadIsInProcess = false;
            }
        }, 5000);

    }

    public boolean isDownloadIsInProcess() {
        return downloadIsInProcess;
    }
}

