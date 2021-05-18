package com.example.week5hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private ProgressBar progressBar;
    private Button buttonMinus50;
    private Button buttonPlus20;
    private Button buttonClear;
    private Button buttonFill100;
    private int progress;
    private boolean isBind = false;
    private PBService pbService;
    public static Handler handler;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PBService.LocalBinder binder = (PBService.LocalBinder) service;
            pbService = binder.getService();
            isBind = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.pb_main_ac);
        buttonMinus50 = findViewById(R.id.btn_minus_50_main_ac);
        buttonPlus20 = findViewById(R.id.btn_plus_20_main_ac);
        buttonClear = findViewById(R.id.btn_clear_main_ac);
        buttonFill100 = findViewById(R.id.btn_100_main_ac);



        buttonMinus50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = progressBar.getProgress();
                if ((progress - 50)>0){
                    progress -= 50;

                }else{
                    progress = 0;
                }
                Log.d(TAG, "onClick: progress is " + progress);
                progressBar.setProgress(progress);
            }
        });

        buttonPlus20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = progressBar.getProgress();
                if ((progress + 20) <= 100) {
                    progress+=20;
                } else {
                    progress = 100;
                }
                progressBar.setProgress(progress);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = 0;
                progressBar.setProgress(0);
            }
        });

        buttonFill100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = 100;
                progressBar.setProgress(100);
            }
        });

        Intent intent = new Intent(this, PBService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(TAG, "handleMessage: message " + msg.what);
                progressBar.setProgress(msg.what);
            }
        };


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBind && pbService != null){
            unbindService(serviceConnection);
        }
    }


}