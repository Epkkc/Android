package com.example.week5hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Interface {

    private Button button;
    private ProgressBar progressBar;
    private TextView textView;

    private LoadService service;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREF = "SH";
    public static final String STATE = "state";


    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder ibinder) {
            LoadService.MyBinder binder = (LoadService.MyBinder) ibinder;
            service = binder.getService();
            binder.setCallback(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.pb);
        textView = findViewById(R.id.tv);
        button = findViewById(R.id.btn);

        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        button.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            textView.setText(R.string.loading);
            button.setEnabled(false);
            service.startDownload();

        });
        Intent intent = new Intent(this, LoadService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);


    }


    @Override
    protected void onResume() {
        super.onResume();

        if (sharedPreferences.getInt(STATE, 0) == 1) {
            loadingIsInProcess();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (service != null && service.isDownloadIsInProcess()) {
            sharedPreferences.edit().putInt(STATE, 1).apply();
        } else {
            sharedPreferences.edit().putInt(STATE, 0).apply();
        }
    }

    @Override
    public void loadingEnded() {
        progressBar.setVisibility(View.GONE);
        textView.setText(R.string.ready);
        button.setEnabled(true);
    }

    private void loadingIsInProcess() {
        progressBar.setVisibility(View.VISIBLE);
        textView.setText(R.string.loading);
        button.setEnabled(false);
    }
}