package com.example.week5hw2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

public class PBService extends Service {
    public static final String TAG = "PBService";

    private IBinder binder = new LocalBinder();
    private Handler handler;
    private int progress = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = MainActivity.handler;

    }

    public class LocalBinder extends Binder{
         PBService getService(){
             return PBService.this;
         }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress += 5;
                Log.d(TAG, "run: progress is " + progress);
                handler.sendEmptyMessage(progress);
                handler.postDelayed(this, 200);
            }
        }, 200);
        return binder;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
