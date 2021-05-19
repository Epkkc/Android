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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private ProgressBar progressBar;
    private boolean isFilled = false;
    private boolean isBind = false;
    private PBService pbService;
    public static Handler handler;

    private final Callback callback = (count) -> {
        progressBar.setProgress(count);
        if (isFilled){
            if (count != 100){
                isFilled = false;
            }
        } else{
            if (count == 100){
                isFilled = true;
                showToast("Progress is " + count);
            }
        }
    };

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PBService.LocalBinder binder = (PBService.LocalBinder) service;
            pbService = binder.getService();
            binder.setCallback(callback);
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
        Button buttonMinus50 = findViewById(R.id.btn_minus_50_main_ac);
        Button buttonPlus20 = findViewById(R.id.btn_plus_20_main_ac);
        Button buttonClear = findViewById(R.id.btn_clear_main_ac);
        Button buttonFill100 = findViewById(R.id.btn_100_main_ac);


        buttonMinus50.setOnClickListener(this::handleMinusClick);

        buttonPlus20.setOnClickListener(v -> pbService.plusProgress(20));

        buttonClear.setOnClickListener(v -> pbService.clearProgress());

        buttonFill100.setOnClickListener(v -> pbService.fillProgress());

        Log.d(TAG, "onCreate: before binding");
        Intent intent = new Intent(this, PBService.class);

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//        handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                Log.d(TAG, "handleMessage: message " + msg.what);
//
//                if (msg.what == 100) {
//                    if (!isFilled) {
//                        isFilled = true;
//                        showToast("Progress is " + msg.what);
//                        progressBar.setProgress(msg.what);
//                    }
//                } else {
//                    isFilled = false;
//                    progressBar.setProgress(msg.what);
//                }
//
//            }
//        };


    }

    private void handleMinusClick(View view) {
        pbService.minusProgress(50);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBind && pbService != null) {
            unbindService(serviceConnection);
        }
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }


    public interface Callback {
        void onTick(int count);
    }
}