package com.example.week6hw3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private Button downloadButton;
    private ImageView imageView;
    private Button showButton;
    private DownloadManager downloadManager;
    private long downloadID;
    private String title;
    private String textURl;
    private static final int Permission_request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        downloadButton = findViewById(R.id.download_btn);
        imageView = findViewById(R.id.img);
        showButton = findViewById(R.id.show_btn);
        registerReceiver(downloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        imageView.setVisibility(View.GONE);
        showButton.setEnabled(false);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    textURl = input.getText().toString();
                    if (textURl.endsWith(".jpeg") || textURl.endsWith(".png") || textURl.endsWith(".bmp")) {
                        // Загрузка картинка из интерента по URL

                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(textURl));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setAllowedOverRoaming(false);
                        title = textURl.split("/")[textURl.split("/").length - 1].split("\\.")[0];
                        request.setTitle(title);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
                        downloadID = downloadManager.enqueue(request);


                        showButton.setEnabled(true);
                    } else {
                        Toast.makeText(MainActivity.this, "Input correct URL", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "There is no permission", Toast.LENGTH_SHORT).show();
                }

            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вставить загруженную картинку в imageView
               //https://tipworker.com/img/cache/company/logo/15484/15484-middle.png
                File file1 = new File("/storage/emulated/0/Download/" + title);
                imageView.setImageURI(Uri.fromFile(file1));
                imageView.setVisibility(View.VISIBLE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_0_1) {
            askWriteExternalPermissionIfNeeded();

        }


    }

    private void askWriteExternalPermissionIfNeeded() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Показыть объяснение
                new AlertDialog.Builder(this).setMessage("Need Permission").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Permission_request_code);
                    }
                }).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Permission_request_code);
            }
        }
    }

    private BroadcastReceiver downloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadID == id) {
                Toast.makeText(MainActivity.this, "Download is completed", Toast.LENGTH_SHORT).show();

            }
        }
    };


    @Override
    protected void onDestroy() {
        unregisterReceiver(downloadComplete);

        super.onDestroy();
    }
}