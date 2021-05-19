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
    private Thread thread;
    private MainActivity.Callback callback;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());

    }

    public class LocalBinder extends Binder {
        PBService getService() {
            return PBService.this;
        }

        void setCallback(MainActivity.Callback callback) {
            PBService.this.callback = callback;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: binding");

//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                progress += 5;
//                Log.d(TAG, "run: progress is " + progress);
//                handler.sendEmptyMessage(progress);
//                try {
//                    Thread.sleep(200);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((progress + 5) >= 100) {
                    progress = 100;
                } else {
                    progress += 5;
                }
                Log.d(TAG, "run: progress is " + progress);
                if (callback != null) {
                    callback.onTick(progress);
                }
                //handler.sendEmptyMessage(progress);
                handler.postDelayed(this, 400);
            }
        }, 400);
        return binder;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void minusProgress(int minus) {
        if ((progress - minus) >= 0) {
            progress -= minus;
        } else {
            progress = 0;
        }

        Log.d(TAG, "minusProgress: progress is " + progress);
    }

    public void plusProgress(int plus) {
        if ((progress + plus) <= 100) {
            progress += plus;
        } else {
            progress = 100;
        }
        progress += plus;
        Log.d(TAG, "plusProgress: progress is " + progress);
    }

    public void clearProgress() {
        progress = 0;
        Log.d(TAG, "clearProgress: progress is " + progress);
    }

    public void fillProgress() {
        progress = 100;
        Log.d(TAG, "fillProgress: progress is " + progress);
    }

}
